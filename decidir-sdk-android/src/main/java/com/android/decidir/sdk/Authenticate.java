package com.android.decidir.sdk;

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

    public Authenticate(final String secretAccessToken) {
        this(secretAccessToken, null, null);
    }

    public Authenticate(final String secretAccessToken, final String apiUrl) {
        this(secretAccessToken, apiUrl, null);
    }


    public DecidirResponse<AuthenticationResponse> authenticate(AuthenticationWithoutToken authenticationWithoutToken, String sessionID, Boolean withCybersource) throws DecidirException {
        return authenticateService.authenticate(authenticationWithoutToken, sessionID, withCybersource);
    }

    public DecidirResponse<AuthenticationResponse> authenticate(AuthenticationWithToken authenticationWithToken, String sessionID, Boolean withCybersource) throws DecidirException {
        return authenticateService.authenticate(authenticationWithToken, sessionID, withCybersource);
    }
}
