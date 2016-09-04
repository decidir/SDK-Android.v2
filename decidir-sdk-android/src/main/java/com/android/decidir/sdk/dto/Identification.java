package com.android.decidir.sdk.dto;

import java.io.Serializable;

/**
 * Created by biandra on 04/08/16.
 */
public class Identification implements Serializable {

    private String type;
    private Long number;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }
}
