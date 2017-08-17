package com.android.decidir.sdk.converters;

import com.android.decidir.sdk.dto.DecidirResponse;

import retrofit2.Response;

/**
 * Created by biandra on 05/08/16.
 */
public class PaymentTokenConverter<T> {

    public DecidirResponse<T> convert(Response<T> response, T tokenResponse){
        DecidirResponse<T> dResponse = new DecidirResponse();
        dResponse.setStatus(response.code());
        dResponse.setResult(tokenResponse);
        dResponse.setMessage(response.message());
        return dResponse;
    }

    /*public DecidirResponse<OfflinePaymentTokenResponse> convert(Response<OfflinePaymentTokenResponse> response, OfflinePaymentTokenResponse offlinePaymentTokenResponse){
        DecidirResponse<OfflinePaymentTokenResponse> dResponse = new DecidirResponse();
        dResponse.setStatus(response.code());
        dResponse.setResult(offlinePaymentTokenResponse);
        dResponse.setMessage(response.message());
        return dResponse;
    }*/

}
