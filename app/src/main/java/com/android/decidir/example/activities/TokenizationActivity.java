package com.android.decidir.example.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.decidir.example.R;
import com.android.decidir.example.adapters.BtnClickListener;
import com.android.decidir.example.adapters.CardTokenAdapter;
import com.android.decidir.example.domain.CardTokenRequest;
import com.android.decidir.example.domain.ErrorDetail;
import com.android.decidir.example.viewlistener.TokenizationActivityListener;
import com.android.decidir.example.viewmodel.TokenizationActivityModel;
import com.decidir.sdk.dto.CardToken;
import com.decidir.sdk.dto.CardTokens;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by biandra on 23/09/16.
 */
public class TokenizationActivity extends AppCompatActivity implements TokenizationActivityListener{

    public static final String TOKEN = "TOKEN";
    @InjectView(R.id.tvCardTokensErrorResult)
    TextView tvCardTokensErrorResult;
    @InjectView(R.id.etUserId)
    EditText etUserId;
    @InjectView(R.id.lvCardTokens)
    ListView lvCardTokens;
    @InjectView(R.id.tvListCardTokens)
    TextView tvListCardTokens;



    @InjectView(R.id.vCardTokensDo)
    View vCardTokensDo;
    @InjectView(R.id.vCardTokensResult)
    View vCardTokensResult;
    @InjectView(R.id.vCardTokensErrorResult)
    View vCardTokensErrorResult;
    @InjectView(R.id.vCardTokensRequestLoading)
    View vCardTokensRequestLoading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tokenization);
        ButterKnife.inject(this);
    }

    @Override
    public void onGetCardTokenStarted() {
        vCardTokensDo.setVisibility(View.VISIBLE);
        vCardTokensErrorResult.setVisibility(View.GONE);
        vCardTokensResult.setVisibility(View.GONE);
        vCardTokensRequestLoading.setVisibility(View.GONE);
    }

    @Override
    public void onGetCardTokenSuccess(CardTokens cardTokens) {
        vCardTokensDo.setVisibility(View.GONE);
        vCardTokensErrorResult.setVisibility(View.GONE);
        vCardTokensResult.setVisibility(View.VISIBLE);
        vCardTokensRequestLoading.setVisibility(View.GONE);
        showCardTokens(cardTokens.getTokens());
        tvListCardTokens.setText("Cards of user: " + etUserId.getText().toString());
    }

    private void showCardTokens(List<CardToken> cardTokens) {
        this.lvCardTokens.setAdapter(new CardTokenAdapter(this, cardTokens, new BtnClickListener() {
            @Override
            public void onBtnClick(int position) {
                CardToken cardToken = (CardToken) lvCardTokens.getAdapter().getItem(position);
                TokenizationActivityModel model = new TokenizationActivityModel(TokenizationActivity.this);
                CardTokenRequest cardTokenRequest = getCardTokenRequest();
                cardTokenRequest.setToken(cardToken.getToken());
                model.execute(cardTokenRequest);
            }
        }));

        this.lvCardTokens.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                CardToken cardToken = (CardToken) lvCardTokens.getAdapter().getItem(position);
                if (cardToken.getExpired()){
                    Toast.makeText(getApplicationContext(), "HAS EXPIRATED", Toast.LENGTH_SHORT).show();
                } else{
                    Intent intent = new Intent(getBaseContext(), PaymentActivity.class);
                    intent.putExtra(TOKEN, cardToken.getToken());
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    public void onGetCardTokenError(ErrorDetail errorDetail) {
        vCardTokensDo.setVisibility(View.GONE);
        vCardTokensErrorResult.setVisibility(View.VISIBLE);
        vCardTokensResult.setVisibility(View.GONE);
        vCardTokensRequestLoading.setVisibility(View.GONE);
        tvCardTokensErrorResult.setText("Error: " + errorDetail.getMessage() + ", description: " + errorDetail.getDescription());
    }

    @OnClick(R.id.bNewSearchCardToken)
    public void newSearch(){
        onGetCardTokenStarted();
    }

    @OnClick(R.id.bNewPay)
    public void newPay(){
        startActivity(new Intent(getBaseContext(), PaymentActivity.class));
    }


    @OnClick(R.id.bSearchCardToken)
    public void search(){
        TokenizationActivityModel model = new TokenizationActivityModel(this);
        model.execute(getCardTokenRequest());
    }

    @OnClick(R.id.bCardTokensErrorAccept)
    public void accept(){
        vCardTokensDo.setVisibility(View.VISIBLE);
        vCardTokensErrorResult.setVisibility(View.GONE);
        vCardTokensResult.setVisibility(View.GONE);
        vCardTokensRequestLoading.setVisibility(View.GONE);
    }

    public CardTokenRequest getCardTokenRequest() {
        CardTokenRequest cardTokenRequest = new CardTokenRequest();
        cardTokenRequest.setUserSiteId(etUserId.getText().toString());
        return cardTokenRequest;
    }

    @Override
    public void onResume() {
        super.onResume();
        onGetCardTokenStarted();
    }
}
