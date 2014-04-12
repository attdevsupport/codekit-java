package com.att.example;

// Import the relevant code kit parts
import com.att.api.ads.service.ADSResponse;
import com.att.api.ads.service.ADSService;
import com.att.api.oauth.OAuthService;
import com.att.api.oauth.OAuthToken;
import com.att.api.rest.RESTException;

public class App {

    private static void setProxySettings() {
        // Uncomment and set any proxy settings if required.
        //RESTConfig.setDefaultProxy("proxy.host", 8080);
    }

    public static void main(String[] args) {
        setProxySettings();

        // To use OAuth you must first set up an app using developer.att.com.
        // Enable the API(s) (e.g. SMS) for the app key/secret that you want to access.

        // Fully qualified domain name.
        final String fqdn = "https://api.att.com";
        // Enter the scope(s) for the resources that you want the token to be able to
        // access. All the scopes specified below must be enabled for the app key and
        // secret as mentioned above. Note: it is not required to specify all the
        // scopes enabled, only those that the token should have access to.
        final String scopes = "ENTER_SCOPE_ONE,ENTER_SCOPE_TWO";
        // Enter the value from 'App Key' field obtained at developer.att.com
        final String clientId = "ENTER VALUE!";
        // Enter the value from 'Secret' field obtained at developer.att.com
        final String clientSecret = "ENTER VALUE!";

        // There are currently two ways to obtain a token.
        // 1. Client Credentials - Does not require user authentication to use the API
        // 2. Authorization Code - Requires user authentication to use the API

        // Create service for requesting an OAuth token
        OAuthService osrvc = new OAuthService(fqdn, clientId, clientSecret);

        try {
            /* This portion showcases the Client Credentials flow. */
            // Get OAuth token using the scope(s) specified above
            OAuthToken token = osrvc.getToken(scopes);
            System.out.println("Client credentials access token: " 
                    + token.getAccessToken());
        } catch (RESTException re) {
            re.printStackTrace();
        } 

        try {
            /* This portion showcases the Authorization Code flow. */
            // Get the OAuth code by opening a browser to the following URL:
            // https://api.att.com/oauth/authorize?client_id=CLIENT_ID&scope=SCOPE&redirect_uri=REDIRECT_URI
            // replacing CLIENT_ID, SCOPE, and REDIRECT_URI with the values configured at 
            // developer.att.com. After authenticating, copy the oauth code from the
            // browser URL.
            final String oauthCode = "ENTER VALUE!";
            // Get OAuth token using the oauth code
            OAuthToken token = osrvc.getTokenUsingCode(oauthCode);
            System.out.println("Authorization code access token: " 
                    + token.getAccessToken());
        } catch (RESTException re) {
            re.printStackTrace();
        }
    }
}
