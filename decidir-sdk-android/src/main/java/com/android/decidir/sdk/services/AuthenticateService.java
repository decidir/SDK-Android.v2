package com.android.decidir.sdk.services;

import android.content.Context;

import com.android.decidir.sdk.converters.AuthenticateConverter;
import com.android.decidir.sdk.converters.ErrorConverter;
import com.android.decidir.sdk.dto.AuthenticationWithToken;
import com.android.decidir.sdk.dto.AuthenticationWithoutToken;
import com.android.decidir.sdk.dto.AuthenticationResponse;
import com.android.decidir.sdk.dto.DecidirError;
import com.android.decidir.sdk.dto.DecidirResponse;
import com.android.decidir.sdk.dto.FraudDetectionData;
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

    public DecidirResponse<AuthenticationResponse> authenticate(AuthenticationWithoutToken authenticationWithoutToken, Context context, String sessionID, Boolean withCybersource) {
        try {
            if (withCybersource){
                authenticationWithoutToken.setFraud_detection(fraudDetectionService.getFraudDetection(sessionID, context));
            }
            Response<AuthenticationResponse> response = this.authenticateApi.authenticate(authenticationWithoutToken).execute();
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

    public DecidirResponse<AuthenticationResponse> authenticate(AuthenticationWithToken authenticationWithToken, Context context, String sessionID, Boolean withCybersource) {
        try {
            if (withCybersource){
                authenticationWithToken.setFraud_detection(fraudDetectionService.getFraudDetection(sessionID, context));
            }
            Response<AuthenticationResponse> response = this.authenticateApi.authenticate(authenticationWithToken).execute();
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
