package com.att.example;
// This Quickstart Guide for the Advertising API requires the Java code kit, 
// which can be found at: 
// https://github.com/attdevsupport/codekit-java

// Import the relevant code kit parts.
import com.att.api.ads.service.ADSResponse;
import com.att.api.ads.service.ADSService;
import com.att.api.ads.service.Category;
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

        // Use the app account settings from developer.att.com for the following
        // values. Make sure that the API scope is set to ADS for the Advertising API  
        // before retrieving the App Key and App Secret.
        final String fqdn = "https://api.att.com";
        // Enter the value from the 'App Key' field obtained at developer.att.com 
        // in your app account.
        final String clientId = "ENTER VALUE!";
        // Enter the value from the 'App Secret' field obtained at developer.att.com 
        // in your app account.
        final String clientSecret = "ENTER VALUE!";
        // Create the service for requesting an OAuth token.
        OAuthService osrvc = new OAuthService(fqdn, clientId, clientSecret);
        // Get the OAuth access token using the API scope set to ADS for the Advertising API.
        OAuthToken token;
        try {
            token = osrvc.getToken("ADS");
        } catch (RESTException re) {
            re.printStackTrace();
            return;
        }

        // Create the service for interacting with the Advertising API.
        ADSService adsSrvc = new ADSService(fqdn, token);

        try {
            // Specify the user agent (must be mobile).
            final String ua = "Mozilla/5.0 (Android; Mobile; rv:13.0) Gecko/13.0 Firefox/13.0";
            // Specify a unique id.
            final String udid = "938382893239492349234923493249";
            // Specify "AUTO" for the category.
            Category cat = Category.AUTO;



            // Send a request to the API Gateway for getting an advertisement using 'AUTO' 
            // as the category.
            final ADSResponse response = adsSrvc.getAdvertisement(cat, ua, udid);
            System.out.println("clickUrl: " + response.getClickUrl());
            System.out.println("ad type: " + response.getType());
        } catch (RESTException re) {
            // Handle exceptions here.
            re.printStackTrace();
        }
    }
}
