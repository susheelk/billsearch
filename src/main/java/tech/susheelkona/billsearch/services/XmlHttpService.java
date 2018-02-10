package tech.susheelkona.billsearch.services;

import org.w3c.dom.Document;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;

/**
 * @author Susheel Kona
 */
public abstract class XmlHttpService {

    public static final String LEGISINFO_LATEST_ORDER = "https://www.parl.ca/LegisInfo/Home.aspx?ParliamentSession=42-1&SortBy=BillLatestEventStartTime&SortDir=DESC&Language=E&download=xml";

    public Document getDocument(String url) throws Exception{
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new URL(url).openStream());
//        Document document = builder.parse(new File("download.xml"));
        document.getDocumentElement().normalize();
//        System.out.println(document.getDocumentElement().getNodeName());
        return document;
    }
}
