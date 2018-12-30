package tech.susheelkona.billsearch.services;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;


/**
 * @author Susheel Kona
 */
@Component
public class ScheduledUpdater {

    @Autowired
    BillService billService;

    @Autowired
    VoteService voteService;

    @Autowired
    MpService mpService;

    @Autowired
    NewsService newsService;

    @Scheduled(fixedRate = 60000*60)
    public void updateAll() {
        new MultiThreadUpdater(billService, voteService, newsService).start();
        new MultiThreadUpdater(mpService).start();
//        new MultiThreadUpdater(voteService).start();
    }
}

class MultiThreadUpdater implements Runnable {

    private final org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass());
    private Thread thread;
    List<Updatable> updatables;

    /**
     *
     * @param updatables services to run on one thread
     */
    public MultiThreadUpdater(Updatable ...updatables) {
        this.updatables = Arrays.asList(updatables);
    }

    @Override
    public void run() {
        log.info("DEPLOYED: "+System.getenv("DEPLOYED"));

        log.info("Update thread started");

//            if (System.getenv("DEPLOYED") == "True") {
//                thread.sleep(7000);
//            }
       updatables.stream().forEach(updatable -> {
           try {
               updatable.update();
           } catch (Exception e) {
               e.printStackTrace();
           }
       });
    }

    public void start() {
        log.info("Starting update thread");
        thread = new Thread(this);
        thread.start();
    }
}