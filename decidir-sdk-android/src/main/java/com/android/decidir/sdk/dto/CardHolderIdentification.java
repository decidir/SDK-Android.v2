package com.android.decidir.sdk.dto;

import java.io.Serializable;

/**
 * Created by biandra on 04/08/16.
 */
public class CardHolderIdentification implements Serializable {

    private IdentificationType type;
    private String number;

    public IdentificationType getType() {
        return type;
    }

    public void setType(IdentificationType type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}
