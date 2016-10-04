package com.android.decidir.sdk.dto;


/**
 * Created by biandra on 04/08/16.
 */
public class AuthenticationWithoutToken extends Authentication {

    private String card_number;
    private String card_expiration_month;
    private String card_expiration_year;
    private String card_holder_name;
    private CardHolderIdentification card_holder_identification;

    public String getCard_number() {
        return card_number;
    }

    public void setCard_number(String card_number) {
        this.card_number = card_number;
    }

    public String getCard_expiration_month() {
        return card_expiration_month;
    }

    public void setCard_expiration_month(String card_expiration_month) {
        this.card_expiration_month = card_expiration_month;
    }

    public String getCard_expiration_year() {
        return card_expiration_year;
    }

    public void setCard_expiration_year(String card_expiration_year) {
        this.card_expiration_year = card_expiration_year;
    }

    public String getCard_holder_name() {
        return card_holder_name;
    }

    public void setCard_holder_name(String card_holder_name) {
        this.card_holder_name = card_holder_name;
    }

    public CardHolderIdentification getCard_holder_identification() {
        return card_holder_identification;
    }

    public void setCard_holder_identification(CardHolderIdentification card_holder_identification) {
        this.card_holder_identification = card_holder_identification;
    }
}
