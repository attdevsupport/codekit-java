package com.att.example;

import com.att.api.oauth.OAuthService;
import com.att.api.oauth.OAuthToken;
import com.att.api.rest.RESTException;
import com.att.api.tl.model.TLResponse;
import com.att.api.tl.service.TLService;

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
            // values. Make sure TL is enabled for the app key/secret.
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

            // Create service for interacting with the TL api
            TLService tlSrvc = new TLService(fqdn, token);

            TLResponse r = tlSrvc.getLocation("10000", "10000", "10000");

            System.out.println("Latitude: " + r.getLatitude());
            System.out.println("Longitude: " + r.getLongitude());
        } catch (RESTException re) {
            // handle exceptions here
            re.printStackTrace();
        } finally {
            // perform any clean up here
        }
    }
}
