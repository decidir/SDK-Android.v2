package com.android.decidir.example.viewmodel;

import android.os.AsyncTask;

import com.android.decidir.example.domain.PaymentError;
import com.android.decidir.example.viewlistener.PaymentActivityListener;
import com.android.decidir.sdk.Authenticate;
import com.android.decidir.sdk.exceptions.ApiException;
import com.android.decidir.sdk.exceptions.DecidirException;
import com.android.decidir.sdk.exceptions.NotFoundException;
import com.android.decidir.sdk.exceptions.ValidateException;
import com.android.decidir.sdk.dto.Authentication;
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
import com.decidir.sdk.dto.TicketingTItem;
import com.decidir.sdk.dto.TicketingTransactionData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by biandra on 17/08/16.
 */
public class PaymentActivityModel extends AsyncTask<Authentication, Void, Payment>{

    public static final String PUBLIC_API_KEY = "4657cfbe7d194fcc8e2b436a3e9384ab";
    public static final String PRIVATE_API_KEY = "47e7675a46c24a69afe00cd6ed425482";
    private PaymentActivityListener view;
    private PaymentError paymentError;

    public PaymentActivityModel(PaymentActivityListener view) {
        super();
        this.view = view;
    }

    public Payment pay(Authentication authentication, int installments){

        Authenticate authenticate = new Authenticate(PUBLIC_API_KEY, "http://127.0.0.1:19002", 10);
        Decidir decidir = new Decidir(PRIVATE_API_KEY, "http://127.0.0.1:19002", 20);

        try {
            DecidirResponse<AuthenticationResponse> responseAuthentication = authenticate.authenticate(authentication, "sessionID");
            com.decidir.sdk.dto.DecidirResponse<Payment> responsePayment = decidir.confirmPayment(
                    getPayment(responseAuthentication.getResult(),
                            installments,
                            authentication.getFraud_detection().getDevice_unique_id()));

        } catch (ApiException apiException){
            this.paymentError = new PaymentError(apiException.getMessage(), apiException.getErrorDetail().getMessage());
        } catch (NotFoundException notFoundException){
            this.paymentError = new PaymentError(notFoundException.getMessage(), notFoundException.getErrorDetail().getMessage());
        } catch (ValidateException validateException){
            this.paymentError = new PaymentError(validateException.getMessage(), validateException.getErrorDetail().toString());
        } catch (DecidirException decidirException){
            this.paymentError = new PaymentError(decidirException.getMessage(), String.valueOf(decidirException.getStatus()));
        } catch (com.decidir.sdk.exceptions.ApiException apiException){
            this.paymentError = new PaymentError(apiException.getMessage(), apiException.getErrorDetail().getMessage());
        } catch (com.decidir.sdk.exceptions.NotFoundException notFoundException){
            this.paymentError = new PaymentError(notFoundException.getMessage(), notFoundException.getErrorDetail().getMessage());
        } catch (com.decidir.sdk.exceptions.ValidateException validateException){
            this.paymentError = new PaymentError(validateException.getMessage(), validateException.getErrorDetail().toString());
        } catch (com.decidir.sdk.exceptions.DecidirException decidirException){
            this.paymentError = new PaymentError(decidirException.getMessage(), String.valueOf(decidirException.getStatus()));
        }
        return null;
    }

    private Payment getPayment(AuthenticationResponse authenticationResponse, int installments, String deviceUniqueIdentifier) {
        Payment payment = new Payment();
        payment.setSite_transaction_id("??");
        payment.setToken(authenticationResponse.getId());
        payment.setCard_brand(Card.AMEX);
        payment.setBin(authenticationResponse.getBin());
        payment.setAmount(2L);
        payment.setCurrency(Currency.ARS);
        payment.setInstallments(installments);
        payment.setDescription("description");
        payment.setPayment_type("single");
        //payment.setSub_payments();
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
        view.onGetPaymentStarted();
    }

    @Override
    protected Payment doInBackground(Authentication... params) {
        return pay(params[0], 1);//TODO: revome hardcode 1
    }

    @Override
    protected void onPostExecute(Payment payment) {
        if (payment != null) {
            view.onGetPaymentSuccess(payment);
        } else {
            view.onGetPaymentError(paymentError);
        }
    }

}
