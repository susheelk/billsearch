package tech.susheelkona.billsearch.controllers.utils.filters;

import tech.susheelkona.billsearch.model.legislation.Bill;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BillFilter extends Filter<Bill> {

    public BillFilter(HttpServletRequest request) {
        super(request);
    }

    @Override
    public List<Bill> doFilter(List<Bill> data) {
        for(Map.Entry<String, String> entry: getFilters().entrySet()) {
            switch (entry.getKey()) {
                case "id":
                    data = filterById(Integer.parseInt(entry.getValue()), data);
                    break;

                case "number":
                    data = filterByNumber(entry.getValue(), data);
                    break;

                case "origin":
                    data = filterByOriginChamber(entry.getValue(), data);
                    break;

                case "law":
                    data = filterByLaw(Boolean.parseBoolean(entry.getValue()), data);
                    break;

                case "sponsor_id":
                    data = filterBySponsorId(Integer.parseInt(entry.getValue()), data);
                    break;

                case "sponsor_name":
                    data = filterBySponsorName(entry.getValue().replace("_", " "), data);

                case "bill_state":
                    data = filterByBillState(entry.getValue().replace("_", ""), data);
                    break;

                case "new":
                    data = filterByNew(Boolean.parseBoolean(entry.getValue()), data);
                    break;
            }
        }
        return data;
    }

    public List<Bill> filterById(int id, List<Bill> data) {
        return data.stream().filter(bill -> bill.getId() == id).collect(Collectors.toList());
    }

    public List<Bill> filterByNumber(String number, List<Bill> data) {
        return data.stream().filter(bill -> bill.getNumber().matches(number)).collect(Collectors.toList());
    }

    public List<Bill> filterByLaw(boolean law, List<Bill> data) {
        return data.stream().filter(bill -> bill.isLaw() == law).collect(Collectors.toList());
    }

    public List<Bill> filterByOriginChamber(String chamber, List<Bill> data) {
        return data.stream().filter(bill -> bill.getNumber().startsWith(chamber.substring(0, 1)))
                .collect(Collectors.toList());
    }

    public List<Bill> filterBySponsorId(int id, List<Bill> data) {
        return data.stream().filter(bill -> bill.getSponsor().getId() == id).collect(Collectors.toList());
    }

    public List<Bill> filterBySponsorName(String name, List<Bill> data){
        return data.stream().filter(bill -> bill.getSponsor().getName().matches(name)).collect(Collectors.toList());
    }

    public List<Bill> filterByBillState(String state, List<Bill> data) {
        return data.stream().filter(bill -> bill.getLastMajorEvent().getStatus().matches(state)).collect(Collectors.toList());
    }

    public List<Bill> filterByNew(boolean bnew, List<Bill> data){
        return data.stream().filter(bill -> (bill.getEvents().size() == 1) == bnew).collect(Collectors.toList());
    }
}
