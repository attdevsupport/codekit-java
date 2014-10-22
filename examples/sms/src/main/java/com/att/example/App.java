package com.att.example;
// This Quickstart guide for the SMS API requires the Java code kit, 
// which can be found at:
// https://github.com/attdevsupport/codekit-java

// Import the relevant code kit parts.
import com.att.api.oauth.OAuthService;
import com.att.api.sms.model.SMSGetResponse;
import com.att.api.sms.model.SMSSendResponse;
import com.att.api.sms.model.SMSStatus;
import com.att.api.sms.service.SMSService;
import com.att.api.oauth.OAuthToken;
import com.att.api.rest.RESTException;

public class App {

    private static void setProxySettings() {
        // If a proxy is required, uncomment the following line to set the proxy.
        // RESTConfig.setDefaultProxy("proxy.host", 8080);
    }

    public static void main(String[] args) {
        setProxySettings();

        // Use the app account settings from developer.att.com for the
        // following values. Make sure that the API scope is set to SMS for the 
        // SMS API before retrieving the App Key and App Secret.

        final String fqdn = "https://api.att.com";
        // Enter the value from the 'App Key' field obtained at developer.att.com 
        // in your app account.
        final String clientId = "ENTER VALUE!";
        // Enter the value from the 'App Secret' field obtained at developer.att.com 
        // in your app account.
        final String clientSecret = "ENTER VALUE!";
        // Create the service for requesting an OAuth access token.
        OAuthService osrvc = new OAuthService(fqdn, clientId, clientSecret);
        OAuthToken token;
        try {
            // Get the OAuth token using the SMS scope.
            token = osrvc.getToken("SMS");
        } catch (RESTException re) {
            re.printStackTrace();
            return;
        } 

        // Create the service for interacting with the SMS API.
        SMSService smsSrvc = new SMSService(fqdn, token);

        // The following lines of code show the possible method calls for
        // the SMSService class; to test only one method, comment out the 
        // other try/catch blocks.

 
        /* This try/catch block tests the sendSMS method. */
        try {
            
            // Enter the phone number where the message is sent. For 
            // example: final String pn = "5555555555";
            final String pn = "ENTER VALUE!";
            final String msg = "Test message.";
            final boolean notifyDeliveryStatus = false;
            // Send the request to send the message.
            SMSSendResponse r = smsSrvc.sendSMS(pn, msg, notifyDeliveryStatus);
            System.out.println("message id: " + r.getMessageId());
        } catch (RESTException re) {
            // Handle exceptions here.
            re.printStackTrace();
        }


        /* This try/catch block tests the getSMSDeliveryStatus method. */
        try {
            
            // Enter the id of the SMS message.
            final String smsId = "ENTER VALUE!";
            // Send the request get the SMS delivery status.
            SMSStatus r = smsSrvc.getSMSDeliveryStatus(smsId);
            System.out.println("resource url: " + r.getResourceUrl());
        } catch (RESTException re) {
            // Handle exceptions here.
            re.printStackTrace();
        }


        /* This try/catch block tests the getMessages method. */
        try {
            
            // Enter the short code used to get the messages.
            final String shortcode = "ENTER VALUE!";
            // Send the request for getting messages sent to 
            // the specified short code.
            SMSGetResponse r = smsSrvc.getSMS(shortcode);
            System.out.println("numberOfMsgs: " + r.getNumberOfMessages());
        } catch (RESTException re) {
            // Handle exceptions here.
            re.printStackTrace();
        }
    }
}
