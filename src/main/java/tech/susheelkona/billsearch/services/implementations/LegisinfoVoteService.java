package tech.susheelkona.billsearch.services.implementations;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import tech.susheelkona.billsearch.model.legislation.Ballot;
import tech.susheelkona.billsearch.model.legislation.Bill;
import tech.susheelkona.billsearch.model.legislation.Vote;
import tech.susheelkona.billsearch.services.BillService;
import tech.susheelkona.billsearch.services.FileService;
import tech.susheelkona.billsearch.services.VoteService;
import tech.susheelkona.billsearch.services.XmlHttpService;
import tech.susheelkona.billsearch.services.cache.Cache;
import tech.susheelkona.billsearch.services.cache.CachedEntity;

import java.text.SimpleDateFormat;
import java.util.*;


public class LegisinfoVoteService extends XmlHttpService implements VoteService {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private int numberVotes;

    @Autowired
    private BillService billService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private FileService fileService;

    @Override
    public void update() throws Exception {
        log.info("Downloading Votes...");
        Cache.updateVotes(getVotes());
        log.info("VOTE UPDATE SUCCESS!");
    }

    private List<Vote> getVotes() throws Exception{

        long timeStart = System.currentTimeMillis();
        Document document = getDocument(XmlHttpService.LEGISINFO_VOTES);
        NodeList nList = document.getElementsByTagName("VoteParticipant");
        numberVotes = nList.getLength();
        log.info("Votes Length: "+numberVotes);
        List<Vote> list = new ArrayList<>(numberVotes);
        System.out.println("Vote List Query Time: "+(System.currentTimeMillis()-timeStart));
        timeStart = System.currentTimeMillis();

        for (int i = 0; i < numberVotes; i++) {
            Node nNode = nList.item(i);

            if(nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nNode;
                Vote vote = new Vote();

                String dateString = element.getElementsByTagName("DecisionEventDateTime").item(0).getTextContent();
                vote.setDate(dateFormat.parse(dateString));

                String sId = element.getElementsByTagName("DecisionDivisionNumber").item(0).getTextContent();
                vote.setId(Integer.parseInt(sId));

                String sResult = element.getElementsByTagName("DecisionResultName").item(0).getTextContent();
                vote.setResult(sResult);

                String sTitle = element.getElementsByTagName("DecisionDivisionSubject").item(0).getTextContent();
                vote.setTitle(sTitle);

                String sYeas = element.getElementsByTagName("DecisionDivisionNumberOfYeas").item(0).getTextContent();
                vote.setYeas(Integer.parseInt(sYeas));

                String sNays = element.getElementsByTagName("DecisionDivisionNumberOfNays").item(0).getTextContent();
                vote.setNays(Integer.parseInt(sNays));

                String sBill = element.getElementsByTagName("BillNumberCode").item(0).getTextContent();
                Bill bill = billService.getByNumber(sBill);
                vote.setBillUrl(bill == null ? null : bill.getResourceUri());

                vote.setBallots(getBallotForVote(vote.getId()));

                vote.setResourceUri("/votes/"+vote.getId());

                if(fileService.voteDescriptionExists(vote.getId())){
                    vote.setDescription(fileService.getVoteDescriptionFromDisk(vote.getId()));
                } else {
                    vote.setDescription(scrapeDescription(vote.getId()));
                    fileService.persistVoteDescription(vote.getId(), vote.getDescription());
                }
//                System.out.println(vote.getDescription());
                list.add(vote);

            }
        }
        Collections.reverse(list);
        System.out.println("Vote List Parse time: "+(System.currentTimeMillis()-timeStart));
        return list;
    }

//    private void attach

    private List<Ballot> getBallotForVote(int id) throws Exception {
        if (fileService.ballotExists(id)){
            return fileService.getBallotsFromDisk(id);
        }
        List<Ballot> ballot = getBallotFromLegisinfo(id);
        fileService.persistBallots(id, ballot);
        return ballot;
    }


    private List<Ballot> getBallotFromLegisinfo(int id) throws Exception{
        Document document = getDocument(XmlHttpService.LEGISINFO_BALLOT+id);
        NodeList nList = document.getElementsByTagName("VoteParticipant");
        List<Ballot> ballots = new ArrayList<>();

        for (int i = 0; i < nList.getLength(); i++) {
            Node node = nList.item(i);

            if(node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
//                Person person = new Person();


                int pid = Integer.parseInt(element.getElementsByTagName("MemberId").item(0).getTextContent());

                String fName = element.getElementsByTagName("FirstName").item(0).getTextContent();
                String lName = element.getElementsByTagName("LastName").item(0).getTextContent();

                String vote = element.getElementsByTagName("VoteValueName").item(0).getTextContent();

                Ballot ballot = new Ballot(pid, fName+" "+lName, vote);
                ballots.add(ballot);
            }
        }

        return ballots;
    }

    private String scrapeDescription(int id){
        WebClient client = new WebClient();
//        client.getOptions().setCssEnabled(false);
        client.getOptions().setJavaScriptEnabled(false);
        try {
            HtmlPage page = client.getPage(XmlHttpService.LEGISINFO_VOTE_HTML+id+"/");
            HtmlElement element = page.getFirstByXPath("//div[@class='custom-message-container MainContentContainer voteDetailsText']");
//            element = element.getFirstByXPath("//div[@class='container main-content']");
//            element = element.getFirstByXPath("//div[@class='vote-details-main-content']");
            return element.getTextContent() != null ? element.getTextContent() : "";
        } catch (NullPointerException e){
            return "";
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public CachedEntity<Vote> getAll() throws Exception {
        CachedEntity<Vote> cache =  Cache.getVotes();
        CachedEntity<Vote> copy = new CachedEntity<>();
        copy.setLastUpdated(cache.getLastUpdated());
        copy.setData(new ArrayList<>(cache.getData()));
        return copy;
    }
}
