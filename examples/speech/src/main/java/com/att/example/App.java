package com.att.example;

// Import the relevant code kit parts
import java.io.File;

import com.att.api.speech.model.SpeechResponse;
import com.att.api.speech.service.SpeechService;
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
            // values. Make sure Speech is enabled for the app key/secret.
            
            final String fqdn = "https://api.att.com";

            // Enter the value from 'App Key' field
            final String clientId = "ENTER VALUE!";

            // Enter the value from 'Secret' field
            final String clientSecret = "ENTER VALUE!";

            // Create service for requesting an OAuth token
            OAuthService osrvc = new OAuthService(fqdn, clientId, clientSecret);

            // Get OAuth token using the Speech scope
            OAuthToken token = osrvc.getToken("SPEECH");

            // Create service for interacting with the Speech api
            SpeechService speechSrvc = new SpeechService(fqdn, token);

            // Set this to a single channel audio file
            final File AUDIO_FILE = null;

            final String context = "";
            final String subcontext = "";
            final String xarg = "";

            final SpeechResponse response 
                = speechSrvc.sendRequest(AUDIO_FILE, xarg, context, subcontext);

            // display our results
            for (String[] kvp : response.getResult()) {
                System.out.println(kvp[0] + ": " + kvp[1]);
            }
        } catch (Exception re) {
            // handle exceptions here
            re.printStackTrace();
        } finally {
            // perform any clean up here
        }
    }
}
