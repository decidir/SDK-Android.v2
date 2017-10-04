package com.android.decidir.example.viewmodel;

import com.android.decidir.example.Constants;
import com.android.decidir.example.DecidirApp;
import com.android.decidir.example.domain.ErrorDetail;
import com.android.decidir.example.services.PaymentService;
import com.android.decidir.example.viewlistener.PaymentActivityListener;
import com.android.decidir.sdk.DecidirPaymentToken;
import com.android.decidir.sdk.dto.DecidirResponse;
import com.android.decidir.sdk.dto.PaymentToken;
import com.android.decidir.sdk.dto.PaymentTokenResponse;
import com.android.decidir.sdk.dto.PaymentTokenWithCardToken;
import com.android.decidir.sdk.exceptions.ApiException;
import com.android.decidir.sdk.exceptions.DecidirException;
import com.android.decidir.sdk.exceptions.NotFoundException;
import com.android.decidir.sdk.exceptions.ValidateException;
import com.android.decidir.sdk.services.DecidirCallback;

import java.util.ArrayList;

/**
 * Created by biandra on 17/08/16.
 */
public class PaymentActivityModel {

    private PaymentActivityListener view;
    private PaymentService paymentService;

    public PaymentActivityModel(PaymentActivityListener view) {
        super();
        this.view = view;
        this.paymentService = new PaymentService(view);
    }

    public void pay(final PaymentToken paymentToken, final Integer installments, final Integer verticalCS, final String userId){
        Boolean withCybersource = verticalCS != 0;

        DecidirPaymentToken decidirPaymentToken = new DecidirPaymentToken(Constants.PUBLIC_API_KEY, Constants.URL, 10);
        decidirPaymentToken.createPaymentToken(paymentToken, DecidirApp.getAppContext(), withCybersource, 30, new DecidirCallback<DecidirResponse<PaymentTokenResponse>>() {
            @Override
            public void onSuccess(final DecidirResponse<PaymentTokenResponse> paymentTokenResponse) {
                final String deviceUniqueIdentifier = (paymentToken.getFraud_detection() != null) ? paymentToken.getFraud_detection().getDevice_unique_identifier() : null;
                paymentService.execute(new ArrayList<Object>() {{
                    add(paymentTokenResponse.getResult());
                    add(installments);
                    add(verticalCS);
                    add(userId);
                    add(deviceUniqueIdentifier);
                }});
            }

            @Override
            public void onFailure(DecidirException e) {
                showError(e);
            }
        });
    }

    public void pay(final PaymentTokenWithCardToken paymentTokenWithCardToken, final Integer installments, final Integer verticalCS, final String userId){
        Boolean withCybersource = verticalCS != 0;
        DecidirPaymentToken decidirPaymentToken = new DecidirPaymentToken(Constants.PUBLIC_API_KEY, Constants.URL, 10);
        decidirPaymentToken.createPaymentTokenWithCardToken(paymentTokenWithCardToken, DecidirApp.getAppContext(), withCybersource, 30, new DecidirCallback<DecidirResponse<PaymentTokenResponse>>() {
            @Override
            public void onSuccess(final DecidirResponse<PaymentTokenResponse> paymentTokenResponse) {
                final String deviceUniqueIdentifier = (paymentTokenWithCardToken.getFraud_detection() != null) ? paymentTokenWithCardToken.getFraud_detection().getDevice_unique_identifier() : null;
                paymentService.execute(new ArrayList<Object>() {{
                    add(paymentTokenResponse.getResult());
                    add(installments);
                    add(verticalCS);
                    add(userId);
                    add(deviceUniqueIdentifier);
                }});
            }

            @Override
            public void onFailure(DecidirException e) {
                showError(e);
            }
        });
    }

    protected void showError(DecidirException e) {
        ErrorDetail errorDetail;
        try {
            throw e;
        }
        catch (ApiException apiException){
            errorDetail = new ErrorDetail(apiException.getMessage(), apiException.getErrorDetail().getMessage());
        } catch (NotFoundException notFoundException){
            errorDetail = new ErrorDetail(notFoundException.getMessage(), notFoundException.getErrorDetail().getMessage());
        } catch (ValidateException validateException){
            errorDetail = new ErrorDetail(validateException.getMessage(), validateException.getErrorDetail().toString());
        } catch (DecidirException decidirException){
            errorDetail = new ErrorDetail(decidirException.getMessage(), String.valueOf(decidirException.getStatus()));
        }
        view.onGetPaymentError(errorDetail);
    }

}
