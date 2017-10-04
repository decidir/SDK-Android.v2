package com.android.decidir.sdk;

/**
 * Created by biandra on 09/11/16.
 */
public class AuthenticationValidatorTests {

   /* @Mock
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
        cardHolderIdentification.setType(IdentificationType.DNI);
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
        cardHolderIdentification.setType(IdentificationType.DNI);
        cardHolderIdentification.setNumber("12123123");
        authentication.setCard_holder_identification(cardHolderIdentification);

        when(context.getResources()).thenReturn(resources);

        PaymentTokenValidator validator = new PaymentTokenValidator();
        Map<PaymentError, String> result = validator.validate(authentication, context);

        assert !result.isEmpty();
        assert result.containsKey(PaymentError.CARD_EXPIRATION);
    }*/
}
