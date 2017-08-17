package com.android.decidir.sdk.dto;

import java.io.Serializable;

/**
 * Created by biandra on 15/08/17.
 */

public class Customer implements Serializable {

    private String name;
    private CardHolderIdentification identification;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CardHolderIdentification getIdentification() {
        return identification;
    }

    public void setIdentification(CardHolderIdentification identification) {
        this.identification = identification;
    }
}
