package com.android.decidir.example.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.decidir.example.R;
import com.android.decidir.example.domain.PaymentError;
import com.android.decidir.example.viewlistener.PaymentActivityListener;
import com.android.decidir.example.viewmodel.PaymentActivityModel;
import com.android.decidir.sdk.dto.Authentication;
import com.android.decidir.sdk.dto.CardHolderIdentification;
import com.decidir.sdk.dto.Payment;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class PaymentActivity extends AppCompatActivity implements PaymentActivityListener {


    @InjectView(R.id.etCreditCardNumber)
    EditText etName;
    @InjectView(R.id.etName)
    EditText etCreditCardNumber;
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

    @InjectView(R.id.vPaymentDo)
    View vPaymentDo;
    @InjectView(R.id.vPaymentResult)
    View vPaymentResult;
    @InjectView(R.id.vPaymentRequestLoading)
    View vPaymentRequestLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        ButterKnife.inject(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_payment, menu);
        return true;
    }

    @Override
    public void onGetPaymentStarted() {
        vPaymentDo.setVisibility(View.GONE);
        vPaymentResult.setVisibility(View.GONE);
        vPaymentRequestLoading.setVisibility(View.VISIBLE);
    }

    @Override
    public void onGetPaymentSuccess(Payment payment) {
        vPaymentDo.setVisibility(View.GONE);
        vPaymentResult.setVisibility(View.VISIBLE);
        vPaymentRequestLoading.setVisibility(View.GONE);

        tvResult.setText("Succes Payment with id: " + payment.getId());
    }

    @Override
    public void onGetPaymentError(PaymentError paymentError) {
        vPaymentDo.setVisibility(View.GONE);
        vPaymentResult.setVisibility(View.VISIBLE);
        vPaymentRequestLoading.setVisibility(View.GONE);
        tvResult.setText("Error: " + paymentError.getMessage() + ", description: " + paymentError.getDescription());
    }

    @OnClick(R.id.bPay)
    public void pay(){
        PaymentActivityModel model = new PaymentActivityModel(this);
        model.execute(getAuthentication());
    }

    @OnClick(R.id.bAccept)
    public void accept(){
        vPaymentDo.setVisibility(View.VISIBLE);
        vPaymentResult.setVisibility(View.GONE);
        vPaymentRequestLoading.setVisibility(View.GONE);
    }

    public Authentication getAuthentication() {
        Authentication authentication = new Authentication();
        authentication.setCard_number(etCreditCardNumber.getText().toString());
        authentication.setCard_expiration_month(etExpirationMonth.getText().toString());
        authentication.setCard_expiration_year(etExpirationYear.getText().toString());
        authentication.setSecurity_code(etSecurityCode.getText().toString());
        authentication.setCard_holder_name(etName.getText().toString());
        CardHolderIdentification cardHolderIdentification = new CardHolderIdentification();
        cardHolderIdentification.setNumber(etDocumentNumber.getText().toString());
        cardHolderIdentification.setType(sDocumentType.getItemAtPosition(sDocumentType.getSelectedItemPosition()).toString());//TODO: cambiar a enum
        cardHolderIdentification.setNumber(etDocumentNumber.getText().toString());
        return authentication;
    }
}
