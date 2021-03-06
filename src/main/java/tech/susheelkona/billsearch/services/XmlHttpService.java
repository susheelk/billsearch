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

import static tech.susheelkona.billsearch.services.Urls.LEGISINFO_VOTES;

/**
 * @author Susheel Kona
 */
public abstract class XmlHttpService {

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

        deployed=true;

        if(!deployed && url == Urls.LEGISINFO_LATEST_ORDER){
            document = builder.parse(new File("data\\bills.xml"));
        } else if (!deployed && url == Urls.LEGISINFO_VOTES) {
            document = builder.parse(new File("data\\votes.xml"));
        }
        else {
            document = builder.parse(new URL(url).openStream());
        }

        document.getDocumentElement().normalize();
//        System.out.println(document.getDocumentElement().getNodeName());
        return document;
    }

}
