package com.android.decidir.sdk.resources;

import com.android.decidir.sdk.dto.AuthenticationWithToken;
import com.android.decidir.sdk.dto.AuthenticationWithoutToken;
import com.android.decidir.sdk.dto.AuthenticationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by biandra on 04/08/16.
 */
public interface AuthenticateApi {

    @POST("tokens")
    Call<AuthenticationResponse> authenticate(@Body AuthenticationWithoutToken authenticationWithoutToken);

    @POST("tokens")
    Call<AuthenticationResponse> authenticate(@Body AuthenticationWithToken authenticationWithToken);

}
