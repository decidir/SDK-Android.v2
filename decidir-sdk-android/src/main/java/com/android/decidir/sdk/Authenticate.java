package com.android.decidir.sdk;

import android.content.Context;

import com.android.decidir.sdk.configuration.DecidirConfiguration;
import com.android.decidir.sdk.dto.AuthenticationWithToken;
import com.android.decidir.sdk.dto.AuthenticationWithoutToken;
import com.android.decidir.sdk.dto.AuthenticationResponse;
import com.android.decidir.sdk.dto.DecidirResponse;
import com.android.decidir.sdk.exceptions.DecidirException;
import com.android.decidir.sdk.resources.AuthenticateApi;
import com.android.decidir.sdk.resources.FraudDetectionApi;
import com.android.decidir.sdk.services.AuthenticateService;

/**
 * Created by biandra on 05/08/16.
 */
public class Authenticate {

    private static String apiUrl = "https://api.decidir.com";
    private static Integer timeOut = 2;
    private AuthenticateService authenticateService;

    /**
     * Authenticate
     * @param secretAccessToken
     * @param apiUrl
     * @param timeOut
     */
    public Authenticate(final String secretAccessToken, final String apiUrl, final Integer timeOut) {
        if (apiUrl != null) {
            this.apiUrl = apiUrl;
        }
        if (timeOut != null){
            this.timeOut =timeOut;
        }
        this.authenticateService = AuthenticateService.getInstance(
                DecidirConfiguration.initRetrofit(secretAccessToken, this.apiUrl, this.timeOut, AuthenticateApi.class),
                DecidirConfiguration.initRetrofit(secretAccessToken, this.apiUrl, this.timeOut, FraudDetectionApi.class));
    }

    /**
     * Authenticate
     * @param secretAccessToken
     */
    public Authenticate(final String secretAccessToken) {
        this(secretAccessToken, null, null);
    }

    /**
     * Authenticate
     * @param secretAccessToken
     * @param apiUrl
     */
    public Authenticate(final String secretAccessToken, final String apiUrl) {
        this(secretAccessToken, apiUrl, null);
    }

    /**
     * Authenticate without token
     * @param authenticationWithoutToken
     * @param context
     * @param withCybersource
     * @param profilingTimeoutSecs
     * @return a {@link AuthenticationResponse} with the {@link AuthenticationResponse}
     * @throws DecidirException when an error ocurrs
     */
    public DecidirResponse<AuthenticationResponse> authenticate(AuthenticationWithoutToken authenticationWithoutToken, Context context, Boolean withCybersource, Integer profilingTimeoutSecs) throws DecidirException {
        return authenticateService.authenticate(authenticationWithoutToken, context, withCybersource, profilingTimeoutSecs);
    }

    /**
     * Authenticate with token
     * @param authenticationWithToken
     * @param context
     * @param withCybersource
     * @param profilingTimeoutSecs
     * @return a {@link AuthenticationResponse} with the {@link AuthenticationResponse}
     * @throws DecidirException when an error ocurrs
     */
    public DecidirResponse<AuthenticationResponse> authenticate(AuthenticationWithToken authenticationWithToken, Context context, Boolean withCybersource, Integer profilingTimeoutSecs) throws DecidirException {
        return authenticateService.authenticate(authenticationWithToken, context, withCybersource, profilingTimeoutSecs);
    }
}
