package com.att.example;

import com.att.api.oauth.OAuthService;
import com.att.api.oauth.OAuthToken;
import com.att.api.rest.RESTException;
import com.att.api.immn.service.SendResponse;
import com.att.api.immn.service.IMMNService;

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
            // values. Make sure IMMN is enabled for the app key/secret.
            final String fqdn = "https://api.att.com";

            // Enter the value from 'App Key' field
            final String clientId = "ENTER VALUE!";

            // Enter the value from 'Secret' field
            final String clientSecret = "ENTER VALUE!";

            // Create service for requesting an OAuth token
            OAuthService osrvc = new OAuthService(fqdn, clientId, clientSecret);

            // Get the OAuth code by opening a browser to the following URL:
            // https://api.att.com/oauth/authorize?client_id=CLIENT_ID&scope=SCOPE&redirect_uri=REDIRECT_URI
            // replacing CLIENT_ID, SCOPE, and REDIRECT_URI with the values configured at 
            // developer.att.com. After authenticating, copy the oauth code from the
            // browser URL.
            final String oauthCode = "ENTER VALUE!";

            // Get OAuth token using the code
            OAuthToken token = osrvc.getTokenUsingCode(oauthCode);

            // Create service for interacting with the IMMN api
            IMMNService immnSrvc = new IMMNService(fqdn, token);

            SendResponse r = immnSrvc.sendMessage("5555555", "This is an example message");

            System.out.println("The response id is: " + r.getId());
        } catch (RESTException re) {
            // handle exceptions here
            re.printStackTrace();
        } finally {
            // perform any clean up here
        }
    }
}
