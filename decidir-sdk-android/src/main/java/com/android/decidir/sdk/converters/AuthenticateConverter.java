package com.android.decidir.sdk.converters;

import com.android.decidir.sdk.dto.AuthenticationResponse;
import com.android.decidir.sdk.dto.DecidirResponse;

import retrofit2.Response;

/**
 * Created by biandra on 05/08/16.
 */
public class AuthenticateConverter {

    public DecidirResponse<AuthenticationResponse> convert(Response<AuthenticationResponse> response, AuthenticationResponse authenticationResponse){
        DecidirResponse<AuthenticationResponse> dResponse = new DecidirResponse();
        dResponse.setStatus(response.code());
        dResponse.setResult(authenticationResponse);
        dResponse.setMessage(response.message());
        return dResponse;
    }

}
