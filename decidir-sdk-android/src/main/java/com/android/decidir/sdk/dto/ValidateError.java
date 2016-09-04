package com.android.decidir.sdk.dto;

import com.android.decidir.sdk.exceptions.ValidateException;

import java.util.List;

/**
 * Created by biandra on 07/07/16.
 */
public class ValidateError extends DecidirError {

    private List<ValidationError> validation_errors;

    public List<ValidationError> getValidation_errors() {
        return validation_errors;
    }

    public void setValidation_errors(List<ValidationError> validation_errors) {
        this.validation_errors = validation_errors;
    }

    @Override
    public ValidateException toException(int status, String message) {
        return new ValidateException(status, message, this);
    }
}
