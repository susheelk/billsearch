package tech.susheelkona.billsearch.controllers.utils.filters;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Filter<T> {

    private Map<String, String> filters;

    public Filter(){
        filters = new HashMap<>();
    }

    public Filter(Map<String, String> filters) {
        this();
        this.filters = filters;
    }

    public Filter(HttpServletRequest request) {
        this();
        addFiltersFromRequest(request);
    }

    public Map<String, String> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, String> filters) {
        this.filters = filters;
    }

    public abstract List<T>  doFilter(List<T> data);

    public void addFilter(String key, String value) {
        filters.put(key, value);
    }

    public void addFiltersFromRequest(HttpServletRequest request) {
        for(Map.Entry<String, String[]> entry: request.getParameterMap().entrySet()) {
            Arrays.stream(entry.getValue()).forEach(val -> addFilter(entry.getKey(), val));
        }
    }
}
