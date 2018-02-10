package tech.susheelkona.billsearch.services;

import model.legislation.Bill;

import java.util.List;

/**
 * @author Susheel Kona
 */
public interface BillService {
    public void update() throws Exception;
    public List<Bill> getAll() throws Exception;
}
