package com.att.example;
// This Quickstart Guide for the In-App Messaging API requires the Java code kit, 
// which can be found at: 
// https://github.com/attdevsupport/codekit-java

import com.att.api.immn.service.DeltaChange;
import com.att.api.immn.service.DeltaResponse;
import com.att.api.immn.service.IMMNService;
import com.att.api.immn.service.Message;
import com.att.api.immn.service.MessageContent;
import com.att.api.immn.service.MessageIndexInfo;
import com.att.api.immn.service.MessageList;
import com.att.api.immn.service.NotificationConnectionDetails;
import com.att.api.immn.service.SendResponse;
import com.att.api.oauth.OAuthService;
import com.att.api.oauth.OAuthToken;
import com.att.api.rest.RESTException;

// Import the relevant code kit parts.

public class App {

    private static void setProxySettings() {
        // If a proxy is required, uncomment the following line to set the proxy.
        // RESTConfig.setDefaultProxy("proxy.host", 8080);
    }

    public static void main(String[] args) {
        setProxySettings();

        // Use the app account settings from developer.att.com for the following 
        // values. Make sure that the API scope is set to IMMN for the In-App Messaging 
        // API before retrieving the App Key and App Secret.
        final String fqdn = "https://api.att.com";
        // Enter the value from the 'App Key' field obtained at developer.att.com 
        // in your app account.
        final String clientId = "ENTER VALUE!";
        // Enter the value from the 'App Secret' field obtained at developer.att.com 
        // in your app account.
        final String clientSecret = "ENTER VALUE!";
        // Create a service for requesting an OAuth access token.
        OAuthService osrvc = new OAuthService(fqdn, clientId, clientSecret);
        // Get the OAuth access code by opening a browser to the following URL:
        // https://api.att.com/oauth/v4/authorize?client_id=CLIENT_ID&scope=SCOPE&redirect_uri=REDIRECT_URI
        // replacing CLIENT_ID, SCOPE, and REDIRECT_URI with the values configured at
        // developer.att.com. After authenticating, copy the OAuth code from the
        // browser URL.
        final String oauthCode = "ENTER VALUE!";

        OAuthToken token;
        try {
            // Get the OAuth access token using the OAuth code.
            token = osrvc.getTokenUsingCode(oauthCode);
        } catch (RESTException re) {
            re.printStackTrace();
            return;
        }

        // Create the service for interacting with the In-App Messaging API.
        IMMNService immnSrvc = new IMMNService(fqdn, token);

        // The following lines of code show the possible method calls for
        // the IMMNService class; to test only one method, comment out the 
        // other try/catch blocks.

        try {
            /* This try/catch block tests the sendMessage method. */
            // Enter the phone number to where the message is sent.
            // For example: final String phoneNumber = "5555555555";
            final String phoneNumber = "ENTER VALUE!";
            final String msg = "This is an example message";
            // Send the request to send the message.
            SendResponse r = immnSrvc.sendMessage(phoneNumber, msg);
            System.out.println("The response id is: " + r.getId());
        } catch (RESTException re) {
            // Handle exceptions here.
            re.printStackTrace();
        }

        try {
            /* This try/catch block tests the getMessageList method. */
            // Enter the message list limit.
            final int limit = 1; // ENTER VALUE!
            // Enter the message list offset.
            final int offset = 0; // ENTER VALUE!
            // Send the request for getting the message list.
            MessageList r = immnSrvc.getMessageList(limit, offset);
            System.out.println("message list total: " + r.getTotal());
        } catch (RESTException re) {
            // Handle exceptions here.
            re.printStackTrace();
        }

        try {
            /* This try/catch block tests the getMessage method. */
            // Enter the message id.
            final String msgId = "ENTER VALUE!";
            // Send the request for getting the message.
            Message r = immnSrvc.getMessage(msgId);
            System.out.println("message from: " + r.getFrom());
        } catch (RESTException re) {
            // Handle exceptions here.
            re.printStackTrace();
        }

        try {
            /* This try/catch block tests the getMessageContent method. */
            // Enter the message id.
            final String msgId = "ENTER VALUE!";
            // Enter the part id.
            final String partId = "ENTER VALUE!";
            // Send the request for getting the message content.
            MessageContent r = immnSrvc.getMessageContent(msgId, partId);
            System.out.println("message content length: " + r.getContentLength());
        } catch (RESTException re) {
            // Handle exceptions here.
            re.printStackTrace();
        }

        try {
            /* This try/catch block tests the getDelta method. */
            // Enter the message state.
            final String state = "ENTER VALUE!";
            // Send the request for getting the message delta.
            DeltaResponse r = immnSrvc.getDelta(state);
            System.out.println("delta state: " + r.getState());
        } catch (RESTException re) {
            // Handle exceptions here.
            re.printStackTrace();
        }

        try {
            /* This try/catch block tests the updateMessages method. */
            // Enter the ids of the messages to update.
            final String msgId = "ENTER VALUE!";
            // Set isFavorite to "true".
            final Boolean isFavorite = true;
            // Set isUnread to "true".
            final Boolean isUnread = true;
            DeltaChange dc = new DeltaChange(msgId, isFavorite, isUnread);
            DeltaChange[] changes = { dc };
            // Send the request for updating the messages.
            immnSrvc.updateMessages(changes);
            System.out.println("update messages was successful");
        } catch (RESTException re) {
            // Handle exceptions here.
            re.printStackTrace();
        }

        try {
            /* This try/catch block tests the updateMessage method. */
            // Enter the id of the message to update.
            final String msgId = "ENTER VALUE!";
            // Set isFavorite to "true".
            final Boolean isFavorite = true;
            // Set isUnread to "true".
            final Boolean isUnread = true;
            // Send the request for updating the message.
            immnSrvc.updateMessage(msgId, isUnread, isFavorite);
            System.out.println("update message was successful");
        } catch (RESTException re) {
            // Handle exceptions here.
            re.printStackTrace();
        }

        try {
            /* This try/catch block tests the deleteMessages method. */
            // Enter ids of the messages to delete.
            final String[] msgIds = { "ENTER VALUE!" };
            // Send the request for deleting the messages.
            immnSrvc.deleteMessages(msgIds);
            System.out.println("delete messages was successful");
        } catch (RESTException re) {
            // Handle exceptions here.
            re.printStackTrace();
        }

        try {
            /* This try/catch tests the createMessageIndex method. */
            // Send the request for creating a message index.
            immnSrvc.createMessageIndex();
            System.out.println("create message index was successful");
        } catch (RESTException re) {
            // Handle exceptions here.
            re.printStackTrace();
        }

        try {
            /* This try/catch block tests the getMessageIndexInfo method. */
            // Send the request for getting the message index information.
            MessageIndexInfo r = immnSrvc.getMessageIndexInfo();
            System.out.println("index info state: " + r.getState());
        } catch (RESTException re) {
            // Handle exceptions here.
            re.printStackTrace();
        }

        try {
            /* This try/catch block tests the getNotificationConnectionDetails method. */
            // Enter queues value.
            final String queues = "ENTER VALUE!";
            // Send the request for getting the notification connection details.
            NotificationConnectionDetails r
                = immnSrvc.getNotificationConnectionDetails(queues);
            System.out.println("username: " + r.getUsername());
        } catch (RESTException re) {
            // Handle exceptions here.
            re.printStackTrace();
        }
    }
}

 
