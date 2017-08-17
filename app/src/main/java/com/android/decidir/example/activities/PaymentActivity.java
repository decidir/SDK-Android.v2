package com.android.decidir.example.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.decidir.example.R;
import com.android.decidir.example.domain.ErrorDetail;
import com.android.decidir.example.viewlistener.PaymentActivityListener;
import com.android.decidir.example.viewmodel.PaymentActivityModel;
import com.android.decidir.sdk.dto.IdentificationType;
import com.android.decidir.sdk.dto.PaymentTokenWithCardToken;
import com.android.decidir.sdk.dto.PaymentToken;
import com.android.decidir.sdk.dto.CardHolderIdentification;
import com.android.decidir.sdk.dto.PaymentError;
import com.android.decidir.sdk.validaters.PaymentTokenValidator;
import com.decidir.sdk.dto.Payment;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class PaymentActivity extends AppCompatActivity implements PaymentActivityListener {


    @InjectView(R.id.etCreditCardNumber)
    EditText etCreditCardNumber;
    @InjectView(R.id.etName)
    EditText etName;
    @InjectView(R.id.etExpirationMonth)
    EditText etExpirationMonth;
    @InjectView(R.id.etExpirationYear)
    EditText etExpirationYear;
    @InjectView(R.id.sDocumentType)
    Spinner sDocumentType;
    @InjectView(R.id.etDocumentNumber)
    EditText etDocumentNumber;
    @InjectView(R.id.etSecurityCode)
    EditText etSecurityCode;
    @InjectView(R.id.sInstallments)
    Spinner sInstallments;
    @InjectView(R.id.sInstallmentsWithToken)
    Spinner sInstallmentsWithToken;
    @InjectView(R.id.tvResult)
    TextView tvResult;
    @InjectView(R.id.etSecurityCodeWithTokenization)
    EditText etSecurityCodeWithTokenization;
    @InjectView(R.id.spinnerCS)
    Spinner spinnerCS;
    @InjectView(R.id.spinnerCSWithToken)
    Spinner spinnerCSWithToken;
    @InjectView(R.id.vPaymentDoWithoutTokenization)
    View vPaymentDoWithoutTokenization;
    @InjectView(R.id.vPaymentDoWithTokenization)
    View vPaymentDoWithTokenization;
    @InjectView(R.id.vPaymentResult)
    View vPaymentResult;
    @InjectView(R.id.vPaymentRequestLoading)
    View vPaymentRequestLoading;

    private String token;
    private PaymentTokenValidator validator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.inject(this);

        Intent intent = getIntent();
        token = intent.getStringExtra(TokenizationActivity.TOKEN);
        if (token == null){
            onGetPaymentStartedWithoutTokenization();
        }
        else{
            onGetPaymentStartedWithTokenization(token);
        }

        validator = new PaymentTokenValidator();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_payment, menu);
        return true;
    }

    @Override
    public void onGetPaymentStartedWithoutTokenization() {
        vPaymentDoWithoutTokenization.setVisibility(View.VISIBLE);
        vPaymentDoWithTokenization.setVisibility(View.GONE);
        vPaymentResult.setVisibility(View.GONE);
        vPaymentRequestLoading.setVisibility(View.GONE);
    }

    @Override
    public void onGetPaymentStartedWithTokenization(String token) {
        vPaymentDoWithoutTokenization.setVisibility(View.GONE);
        vPaymentDoWithTokenization.setVisibility(View.VISIBLE);
        vPaymentResult.setVisibility(View.GONE);
        vPaymentRequestLoading.setVisibility(View.GONE);
    }

    @Override
    public void onGetPaymentLoading() {
        vPaymentDoWithoutTokenization.setVisibility(View.GONE);
        vPaymentDoWithTokenization.setVisibility(View.GONE);
        vPaymentResult.setVisibility(View.GONE);
        vPaymentRequestLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void onGetPaymentSuccess(Payment payment) {
        vPaymentDoWithoutTokenization.setVisibility(View.GONE);
        vPaymentDoWithTokenization.setVisibility(View.GONE);
        vPaymentResult.setVisibility(View.VISIBLE);
        vPaymentRequestLoading.setVisibility(View.GONE);

        tvResult.setText("Succes Payment with id: " + payment.getId());
    }

    @Override
    public void onGetPaymentError(ErrorDetail errorDetail) {
        vPaymentDoWithoutTokenization.setVisibility(View.GONE);
        vPaymentDoWithTokenization.setVisibility(View.GONE);
        vPaymentResult.setVisibility(View.VISIBLE);
        vPaymentRequestLoading.setVisibility(View.GONE);
        tvResult.setText("Error: " + errorDetail.getMessage() + ", description: " + errorDetail.getDescription());
    }

    @OnClick(R.id.bPayWithoutTokenization)
    public void payWithoutTokenization(){
        final PaymentToken authentication = getPaymentToken();
        Map<PaymentError, String> validation = validator.validate(authentication, getApplicationContext());
        if (validation.isEmpty()){
            PaymentActivityModel model = new PaymentActivityModel(this);
            model.execute(new ArrayList<Object>() {{
                add(authentication);
                add(sInstallments.getSelectedItem());
                add(spinnerCS.getSelectedItemPosition());
                add(etName.getText().toString());
            }});
        } else {
            showErrors(authentication, validation);
        }
    }

    @OnClick(R.id.bPayWithTokenization)
    public void payWithTokenization(){
        final PaymentTokenWithCardToken authentication = getPaymentTokenWithCardToken();
        Map<PaymentError, String> validation = validator.validate(authentication, getApplicationContext());
        if (validation.isEmpty()){
            PaymentActivityModel model = new PaymentActivityModel(this);
            model.execute(new ArrayList<Object>() {{
                add(authentication);
                add(sInstallmentsWithToken.getSelectedItem());
                add(spinnerCSWithToken.getSelectedItemPosition());
            }});
        } else {
            showErrors(validation);
        }
    }

    private void showErrors(Map<PaymentError, String> validation) {
        etSecurityCodeWithTokenization.setError(validation.get(PaymentError.SECURITY_CODE));
    }

    private void showErrors(PaymentToken paymentToken, Map<PaymentError, String> validation) {
        etCreditCardNumber.setError(validation.get(PaymentError.CARD_NUMBER));
        etName.setError(validation.get(PaymentError.CARD_HOLDER_NAME));
        etSecurityCode.setError(validation.get(PaymentError.SECURITY_CODE));
        etDocumentNumber.setError(validation.get(PaymentError.TYPE_ID));
        etExpirationMonth.setError(validation.get(PaymentError.CARD_EXPIRATION_MONTH));
        etExpirationYear.setError(validation.get(PaymentError.CARD_EXPIRATION_YEAR));
        if (validation.get(PaymentError.CARD_EXPIRATION) != null){
            Toast.makeText(getApplicationContext(), "CARD EXPIRATED", Toast.LENGTH_SHORT).show();
        }
    }

    @OnClick(R.id.bAccept)
    public void accept(){
        this.finish();
    }

    public PaymentToken getPaymentToken() {
        PaymentToken paymentToken = new PaymentToken();
        paymentToken.setCard_number(etCreditCardNumber.getText().toString());
        paymentToken.setCard_expiration_month(etExpirationMonth.getText().toString());
        paymentToken.setCard_expiration_year(etExpirationYear.getText().toString());
        paymentToken.setSecurity_code(etSecurityCode.getText().toString());
        paymentToken.setCard_holder_name(etName.getText().toString());
        CardHolderIdentification cardHolderIdentification = new CardHolderIdentification();
        cardHolderIdentification.setNumber(etDocumentNumber.getText().toString());
        cardHolderIdentification.setType(IdentificationType.DNI);//sDocumentType.getItemAtPosition(sDocumentType.getSelectedItemPosition()).toString());//TODO: cambiar a enum
        cardHolderIdentification.setNumber(etDocumentNumber.getText().toString());
        paymentToken.setCard_holder_identification(cardHolderIdentification);
        paymentToken.setCard_holder_birthday(new Date());
        paymentToken.setCard_holder_door_number(3);
        return paymentToken;
    }

    public PaymentTokenWithCardToken getPaymentTokenWithCardToken() {
        PaymentTokenWithCardToken paymentTokenWithCardToken = new PaymentTokenWithCardToken();
        paymentTokenWithCardToken.setToken(this.token);
        paymentTokenWithCardToken.setSecurity_code(etSecurityCodeWithTokenization.getText().toString());
        return paymentTokenWithCardToken;
    }
}
