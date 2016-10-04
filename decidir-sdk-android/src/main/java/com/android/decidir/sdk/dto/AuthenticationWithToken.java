package com.android.decidir.sdk.dto;

/**
 * Created by biandra on 24/09/16.
 */
public class AuthenticationWithToken extends Authentication {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
