package tech.susheelkona.billsearch.controllers.utils.filters;

import tech.susheelkona.billsearch.model.NewsItem;

import java.util.List;
import java.util.Map;

public class NewsFilter extends Filter<NewsItem> {


    @Override
    public List<NewsItem> doFilter(List<NewsItem> data) {
        for(Map.Entry<String, String> entry: getFilters().entrySet()) {
            switch (entry.getKey()) {
                case "bills":
//                    String[] bi
//                    data = bills(data, );
                break;
            }
        }

        return data;
    }

//    private List<NewsItem> bills(List<NewsItem> data, List<String> billNumbers) {
//    }
}
