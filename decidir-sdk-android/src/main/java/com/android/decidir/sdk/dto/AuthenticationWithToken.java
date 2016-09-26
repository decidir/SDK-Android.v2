package com.android.decidir.sdk.dto;

import java.io.Serializable;

/**
 * Created by biandra on 24/09/16.
 */
public class AuthenticationWithToken extends Authentication implements Serializable {

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
