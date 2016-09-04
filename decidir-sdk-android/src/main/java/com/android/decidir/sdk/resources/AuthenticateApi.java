package com.android.decidir.sdk.resources;

import com.android.decidir.sdk.dto.Authentication;
import com.android.decidir.sdk.dto.AuthenticationResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by biandra on 04/08/16.
 */
public interface AuthenticateApi {

    @POST("tokens")
    Call<AuthenticationResponse> authenticate(@Body Authentication authentication);

}
