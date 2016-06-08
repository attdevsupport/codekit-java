package com.att.example;

// Import the relevant code kit parts.
import com.att.api.oauth.OAuthService;
import com.att.api.oauth.OAuthToken;
import com.att.api.rest.RESTException;

public class App {

    private static void setProxySettings() {
        // Uncomment and set any proxy settings if required.
        // RESTConfig.setDefaultProxy("proxy.host", 8080);
    }

    public static void main(String[] args) {
        setProxySettings();

        // To use OAuth you must first set up an app using developer.att.com.
        // Enable the APIs (for example: SMS) for the App Key and App Secret that 
        // you want to access.

        // Specify the fully qualified domain name.
        final String fqdn = "https://api.att.com";
        // Enter the API scopes for the resources that you want the token to access.
        // All the scopes specified below must be enabled for the App Key and
        // App Secret as mentioned above. Note: it is not required to specify all the
        // scopes of all the enabled APIs, only those that the token should have access to.
        final String scopes = "ENTER_SCOPE_ONE,ENTER_SCOPE_TWO";
        // Enter the value from 'App Key' field obtained at developer.att.com
        final String clientId = "ENTER VALUE!";
        // Enter the value from 'Secret' field obtained at developer.att.com
        final String clientSecret = "ENTER VALUE!";

        // Obtain a token using one of the following ways:
        //  Client Credentials--This does not require user authentication to use the API.
        //  Authorization Code--This requires user authentication to use the API.

        // Create a service for requesting an OAuth access token.
        OAuthService osrvc = new OAuthService(fqdn, clientId, clientSecret);

        try {
            /* This try/catch block showes the Client Credentials flow. */
            // Get the OAuth access token using the scopes specified above.
            OAuthToken token = osrvc.getToken(scopes);
            System.out.println("Client credentials access token: "
                    + token.getAccessToken());
        } catch (RESTException re) {
            // Handle exceptions here.
            re.printStackTrace();
        }

        try {
            /* This try/catch block shows the Authorization Code flow. */
            // Get the OAuth code by opening a browser to the following URL:
            // https://api.att.com/oauth/v4/authorize?client_id=CLIENT_ID&scope=SCOPE&redirect_uri=REDIRECT_URI
            // replacing CLIENT_ID, SCOPE, and REDIRECT_URI with the values configured at
            // developer.att.com. After authenticating, copy the OAuth code from the
            // browser URL.
            final String oauthCode = "ENTER VALUE!";
            // Get the OAuth access token using the OAuth code.
            OAuthToken token = osrvc.getTokenUsingCode(oauthCode);
            System.out.println("Authorization code access token: "
                    + token.getAccessToken());
        } catch (RESTException re) {
            // Handle exceptions here.
            re.printStackTrace();
        }
    }
}
