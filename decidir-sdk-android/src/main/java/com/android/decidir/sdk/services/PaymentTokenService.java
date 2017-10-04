package com.android.decidir.sdk.services;

import android.content.Context;

import com.android.decidir.sdk.converters.PaymentTokenConverter;
import com.android.decidir.sdk.converters.ErrorConverter;
import com.android.decidir.sdk.dto.FraudDetectionData;
import com.android.decidir.sdk.dto.OfflinePaymentToken;
import com.android.decidir.sdk.dto.OfflinePaymentTokenResponse;
import com.android.decidir.sdk.dto.PaymentTokenWithCardToken;
import com.android.decidir.sdk.dto.PaymentToken;
import com.android.decidir.sdk.dto.PaymentTokenResponse;
import com.android.decidir.sdk.dto.DecidirError;
import com.android.decidir.sdk.dto.DecidirResponse;
import com.android.decidir.sdk.exceptions.DecidirException;
import com.android.decidir.sdk.resources.PaymentTokenApi;
import com.android.decidir.sdk.resources.FraudDetectionApi;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by biandra on 04/08/16.
 */
public class PaymentTokenService {

    private PaymentTokenApi paymentTokenApi;
    private static PaymentTokenService service = null;
    private static FraudDetectionService fraudDetectionService = null;
    public static final int HTTP_500 = 500;
    private ErrorConverter errorConverter;
    private PaymentTokenConverter paymentTokenConverter;

    private PaymentTokenService(PaymentTokenApi paymentTokenApi, ErrorConverter errorConverter, PaymentTokenConverter paymentTokenConverter){
        this.paymentTokenApi = paymentTokenApi;
        this.errorConverter = errorConverter;
        this.paymentTokenConverter = paymentTokenConverter;
    }

    public static PaymentTokenService getInstance(PaymentTokenApi authenticateApi, FraudDetectionApi fraudDetectionApi) {
        if(service == null) {
            ErrorConverter errorConverter = new ErrorConverter();
            service = new PaymentTokenService(authenticateApi, errorConverter, new PaymentTokenConverter());
            fraudDetectionService = new FraudDetectionService(fraudDetectionApi, errorConverter);
        }
        return service;
    }

    public void getPaymentToken(final PaymentToken paymentToken,
                                     final Context context,
                                     Boolean withCybersource,
                                     final Integer profilingTimeoutSecs,
                                     final DecidirCallback<DecidirResponse<PaymentTokenResponse>> callback) {
        if (withCybersource){
            fraudDetectionService.getFraudDetection(context, profilingTimeoutSecs, new DecidirCallback<FraudDetectionData>() {
                @Override
                public void onSuccess(FraudDetectionData response) {
                    paymentToken.setFraud_detection(response);
                    doGetPayment(paymentToken, callback);
                }

                @Override
                public void onFailure(DecidirException e) {
                    callback.onFailure(e);
                }
            });
        } else {
            doGetPayment(paymentToken, callback);
        }
    }

    private void doGetPayment(PaymentToken paymentToken, final DecidirCallback<DecidirResponse<PaymentTokenResponse>> callback) {
        this.paymentTokenApi.get(paymentToken).enqueue(new Callback<PaymentTokenResponse>() {
            @Override
            public void onResponse(Call<PaymentTokenResponse> call, Response<PaymentTokenResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(paymentTokenConverter.convert(response, response.body()));
                } else {
                    try {
                        DecidirResponse<DecidirError> error = errorConverter.convert(response);
                        DecidirException e = DecidirException.wrap(error.getStatus(), error.getMessage(), error.getResult());
                        callback.onFailure(e);
                    } catch(IOException ioe) {
                        callback.onFailure(new DecidirException(HTTP_500, ioe.getMessage()));
                    }
                }
            }

            @Override
            public void onFailure(Call<PaymentTokenResponse> call, Throwable t) {
                callback.onFailure(new DecidirException(HTTP_500, t.getMessage()));
            }
        });
    }

    public void getPaymentToken(final PaymentTokenWithCardToken paymentTokenWithCardToken,
                                     final Context context,
                                     Boolean withCybersource,
                                     final Integer profilingTimeoutSecs,
                                     final DecidirCallback<DecidirResponse<PaymentTokenResponse>> callback) {
        if (withCybersource){
            fraudDetectionService.getFraudDetection(context, profilingTimeoutSecs, new DecidirCallback<FraudDetectionData>() {
                @Override
                public void onSuccess(FraudDetectionData response) {
                    paymentTokenWithCardToken.setFraud_detection(response);
                    doGetPaymentToken(paymentTokenWithCardToken, callback);
                }

                @Override
                public void onFailure(DecidirException e) {
                    callback.onFailure(e);
                }
            });
        } else {
            doGetPaymentToken(paymentTokenWithCardToken, callback);
        }
    }

    private void doGetPaymentToken(PaymentTokenWithCardToken paymentTokenWithCardToken, final DecidirCallback<DecidirResponse<PaymentTokenResponse>> callback) {
        this.paymentTokenApi.get(paymentTokenWithCardToken).enqueue(new Callback<PaymentTokenResponse>() {
            @Override
            public void onResponse(Call<PaymentTokenResponse> call, Response<PaymentTokenResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(paymentTokenConverter.convert(response, response.body()));
                } else {
                    try {
                        DecidirResponse<DecidirError> error = errorConverter.convert(response);
                        DecidirException e = DecidirException.wrap(error.getStatus(), error.getMessage(), error.getResult());
                        callback.onFailure(e);
                    } catch(IOException ioe) {
                        callback.onFailure(new DecidirException(HTTP_500, ioe.getMessage()));
                    }
                }
            }

            @Override
            public void onFailure(Call<PaymentTokenResponse> call, Throwable t) {
                callback.onFailure(new DecidirException(HTTP_500, t.getMessage()));
            }
        });
    }

    public void getPaymentToken(OfflinePaymentToken offlinePaymentToken,
                                     final DecidirCallback<DecidirResponse<OfflinePaymentTokenResponse>> callback) {
        this.paymentTokenApi.get(offlinePaymentToken).enqueue(new Callback<OfflinePaymentTokenResponse>() {
            @Override
            public void onResponse(Call<OfflinePaymentTokenResponse> call, Response<OfflinePaymentTokenResponse> response) {
                if (response.isSuccessful()) {
                    callback.onSuccess(paymentTokenConverter.convert(response, response.body()));
                } else {
                    try {
                        DecidirResponse<DecidirError> error = errorConverter.convert(response);
                        DecidirException e = DecidirException.wrap(error.getStatus(), error.getMessage(), error.getResult());
                        callback.onFailure(e);
                    } catch (IOException ioe) {
                        callback.onFailure(new DecidirException(HTTP_500, ioe.getMessage()));
                    }
                }
            }

            @Override
            public void onFailure(Call<OfflinePaymentTokenResponse> call, Throwable t) {
                callback.onFailure(new DecidirException(HTTP_500, t.getMessage()));
            }
        });
    }
}
