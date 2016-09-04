package com.android.decidir.sdk.exceptions;

import com.android.decidir.sdk.dto.DecidirError;

/**
 * Created by biandra on 08/07/16.
 */
public class DecidirException extends RuntimeException {

    private int status;
    private String message;

    static public DecidirException wrap(int status, String message, DecidirError decidirError) {
        return decidirError.toException(status, message);
    }

    public DecidirException(int status, String message){
        this.status = status;
        this.message = message;
    }

    public DecidirException(){
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
