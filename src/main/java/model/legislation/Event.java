package model.legislation;

import java.util.Date;

/**
 * @author Susheel Kona
 */

public class Event {
    private String status;
    private int id;
    private Date date;

    public Event(String status, int id, Date date) {
        this.status = status;
        this.id = id;
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
