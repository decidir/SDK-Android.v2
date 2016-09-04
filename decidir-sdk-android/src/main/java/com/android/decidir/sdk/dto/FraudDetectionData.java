package com.android.decidir.sdk.dto;

import java.io.Serializable;

/**
 * Created by biandra on 21/06/16.
 */
public class FraudDetectionData implements Serializable {

    private String device_unique_id;

    public String getDevice_unique_id() {
        return device_unique_id;
    }

    public void setDevice_unique_id(String device_unique_id) {
        this.device_unique_id = device_unique_id;
    }
}
