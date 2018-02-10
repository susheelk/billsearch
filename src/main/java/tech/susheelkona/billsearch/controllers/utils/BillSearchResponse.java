package tech.susheelkona.billsearch.controllers.utils;

import java.util.Date;

/**
 * @author Susheel Kona
 */
public class BillSearchResponse<T> {
    private T data;
    private Date lastUpdated;
    private Date nextUpdate;
    private boolean currentlyUpdating;

    public T getData() {
        return data;
    }

    public void setData(T data) {
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
}
