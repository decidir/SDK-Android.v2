package com.android.decidir.sdk.dto;

import java.io.Serializable;

/**
 * Created by biandra on 03/08/17.
 */

public class OfflinePaymentToken implements Serializable {

    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
