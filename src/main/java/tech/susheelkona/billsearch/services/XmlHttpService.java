package tech.susheelkona.billsearch.services;

import org.w3c.dom.Document;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.net.URL;

/**
 * @author Susheel Kona
 */
public abstract class XmlHttpService {

    public static final String LEGISINFO_LATEST_ORDER = "https://www.parl.ca/LegisInfo/Home.aspx?ParliamentSession=42-1&SortBy=BillLatestEventStartTime&SortDir=DESC&Language=E&download=xml";

    public Document getDocument(String url) throws Exception{

        // From Stack Overflow, suppresses SSL security check
        TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                    public void checkClientTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                    public void checkServerTrusted(
                            java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }};
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {
        }
        //////////////////


        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document;

        boolean deployed;
        try {
            deployed = System.getenv("DEPLOYED").matches("Y");
        } catch (Exception e) {
            deployed = false;
        }

//        deployed=true;

        if(deployed){
            document = builder.parse(new URL(url).openStream());
        } else {
            document = builder.parse(new File("download.xml"));
        }

        document.getDocumentElement().normalize();
//        System.out.println(document.getDocumentElement().getNodeName());
        return document;
    }
}
