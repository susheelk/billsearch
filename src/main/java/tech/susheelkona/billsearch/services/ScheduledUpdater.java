package tech.susheelkona.billsearch.services;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * @author Susheel Kona
 */
@Component
public class ScheduledUpdater {

    @Autowired
    BillService billService;

    @Scheduled(fixedRate = 60000*60)
    public void updateAll() {
        new MultiThreadUpdater(billService).start();
    }
}

class MultiThreadUpdater implements Runnable {

    private final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());
    private Thread thread;
    BillService billService;

    public MultiThreadUpdater(BillService billService) {
        this.billService = billService;
    }

    @Override
    public void run() {
        try {
            log.info("DEPLOYED: "+System.getenv("DEPLOYED"));

            log.info("Update thread started");

//            if (System.getenv("DEPLOYED") == "True") {
//                thread.sleep(7000);
//            }
            billService.update();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        log.info("Starting update thread");
        thread = new Thread(this);
        thread.start();
    }
}