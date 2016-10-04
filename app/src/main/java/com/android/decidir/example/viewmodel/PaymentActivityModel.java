package com.android.decidir.example.viewmodel;

import android.os.AsyncTask;

import com.android.decidir.example.Constants;
import com.android.decidir.example.DecidirApp;
import com.android.decidir.example.domain.ErrorDetail;
import com.android.decidir.example.viewlistener.PaymentActivityListener;
import com.android.decidir.sdk.Authenticate;
import com.android.decidir.sdk.dto.Authentication;
import com.android.decidir.sdk.dto.AuthenticationWithToken;
import com.android.decidir.sdk.exceptions.ApiException;
import com.android.decidir.sdk.exceptions.DecidirException;
import com.android.decidir.sdk.exceptions.NotFoundException;
import com.android.decidir.sdk.exceptions.ValidateException;
import com.android.decidir.sdk.dto.AuthenticationWithoutToken;
import com.android.decidir.sdk.dto.AuthenticationResponse;
import com.android.decidir.sdk.dto.DecidirResponse;
import com.decidir.sdk.dto.BillingData;
import com.decidir.sdk.Decidir;
import com.decidir.sdk.dto.Card;
import com.decidir.sdk.dto.Channel;
import com.decidir.sdk.dto.Currency;
import com.decidir.sdk.dto.CustomerInSite;
import com.decidir.sdk.dto.FraudDetectionData;
import com.decidir.sdk.dto.Payment;
import com.decidir.sdk.dto.PurchaseTotals;
import com.decidir.sdk.dto.SubPayment;
import com.decidir.sdk.dto.TicketingTItem;
import com.decidir.sdk.dto.TicketingTransactionData;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.UUID;

/**
 * Created by biandra on 17/08/16.
 */
public class PaymentActivityModel extends AsyncTask<Authentication, Void, Payment>{

    private PaymentActivityListener view;
    private ErrorDetail errorDetail;

    public PaymentActivityModel(PaymentActivityListener view) {
        super();
        this.view = view;
    }

    public Payment pay(AuthenticationWithoutToken authenticationWithoutToken, int installments){
        Boolean withCybersource = true;
        Authenticate authenticate = new Authenticate(Constants.PUBLIC_API_KEY, Constants.URL, 10);
        Decidir decidir = new Decidir(Constants.PRIVATE_API_KEY, Constants.URL, 20);
        DecidirResponse<AuthenticationResponse> responseAuthentication = authenticate.authenticate(authenticationWithoutToken, DecidirApp.getAppContext(), getSessionID(), withCybersource);
        com.decidir.sdk.dto.DecidirResponse<Payment> responsePayment = decidir.confirmPayment(
                getPayment(responseAuthentication.getResult(),
                        installments,
                        (withCybersource) ? authenticationWithoutToken.getFraud_detection().getDevice_unique_identifier() : null));
        return responsePayment.getResult();
    }


    public Payment pay(AuthenticationWithToken authenticationWithToken, int installments){
        Boolean withCybersource = true;
        Authenticate authenticate = new Authenticate(Constants.PUBLIC_API_KEY, Constants.URL, 10);
        Decidir decidir = new Decidir(Constants.PRIVATE_API_KEY, Constants.URL, 20);
        DecidirResponse<AuthenticationResponse> responseAuthentication = authenticate.authenticate(authenticationWithToken, DecidirApp.getAppContext(), getSessionID(), withCybersource);
        com.decidir.sdk.dto.DecidirResponse<Payment> responsePayment = decidir.confirmPayment(
                getPayment(responseAuthentication.getResult(),
                        installments,
                        (withCybersource) ? authenticationWithToken.getFraud_detection().getDevice_unique_identifier() : null));
        return responsePayment.getResult();
    }

    private Payment getPayment(AuthenticationResponse authenticationResponse, int installments, String deviceUniqueIdentifier) {
        Payment payment = new Payment();
        payment.setSite_transaction_id(String.valueOf(GregorianCalendar.getInstance().getTimeInMillis()));
        payment.setToken(authenticationResponse.getId());
        payment.setUser_id("biandra");
        payment.setCard_brand(Card.VISA);
        payment.setBin(authenticationResponse.getBin());
        payment.setAmount(2L);
        payment.setCurrency(Currency.ARS);
        payment.setInstallments(installments);
        payment.setDescription("description");
        payment.setPayment_type("single");
        payment.setSub_payments(new ArrayList<SubPayment>());
        BillingData billingData = new BillingData();
        billingData.setCity("Buenos Aires");
        billingData.setCountry("AR");
        billingData.setCustomer_id("maxiid");
        billingData.setEmail("accept@decidir.com.ar");
        billingData.setFirst_name("Maxi");
        billingData.setLast_name("Biandratti");
        billingData.setPhone_number("1547766329");
        billingData.setPostal_code("1427");
        billingData.setState("BA");
        billingData.setStreet1("Thames 677");
        billingData.setIp_address("127.0.0.1");
        FraudDetectionData fraudDetection = new FraudDetectionData();
        fraudDetection.setBill_to(billingData);
        PurchaseTotals purchaseTotals = new PurchaseTotals();
        purchaseTotals.setAmount(2L);
        purchaseTotals.setCurrency(Currency.ARS);
        fraudDetection.setPurchase_totals(purchaseTotals);
        fraudDetection.setChannel(Channel.WEB);
        CustomerInSite customerInSite = new CustomerInSite();
        customerInSite.setDays_in_site(243);
        customerInSite.setIs_guest(false);
        customerInSite.setPassword("abracadabra");
        customerInSite.setNum_of_transactions(1);
        customerInSite.setCellphone_number("12121");
        fraudDetection.setCustomer_in_site(customerInSite);
        fraudDetection.setDevice_unique_id(deviceUniqueIdentifier);
        TicketingTransactionData ticketingTransactionData = new TicketingTransactionData();
        ticketingTransactionData.setDays_to_event(55);
        ticketingTransactionData.setDelivery_type("Pick up");
        List<TicketingTItem> items = new ArrayList<>();
        TicketingTItem ticketingTItem = new TicketingTItem();
        ticketingTItem.setCode("popblacksabbat2016");
        ticketingTItem.setDescription("Popular Black Sabbath 2016");
        ticketingTItem.setName("popblacksabbat2016ss");
        ticketingTItem.setSku("asas");
        ticketingTItem.setTotal_amount(2L);
        ticketingTItem.setQuantity(1);
        ticketingTItem.setUnit_price(2L);
        items.add(ticketingTItem);
        ticketingTransactionData.setItems(items);
        fraudDetection.setTicketing_transaction_data(ticketingTransactionData);

        return payment;
    }


    @Override
    protected void onPreExecute(){
        view.onGetPaymentLoading();
    }

    @Override
    protected Payment doInBackground(Authentication... params) {
        try {
            if (params[0] instanceof AuthenticationWithoutToken) {
                return pay((AuthenticationWithoutToken) params[0], 1);//TODO: revome hardcode 1
            } else if (params[0] instanceof AuthenticationWithToken) {
                return pay((AuthenticationWithToken) params[0], 1);//TODO: revome hardcode 1
            }
        }
        catch (ApiException apiException){
            this.errorDetail = new ErrorDetail(apiException.getMessage(), apiException.getErrorDetail().getMessage());
        } catch (NotFoundException notFoundException){
            this.errorDetail = new ErrorDetail(notFoundException.getMessage(), notFoundException.getErrorDetail().getMessage());
        } catch (ValidateException validateException){
            this.errorDetail = new ErrorDetail(validateException.getMessage(), validateException.getErrorDetail().toString());
        } catch (DecidirException decidirException){
            this.errorDetail = new ErrorDetail(decidirException.getMessage(), String.valueOf(decidirException.getStatus()));
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
    protected void onPostExecute(Payment payment) {
        if (payment != null) {
            view.onGetPaymentSuccess(payment);
        } else {
            view.onGetPaymentError(errorDetail);
        }
    }

    public String getSessionID() {
        return UUID.randomUUID().toString();
    }
}
