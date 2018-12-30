package tech.susheelkona.billsearch.services.implementations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import tech.susheelkona.billsearch.model.NewsItem;
import tech.susheelkona.billsearch.model.legislation.Bill;
import tech.susheelkona.billsearch.services.BillService;
import tech.susheelkona.billsearch.services.NewsService;
import tech.susheelkona.billsearch.services.Updatable;
import tech.susheelkona.billsearch.services.XmlHttpService;
import tech.susheelkona.billsearch.services.cache.Cache;
import tech.susheelkona.billsearch.services.cache.CachedEntity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class LegisinfoNewsService extends XmlHttpService implements NewsService {

    @Autowired
    BillService billService;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public void update() throws Exception {
        log.info("Downloading News Items....");
        long timeStart = System.currentTimeMillis();
        Document document = getDocument(XmlHttpService.LEGISINFO_UPDATES);
        NodeList nList = document.getElementsByTagName("item");
        log.info(nList.getLength()+" news items found");
        List<NewsItem> list = new ArrayList<>(nList.getLength());
        System.out.println("News Query Time: "+(System.currentTimeMillis()-timeStart));
        timeStart = System.currentTimeMillis();

        for (int i = 0; i < nList.getLength() ; i++) {
            Node node = nList.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                NewsItem newsItem = new NewsItem();

                Element eTitle = (Element) element.getElementsByTagName("title").item(0);
                String fullTitle = eTitle.getTextContent();
                int comInd = fullTitle.indexOf(',');
                String title = fullTitle.substring(comInd+2);
                String billNum = fullTitle.substring(0, comInd);

                Element eDesc = (Element) element.getElementsByTagName("description").item(0);
                fullTitle = eDesc.getTextContent();
                comInd = fullTitle.indexOf(',');
                String text = fullTitle.substring(comInd+2);
                newsItem.setDescription(text);

                Element eDate = (Element) element.getElementsByTagName("pubDate").item(0);
                String sDate = eDate.getTextContent();
                int spInd = sDate.indexOf("0");
                sDate = sDate.substring(0, spInd-1);
                newsItem.setDate(sDate);

                Bill bill = billService.getByNumber(billNum);
                if (bill != null){
                    newsItem.setBillId(bill.getId());
                    newsItem.setUrl("/bills/"+bill.getId());
                }

                newsItem.setTitle(title);
                newsItem.setBillNumber(billNum);
                newsItem.generateTagline();
                list.add(newsItem);
            }
        }
        System.out.println("Bill Parse time: "+(System.currentTimeMillis()-timeStart));
        Cache.updateNewsItems(list);
    }

    @Override
    public CachedEntity<NewsItem> getNewsItems() {

        CachedEntity<NewsItem> cache = Cache.getNewsItems();
        CachedEntity<NewsItem> copy = new CachedEntity<>();
        copy.setLastUpdated(cache.getLastUpdated());
        copy.setData(new ArrayList<>(cache.getData()));
        return copy;
    }
}
