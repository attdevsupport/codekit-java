package com.att.example;
// This quickstart guide requires the Java codekit, which can be found at:
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
        // set any proxy settings
        //RESTConfig.setDefaultProxy("proxy.host", 8080);
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
            SpeechCustomService speechSrvc = new SpeechCustomService(fqdn,
                    token);

            // Set this to a single channel audio file
            final File AUDIO_FILE = new File("/tmp/audio.wav");

            // Add a grammar and dictionary file to use with the api
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
            // handle exceptions here
            re.printStackTrace();
        } finally {
            // perform any clean up here
        }
    }
}
