package tech.susheelkona.billsearch.controllers.utils;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Date;
import java.util.List;

/**
 * @author Susheel Kona
 */
@JsonPropertyOrder({"lastUpdated", "nextPage", "previousPage", "totalSize", "data"})
public class PaginatedResponse<T> {
    private Date lastUpdated;
    private String nextPage;
    private String previousPage;
    private List<T> data;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }


    public String getNextPage() {
        return nextPage;
    }

    public void setNextPage(String nextPage) {
        this.nextPage = nextPage;
    }

    public String getPreviousPage() {
        return previousPage;
    }

    public void setPreviousPage(String previousPage) {
        this.previousPage = previousPage;
    }

    @JsonGetter("totalSize")

    public int getTotalSize() {
        return data.size();
    }
}
