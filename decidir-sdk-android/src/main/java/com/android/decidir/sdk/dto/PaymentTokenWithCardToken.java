package com.android.decidir.sdk.dto;

/**
 * Created by biandra on 24/09/16.
 */
public class PaymentTokenWithCardToken extends CommonPayToken {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
