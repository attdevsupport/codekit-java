package com.att.example;
// This Quickstart Guide for the Text To Speech method of the Speech API requires 
// the Java code kit, which can be found at: 
// https://github.com/attdevsupport/codekit-java

// Import the relevant code kit parts.
import com.att.api.oauth.OAuthService;
import com.att.api.oauth.OAuthToken;
import com.att.api.speech.service.TtsService;

public class App {

    private static void setProxySettings() {
        // If a proxy is required, uncomment the following line to set the proxy.
        // RESTConfig.setDefaultProxy("proxy.host", 8080);
    }

    public static void main(String[] args) {
        try {
            setProxySettings();

            // Use the app account settings from developer.att.com for the following
            // values. Make sure that the API scope is set to TTS to access the 
            // Text To Speech method of the Speech API before retrieving the 
            // App Key and App Secret.
            
            final String fqdn = "https://api.att.com";

            // Enter the value from the 'App Key' field obtained at developer.att.com 
            // in your app account.
            final String clientId = "ENTER VALUE!";

            // Enter the value from the 'App Secret' field obtained at developer.att.com 
            // in your app account.
            final String clientSecret = "ENTER VALUE!";

            // Specify the text to turn into audio.
            final String text = "This is the example text to turn into audio!";

            // Create a service to request an OAuth token.
            OAuthService osrvc = new OAuthService(fqdn, clientId, clientSecret);

            // Get the OAuth access token using the API scope set to TTS for 
            // the Text To Speech method of the Speech API.
            OAuthToken token = osrvc.getToken("TTS");

            // Create the service to interact with the Speech API.
            TtsService ttsService = new TtsService(fqdn, token);

            // Send the request to obtain the audio.
            byte[] audio = ttsService.sendRequest("text/plain", text, "");

            // Print the following message. The call has succeeded, otherwise 
            // an exception would be thrown.
            System.out.println("Successfully got audio file!");

        } catch (Exception re) {
            // Handle exceptions here.
            re.printStackTrace();
        } finally {
            // Perform any clean up here.
        }
    }
}

    
