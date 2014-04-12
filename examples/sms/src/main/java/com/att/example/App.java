package com.att.example;
// This quickstart guide requires the Java codekit, which can be found at:
// https://github.com/attdevsupport/codekit-java

// Import the relevant code kit parts
import com.att.api.oauth.OAuthService;
import com.att.api.sms.model.SMSGetResponse;
import com.att.api.sms.model.SMSSendResponse;
import com.att.api.sms.model.SMSStatus;
import com.att.api.sms.service.SMSService;
import com.att.api.oauth.OAuthToken;
import com.att.api.rest.RESTException;

public class App {

    private static void setProxySettings() {
        // set any proxy settings
        //RESTConfig.setDefaultProxy("proxy.host", 8080);
    }

    public static void main(String[] args) {
        setProxySettings();

        // Use the app account settings from developer.att.com for the
        // following values. Make sure SMS is enabled for the App Key and
        // App Secret.
        final String fqdn = "https://api.att.com";
        // Enter the value from the 'App Key' field
        final String clientId = "ENTER VALUE!";
        // Enter the value from the 'App Secret' field
        final String clientSecret = "ENTER VALUE!";
        // Create service for requesting an OAuth token
        OAuthService osrvc = new OAuthService(fqdn, clientId, clientSecret);
        OAuthToken token;
        try {
            // Get OAuth token using the SMS scope
            token = osrvc.getToken("SMS");
        } catch (RESTException re) {
            re.printStackTrace();
            return;
        } 

        // Create service for interacting with the SMS API
        SMSService smsSrvc = new SMSService(fqdn, token);

        // The following lines of code showcase the possible method calls for
        // the SMSService class; to test only a particular method call, comment
        // out any other method calls.

        try {
            /* This portion showcases the Send SMS API call. */
            // Enter Phone Number; e.g. final String pn = "5555555555";
            final String pn = "ENTER VALUE!";
            final String msg = "Test message.";
            final boolean notifyDeliveryStatus = false;
            // Send request for sending SMS
            SMSSendResponse r = smsSrvc.sendSMS(pn, msg, notifyDeliveryStatus);
            System.out.println("message id: " + r.getMessageId());
        } catch (RESTException re) {
            re.printStackTrace();
        }

        try {
            /* This portion showcases the Get SMS Delivery Status API call. */
            // Enter SMS id used to get status
            final String smsId = "ENTER VALUE!";
            // Send request for getting SMS delivery status
            SMSStatus r = smsSrvc.getSMSDeliveryStatus(smsId);
            System.out.println("resource url: " + r.getResourceUrl());
        } catch (RESTException re) {
            re.printStackTrace();
        }

        try {
            /* This portion showcases the Get Messages API call. */
            // Enter shortcode used to get messages
            final String shortcode = "ENTER VALUE!";
            // Send request for getting messages sent to the specified short code
            SMSGetResponse r = smsSrvc.getSMS(shortcode);
            System.out.println("numberOfMsgs: " + r.getNumberOfMessages());
        } catch (RESTException re) {
            re.printStackTrace();
        }
    }
}
