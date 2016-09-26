package com.android.decidir.example.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.decidir.example.R;
import com.android.decidir.example.domain.ErrorDetail;
import com.android.decidir.example.viewlistener.PaymentActivityListener;
import com.android.decidir.example.viewmodel.PaymentActivityModel;
import com.android.decidir.sdk.dto.AuthenticationWithToken;
import com.android.decidir.sdk.dto.AuthenticationWithoutToken;
import com.android.decidir.sdk.dto.CardHolderIdentification;
import com.decidir.sdk.dto.Payment;

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
    @InjectView(R.id.tvResult)
    TextView tvResult;
    @InjectView(R.id.etSecurityCodeWithTokenization)
    EditText etSecurityCodeWithTokenization;

    @InjectView(R.id.vPaymentDoWithoutTokenization)
    View vPaymentDoWithoutTokenization;
    @InjectView(R.id.vPaymentDoWithTokenization)
    View vPaymentDoWithTokenization;
    @InjectView(R.id.vPaymentResult)
    View vPaymentResult;
    @InjectView(R.id.vPaymentRequestLoading)
    View vPaymentRequestLoading;

    private String token;


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
        PaymentActivityModel model = new PaymentActivityModel(this);
        //etCreditCardNumber.setError("Invalid cardNumber");
        model.execute(getAuthentication());
    }

    @OnClick(R.id.bPayWithTokenization)
    public void payWithTokenization(){
        PaymentActivityModel model = new PaymentActivityModel(this);
        model.execute(getAuthenticationWithToken());
    }

    @OnClick(R.id.bAccept)
    public void accept(){
        this.finish();
    }

    public AuthenticationWithoutToken getAuthentication() {
        AuthenticationWithoutToken authenticationWithoutToken = new AuthenticationWithoutToken();
        authenticationWithoutToken.setCard_number(etCreditCardNumber.getText().toString());
        authenticationWithoutToken.setCard_expiration_month(etExpirationMonth.getText().toString());
        authenticationWithoutToken.setCard_expiration_year(etExpirationYear.getText().toString());
        authenticationWithoutToken.setSecurity_code(etSecurityCode.getText().toString());
        authenticationWithoutToken.setCard_holder_name(etName.getText().toString());
        CardHolderIdentification cardHolderIdentification = new CardHolderIdentification();
        cardHolderIdentification.setNumber(etDocumentNumber.getText().toString());
        cardHolderIdentification.setType(sDocumentType.getItemAtPosition(sDocumentType.getSelectedItemPosition()).toString());//TODO: cambiar a enum
        cardHolderIdentification.setNumber(etDocumentNumber.getText().toString());
        authenticationWithoutToken.setCard_holder_identification(cardHolderIdentification);
        return authenticationWithoutToken;
    }

    public AuthenticationWithToken getAuthenticationWithToken() {
        AuthenticationWithToken authenticationWithToken = new AuthenticationWithToken();
        authenticationWithToken.setToken(this.token);
        authenticationWithToken.setSecurity_code(etSecurityCodeWithTokenization.getText().toString());
        return authenticationWithToken;
    }
}
