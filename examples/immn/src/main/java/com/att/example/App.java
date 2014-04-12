package com.att.example;
// This quickstart guide requires the Java codekit, which can be found at:
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

// Import the relevant code kit parts

public class App {

    private static void setProxySettings() {
        // set any proxy settings
        //RESTConfig.setDefaultProxy("proxy.host", 8080);
    }

    public static void main(String[] args) {
        setProxySettings();

        // Use the app account settings from developer.att.com for the
        // following values. Make sure IMMN is enabled for the App Key and
        // App Secret.
        final String fqdn = "https://api.att.com";
        // Enter the value from the 'App Key' field
        final String clientId = "ENTER VALUE!";
        // Enter the value from the 'App Secret' field
        final String clientSecret = "ENTER VALUE!";
        // Create service for requesting an OAuth access token
        OAuthService osrvc = new OAuthService(fqdn, clientId, clientSecret);
        // Get the OAuth code by opening a browser to the following URL:
        // https://api.att.com/oauth/authorize?client_id=CLIENT_ID&scope=SCOPE&redirect_uri=REDIRECT_URI
        // replacing CLIENT_ID, SCOPE, and REDIRECT_URI with the values configured at 
        // developer.att.com. After authenticating, copy the oauth code from the
        // browser URL.
        final String oauthCode = "ENTER VALUE!";

        OAuthToken token;
        try {
            // Get OAuth access token using the OAuth code
            token = osrvc.getTokenUsingCode(oauthCode);
        } catch (RESTException re) {
            re.printStackTrace();
            return;
        }

        // Create service for interacting with the IMMN API
        IMMNService immnSrvc = new IMMNService(fqdn, token);

        // The following lines of code showcase the possible method calls for
        // the IMMNService class; to test only a particular method call,
        // comment out any other method calls.

        try {
            /* This portion showcases the Send Message API call. */
            // Enter phone number to send message to
            final String phoneNumber = "ENTER TIME!";
            final String msg = "This is an example message";
            // Send request to send message
            SendResponse r = immnSrvc.sendMessage(phoneNumber, msg);
            System.out.println("The response id is: " + r.getId());
        } catch (RESTException re) {
            // handle exceptions here
            re.printStackTrace();
        }

        try {
            /* This portion showcases the Get Message List API call. */
            // Enter message list limit
            final int limit = 1; // ENTER VALUE!
            // Enter message list offset
            final int offset = 0; // ENTER VALUE!
            // Send request for getting message list
            MessageList r = immnSrvc.getMessageList(limit, offset);
            System.out.println("message list total: " + r.getTotal());
        } catch (RESTException re) {
            // handle exceptions here
            re.printStackTrace();
        }

        try {
            /* This portion showcases the Get Message API call. */
            // Enter message id
            final String msgId = "ENTER VALUE!";
            // Send request for getting message
            Message r = immnSrvc.getMessage(msgId);
            System.out.println("message from: " + r.getFrom());
        } catch (RESTException re) {
            // handle exceptions here
            re.printStackTrace();
        }

        try {
            /* This portion showcases the Get Message Content API call. */
            // Enter message id
            final String msgId = "ENTER VALUE!";
            // Enter part id
            final String partId = "ENTER VALUE!";
            // Send request for getting message content
            MessageContent r = immnSrvc.getMessageContent(msgId, partId);
            System.out.println("message content length: " + r.getContentLength());
        } catch (RESTException re) {
            // handle exceptions here
            re.printStackTrace();
        }

        try {
            /* This portion showcases the Get Messages Delta API call. */
            // Enter message state
            final String state = "ENTER VALUE!";
            // Send request for getting messages delta 
            DeltaResponse r = immnSrvc.getDelta(state);
            System.out.println("delta state: " + r.getState());
        } catch (RESTException re) {
            // handle exceptions here
            re.printStackTrace();
        }

        try {
            /* This portion showcases the Update Messages API call. */
            // Enter message id to update
            final String msgId = "ENTER VALUE!";
            // Update the message favorite status to true
            final Boolean isFavorite = true;
            // Update the unread status to true
            final Boolean isUnread = true;
            DeltaChange dc = new DeltaChange(msgId, isFavorite, isUnread);
            DeltaChange[] changes = { dc };
            // Send request for updating messages
            immnSrvc.updateMessages(changes);
            System.out.println("update messages was successful");
        } catch (RESTException re) {
            // handle exceptions here
            re.printStackTrace();
        }

        try {
            /* This portion showcases the Update Message API call. */
            // Enter message id to update
            final String msgId = "ENTER VALUE!";
            // Update the message favorite status to true
            final Boolean isFavorite = true;
            // Update the unread status to true
            final Boolean isUnread = true;
            // Send request for updating message
            immnSrvc.updateMessage(msgId, isUnread, isFavorite);
            System.out.println("update message was successful");
        } catch (RESTException re) {
            // handle exceptions here
            re.printStackTrace();
        }

        try {
            /* This portion showcases the Delete Messages API call. */
            // Enter message ids to delete
            final String[] msgIds = { "ENTER VALUE!" };
            // Send request for deleting messages
            immnSrvc.deleteMessages(msgIds);
            System.out.println("delete messages was successful");
        } catch (RESTException re) {
            // handle exceptions here
            re.printStackTrace();
        }

        try {
            /* This portion showcases the Create Message Index API call. */
            // Send request for creating message index
            immnSrvc.createMessageIndex();
            System.out.println("create message index was successful");
        } catch (RESTException re) {
            // handle exceptions here
            re.printStackTrace();
        }

        try {
            /* This portion showcases the Get Message Index Info API call. */
            // Send request for getting the message index info
            MessageIndexInfo r = immnSrvc.getMessageIndexInfo();
            System.out.println("index info state: " + r.getState());
        } catch (RESTException re) {
            // handle exceptions here
            re.printStackTrace();
        }

        try {
            /* This portion showcases the Get Notification Connection Details API call. */
            // Enter queues value
            final String queues = "ENTER VALUES!";
            // Send request for getting the notification connection details
            NotificationConnectionDetails r 
                = immnSrvc.getNotificationConnectionDetails(queues);
            System.out.println("username: " + r.getUsername());
        } catch (RESTException re) {
            // handle exceptions here
            re.printStackTrace();
        }
    }
}
