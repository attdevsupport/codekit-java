package com.att.example;
// This Quickstart Guide for the Address Book API requires the Java code kit,
// which can be found at: https://github.com/attdevsupport/codekit-java

import com.att.api.aab.service.AABService;
import com.att.api.aab.service.Address;
import com.att.api.aab.service.Contact;
import com.att.api.aab.service.ContactResultSet;
import com.att.api.aab.service.Email;
import com.att.api.aab.service.Group;
import com.att.api.aab.service.GroupResultSet;
import com.att.api.aab.service.Im;
import com.att.api.aab.service.Phone;
import com.att.api.aab.service.QuickContact;
import com.att.api.aab.service.WebURL;
import com.att.api.oauth.OAuthService;
import com.att.api.oauth.OAuthToken;
import com.att.api.rest.RESTException;

// Import the relevant code kit parts.

public class App {

    private static void setProxySettings() {
        // If a proxy is required, 
        // uncomment the following line to set the proxy.  
        // RESTConfig.setDefaultProxy("proxy.host", 8080);
    }

    public static void main(String[] args) {
        setProxySettings();

        // Use the app account settings from developer.att.com for the
        // following values. Make sure that the API scope is set to AAB for the
        // Address Book API before retrieving the App Key and App Secret.
        //final String fqdn = "https://api.att.com";
        final String fqdn = "http://localhost:8888";

        // Enter the value from the 'App Key' field obtained at
        // developer.att.com in your app account.
        final String clientId = "ENTER VALUE!";

        // Enter the value from the 'App Secret' field obtained at
        // developer.att.com in your app account.
        final String clientSecret = "ENTER VALUE!";

        // Create a service for requesting an OAuth access token.
        OAuthService osrvc = new OAuthService(fqdn, clientId, clientSecret);

        // Get the OAuth access code by opening a browser to the following URL:
        // https://api.att.com/oauth/v4/authorize?client_id=CLIENT_ID&scope=SCOPE&redirect_uri=REDIRECT_URI
        // replacing CLIENT_ID, SCOPE, and REDIRECT_URI with the values
        // configured at developer.att.com. After authenticating, copy the
        // OAuth code from the browser URL.
        final String oauthCode = "ENTER VALUE!";

        OAuthToken token;
        try {
            // Get the OAuth access token using the OAuth code.
            token = osrvc.getTokenUsingCode(oauthCode);
        } catch (RESTException re) {
            re.printStackTrace();
            return;
        }

        // Create the service for interacting with the Address Book API.
        AABService aabSrvc = new AABService(fqdn, token);

        // setup names
        String contactName = "Isaac";
        String groupName = "Example";

        // The following lines of code show the possible method calls for
        // the AABService class; to test only one method, comment out the 
        // other try/catch blocks.
        
        String contactId = null;

        try {
            Contact contact = new Contact.Builder()
                .setFirstName(contactName)
                .setLastName("Newton")
                .build();

            String location = aabSrvc.createContact(contact);

            System.out.println("Created new contact.");
            System.out.println("Location: " + location);

            //Contact id is the last portion of the location url
            String[] split = location.split("/");
            contactId = split[split.length - 1];
        } catch (RESTException re) {
            // Handle exceptions here.
            re.printStackTrace();
        }

        try {
            Contact contact = new Contact.Builder()
                .setLastName("Asimov")
                .build();

            aabSrvc.updateContact(contact, contactId);

            System.out.println("Successfully updated contact's name!");
        } catch (RESTException re) {
            // Handle exceptions here.
            re.printStackTrace();
        }

        try {
            ContactResultSet contacts = aabSrvc.getContacts(contactName);

            System.out.println("Search results for: " + contactName);
            System.out.println("Total records: " + contacts.getTotalRecords());
            if (contacts.getContacts() != null) {
                for (Contact contact : contacts.getContacts()) {
                    System.out.println("Contact: ");
                    printContact(contact);
                }
            }
            if (contacts.getQuickContacts() != null) {
                for (QuickContact contact : contacts.getQuickContacts()) {
                    System.out.println("Contact: ");
                    printQuickContact(contact);
                }
            }
            System.out.println();
        } catch (RESTException re) {
            // Handle exceptions here.
            re.printStackTrace();
        }

        String groupId = null;
        try {
            Group group = new Group(groupName);

            String location = aabSrvc.createGroup(group);

            System.out.println("Created new group.");
            System.out.println("Location: " + location);

            //Contact id is the last portion of the location url
            String[] split = location.split("/");
            groupId = split[split.length - 1];
        } catch (RESTException re) {
            // Handle exceptions here.
            re.printStackTrace();
        }

        try {
            aabSrvc.addContactsToGroup(groupId, contactId);

            System.out.println("Added " + contactId + " to group: " + groupId);
        } catch (RESTException re) {
            // Handle exceptions here.
            re.printStackTrace();
        }

        try {
            GroupResultSet grs = aabSrvc.getContactGroups(contactId);
            for(Group group : grs.getGroups()) {
                printGroup(group);
                System.out.println();
            }
        } catch (RESTException re) {
            // Handle exceptions here.
            re.printStackTrace();
        }

        try {
            aabSrvc.removeContactsFromGroup(groupId, contactId);
            System.out.println("Removed " + contactId 
                    + " from group " + groupId);
        } catch (RESTException re) {
            // Handle exceptions here.
            re.printStackTrace();
        }

        try {
            GroupResultSet grs = aabSrvc.getGroups(groupName);
            for(Group group : grs.getGroups()) {
                printGroup(group);
                System.out.println();
            }
        } catch (RESTException re) {
            // Handle exceptions here.
            re.printStackTrace();
        }

        try {
            aabSrvc.deleteGroup(groupId);
            System.out.println("Deleted group: " + groupId);
        } catch (RESTException re) {
            // Handle exceptions here.
            re.printStackTrace();
        }

        try {
            aabSrvc.deleteContact(contactId);
            System.out.println("Deleted contact: " + contactId);
        } catch (RESTException re) {
            // Handle exceptions here.
            re.printStackTrace();
        }

        try {
            Contact contact = new Contact.Builder()
                .setFirstName(contactName)
                .setLastName("Newton")
                .build();

            aabSrvc.updateMyInfo(contact);
            System.out.println("MyInfo updated");
        } catch (RESTException re) {
            // Handle exceptions here.
            re.printStackTrace();
        }

        try {
            Contact me = aabSrvc.getMyInfo();
            printContact(me);
        } catch (RESTException re) {
            // Handle exceptions here.
            re.printStackTrace();
        }
    }

    private static void printGroup(Group group) {
        System.out.println("Group name: " + group.getGroupName());
        System.out.println("Group type: " + group.getGroupType());
        System.out.println("Group id: " + group.getGroupId());
    }

    private static void printQuickContact(QuickContact contact) {
        System.out.println(contact.getFormattedName());
        System.out.println(contact.getPrefix());
        System.out.println(contact.getFirstName());
        System.out.println(contact.getNickname());
        System.out.println(contact.getMiddleName());
        System.out.println(contact.getLastName());
        System.out.println(contact.getSuffix());

        if (contact.getAddress() != null) {
                printAddress(contact.getAddress());
                System.out.println();
        }
        
        if (contact.getPhone() != null) {
                printPhone(contact.getPhone());
                System.out.println();
        }

        if (contact.getEmail() != null) {
                printEmail(contact.getEmail());
                System.out.println();
        }

        if (contact.getIm() != null) {
                printIm(contact.getIm());
                System.out.println();
        }
    }

    private static void printContact(Contact contact) {
        System.out.println(contact.getFormattedName());
        System.out.println(contact.getPrefix());
        System.out.println(contact.getFirstName());
        System.out.println(contact.getNickname());
        System.out.println(contact.getMiddleName());
        System.out.println(contact.getLastName());
        System.out.println(contact.getSuffix());
        System.out.println("Spouse: " + contact.getSpouse());
        System.out.println("Anniversary: " + contact.getAnniversary());
        System.out.println("Assistant: " + contact.getAssistant());
        System.out.println("Children: " + contact.getChildren());
        System.out.println("Creation date: " + contact.getCreationDate());
        System.out.println("Org: " + contact.getOrganization());

        if (contact.getAddresses() != null) {
            for(Address addr : contact.getAddresses()) {
                printAddress(addr);
                System.out.println();
            }
        }
        
        if (contact.getPhones() != null) {
            for(Phone phone : contact.getPhones()){
                printPhone(phone);
                System.out.println();
            }
        }

        if (contact.getWeburls() != null) {
            for(WebURL url : contact.getWeburls()){
                printWeburl(url);
                System.out.println();
            }
        }

        if (contact.getEmails() != null) {
            for(Email email : contact.getEmails()){
                printEmail(email);
                System.out.println();
            }
        }

        if (contact.getIms() != null) {
            for (Im im : contact.getIms()){
                printIm(im);
                System.out.println();
            }
        }
    }

    private static void printWeburl(WebURL url) {
        System.out.println("Weburls:");
        System.out.println("URL: " + url.getUrl());
        System.out.println("Type: " + url.getType());
        System.out.println("Preferred: " + url.getPreferred());
    }

    private static void printEmail(Email email) {
        System.out.println("Email:");
        System.out.println("Address: " + email.getEmailAddress());
        System.out.println("Type: " + email.getType());
        System.out.println("Preferred: " + email.getPreferred());
    }

    private static void printIm(Im im) {
        System.out.println("IM:");
        System.out.println("URI: " + im.getImUri());
        System.out.println("Type: " + im.getType());
        System.out.println("Preferred: " + im.getPreferred());
    }

    private static void printAddress(Address addr) {
        System.out.println("Address:");
        System.out.println("\tType: " + addr.getType());
        System.out.println("\tPreferred: " + addr.getPreferred());
        System.out.println("\tPO Box: " + addr.getPoBox());
        System.out.println("\tAddress Line One: " + addr.getAddressLineOne());
        System.out.println("\tAddress Line Two: " + addr.getAddressLineTwo());
        System.out.println("\tCity: " + addr.getCity());
        System.out.println("\tCountry: " + addr.getCountry());
        System.out.println("\tZipcode: " + addr.getZipcode());
    }

    private static void printPhone(Phone phone) {
        System.out.println("Phone:");
        System.out.println("Number: " + phone.getNumber());
        System.out.println("Type: " + phone.getType());
        System.out.println("Preferred: " + phone.getPreferred());
    }
}
