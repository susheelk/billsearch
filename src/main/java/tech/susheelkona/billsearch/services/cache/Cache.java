package tech.susheelkona.billsearch.services.cache;

import tech.susheelkona.billsearch.model.legislation.Bill;
import tech.susheelkona.billsearch.model.legislation.Vote;

import java.util.List;

/**
 * @author Susheel Kona
 */
public final class Cache {
    private static CachedEntity<Bill> bills = new CachedEntity<>();
    private static CachedEntity<Vote> votes = new CachedEntity<>();


    public synchronized static CachedEntity<Bill> getBills() {
        return bills;
    }

    public synchronized static void updateBills(List<Bill> list) {
        bills = new CachedEntity<>(list);
    }

    public synchronized static CachedEntity<Vote> getVotes(){
        return votes;
    }

    public synchronized static void updateVotes(List<Vote> list) {
        votes = new CachedEntity<>(list);
    }
}
