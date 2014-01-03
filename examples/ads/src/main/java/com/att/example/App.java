package com.att.example;

// Import the relevant code kit parts
import com.att.api.ads.service.ADSResponse;
import com.att.api.ads.service.ADSService;
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
            // values. Make sure ADS is enabled for the app key/secret.
            
            final String fqdn = "https://api.att.com";

            // Enter the value from 'App Key' field
            final String clientId = "ENTER VALUE!";

            // Enter the value from 'Secret' field
            final String clientSecret = "ENTER VALUE!";

            // Create service for requesting an OAuth token
            OAuthService osrvc = new OAuthService(fqdn, clientId, clientSecret);

            // Get OAuth token using the ADS scope
            OAuthToken token = osrvc.getToken("ADS");

            // Create service for interacting with the ADS api
            ADSService adsSrvc = new ADSService(fqdn, token);

            // User agent (must be mobile)
            final String ua = "Mozilla/5.0 (Android; Mobile; rv:13.0) Gecko/13.0 Firefox/13.0";

            // Random unique value
            final String udid = "938382893239492349234923493249";

            final ADSResponse response 
                = adsSrvc.getAdvertisement("auto", ua, udid, null);

            System.out.println("clickUrl: " + response.getClickUrl());
            System.out.println("ad type: " + response.getType());
        } catch (RESTException re) {
            // handle exceptions here
            re.printStackTrace();
        } finally {
            // perform any clean up here
        }
    }
}
