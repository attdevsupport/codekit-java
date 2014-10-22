package com.att.example;
// This Quickstart Guide for the Device Capabilities API requires the Java code kit,
// which can be found at: 
// https://github.com/attdevsupport/codekit-java


// Import the relevant code kit parts.
import com.att.api.dc.model.DCResponse;
import com.att.api.dc.service.DCService;
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
        // following values. Make sure that the API scope is set to DC for 
        // the Device Capabilities API before retrieving the App Key and
        // App Secret.
        final String fqdn = "https://api.att.com";
        // Enter the value from the 'App Key' field obtained at developer.att.com 
        // in your app account.
        final String clientId = "ENTER VALUE!";
        // Enter the value from the 'App Secret' field obtained at developer.att.com 
        // in your app account.
        final String clientSecret = "ENTER VALUE!";

        // Create the service for requesting an OAuth access token.
        OAuthService osrvc = new OAuthService(fqdn, clientId, clientSecret);

        // Get the OAuth code by opening a browser to the following URL:
        // https://api.att.com/oauth/v4/authorize?client_id=CLIENT_ID&scope=SCOPE&redirect_uri=REDIRECT_URI
        // replacing CLIENT_ID, SCOPE, and REDIRECT_URI with the values configured at 
        // developer.att.com. After authenticating, copy the OAuth code from the
        // browser URL.
        // For the Device Capabilities API, the internet connection must be OnNet 
        // (https://developer.att.com/developer/forward.jsp?passedItemId=13200290).
        final String oauthCode = "ENTER VALUE!";

        // Get the OAuth access token.
        OAuthToken token; 
        try {
            token = osrvc.getTokenUsingCode(oauthCode);
        } catch (RESTException re) {
            re.printStackTrace();
            return;
        }

        // Create the service for interacting with the Device Capabilities API.
        DCService dcSrvc = new DCService(fqdn, token);

        try {
            // Send a request to the API Gateway for getting device capabilities.
            DCResponse r = dcSrvc.getDeviceCapabilities();

            System.out.println("Name: " + r.getName());
            System.out.println("Vendor: " + r.getVendor());
        } catch (RESTException re) {
            // Handle exceptions here.
            re.printStackTrace();
        }
    }
}
