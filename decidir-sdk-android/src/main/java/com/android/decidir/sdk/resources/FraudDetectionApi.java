package com.android.decidir.sdk.resources;

import com.android.decidir.sdk.dto.FraudDetectionResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by biandra on 05/08/16.
 */
public interface FraudDetectionApi {

    @GET("frauddetectionconf")
    Call<FraudDetectionResponse> getfrauddetectionconf();

}
