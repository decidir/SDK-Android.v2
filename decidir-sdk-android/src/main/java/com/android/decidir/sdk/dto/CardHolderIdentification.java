package com.android.decidir.sdk.dto;

import java.io.Serializable;

/**
 * Created by biandra on 04/08/16.
 */
public class CardHolderIdentification implements Serializable {

    private String type;
    private String number;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
