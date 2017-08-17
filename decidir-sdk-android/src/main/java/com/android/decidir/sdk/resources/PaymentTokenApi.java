package com.android.decidir.sdk.resources;

import com.android.decidir.sdk.dto.OfflinePaymentToken;
import com.android.decidir.sdk.dto.OfflinePaymentTokenResponse;
import com.android.decidir.sdk.dto.PaymentTokenWithCardToken;
import com.android.decidir.sdk.dto.PaymentToken;
import com.android.decidir.sdk.dto.PaymentTokenResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by biandra on 04/08/16.
 */
public interface PaymentTokenApi {

    @POST("tokens")
    Call<PaymentTokenResponse> get(@Body PaymentToken paymentToken);

    @POST("tokens")
    Call<PaymentTokenResponse> get(@Body PaymentTokenWithCardToken paymentTokenWithCardToken);

    @POST("tokens")
    Call<OfflinePaymentTokenResponse> get(@Body OfflinePaymentToken offlinePaymentToken);

}
