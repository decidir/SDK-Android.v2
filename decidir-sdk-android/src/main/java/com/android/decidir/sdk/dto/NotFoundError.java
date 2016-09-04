package com.android.decidir.sdk.dto;

import com.android.decidir.sdk.exceptions.NotFoundException;

/**
 * Created by biandra on 07/07/16.
 */
public class NotFoundError extends DecidirError {

    private String message;
    private String code;
    private String entityName;
    private String id;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public NotFoundException toException(int status, String message) {
        return new NotFoundException(status, message, this);
    }
}
