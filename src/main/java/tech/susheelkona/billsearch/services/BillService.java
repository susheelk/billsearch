package tech.susheelkona.billsearch.services;

import tech.susheelkona.billsearch.model.legislation.Bill;
import tech.susheelkona.billsearch.services.cache.CachedEntity;

import java.util.List;

/**
 * @author Susheel Kona
 */
public interface BillService {
    public void update() throws Exception;
    public CachedEntity<Bill> getAll() throws Exception;
}
