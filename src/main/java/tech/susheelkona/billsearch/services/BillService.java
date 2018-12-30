package tech.susheelkona.billsearch.services;

import tech.susheelkona.billsearch.model.legislation.Bill;
import tech.susheelkona.billsearch.services.cache.CachedEntity;

import java.util.List;

/**
 * @author Susheel Kona
 */
public interface BillService extends Updatable {
    CachedEntity<Bill> getAll() throws Exception;
    Bill getByNumber(String number) throws Exception;
}
