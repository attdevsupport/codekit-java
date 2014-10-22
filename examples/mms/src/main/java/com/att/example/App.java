package com.att.example;
// This Quickstart Guide for the MMS API requires the Java code kit, 
// which can be found at: 
// https://github.com/attdevsupport/codekit-java

// Import the relevant code kit parts.
import com.att.api.mms.model.MMSStatus;
import com.att.api.mms.model.SendMMSResponse;
import com.att.api.mms.service.MMSService;
import com.att.api.oauth.OAuthService;
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
        // following values. Make sure that the API scope is set to MMS for the 
        // MMS API before retrieving the App Key and App Secret.

        final String fqdn = "https://api.att.com";
        // Enter the value from the 'App Key' field obtained at developer.att.com 
        // in your app account.
        final String clientId = "ENTER VALUE!";
        // Enter the value from the 'App Secret' field obtained at developer.att.com 
        // in your app account.
        final String clientSecret = "ENTER VALUE!";
        // Create a service for requesting an OAuth access token.
        OAuthService osrvc = new OAuthService(fqdn, clientId, clientSecret);

        OAuthToken token;
        try {
            // Get the OAuth access token using the MMS scope.
            token = osrvc.getToken("MMS");
        } catch (RESTException re) {
            re.printStackTrace();
            return;
        }
        // Create the service for interacting with the MMS API.
        MMSService mmsSrvc = new MMSService(fqdn, token);

        // The following lines of code show the possible method calls for
        // the MMSService class; to test only one method, comment out the 
        // other try/catch blocks.

        try {
            /* This try/catch block tests the sendMMS method. */
            // Enter the phone number where the message is sent. For  
            // example: final String pn = "5555555555";
            final String pn = "ENTER VALUE!";
            // Set attachments to the message.
            final String[] attachments = { "/tmp/att.gif" };
            final String subject = "subject";
            final String priority = null;
            final boolean notifyDelStatus = false;
            // Send the request to send the message with the specified 
            // attachments to the specified phone number.
            SendMMSResponse r = mmsSrvc
                .sendMMS(pn, attachments, subject, priority, notifyDelStatus);
            System.out.println("message id: " + r.getMessageId());
        } catch (RESTException re) {
            //Handle exceptions here.
            re.printStackTrace();
        }

        try {
            /* This try/catch block tests the getMMSStatus method. */
            // Enter the id of the message.
            final String msgId = "ENTER VALUE!";
            // Send the request for getting the message status.
            MMSStatus r = mmsSrvc.getMMSStatus(msgId);
            System.out.println("resource url: " + r.getResourceUrl());
        } catch (RESTException re) {
           // Handle exceptions here.
            re.printStackTrace();
        }
    }
}

