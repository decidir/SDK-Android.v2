package com.android.decidir.example.viewmodel;

import android.os.AsyncTask;

import com.android.decidir.example.Constants;
import com.android.decidir.example.domain.ErrorDetail;
import com.android.decidir.example.domain.CardTokenRequest;
import com.android.decidir.example.viewlistener.TokenizationActivityListener;
import com.decidir.sdk.Decidir;
import com.decidir.sdk.dto.tokens.CardTokens;

/**
 * Created by biandra on 23/09/16.
 */
public class TokenizationActivityModel extends AsyncTask<CardTokenRequest, Void, CardTokens> {

    private TokenizationActivityListener view;
    private ErrorDetail errorDetail;

    public TokenizationActivityModel(TokenizationActivityListener view) {
        super();
        this.view = view;
    }

    @Override
    protected void onPreExecute(){
        view.onGetCardTokenStarted();
    }

    @Override
    protected CardTokens doInBackground(CardTokenRequest... params) {
        CardTokenRequest cardTokenRequest = params[0];
        if (cardTokenRequest.getToken() != null){
            return deleteCardToken(cardTokenRequest);
        } else {
            return getCardTokens(cardTokenRequest);
        }

    }

    private CardTokens deleteCardToken(CardTokenRequest cardTokenRequest) {
        Decidir decidir = new Decidir(Constants.PRIVATE_API_KEY, Constants.URL, 20);
        try{
            com.decidir.sdk.dto.DecidirResponse<Void> responseDelete = decidir.deleteCardToken(cardTokenRequest.getToken());
            return getCardTokens(cardTokenRequest);
        } catch (com.decidir.sdk.exceptions.ApiException apiException){
            this.errorDetail = new ErrorDetail(apiException.getMessage(), apiException.getErrorDetail().getMessage());
        } catch (com.decidir.sdk.exceptions.NotFoundException notFoundException){
            this.errorDetail = new ErrorDetail(notFoundException.getMessage(), notFoundException.getErrorDetail().getMessage());
        } catch (com.decidir.sdk.exceptions.ValidateException validateException){
            this.errorDetail = new ErrorDetail(validateException.getMessage(), validateException.getErrorDetail().toString());
        } catch (com.decidir.sdk.exceptions.DecidirException decidirException){
            this.errorDetail = new ErrorDetail(decidirException.getMessage(), String.valueOf(decidirException.getStatus()));
        }
        return null;
    }

    private CardTokens getCardTokens(CardTokenRequest cardTokenRequest) {
        Decidir decidir = new Decidir(Constants.PRIVATE_API_KEY, Constants.URL, 20);
        try{
            com.decidir.sdk.dto.DecidirResponse<CardTokens> responsePayment = decidir.getCardTokens(cardTokenRequest.getUserSiteId(),
                    cardTokenRequest.getBin(),
                    cardTokenRequest.getLastFourDigits(),
                    cardTokenRequest.getExpirationMonth(),
                    cardTokenRequest.getExpirationYear());
            return responsePayment.getResult();
        } catch (com.decidir.sdk.exceptions.ApiException apiException){
            this.errorDetail = new ErrorDetail(apiException.getMessage(), apiException.getErrorDetail().getMessage());
        } catch (com.decidir.sdk.exceptions.NotFoundException notFoundException){
            this.errorDetail = new ErrorDetail(notFoundException.getMessage(), notFoundException.getErrorDetail().getMessage());
        } catch (com.decidir.sdk.exceptions.ValidateException validateException){
            this.errorDetail = new ErrorDetail(validateException.getMessage(), validateException.getErrorDetail().toString());
        } catch (com.decidir.sdk.exceptions.DecidirException decidirException){
            this.errorDetail = new ErrorDetail(decidirException.getMessage(), String.valueOf(decidirException.getStatus()));
        }
        return null;
    }

    @Override
    protected void onPostExecute(CardTokens cardTokens) {
        if (cardTokens != null) {
            view.onGetCardTokenSuccess(cardTokens);
        } else {
            view.onGetCardTokenError(errorDetail);
        }
    }
}
