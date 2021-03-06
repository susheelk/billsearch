package tech.susheelkona.billsearch.model.legislation;

import com.fasterxml.jackson.annotation.JsonFilter;
import tech.susheelkona.billsearch.model.Person;
import tech.susheelkona.billsearch.model.Resource;

import java.util.Date;
import java.util.List;
import java.util.Map;

@JsonFilter("includer")
public class Vote extends Resource {
    private int id;
    private Date date;
    private String title;
    private String billUrl;
    private int billId;
    private String description;
    private int yeas;
    private int nays;
    private String result;

    private List<Ballot> ballots;

    public Vote(){}

    public Vote(Vote vote) {
        this.id = vote.getId();
        this.date = vote.getDate();
        this.title = vote.getTitle();
        this.billUrl = vote.getBillUrl();
        this.billId = vote.getBillId();
        this.description = vote.getDescription();
        this.yeas = vote.getYeas();
        this.nays = vote.getNays();
        this.result = vote.getResult();
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBillUrl() {
        return billUrl;
    }

    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public void setBillUrl(String billUrl) {
        this.billUrl = billUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYeas() {
        return yeas;
    }

    public void setYeas(int yeas) {
        this.yeas = yeas;
    }

    public int getNays() {
        return nays;
    }

    public void setNays(int nays) {
        this.nays = nays;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<Ballot> getBallots() {
        return ballots;
    }

    public void setBallots(List<Ballot> ballots) {
        this.ballots = ballots;
    }
}
