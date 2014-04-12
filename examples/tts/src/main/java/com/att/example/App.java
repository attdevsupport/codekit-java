package com.att.example;
// This quickstart guide requires the Java codekit, which can be found at:
// https://github.com/attdevsupport/codekit-java

// Import the relevant code kit parts
import com.att.api.oauth.OAuthService;
import com.att.api.oauth.OAuthToken;
import com.att.api.speech.service.TtsService;

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

            final String text = "This is the example text to turn into audio!";

            // Create service for requesting an OAuth token
            OAuthService osrvc = new OAuthService(fqdn, clientId, clientSecret);

            // Get OAuth token using the Text To Speech scope
            OAuthToken token = osrvc.getToken("TTS");

            // Create service for interacting with the Speech api
            TtsService ttsService = new TtsService(fqdn, token);

            // Send the request to obtain the audio
            byte[] audio = ttsService.sendRequest("text/plain", text, "");

            // If the request fails an exception is thrown, thus if we get here
            // we've succeeded. 
            System.out.println("Successfully got audio file!");

        } catch (Exception re) {
            // handle exceptions here
            re.printStackTrace();
        } finally {
            // perform any clean up here
        }
    }
}
