package com.android.decidir.sdk.services;

import com.android.decidir.sdk.converters.AuthenticateConverter;
import com.android.decidir.sdk.converters.ErrorConverter;
import com.android.decidir.sdk.dto.Authentication;
import com.android.decidir.sdk.dto.AuthenticationResponse;
import com.android.decidir.sdk.dto.DecidirError;
import com.android.decidir.sdk.dto.DecidirResponse;
import com.android.decidir.sdk.exceptions.DecidirException;
import com.android.decidir.sdk.resources.AuthenticateApi;
import com.android.decidir.sdk.resources.FraudDetectionApi;
import java.io.IOException;
import retrofit2.Response;

/**
 * Created by biandra on 04/08/16.
 */
public class AuthenticateService {

    private AuthenticateApi authenticateApi;
    private static AuthenticateService service = null;
    private static FraudDetectionService fraudDetectionService = null;
    public static final int HTTP_500 = 500;
    private ErrorConverter errorConverter;
    private AuthenticateConverter authenticateConverter;

    private AuthenticateService(AuthenticateApi authenticateApi, ErrorConverter errorConverter, AuthenticateConverter authenticateConverter){
        this.authenticateApi = authenticateApi;
        this.errorConverter = errorConverter;
        this.authenticateConverter = authenticateConverter;
    }

    public static AuthenticateService getInstance(AuthenticateApi authenticateApi, FraudDetectionApi fraudDetectionApi) {
        if(service == null) {
            ErrorConverter errorConverter = new ErrorConverter();
            service = new AuthenticateService(authenticateApi, errorConverter, new AuthenticateConverter());
            fraudDetectionService = new FraudDetectionService(fraudDetectionApi, errorConverter);
        }
        return service;
    }

    public DecidirResponse<AuthenticationResponse> authenticate(Authentication authentication, String sessionID) {
        try {
            authentication.setFraud_detection(fraudDetectionService.getFraudDetection(sessionID));
            Response<AuthenticationResponse> response = this.authenticateApi.authenticate(authentication).execute();
            if (response.isSuccessful()) {
                return authenticateConverter.convert(response, response.body());
            } else {
                DecidirResponse<DecidirError> error = errorConverter.convert(response);
                throw DecidirException.wrap(error.getStatus(), error.getMessage(), error.getResult());
            }
        } catch(IOException ioe) {
            throw new DecidirException(HTTP_500, ioe.getMessage());
        }
    }

}
