package com.android.decidir.sdk.services;

import android.content.Context;

import com.android.decidir.sdk.converters.PaymentTokenConverter;
import com.android.decidir.sdk.converters.ErrorConverter;
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

    public DecidirResponse<PaymentTokenResponse> getPaymentToken(PaymentToken paymentToken, Context context, Boolean withCybersource, Integer profilingTimeoutSecs) {
        try {
            if (withCybersource){
                paymentToken.setFraud_detection(fraudDetectionService.getFraudDetection(context, profilingTimeoutSecs));
            }
            Response<PaymentTokenResponse> response = this.paymentTokenApi.get(paymentToken).execute();
            if (response.isSuccessful()) {
                return paymentTokenConverter.convert(response, response.body());
            } else {
                DecidirResponse<DecidirError> error = errorConverter.convert(response);
                throw DecidirException.wrap(error.getStatus(), error.getMessage(), error.getResult());
            }
        } catch(IOException ioe) {
            throw new DecidirException(HTTP_500, ioe.getMessage());
        }
    }

    public DecidirResponse<PaymentTokenResponse> getPaymentToken(PaymentTokenWithCardToken paymentTokenWithCardToken, Context context, Boolean withCybersource, Integer profilingTimeoutSecs) {
        try {
            if (withCybersource){
                paymentTokenWithCardToken.setFraud_detection(fraudDetectionService.getFraudDetection(context, profilingTimeoutSecs));
            }
            Response<PaymentTokenResponse> response = this.paymentTokenApi.get(paymentTokenWithCardToken).execute();
            if (response.isSuccessful()) {
                return paymentTokenConverter.convert(response, response.body());
            } else {
                DecidirResponse<DecidirError> error = errorConverter.convert(response);
                throw DecidirException.wrap(error.getStatus(), error.getMessage(), error.getResult());
            }
        } catch(IOException ioe) {
            throw new DecidirException(HTTP_500, ioe.getMessage());
        }
    }

    public DecidirResponse<OfflinePaymentTokenResponse> getPaymentToken(OfflinePaymentToken offlinePaymentToken) {
        try {
            Response<OfflinePaymentTokenResponse> response = this.paymentTokenApi.get(offlinePaymentToken).execute();
            if (response.isSuccessful()) {
                return paymentTokenConverter.convert(response, response.body());
            } else {
                DecidirResponse<DecidirError> error = errorConverter.convert(response);
                throw DecidirException.wrap(error.getStatus(), error.getMessage(), error.getResult());
            }
        } catch(IOException ioe) {
            throw new DecidirException(HTTP_500, ioe.getMessage());
        }
    }
}
