package com.att.example;

// Import the relevant code kit parts
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
        try {
            setProxySettings();

            // Use the app settings from developer.att.com for the following
            // values. Make sure MMS is enabled for the app key/secret.
            
            final String fqdn = "https://api.att.com";

            // Enter the value from 'App Key' field
            final String clientId = "ENTER VALUE!";

            // Enter the value from 'Secret' field
            final String clientSecret = "ENTER VALUE!";

            // Create service for requesting an OAuth token
            OAuthService osrvc = new OAuthService(fqdn, clientId, clientSecret);

            // Get OAuth token using the MMS scope
            OAuthToken token = osrvc.getToken("MMS");

            // Create service for interacting with the MMS api
            MMSService mmsSrvc = new MMSService(fqdn, token);

            // Phone number
            final String pn = "5555555555";

            // Set attachments
            final String[] attachments = { "/tmp/att.gif" };

            MMSService.sendMMS(pn, attachments, "subject", null, false);

        } catch (RESTException re) {
            // handle exceptions here
            re.printStackTrace();
        } finally {
            // perform any clean up here
        }
    }
}
