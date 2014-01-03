package com.att.example;

// Import the relevant code kit parts
import java.util.HashMap;
import java.util.Map;

import com.att.api.cms.service.CMSService;
import com.att.api.cms.service.CMSSessionResponse;
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
            // values. Make sure CMS is enabled for the app key/secret.
            
            final String fqdn = "https://api.att.com";

            // Enter the value from 'App Key' field
            final String clientId = "ENTER VALUE!";

            // Enter the value from 'Secret' field
            final String clientSecret = "ENTER VALUE!";

            // Create service for requesting an OAuth token
            OAuthService osrvc = new OAuthService(fqdn, clientId, clientSecret);

            // Get OAuth token using the CMS scope
            OAuthToken token = osrvc.getToken("CMS");

            // Create service for interacting with the CMS api
            CMSService cmsSrvc = new CMSService(fqdn, token);
                
            final Map<String, String> emptyMap = new HashMap<String, String>();
            CMSSessionResponse response = cmsSrvc.createSession(emptyMap);

            System.out.println("id: " + response.getId());
            System.out.println("success: " + response.getSuccess());
        } catch (RESTException re) {
            // handle exceptions here
            re.printStackTrace();
        } finally {
            // perform any clean up here
        }
    }
}
