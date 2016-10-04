package com.android.decidir.sdk.dto;

import java.io.Serializable;

/**
 * Created by biandra on 21/06/16.
 */
public class FraudDetectionData implements Serializable {

    private String device_unique_identifier;

    public String getDevice_unique_identifier() {
        return device_unique_identifier;
    }

    public void setDevice_unique_identifier(String device_unique_identifier) {
        this.device_unique_identifier = device_unique_identifier;
    }
}
