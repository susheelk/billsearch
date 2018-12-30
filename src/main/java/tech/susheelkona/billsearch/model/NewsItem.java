package tech.susheelkona.billsearch.model;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.Date;

public class NewsItem {
    private String title;
    private String billNumber;
    private String url;
    private String date;
    private String description;
    protected String tagline;
    private int billId;


    @JsonGetter("billId")
    public int getBillId() {
        return billId;
    }

    public void setBillId(int billId) {
        this.billId = billId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;

    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTagline() {
        return tagline;
    }

    public void setTagline(String tagline) {
        this.tagline = tagline;
    }

    public void generateTagline() {
        String tag = "";

        switch (title){
            case "Concurrence in Senate Amendments in the House of Commons":
                tag = "House of Commons adopts Senate Amendments to Bill " + billNumber;
                break;

            case "Consideration of Senate Amendments in the House of Commons":
                tag = "House of Commons considers Senate Amendments to Bill " + billNumber;
                break;

            case "Royal Assent in the Senate":
                tag = "Bill " + billNumber + " receives Royal Assent, becomes law";
                break;

            default: tag = billNumber+", "+title;
        }

        if (title.contains("Introduction and")) {
            int aInd = title.indexOf("and");
            int rInd = title.indexOf("Reading");
            String time = title.substring(aInd+4, rInd-1);
            String chamb = title.substring(rInd+8);
            tag = "Bill "+ billNumber + " introduced " + chamb;
        } else  if (title.contains("Reading in the")) {
            int rInd = title.indexOf("Reading");
            String time = title.substring(0, rInd-1);
            String chamb = title.substring(rInd+8);
            tag = "Bill " + billNumber + " read for the " + time + " time " + chamb;
        }
        setTagline(tag);
    }
}