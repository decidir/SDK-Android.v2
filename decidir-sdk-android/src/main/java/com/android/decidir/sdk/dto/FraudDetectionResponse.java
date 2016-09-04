package com.android.decidir.sdk.dto;

import java.io.Serializable;

/**
 * Created by biandra on 05/08/16.
 */
public class FraudDetectionResponse implements Serializable {

    private String org_id;
    private String merchant_id;

    public String getOrg_id() {
        return org_id;
    }

    public void setOrg_id(String org_id) {
        this.org_id = org_id;
    }

    public String getMerchant_id() {
        return merchant_id;
    }

    public void setMerchant_id(String merchant_id) {
        this.merchant_id = merchant_id;
    }
}
