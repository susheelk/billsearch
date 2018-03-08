package tech.susheelkona.billsearch.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonSetter;


/**
 * @author Susheel Kona
 */
public class Resource {
    private String resourceUri;

//    @JsonInclude(JsonInclude.Include.NON_NULL)
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonGetter("url")
    public String getResourceUri() {
        return resourceUri;
    }

    @JsonSetter("url")
    public void setResourceUri(String resourceUri) {
        this.resourceUri = resourceUri;
    }
}
