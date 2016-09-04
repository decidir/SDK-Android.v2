package com.android.decidir.sdk.dto;

import java.io.Serializable;

/**
 * Created by biandra on 04/08/16.
 */
public class CardholderData implements Serializable {

    private Identification identification;
    private String name;

    public Identification getIdentification() {
        return identification;
    }

    public void setIdentification(Identification identification) {
        this.identification = identification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
