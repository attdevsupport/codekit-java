package com.att.example;
// This Quickstart Guide for the Payment API requires the Java code kit, 
// which can be found at: 
// https://github.com/attdevsupport/codekit-java

// Import the relevant code kit parts.
import java.util.Iterator;
import java.util.Scanner;

import org.json.JSONObject;

import com.att.api.oauth.OAuthService;
import com.att.api.oauth.OAuthToken;
import com.att.api.payment.model.AppCategory;
import com.att.api.payment.model.RefundReason;
import com.att.api.payment.model.Notary;
import com.att.api.payment.model.Transaction;
import com.att.api.payment.model.Subscription;
import com.att.api.payment.service.NotaryService;
import com.att.api.payment.service.PaymentService;
import com.att.api.rest.RESTException;



public class App {

    private static void setProxySettings() {
        // If a proxy is required, uncomment the following line to set the proxy.
        // RESTConfig.setDefaultProxy("proxy.host", 8080);
    }

    public static void main(String[] args) {
        setProxySettings();

        // Use the app settings from developer.att.com for the following
        // values. Make sure that the API scope is set to PAYMENT for the Payment 
        // API before retrieving the App Key and App Secret.
        
        // Set the fully-qualified domain name.
        final String fqdn = "https://api.att.com";

        // Enter the value from the 'App Key' field obtained at developer.att.com 
        // in your app account.
        final String clientId = "ENTER_VALUE!";

        // Enter the value from the 'App Secret' field obtained at developer.att.com 
        // in your app account.
        final String clientSecret = "ENTER_VALUE!";

        // Enter the notification id to test notifications.
        final String notificationId = "ENTER_VALUE!";

        // Set the url to redirect to after authorizing a payment.
        final String REDIRECT = "http://localhost:123123/auth";

        OAuthService osrvc = null;
        OAuthToken token = null;

        /*Create the services.*/

        try {
            
            // Create the service for requesting an OAuth access token.
            osrvc = new OAuthService(fqdn, clientId, clientSecret);

            // Get the OAuth access token using the Payment scope.
            token = osrvc.getToken("Payment");

        } catch(RESTException re){
            // Handle exceptions here.
            re.printStackTrace();
        }

        // Create the service for interacting with the Payment API.
        final PaymentService paymentSrvc = new PaymentService(fqdn, token);

        // Create a Notary service.
        final NotaryService notaryService = new NotaryService(fqdn, clientId,
                clientSecret);


        /* Create the transaction.*/  
        try {

            // Specify how much the product costs.
            final double AMOUNT = 1;

            // Specify a description of the product.
            final String DESC = "example game xtreme";

            // Specify a unique merchant transaction id.
            final String MERCH_TRANS_ID = "example" +
                System.currentTimeMillis();

            // Specify the merchant product id.
            final String MERCH_PROD_ID = "exampleGame";

            // Create a basic transaction.
            final Transaction trans = new Transaction.Builder(
                    AMOUNT,
                    AppCategory.APP_OTHER,
                    DESC,
                    MERCH_TRANS_ID,
                    MERCH_PROD_ID,
                    REDIRECT).build();

            // Obtain a signed notary from the transaction, for use in
            //  authenticating the payment.
            final Notary notary = notaryService.getTransactionNotary(trans);

            System.out.println("Please use the following url to"
                    + " authenticate the payment: ");

            // Obtain the url used for authenticating the payment.
            String url = PaymentService.getNewTransactionURL(fqdn, clientId,
                    notary);
            System.out.println(url);

            System.out.println();

            // Create a scanner to accept the authorization code.
            System.out.println("Please input the authcode returned to "
                    + "the redirect");
            Scanner sc = new Scanner(System.in);
            String code = sc.next();

            System.out.println();

            // Get the status of the transaction using the authorization code.
            JSONObject response = paymentSrvc.getTransactionStatus(
                    Transaction.Type.AUTHCODE, code);

            // Print the returned response to the console.
            System.out.println("Transaction Status:");
            printJSONObject(response);

            System.out.println();


            /* Refund the transaction.*/

            final String transId = response.getString("TransactionId");
            final String reason = "This was an example transaction";
            final RefundReason reasonCode = RefundReason.CP_NONE;

            JSONObject refund = paymentSrvc.refundTransaction(transId,
                    reason, reasonCode);

            // Print the returned response to the console.
            System.out.println("Refund:");
            printJSONObject(refund);

            System.out.println();

        } catch (RESTException re) {
            // Handle exceptions here.
            re.printStackTrace();
        }

  
           /* Create a subscription. */      
        try {
 

            // Define how much the product costs.
            final double AMOUNT = 1;

            // Give the item a description.
            final String DESC = "example game xtreme";

            // Define a uniq merchant transaction id.
            final String MERCH_TRANS_ID = "example" +
                System.currentTimeMillis();

            // Set the merchant product id.
            final String MERCH_PROD_ID = "exampleGame";

            final String MERCH_SUB_ID = "ExampleMerchSubID";

            // Create a basic transaction.
            final Subscription sub = new Subscription.Builder(
                    AMOUNT,
                    AppCategory.APP_OTHER,
                    DESC,
                    MERCH_TRANS_ID,
                    MERCH_PROD_ID,
                    REDIRECT,
                    MERCH_SUB_ID).build();

            // Turn our transaction into a signed notary.
            final Notary notary = notaryService.getSubscriptionNotary(sub);

            System.out.println("Please use the following url to"
                    + " authenticate the subscription: ");

            // Obtain the url used for authenticating the payment.
            String url = PaymentService.getNewSubscriptionURL(fqdn, clientId,
                    notary);

            System.out.println(url);

            System.out.println();

            // Create a scanner to accept the authorization code.
            System.out.println("Please input the authcode returned to "
                    + "the redirect");
            Scanner sc = new Scanner(System.in);
            String code = sc.next();

            System.out.println();

            // Get the transaction status using the authorization code.
            JSONObject sub_response = paymentSrvc.getSubscriptionStatus(
                    Subscription.Type.AUTHCODE, code);

            System.out.println("Subscription Status:");
            printJSONObject(sub_response);


            /* Get subscription details. */

            String merchId = sub_response.getString("MerchantSubscriptionId");
            String consumerId = sub_response.getString("ConsumerId");

            JSONObject sub_details =
                paymentSrvc.getSubscriptionDetails(merchId, consumerId);

            System.out.println();
            System.out.println("Subscription Details:");
            printJSONObject(sub_details);

            /* Refund the subscription. */

            String subId = sub_response.getString("SubscriptionId");
            String reason = "This was an example subscription";
            final RefundReason reasonCode = RefundReason.CP_NONE;

            JSONObject refund =
                paymentSrvc.refundSubscription(subId, reason, reasonCode);

            System.out.println();
            System.out.println("Subscription Refund:");
            printJSONObject(refund);

        } catch (RESTException re) {
            // Handle exceptions here.
            re.printStackTrace();
        }

        
        /* Notifications. */
        try {
            

            JSONObject info = paymentSrvc.getNotification(notificationId);

            System.out.println();
            System.out.println("Notification:");
            printJSONObject(info);

        } catch (RESTException re) {
            // Handle exceptions here
            re.printStackTrace();
        }
    }

    private static void printJSONObject(JSONObject json, String pre,
            int cyclicCheck) {
        // Do a check to make sure you do not endlessly recurse.
        if (cyclicCheck <= 0)
            return;

        // Simply output data
        @SuppressWarnings("unchecked")
        Iterator<String> keys = json.keys();
        while(keys.hasNext()){
            String key = keys.next();

            // Do not use exceptions for the call flow.
            // This example only displays the values.
            try {
                JSONObject obj = json.getJSONObject(key);
                System.out.println(pre + key + ":");
                printJSONObject(obj, pre + "\t", cyclicCheck - 1);
            } catch(Exception e) {
                System.out.println(pre + key + ": " + json.get(key));
            }
        }
    }

    private static void printJSONObject(JSONObject json) {
        printJSONObject(json, "\t", 15);
    }

}

 
