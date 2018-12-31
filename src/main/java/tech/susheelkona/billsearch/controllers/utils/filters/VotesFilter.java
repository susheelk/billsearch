package tech.susheelkona.billsearch.controllers.utils.filters;

import tech.susheelkona.billsearch.model.legislation.Vote;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class VotesFilter extends Filter<Vote> {

    public VotesFilter(HttpServletRequest request){
        super(request);
    }

    public VotesFilter(){

    }

    @Override
    public List<Vote> doFilter(List<Vote> data) {
        for(Map.Entry<String, String> entry: getFilters().entrySet()){
            switch (entry.getKey()){
                case "id":
                    data = id(Integer.parseInt(entry.getValue()), data);
                    break;
                case "bill_id":
                    data = billId(Integer.parseInt(entry.getValue()), data);
                    break;
                case "result":
                    data = status(entry.getValue(), data);

            }
        }
        return data;
    }

    private List<Vote> status(String value, List<Vote> data) {
        return data.stream().filter(vote -> vote.getResult().matches(value)).collect(Collectors.toList());
    }

    public List<Vote> id(int id, List<Vote> data) {
        return data.stream().filter(vote -> vote.getId() == id).collect(Collectors.toList());
    }

    public List<Vote> billId(int id, List<Vote> data){
        return data.stream().filter(vote -> vote.getBillId() == id).collect(Collectors.toList());
    }
}
