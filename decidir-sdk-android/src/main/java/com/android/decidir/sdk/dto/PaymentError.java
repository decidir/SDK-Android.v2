package com.android.decidir.sdk.dto;

/**
 * Created by biandra on 04/10/16.
 */
public enum PaymentError {

    SECURITY_CODE("security_code"),
    CARD_NUMBER("card_number"),
    CARD_HOLDER_NAME("card_holder_name"),
    DNI("dni"),
    CARD_EXPIRATION_MONTH("card_expiration_month"),
    CARD_EXPIRATION_YEAR("card_expiration_year"),
    CARD_EXPIRATION("card_expiration");

    private String errorId;

    private PaymentError(final String errorId) {
        this.errorId = errorId;
    }
}
