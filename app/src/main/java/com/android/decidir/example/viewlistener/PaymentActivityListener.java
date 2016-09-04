package com.android.decidir.example.viewlistener;

import com.android.decidir.example.domain.PaymentError;
import com.decidir.sdk.dto.Payment;

/**
 * Created by biandra on 17/08/16.
 */
public interface PaymentActivityListener {

    void onGetPaymentStarted();
    void onGetPaymentSuccess(Payment payment);
    void onGetPaymentError(PaymentError error);

}
