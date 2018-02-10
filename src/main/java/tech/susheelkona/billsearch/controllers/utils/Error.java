package tech.susheelkona.billsearch.controllers.utils;

import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * @author Susheel Kona
 */
@JsonRootName("error")
public class Error {
    private String message;

    public Error(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
