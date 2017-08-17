package com.android.decidir.sdk.dto;

import com.android.decidir.sdk.utils.JsonDateDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by biandra on 04/08/16.
 */
public class CardholderData implements Serializable {

    private Identification identification;
    private String name;
    private Date birthday;
    private Integer nro_puerta;

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

    @JsonDeserialize(using = JsonDateDeserializer.class)
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Integer getNro_puerta() {
        return nro_puerta;
    }

    public void setNro_puerta(Integer nro_puerta) {
        this.nro_puerta = nro_puerta;
    }
}
