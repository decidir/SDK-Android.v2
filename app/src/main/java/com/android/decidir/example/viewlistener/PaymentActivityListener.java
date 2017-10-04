package com.android.decidir.example.viewlistener;

import com.android.decidir.example.domain.ErrorDetail;
import com.decidir.sdk.payments.Payment;

/**
 * Created by biandra on 17/08/16.
 */
public interface PaymentActivityListener {

    void onGetPaymentStartedWithoutTokenization();
    void onGetPaymentStartedWithTokenization(String token);
    void onGetPaymentLoading();
    void onGetPaymentSuccess(Payment payment);
    void onGetPaymentError(ErrorDetail error);

}
