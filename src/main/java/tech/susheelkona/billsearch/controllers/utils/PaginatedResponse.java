package tech.susheelkona.billsearch.controllers.utils;

import java.util.Date;
import java.util.List;

/**
 * @author Susheel Kona
 */
public class PaginatedResponse<T> {
    private Date lastUpdated;
    private Date nextUpdate;
    private boolean currentlyUpdating;
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

    public Date getNextUpdate() {
        return nextUpdate;
    }

    public void setNextUpdate(Date nextUpdate) {
        this.nextUpdate = nextUpdate;
    }

    public boolean isCurrentlyUpdating() {
        return currentlyUpdating;
    }

    public void setCurrentlyUpdating(boolean currentlyUpdating) {
        this.currentlyUpdating = currentlyUpdating;
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
}