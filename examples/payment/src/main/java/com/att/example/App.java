package com.att.example;

import org.json.JSONObject;

import com.att.api.oauth.OAuthService;
import com.att.api.oauth.OAuthToken;
import com.att.api.payment.service.PaymentService;
import com.att.api.rest.RESTException;

// Import the relevant code kit parts

public class App {

    private static void setProxySettings() {
        // set any proxy settings
        //RESTConfig.setDefaultProxy("proxy.host", 8080);
    }

    public static void main(String[] args) {
        try {
            setProxySettings();

            // Use the app settings from developer.att.com for the following
            // values. Make sure DC is enabled for the app key/secret.
            final String fqdn = "https://api.att.com";

            // Enter the value from 'App Key' field
            final String clientId = "ENTER VALUE!";

            // Enter the value from 'Secret' field
            final String clientSecret = "ENTER VALUE!";

            // Create service for requesting an OAuth token
            OAuthService osrvc = new OAuthService(fqdn, clientId, clientSecret);

            // Get OAuth token using the Payment scope
            OAuthToken token = osrvc.getToken("Payment");

            // Create service for interacting with the Payment api
            PaymentService paymentSrvc = new PaymentService(fqdn, token);

            // Enter notification id
            final String notificationId = "ENTER VALUE!";

            JSONObject r = paymentSrvc.getNotification(notificationId);

            System.out.println("Info: " + r.toString());
        } catch (RESTException re) {
            // handle exceptions here
            re.printStackTrace();
        } finally {
            // perform any clean up here
        }
    }
}
