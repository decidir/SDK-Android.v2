package com.android.decidir.sdk;

import android.content.Context;

import com.android.decidir.sdk.configuration.DecidirConfiguration;
import com.android.decidir.sdk.dto.OfflinePaymentToken;
import com.android.decidir.sdk.dto.OfflinePaymentTokenResponse;
import com.android.decidir.sdk.dto.PaymentTokenWithCardToken;
import com.android.decidir.sdk.dto.PaymentToken;
import com.android.decidir.sdk.dto.PaymentTokenResponse;
import com.android.decidir.sdk.dto.DecidirResponse;
import com.android.decidir.sdk.exceptions.DecidirException;
import com.android.decidir.sdk.resources.PaymentTokenApi;
import com.android.decidir.sdk.resources.FraudDetectionApi;
import com.android.decidir.sdk.services.DecidirCallback;
import com.android.decidir.sdk.services.PaymentTokenService;

/**
 * Created by biandra on 05/08/16.
 */
public class DecidirPaymentToken {

    private static String apiUrl = "https://live.decidir.com/api/v1";
    private static Integer timeOut = 2;
    private PaymentTokenService paymentTokenService;

    /**
     * DecidirPaymentToken
     * @param secretAccessToken
     * @param apiUrl
     * @param timeOut
     */
    public DecidirPaymentToken(final String secretAccessToken, final String apiUrl, final Integer timeOut) {
        if (apiUrl != null) {
            this.apiUrl = apiUrl;
        }
        if (timeOut != null){
            this.timeOut =timeOut;
        }
        this.paymentTokenService = PaymentTokenService.getInstance(
                DecidirConfiguration.initRetrofit(secretAccessToken, this.apiUrl, this.timeOut, PaymentTokenApi.class),
                DecidirConfiguration.initRetrofit(secretAccessToken, this.apiUrl, this.timeOut, FraudDetectionApi.class));
    }

    /**
     * DecidirPaymentToken
     * @param secretAccessToken
     */
    public DecidirPaymentToken(final String secretAccessToken) {
        this(secretAccessToken, null, null);
    }

    /**
     * DecidirPaymentToken
     * @param secretAccessToken
     * @param apiUrl
     */
    public DecidirPaymentToken(final String secretAccessToken, final String apiUrl) {
        this(secretAccessToken, apiUrl, null);
    }

    /**
     * Create Payment Token without card token
     * @param paymentToken
     * @param context
     * @param withCybersource
     * @param profilingTimeoutSecs
     * @return a {@link PaymentTokenResponse} with the {@link PaymentTokenResponse}
     * @throws DecidirException when an error ocurrs
     */
    public DecidirResponse<PaymentTokenResponse> createPaymentToken(PaymentToken paymentToken, Context context, Boolean withCybersource, Integer profilingTimeoutSecs) throws DecidirException {
        return paymentTokenService.getPaymentToken(paymentToken, context, withCybersource, profilingTimeoutSecs);
    }

    /**
     * Create Payment Token without card token asynchronously
     * @param paymentToken
     * @param context
     * @param withCybersource
     * @param profilingTimeoutSecs
     * @param callback
     * @return a {@link PaymentTokenResponse} with the {@link PaymentTokenResponse}
     */
    public void createPaymentTokenAsync(PaymentToken paymentToken, Context context, Boolean withCybersource, Integer profilingTimeoutSecs, DecidirCallback<DecidirResponse<PaymentTokenResponse>> callback) {
        paymentTokenService.getPaymentTokenAsync(paymentToken, context, withCybersource, profilingTimeoutSecs, callback);
    }

    /**
     * Create Payment Token  with card token
     * @param paymentTokenWithCardToken
     * @param context
     * @param withCybersource
     * @param profilingTimeoutSecs
     * @return a {@link PaymentTokenResponse} with the {@link PaymentTokenResponse}
     * @throws DecidirException when an error ocurrs
     */
    public DecidirResponse<PaymentTokenResponse> createPaymentTokenWithCardToken(PaymentTokenWithCardToken paymentTokenWithCardToken, Context context, Boolean withCybersource, Integer profilingTimeoutSecs) throws DecidirException {
        return paymentTokenService.getPaymentToken(paymentTokenWithCardToken, context, withCybersource, profilingTimeoutSecs);
    }

    /**
     * Create Payment Token  with card token asynchronously
     * @param paymentTokenWithCardToken
     * @param context
     * @param withCybersource
     * @param profilingTimeoutSecs
     * @param callback
     * @return a {@link PaymentTokenResponse} with the {@link PaymentTokenResponse}
     */
    public void createPaymentTokenWithCardTokenAsync(PaymentTokenWithCardToken paymentTokenWithCardToken, Context context, Boolean withCybersource, Integer profilingTimeoutSecs, DecidirCallback<DecidirResponse<PaymentTokenResponse>> callback) {
        paymentTokenService.getPaymentTokenAsync(paymentTokenWithCardToken, context, withCybersource, profilingTimeoutSecs, callback);
    }

    /**
     *
     * @param offlinePaymentToken
     * @return a {@link OfflinePaymentTokenResponse} with the {@link OfflinePaymentTokenResponse}
     * @throws DecidirException when an error ocurrs
     */
    public DecidirResponse<OfflinePaymentTokenResponse> createOfflinePaymentToken(OfflinePaymentToken offlinePaymentToken) throws DecidirException {
        return paymentTokenService.getPaymentToken(offlinePaymentToken);
    }

    /**
     *
     * @param offlinePaymentToken
     * @param callback
     * @return a {@link OfflinePaymentTokenResponse} with the {@link OfflinePaymentTokenResponse}
     */
    public void createOfflinePaymentTokenAsync(OfflinePaymentToken offlinePaymentToken, DecidirCallback<DecidirResponse<OfflinePaymentTokenResponse>> callback)  {
        paymentTokenService.getPaymentTokenAsync(offlinePaymentToken, callback);
    }
}
