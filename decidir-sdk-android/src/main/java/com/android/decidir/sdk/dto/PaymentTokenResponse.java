package com.android.decidir.sdk.dto;

import com.android.decidir.sdk.utils.JsonUTCDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by biandra on 04/08/16.
 */
public class PaymentTokenResponse implements Serializable {

    private String id;
    private String status;
    private Integer card_number_length;
    private Date date_created;
    private String bin;
    private String last_four_digits;
    private Integer security_code_length;
    private Integer expiration_month;
    private Integer expiration_year;
    private Date date_due;
    private CardholderData cardholder;

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

    public Integer getCard_number_length() {
        return card_number_length;
    }

    public void setCard_number_length(Integer card_number_length) {
        this.card_number_length = card_number_length;
    }

    @JsonDeserialize(using = JsonUTCDateDeserializer.class)
    public Date getDate_created() {
        return date_created;
    }

    public void setDate_created(Date date_created) {
        this.date_created = date_created;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public String getLast_four_digits() {
        return last_four_digits;
    }

    public void setLast_four_digits(String last_four_digits) {
        this.last_four_digits = last_four_digits;
    }

    public Integer getSecurity_code_length() {
        return security_code_length;
    }

    public void setSecurity_code_length(Integer security_code_length) {
        this.security_code_length = security_code_length;
    }

    public Integer getExpiration_month() {
        return expiration_month;
    }

    public void setExpiration_month(Integer expiration_month) {
        this.expiration_month = expiration_month;
    }

    public Integer getExpiration_year() {
        return expiration_year;
    }

    public void setExpiration_year(Integer expiration_year) {
        this.expiration_year = expiration_year;
    }

    @JsonDeserialize(using = JsonUTCDateDeserializer.class)
    public Date getDate_due() {
        return date_due;
    }

    public void setDate_due(Date date_due) {
        this.date_due = date_due;
    }

    public CardholderData getCardholder() {
        return cardholder;
    }

    public void setCardholder(CardholderData cardholder) {
        this.cardholder = cardholder;
    }
}
