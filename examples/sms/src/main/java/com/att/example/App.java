package com.att.example;

// Import the relevant code kit parts
import com.att.api.sms.service.SMSService;
import com.att.api.oauth.OAuthService;
import com.att.api.oauth.OAuthToken;
import com.att.api.rest.RESTException;

public class App {

    private static void setProxySettings() {
        // set any proxy settings
        //RESTConfig.setDefaultProxy("proxy.host", 8080);
    }

    public static void main(String[] args) {
        try {
            setProxySettings();

            // Use the app settings from developer.att.com for the following
            // values. Make sure SMS is enabled for the app key/secret.
            
            final String fqdn = "https://api.att.com";

            // Enter the value from 'App Key' field
            final String clientId = "ENTER VALUE!";

            // Enter the value from 'Secret' field
            final String clientSecret = "ENTER VALUE!";

            // Create service for requesting an OAuth token
            OAuthService osrvc = new OAuthService(fqdn, clientId, clientSecret);

            // Get OAuth token using the SMS scope
            OAuthToken token = osrvc.getToken("SMS");

            // Create service for interacting with the SMS api
            SMSService smsSrvc = new SMSService(fqdn, token);

            // Phone number
            final String pn = "5555555555";

            // Message
            final String msg = "msg";


            SMSService.sendSMS(pn, msg, false);

        } catch (RESTException re) {
            // handle exceptions here
            re.printStackTrace();
        } finally {
            // perform any clean up here
        }
    }
}
