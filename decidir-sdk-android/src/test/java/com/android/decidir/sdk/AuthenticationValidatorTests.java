package com.android.decidir.sdk;

import android.content.Context;
import android.content.res.Resources;
import com.android.decidir.sdk.dto.PaymentToken;
import com.android.decidir.sdk.dto.CardHolderIdentification;
import com.android.decidir.sdk.dto.PaymentError;
import com.android.decidir.sdk.validaters.PaymentTokenValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;

import java.util.Map;

/**
 * Created by biandra on 09/11/16.
 */
public class AuthenticationValidatorTests {

    @Mock
    Context context;
    @Mock
    Resources resources;

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void authenticationWithoutTokenValid() {
        PaymentToken authentication = new PaymentToken();
        authentication.setCard_expiration_month("02");
        authentication.setCard_expiration_year("22");
        authentication.setCard_holder_name("Biandra");
        authentication.setCard_number("4507990000004905");
        authentication.setSecurity_code("1234");
        CardHolderIdentification cardHolderIdentification = new CardHolderIdentification();
        cardHolderIdentification.setType("dni");
        cardHolderIdentification.setNumber("12123123");
        authentication.setCard_holder_identification(cardHolderIdentification);

        when(context.getResources()).thenReturn(resources);
        //when(resources.getString(anyInt())).thenReturn("error");

        PaymentTokenValidator validator = new PaymentTokenValidator();
        Map<PaymentError, String> result = validator.validate(authentication, context);

        assert result.isEmpty();
    }

    @Test
    public void authenticationWithoutTokenExpiratedDay() {
        PaymentToken authentication = new PaymentToken();
        authentication.setCard_expiration_month("02");
        authentication.setCard_expiration_year("15");
        authentication.setCard_holder_name("Biandra");
        authentication.setCard_number("4507990000004905");
        authentication.setSecurity_code("1234");
        CardHolderIdentification cardHolderIdentification = new CardHolderIdentification();
        cardHolderIdentification.setType("dni");
        cardHolderIdentification.setNumber("12123123");
        authentication.setCard_holder_identification(cardHolderIdentification);

        when(context.getResources()).thenReturn(resources);

        PaymentTokenValidator validator = new PaymentTokenValidator();
        Map<PaymentError, String> result = validator.validate(authentication, context);

        assert !result.isEmpty();
        assert result.containsKey(PaymentError.CARD_EXPIRATION);
    }
}
