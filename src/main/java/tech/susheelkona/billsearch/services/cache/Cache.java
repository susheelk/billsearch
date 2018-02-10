package tech.susheelkona.billsearch.services.cache;

import model.legislation.Bill;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Susheel Kona
 */
public final class Cache {
    private static CachedEntity<Bill> bills = new CachedEntity<Bill>();

    public synchronized static CachedEntity<Bill> getBills() {
        return bills;
    }

    public synchronized static void updateBills(List<Bill> list) {
        bills = new CachedEntity<>(list);
    }
}
