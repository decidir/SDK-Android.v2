package com.android.decidir.sdk.dto;

import java.io.Serializable;

/**
 * Created by biandra on 23/09/16.
 */
public abstract class CommonPayToken implements Serializable {

    private FraudDetectionData fraud_detection ;
    private String security_code;

    public FraudDetectionData getFraud_detection() {
        return fraud_detection;
    }

    public void setFraud_detection(FraudDetectionData fraud_detection) {
        this.fraud_detection = fraud_detection;
    }

    public String getSecurity_code() {
        return security_code;
    }

    public void setSecurity_code(String security_code) {
        this.security_code = security_code;
    }
}
