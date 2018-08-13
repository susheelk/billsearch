package tech.susheelkona.billsearch.controllers.utils.filters;

import tech.susheelkona.billsearch.model.legislation.Vote;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public class VotesFilter extends Filter<Vote> {

    public VotesFilter(HttpServletRequest request){
        super(request);
    }

    @Override
    public List<Vote> doFilter(List<Vote> data) {
        return data;
    }
}
