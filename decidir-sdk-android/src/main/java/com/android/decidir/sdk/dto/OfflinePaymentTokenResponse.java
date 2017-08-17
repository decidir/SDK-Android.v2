package com.android.decidir.sdk.dto;

import com.android.decidir.sdk.utils.JsonUTCDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by biandra on 15/08/17.
 */

public class OfflinePaymentTokenResponse implements Serializable {

    private String id;
    private String status;
    private Date date_created;
    private Date date_due;
    private Customer customer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @JsonDeserialize(using = JsonUTCDateDeserializer.class)
    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    @JsonDeserialize(using = JsonUTCDateDeserializer.class)
    public Date getDate_due() {
        return date_due;
    }

    public void setDate_due(Date date_due) {
        this.date_due = date_due;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
