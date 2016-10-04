package com.android.decidir.sdk.validaters;

import android.content.Context;
import android.content.res.Resources;

import com.android.decidir.sdk.R;
import com.android.decidir.sdk.dto.AuthenticationWithToken;
import com.android.decidir.sdk.dto.AuthenticationWithoutToken;
import com.android.decidir.sdk.dto.PaymentError;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by biandra on 30/09/16.
 */
public class AuthenticationValidator {

    private Context context;

    public Map<PaymentError, String> validate(AuthenticationWithoutToken authentication, Context context){
        this.context = context;
        Map<PaymentError, String> validation = new HashMap<>();
        creditCardNumberValidator(authentication.getCard_number(), validation);
        cvcValidate(authentication.getSecurity_code(), validation);
        expiryDateValidator(authentication.getCard_expiration_month(), authentication.getCard_expiration_year(), validation);
        cardHolderNameValidator(authentication.getCard_holder_name(), validation);
        dniValidator(authentication.getCard_holder_identification().getNumber(), validation);
        return validation;
    }

    private void dniValidator(String dni, Map<PaymentError, String> validation) {
        if (!matcheNumber(dni)) {
            validation.put(PaymentError.DNI, context.getResources().getString(R.string.dni_validate));
        }
    }

    private void cardHolderNameValidator(String cardHolderName, Map<PaymentError, String> validation) {
        if (cardHolderName.isEmpty()){
            validation.put(PaymentError.CARD_HOLDER_NAME, context.getResources().getString(R.string.card_holder_name_validate));
        }
    }

    private void expiryDateValidator(String cardExpirationMonth, String cardExpirationYear, Map<PaymentError, String> validation) {
        boolean validCEM = validExpirationMonth(cardExpirationMonth);
        boolean validCEY = validExpirationYear(cardExpirationYear);
        if (!validCEM) {
            validation.put(PaymentError.CARD_EXPIRATION_MONTH, context.getResources().getString(R.string.card_expiration_month_validate));
        }
        if (!validCEY) {
            validation.put(PaymentError.CARD_EXPIRATION_YEAR, context.getResources().getString(R.string.card_expiration_year_validate));
        }
        if(validCEM && validCEY){
            SimpleDateFormat dateFormat = new SimpleDateFormat("MM/yyyy");
            Calendar cal = Calendar.getInstance();
            try {
                Date currentDate = dateFormat.parse(String.valueOf(cal.get(Calendar.MONTH) + 1) + "/" + String.valueOf(cal.get(Calendar.YEAR)));
                Date expirationDate = dateFormat.parse(cardExpirationMonth + "/" + "20" + cardExpirationYear);
                if (expirationDate.before(currentDate)) {
                    validation.put(PaymentError.CARD_EXPIRATION, context.getResources().getString(R.string.card_expiration_validate));
                }
            } catch (ParseException e) {
                validation.put(PaymentError.CARD_EXPIRATION, context.getResources().getString(R.string.card_expiration_validate));

            }
        }
    }

    private boolean validExpirationMonth(String cardExpirationMonth) {
        return !cardExpirationMonth.isEmpty() && cardExpirationMonth.matches("1[0-2]|0[1-9]");
    }

    private boolean validExpirationYear(String cardExpirationYear) {
        return !cardExpirationYear.isEmpty() && cardExpirationYear.matches("\\d{2}");
    }

    private void creditCardNumberValidator(String card_number, Map<PaymentError, String> validation) {
        String cardType = getCardType(card_number);
        boolean validLuhn = luhnValidator(card_number);
        if (!matcheNumber(card_number) ||
                (!validLuhn && !isNaranja(cardType)) ||
                !validLuhn) {
            validation.put(PaymentError.CARD_NUMBER, context.getResources().getString(R.string.card_number_validate));
        }
    }

    private boolean isNaranja(String cardType) {
        return "naranja".equals(cardType);
    }

    private String getCardType(String cardNumber) {
        List<IssuingNetworks> issuingNetworkses = getIssuingNetworks();
        for (int i=0; i>= issuingNetworkses.size(); i++){
            if (cardNumber.matches(issuingNetworkses.get(i).regEx)){
                return issuingNetworkses.get(i).name;
            }
        }
        return null;
    }

    public Map<PaymentError, String> validate(AuthenticationWithToken authentication, Context context){
        this.context = context;
        Map<PaymentError, String> validation = new HashMap<>();
        cvcValidate(authentication.getSecurity_code(), validation);
        return validation;
    }

    private void cvcValidate(String securityCode, Map<PaymentError, String> validation) {
        if (!matcheNumber(securityCode)) {
            validation.put(PaymentError.SECURITY_CODE, context.getResources().getString(R.string.cvc_validate));
        }
    }

    private boolean matcheNumber(String value){
        return !value.isEmpty() && value.matches("\\d+");
    }


    private boolean luhnValidator(String ccNumber)
    {
        ccNumber.toString().replace("[ .-]", "");
        int total = 0;
        boolean alternate = false;
        for (int i = ccNumber.length() - 1; i >= 0; i--)
        {
            int curDigit = Integer.parseInt(ccNumber.substring(i, i + 1));
            if (alternate)
            {
                curDigit *= 2;
                if (curDigit > 9)
                    curDigit -= 9;
            }
            total += curDigit;
            alternate = !alternate;
        }
        return total % 10 == 0;
    }


    private List<IssuingNetworks> getIssuingNetworks() {
        List<IssuingNetworks> issuingNetworkses = new ArrayList<>();
        issuingNetworkses.add(new IssuingNetworks("visa", "^4[0-9]{12}(?:[0-9]{3})?$"));
        issuingNetworkses.add(new IssuingNetworks("mastercard", "^5[1-5][0-9]{14}$"));
        issuingNetworkses.add(new IssuingNetworks("amex", "^3[47][0-9]{13}$"));
        issuingNetworkses.add(new IssuingNetworks("Carte Blanche Card", "^389[0-9]{11}$"));
        issuingNetworkses.add(new IssuingNetworks("discover", "^65[4-9][0-9]{13}|64[4-9][0-9]{13}|6011[0-9]{12}|(622(?:12[6-9]|1[3-9][0-9]|[2-8][0-9][0-9]|9[01][0-9]|92[0-5])[0-9]{10})$"));
        //issuingNetworkses.add(new IssuingNetworks("jcb", "^(?:2131|1800|35\d{3})\d{11}$"));
        issuingNetworkses.add(new IssuingNetworks("visamaster", "^(?:4[0-9]{12}(?:[0-9]{3})?|5[1-5][0-9]{14})$"));
        issuingNetworkses.add(new IssuingNetworks("insta", "^63[7-9][0-9]{13}$"));
        issuingNetworkses.add(new IssuingNetworks("laser", "^(6304|6706|6709|6771)[0-9]{12,15}$"));
        issuingNetworkses.add(new IssuingNetworks("maestro", "^(5018|5020|5038|6304|6759|6761|6763)[0-9]{8,15}$"));
        issuingNetworkses.add(new IssuingNetworks("solo", "^(6334|6767)[0-9]{12}|(6334|6767)[0-9]{14}|(6334|6767)[0-9]{15}$/"));
        issuingNetworkses.add(new IssuingNetworks("switch", "^(4903|4905|4911|4936|6333|6759)[0-9]{12}|(4903|4905|4911|4936|6333|6759)[0-9]{14}|(4903|4905|4911|4936|6333|6759)[0-9]{name: {15}|564182[0-9]{10}|564182[0-9]{12}|564182[0-9]{13}|633110[0-9]{10}|633110[0-9]{12}|633110[0-9]{13}$"));
        issuingNetworkses.add(new IssuingNetworks("union", "^(62[0-9]{14,17})$"));
        issuingNetworkses.add(new IssuingNetworks("korean", "^9[0-9]{15}$"));
        issuingNetworkses.add(new IssuingNetworks("bcglobal", "^(6541|6556)[0-9]{12}$"));
        issuingNetworkses.add(new IssuingNetworks("naranja", "^589562[0-9]{10}$"));
        return issuingNetworkses;
    }

    class IssuingNetworks {
        String name;
        String regEx;
        public IssuingNetworks(String name, String regEx){
            this.name = name;
            this.regEx = regEx;
        }
    }

}
