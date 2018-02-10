package tech.susheelkona.billsearch.services.cache;

import java.util.Date;
import java.util.List;

/**
 * @author Susheel Kona
 */
public class CachedEntity<T> {
    private List<T> data;
    private Date lastUpdated;

    public CachedEntity() {
    }

    public CachedEntity(List<T> data) {
        this.data = data;
        this.lastUpdated = new Date();
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
