package com.android.decidir.sdk.exceptions;

import com.android.decidir.sdk.dto.ApiError;

/**
 * Created by biandra on 08/07/16.
 */
public class ApiException extends DecidirException{

    private ApiError errorDetail;

    public ApiException(int status, String message, ApiError errorDetail){
        super();
        this.setStatus(status);
        this.setMessage(message);
        this.errorDetail = errorDetail;
    }

    public ApiError getErrorDetail() {
        return errorDetail;
    }

}
