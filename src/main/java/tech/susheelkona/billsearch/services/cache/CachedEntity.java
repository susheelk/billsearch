package tech.susheelkona.billsearch.services.cache;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import tech.susheelkona.billsearch.controllers.utils.PaginatedResponse;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author Susheel Kona
 */

public class CachedEntity<T>{
    private List<T> data;
    private Date lastUpdated;

    public CachedEntity() {
    }

    public CachedEntity(List<T> data) {
        this.data = data;
        this.lastUpdated = new Date();
    }

    public PaginatedResponse getPaginatedRespone(int pageSize, int pageIndex) {
        PaginatedResponse<T> response = new PaginatedResponse<>();
        int start = (pageIndex-1)*pageSize;

        response.setData(new ArrayList<T>(data.subList(start, start+pageSize)));
        response.setLastUpdated(lastUpdated);

        if (pageIndex != 1) {
            response.setPreviousPage(pageIndex-1+"");
        }
        if (pageIndex != getTotalPages(pageSize)) {
            response.setNextPage(pageIndex+1+"");
        }
        return response;
    }

    public int getTotalPages(int pageSize) {
        return (data.size() / pageSize) + (data.size() % pageSize);
    }

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
}
