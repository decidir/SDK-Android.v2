package com.android.decidir.example.domain;

/**
 * Created by biandra on 28/08/16.
 */
public class PaymentError {

    private String message;
    private String description;

    public PaymentError(String message, String description){
        this.message = message;
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
