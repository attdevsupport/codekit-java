package com.att.example;
// This Quickstart Guide for the Speech API requires the Java code kit, 
// which can be found at: 
// https://github.com/attdevsupport/codekit-java

// Import the relevant code kit parts
import java.io.File;
import java.util.Iterator;
import java.util.List;

import com.att.api.speech.model.NBest;
import com.att.api.speech.model.NLUHypothesis;
import com.att.api.speech.model.OutComposite;
import com.att.api.speech.model.SpeechResponse;
import com.att.api.speech.service.SpeechCustomService;
import com.att.api.oauth.OAuthService;
import com.att.api.oauth.OAuthToken;

public class App {

    private static void setProxySettings() {
        // If a proxy is required, uncomment the following line to set the proxy.
        // RESTConfig.setDefaultProxy("proxy.host", 8080);
    }

    private static String formatList(String preface, List<?> lstring) {
        StringBuilder sbuild = new StringBuilder(preface);

        Iterator<?> itr = lstring.iterator();
        if (itr.hasNext()) {
            sbuild.append("[ " + itr.next());
            while (itr.hasNext()) {
                sbuild.append(", " + itr.next());
            }
        }
        sbuild.append(" ]");

        return sbuild.toString();
    }

    public static void main(String[] args) {
        try {
            setProxySettings();

            // Use the app account settings from developer.att.com for the following
            // values. Make sure that the API scope is set to Speech for the Speech API 
            // before retrieving the App Key and App Secret.

            final String fqdn = "https://api.att.com";

            // Enter the value from the 'App Key' field obtained at developer.att.com 
            // in your app account.
            final String clientId = "ENTER VALUE!";

            // Enter the value from the 'App Secret' field obtained at developer.att.com 
            // in your app account.
            final String clientSecret = "ENTER VALUE!";

            // Create a service for requesting an OAuth access token.
            OAuthService osrvc = new OAuthService(fqdn, clientId, clientSecret);

            // Get the OAuth access token using the API scope set to Speech for the Speech API.
            OAuthToken token = osrvc.getToken("SPEECH");

            // Create the service for interacting with the Speech API.
            SpeechCustomService speechSrvc = new SpeechCustomService(fqdn,
                    token);

            // Set to a single channel audio file.
            final File AUDIO_FILE = new File("/tmp/audio.wav");

            // Add a grammar and dictionary file to use with the API.
            final File GRAMMAR = new File("/tmp/grammar.grxml");
            final File DICTIONARY = new File("/tmp/dictionary.pls");

            final SpeechResponse response = speechSrvc.speechToText(AUDIO_FILE,
                    GRAMMAR, DICTIONARY);

            System.out.println("Converted Speech with status response:"
                    + response.getStatus());
            System.out .println("Response ID:"
                    + response.getResponseId() + "\n");

            System.out.println("NBest values:");
            System.out.println("---------------");
            for (NBest nbest : response.getNbests()){
                System.out.println("\tHypothesis: " + nbest.getHypothesis());
                System.out.println("\tConfidence: " + nbest.getConfidence());
                System.out.println("\tGrade: " + nbest.getGrade());
                System.out.println("\tLanguage Id: " + nbest.getLanguageId());
                System.out.println("\tResult Text: " + nbest.getResultText());
                System.out.println(formatList("\tWords: ",
                            nbest.getWords())
                        );
                System.out.println(formatList("\tWord Scores: ",
                            nbest.getWordScores())
                        );

                NLUHypothesis nlu = nbest.getNluHypothesis();
                if (nlu != null) {
                    System.out.println("\tNluHypothesis:");
                    List<OutComposite> composites = nlu.getOutComposite();
                    for (OutComposite comp : composites) {
                        System.out.println("\t\tOut: " + comp.getOut());
                        System.out.println("\t\tGrammar: "
                                + comp.getGrammar());
                    }
                }
            }

        } catch (Exception re) {
            // Handle exceptions here.
            re.printStackTrace();
        } finally {
            // Perform any clean up here.
        }
    }
}
