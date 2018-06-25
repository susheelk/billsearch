package tech.susheelkona.billsearch.services.implementations;

import tech.susheelkona.billsearch.model.Person;
import tech.susheelkona.billsearch.model.legislation.Bill;
import tech.susheelkona.billsearch.model.legislation.Event;
import tech.susheelkona.billsearch.model.legislation.Publication;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import tech.susheelkona.billsearch.services.BillService;
import tech.susheelkona.billsearch.services.cache.Cache;
import tech.susheelkona.billsearch.services.XmlHttpService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import tech.susheelkona.billsearch.services.cache.CachedEntity;

/**
 * @author Susheel Kona
 */
public class LegisinfoBillService extends XmlHttpService implements BillService {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void update() throws Exception {
        log.info("Downloading Bills...");
        long timeStart = System.currentTimeMillis();
        List<Bill> list = new ArrayList<>();
//        String url = URLEncoder.encode(address, "UTF-8");
        Document document = getDocument(XmlHttpService.LEGISINFO_LATEST_ORDER);


        NodeList nList = document.getElementsByTagName("Bill");
        System.out.println(nList.getLength());
        System.out.println("Query Time: "+(System.currentTimeMillis()-timeStart));
        timeStart = System.currentTimeMillis();

        for (int i = 0; i < nList.getLength(); i++) {

            Node nNode = nList.item(i);

            if(nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nNode;
                Bill bill = new Bill();
                bill.setId(Integer.parseInt(element.getAttribute("id")));

                // Last updated
                String dateString = element.getAttribute("lastUpdated").substring(0, 10);
                bill.setDateLastUpdated(dateFormat.parse(dateString));

                // Date introduced
                Element eDateIntroduced = (Element) element.getElementsByTagName("BillIntroducedDate").item(0);
                dateString = eDateIntroduced.getTextContent().substring(0, 10);
                bill.setDateIntroduced(dateFormat.parse(dateString));

                // Session number
                Element eSession = (Element) element.getElementsByTagName("ParliamentSession").item(0);
                String session = eSession.getAttribute("parliamentNumber")+ "-"+
                        eSession.getAttribute("sessionNumber");
                bill.setSession(session);

                // Bill Number
                Element eNumber = (Element) element.getElementsByTagName("BillNumber").item(0);
                String number = eNumber.getAttribute("prefix")+"-"+
                        eNumber.getAttribute("number");
                bill.setNumber(number);

                // Bill Title
                Element eTitles = (Element) element.getElementsByTagName("BillTitle").item(0);
                Element enTitle = (Element) eTitles.getElementsByTagName("Title").item(0);
                bill.setTitle(enTitle.getTextContent());

                // Short Title
                Element esTitles = (Element) element.getElementsByTagName("ShortTitle").item(0);
                Element ensTitle = (Element) esTitles.getElementsByTagName("Title").item(0);
                bill.setShortTitle(ensTitle.getTextContent());

                // Bill Type
                Element eType = (Element)((Element) element.getElementsByTagName("BillType").item(0))
                        .getElementsByTagName("Title").item(0);
                bill.setBillType(eType.getTextContent());

                // Bill Sponsor
                Person sponsor = new Person();
                Element eSponsor = (Element) element.getElementsByTagName("SponsorAffiliation").item(0);
                Element ePerson = (Element) eSponsor.getElementsByTagName("Person").item(0);
                Element efName = (Element) ePerson.getElementsByTagName("FirstName").item(0);
                Element elName = (Element) ePerson.getElementsByTagName("LastName").item(0);
                sponsor.setFirstName(efName.getTextContent());
                sponsor.setLastName(elName.getTextContent());
                bill.setSponsor(sponsor);

                // Publications
                List<Publication> publications = new ArrayList<>();
                Element ePubs = (Element) element.getElementsByTagName("Publications").item(0);
                NodeList nPubsList = ePubs.getElementsByTagName("Publication");
                for (int x = 0; x < nPubsList.getLength(); x++) {
                    Node nPub = nPubsList.item(x);
                    Element ePub = (Element) nPub;

                    Publication publication = new Publication();
                    int id = Integer.parseInt(ePub.getAttribute("id"));
                    publication.setId(id);

                    Element eTitle = (Element) ePub.getElementsByTagName("Title").item(0);
                    String title = eTitle.getTextContent();
                    publication.setTitle(title);

                    // Publication urls
                    Element ePubFile = (Element) ePub.getElementsByTagName("PublicationFile").item(0);
                    String path = ePubFile.getAttribute("relativePath");
                    path = path.substring(2, path.length());
                    path = "http://www."+path;
                    publication.setUrl(path);

                    publications.add(publication);
                }
                bill.setPublications(publications);

                // Events
                Element eEvents = (Element) element.getElementsByTagName("Events").item(0);

                // All Events
                Element eAllEvents = (Element) eEvents.getElementsByTagName("LegislativeEvents").item(0);
                NodeList nEventsList = eAllEvents.getElementsByTagName("Event");
                List<Event> allEventsList = new ArrayList<>();
                for (int x = 0; x < nEventsList.getLength(); x++) {
                    Element eEvent = (Element) nEventsList.item(x);
                    allEventsList.add(extractEvent(eEvent));
                }
                bill.setEvents(allEventsList);

                // Latest Event
                Element eLatestEvent = (Element) eEvents.getElementsByTagName("LastMajorStageEvent").item(0);
                Element eEventN = (Element) eLatestEvent.getElementsByTagName("Event").item(0);
                bill.setLastMajorEvent(extractEvent(eEventN));
                if(bill.getLastMajorEvent().getStatus().matches("Royal Assent")) {
                    bill.setLaw(true);
                }

                bill.setResourceUri("/bills/"+bill.getId());
                list.add(bill);
            }
        }

        System.out.println("Parse Time: "+(System.currentTimeMillis()-timeStart));
//        Collections.reverse(list);
        timeStart = System.currentTimeMillis();
//        System.out.println("Reverse Time: "+(System.currentTimeMillis()-timeStart));

        Cache.updateBills(list);
        log.info("BILL UPDATE SUCCESS");
    }

    private Event extractEvent(Element eEvent) throws ParseException {
        Event event = new Event();
        event.setId(Integer.parseInt(eEvent.getAttribute("id")));

        Element eEventStatus = (Element)(eEvent.getElementsByTagName("Status").item(0));
        Element eEventTitle = (Element) (eEventStatus.getElementsByTagName("Title").item(0));
        event.setStatus(eEventTitle.getTextContent());

//        if(event.getStatus().matches("Royal Assent")) {
//            bill.setLaw(true);
//        }

        // Chamber
        String sChamber = eEvent.getAttribute("chamber");
        event.setChamber(sChamber.matches("HOC") ? "House of Commons" : "Senate");

        // Event date
        String sDate = eEvent.getAttribute("date").substring(0, 10);
        event.setDate(dateFormat.parse(sDate));

        return event;
    }

    @Override
    public CachedEntity<Bill> getAll() throws Exception {
        return Cache.getBills();
    }
}
