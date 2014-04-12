package com.att.example;
// This quickstart guide requires the Java codekit, which can be found at:
// https://github.com/attdevsupport/codekit-java

// Import the relevant code kit parts
import com.att.api.mms.model.MMSStatus;
import com.att.api.mms.model.SendMMSResponse;
import com.att.api.mms.service.MMSService;
import com.att.api.oauth.OAuthService;
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
        // following values. Make sure MMS is enabled for the App Key and
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
            // Get OAuth token using the MMS scope
            token = osrvc.getToken("MMS");
        } catch (RESTException re) {
            re.printStackTrace();
            return;
        }
        // Create service for interacting with the MMS API
        MMSService mmsSrvc = new MMSService(fqdn, token);

        // The following lines of code showcase the possible method calls for
        // the MMSService class; to test only a particular method call, comment
        // out any other method calls.

        try { 
            /* This portion showcases the Send MMS API call. */
            // Enter Phone Number; e.g. final String pn = "5555555555";
            final String pn = "ENTER VALUE!";
            // Set attachments
            final String[] attachments = { "/tmp/att.gif" };
            final String subject = "subject";
            final String priority = null;
            final boolean notifyDelStatus = false;
            // Send request for sending MMS to the specified number with the
            // specified attachment(s)
            SendMMSResponse r = mmsSrvc
                .sendMMS(pn, attachments, subject, priority, notifyDelStatus);
            System.out.println("message id: " + r.getMessageId());
        } catch (RESTException re) {
            re.printStackTrace();
        }

        try { 
            /* This portion showcases the Get MMS Status API call. */
            // Enter message id to get status for
            final String msgId = "ENTER VALUE!";
            // Send request for getting status
            MMSStatus r = mmsSrvc.getMMSStatus(msgId);
            System.out.println("resource url: " + r.getResourceUrl());
        } catch (RESTException re) {
            re.printStackTrace();
        }
    }
}
