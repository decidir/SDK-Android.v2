package com.android.decidir.example.domain;

/**
 * Created by biandra on 23/09/16.
 */
public class CardTokenRequest {

    private String token;
    private String userSiteId;
    private String bin;
    private String lastFourDigits;
    private String expirationMonth;
    private String expirationYear;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserSiteId() {
        return userSiteId;
    }

    public void setUserSiteId(String userSiteId) {
        this.userSiteId = userSiteId;
    }

    public String getBin() {
        return bin;
    }

    public void setBin(String bin) {
        this.bin = bin;
    }

    public String getLastFourDigits() {
        return lastFourDigits;
    }

    public void setLastFourDigits(String lastFourDigits) {
        this.lastFourDigits = lastFourDigits;
    }

    public String getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(String expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public String getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(String expirationYear) {
        this.expirationYear = expirationYear;
    }
}
