package com.android.decidir.example.services;

import android.os.AsyncTask;

import com.android.decidir.example.Constants;
import com.android.decidir.example.domain.ErrorDetail;
import com.android.decidir.example.viewlistener.PaymentActivityListener;
import com.android.decidir.sdk.dto.PaymentTokenResponse;
import com.decidir.sdk.Decidir;
import com.decidir.sdk.dto.cybersource.BillingData;
import com.decidir.sdk.dto.cybersource.Channel;
import com.decidir.sdk.dto.cybersource.Item;
import com.decidir.sdk.dto.cybersource.PurchaseTotals;
import com.decidir.sdk.dto.cybersource.verticals.ticketing.TicketingFraudDetectionData;
import com.decidir.sdk.dto.cybersource.verticals.ticketing.TicketingTransactionData;
import com.decidir.sdk.dto.forms.CustomerInSite;
import com.decidir.sdk.dto.payments.Currency;
import com.decidir.sdk.dto.payments.Customer;
import com.decidir.sdk.dto.payments.PaymentRequest;
import com.decidir.sdk.dto.payments.PaymentResponse;
import com.decidir.sdk.dto.payments.PaymentType;
import com.decidir.sdk.dto.payments.SubPayment;
import com.decidir.sdk.payments.Payment;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * Created by biandra on 01/10/17.
 */

public class PaymentService extends AsyncTask<ArrayList<Object>, Void, Payment> {

    private PaymentActivityListener view;
    private ErrorDetail errorDetail;

    public PaymentService(PaymentActivityListener view) {
        super();
        this.view = view;
    }

    private Payment pay(PaymentTokenResponse paymentTokenResponse, int installments, int verticalCS, String userId, String deviceUniqueIdentifier){
        PaymentRequest payment = getPayment(paymentTokenResponse, installments);
        if (userId != null) {
            Customer customer = new Customer();
            customer.setId(userId);
            payment.setCustomer(customer);
        }
        switch (verticalCS){
            case 1: {
                payment.setFraud_detection(getTicketingVertical(deviceUniqueIdentifier));
                break;
            }
            case 2: {
                payment.setFraud_detection(getTicketingVertical(deviceUniqueIdentifier));
                break;
            }
        }

        Decidir decidir = new Decidir(Constants.PRIVATE_API_KEY, Constants.URL, 20);
        com.decidir.sdk.dto.DecidirResponse<PaymentResponse> responsePayment = decidir.payment(payment);
        return responsePayment.getResult();
    }

    private PaymentRequest getPayment(PaymentTokenResponse paymentTokenResponse, int installments) {
        PaymentRequest payment = new PaymentRequest();
        payment.setSite_transaction_id(String.valueOf(GregorianCalendar.getInstance().getTimeInMillis()));
        payment.setToken(paymentTokenResponse.getId());
        payment.setPayment_method_id(1);
        payment.setBin(paymentTokenResponse.getBin());
        payment.setAmount(2L);
        payment.setCurrency(Currency.ARS);
        payment.setInstallments(installments);
        payment.setPayment_type(PaymentType.SINGLE);
        payment.setSub_payments(new ArrayList<SubPayment>());
        return payment;
    }

    private TicketingFraudDetectionData getTicketingVertical(String deviceUniqueIdentifier) {
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

        TicketingFraudDetectionData fraudDetection = new TicketingFraudDetectionData();
        fraudDetection.setSend_to_cs(false);
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
        List<Item> items = new ArrayList<>();
        Item ticketingTItem = new Item();
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
        return fraudDetection;
    }


    @Override
    protected Payment doInBackground(ArrayList<Object>... params) {
        try {
            return pay((PaymentTokenResponse) params[0].get(0), (Integer) params[0].get(1), (Integer) params[0].get(2), (String) params[0].get(3), (String) params[0].get(4));
        }
        catch (com.decidir.sdk.exceptions.ApiException apiException){
            this.errorDetail = new ErrorDetail(apiException.getMessage(), apiException.getErrorDetail().getMessage());
        } catch (com.decidir.sdk.exceptions.NotFoundException notFoundException){
            this.errorDetail = new ErrorDetail(notFoundException.getMessage(), notFoundException.getErrorDetail().getMessage());
        } catch (com.decidir.sdk.exceptions.ValidateException validateException){
            this.errorDetail = new ErrorDetail(validateException.getMessage(), validateException.getErrorDetail().toString());
        } catch (com.decidir.sdk.exceptions.DecidirException decidirException){
            this.errorDetail = new ErrorDetail(decidirException.getMessage(), String.valueOf(decidirException.getStatus()));
        } catch (com.decidir.sdk.exceptions.responses.PaymentException paymentException){
            this.errorDetail = new ErrorDetail(paymentException.getMessage(), paymentException.getResponse().getStatus_details().toString());
        }
        return null;
    }

    @Override
    protected void onPreExecute(){
        view.onGetPaymentLoading();
    }
    @Override
    protected void onPostExecute(Payment payment) {
        if (payment != null) {
            view.onGetPaymentSuccess(payment);
        } else {
            view.onGetPaymentError(errorDetail);
        }
    }
}
