package com.att.example;
// This quickstart guide requires the Java codekit, which can be found at:
// https://github.com/attdevsupport/codekit-java

// Import the relevant code kit parts
import com.att.api.ads.service.ADSResponse;
import com.att.api.ads.service.ADSService;
import com.att.api.ads.service.Category;
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

        // Use the app account settings from developer.att.com for the following
        // values. Make sure ADS is enabled for the app key/secret.
        final String fqdn = "https://api.att.com";
        // Enter the value from the 'App Key' field
        final String clientId = "ENTER VALUE!";
        // Enter the value from the 'App Secret' field
        final String clientSecret = "ENTER VALUE!";
        // Create service for requesting an OAuth token
        OAuthService osrvc = new OAuthService(fqdn, clientId, clientSecret);
        // Get OAuth token using the ADS scope
        OAuthToken token;
        try {
            token = osrvc.getToken("ADS");
        } catch (RESTException re) {
            re.printStackTrace();
            return;
        }

        // Create service for interacting with the ADS api
        ADSService adsSrvc = new ADSService(fqdn, token);

        try {
            // User agent (must be mobile)
            final String ua = "Mozilla/5.0 (Android; Mobile; rv:13.0) Gecko/13.0 Firefox/13.0";
            // Random unique value
            final String udid = "938382893239492349234923493249";
            // Use "auto" for Category
            Category cat = Category.AUTO;
            // Send request for getting advertisement
            final ADSResponse response = adsSrvc.getAdvertisement(cat, ua, udid);
            System.out.println("clickUrl: " + response.getClickUrl());
            System.out.println("ad type: " + response.getType());
        } catch (RESTException re) {
            // handle exceptions here
            re.printStackTrace();
        }
    }
}
