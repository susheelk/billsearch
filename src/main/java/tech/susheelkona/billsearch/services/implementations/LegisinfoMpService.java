package tech.susheelkona.billsearch.services.implementations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import tech.susheelkona.billsearch.model.CabinetMember;
import tech.susheelkona.billsearch.model.Person;
import tech.susheelkona.billsearch.services.MpService;
import tech.susheelkona.billsearch.services.XmlHttpService;
import tech.susheelkona.billsearch.services.cache.Cache;
import tech.susheelkona.billsearch.services.cache.CachedEntity;

import java.util.ArrayList;
import java.util.List;

public class LegisinfoMpService extends XmlHttpService implements MpService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public void update() throws Exception{
        log.info("Downloading Cabinet...");
        List<CabinetMember> list = getMinisters();
        Cache.updateCabinetMembers(list);
        log.info("CABINET UPDATE SUCCESS");
    }

    private List<CabinetMember> getMinisters() throws Exception {
        long timeStart = System.currentTimeMillis();
        Document document = getDocument(XmlHttpService.LEGISINFO_MINISTERS);
        NodeList nodeList = document.getElementsByTagName("Minister");
        System.out.println("Minister Query time: "+(System.currentTimeMillis()-timeStart));
        timeStart = System.currentTimeMillis();
        List<CabinetMember> list = new ArrayList<>();

        for(int i = 0; i < nodeList.getLength(); i++){
            Node node = nodeList.item(i);

            if(node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                CabinetMember member = new CabinetMember();

                String order = element.getElementsByTagName("OrderOfPrecedence").item(0).getTextContent();
                member.setOrderOfPrecedence(Integer.parseInt(order));

                String firstName = element.getElementsByTagName("PersonOfficialFirstName").item(0).getTextContent();
                member.setFirstName(firstName);
                String lastName = element.getElementsByTagName("PersonOfficialLastName").item(0).getTextContent();
                member.setLastName(lastName);

                member.setPosition(element.getElementsByTagName("Title").item(0).getTextContent());
                member.setTitle("MP");
                list.add(member);
            }
        }
        System.out.println("Minister Parse time: "+(System.currentTimeMillis()-timeStart));
        return list;
    }

    @Override
    public CachedEntity<CabinetMember> getCabinetMembers() {
        CachedEntity<CabinetMember> cache = Cache.getCabinetMembers();
        CachedEntity<CabinetMember> copy = new CachedEntity<>();
        copy.setData(new ArrayList<>(cache.getData()));
        copy.setLastUpdated(cache.getLastUpdated());
        return copy;
    }
}
