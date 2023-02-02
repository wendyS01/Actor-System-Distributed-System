package service.actor;

import akka.actor.AbstractActor;
import service.core.ClientInfo;
import service.core.Quotation;
import service.messages.ApplicationRequest;
import service.messages.ApplicationResponse;

import java.text.NumberFormat;
import java.util.HashMap;

public class Client extends AbstractActor {
    static int SEED_ID = 0;
    //initialize a cache to save the information
    HashMap<Integer,ClientInfo> cache = new HashMap<Integer,ClientInfo>();

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                        .match(String.class,
                                        msg -> {
                                            //Get the "start" message from the client and the client can send the
                                            // application request of each client to the broker
                                            if (!msg.equals("start")) return;
                                            for(ClientInfo info:clients) {
                                                ApplicationRequest applicationRequest = new ApplicationRequest(SEED_ID,info);
                                                cache.put(SEED_ID++,info);
                                                getSender().tell(applicationRequest,getSelf());
                                                System.out.println("send the application request");
                                            }
                                        })
                        .match(ApplicationResponse.class,applicationResponse -> {
                            //check if the application response message's id is tha same as one of key in the cache
                            if(!cache.containsKey(applicationResponse.getId()))
                            {return; } else {
                                //print the client info
                                displayProfile(applicationResponse.getClientInfo());
                                //loop through the quoations of client application message and print them
                                for(Quotation quotation:applicationResponse.getQuotations()) {
                                    displayQuotation(quotation);
                                }
                                System.out.println("\n");
                            }
                        }).build();
    }
    /**
     * Display the client info nicely.
     *
     * @param info
     */
    public static void displayProfile(ClientInfo info) {
        System.out.println("|=================================================================================================================|");
        System.out.println("|                                     |                                     |                                     |");
        System.out.println(
                        "| Name: " + String.format("%1$-29s", info.getName()) +
                                        " | Gender: " + String.format("%1$-27s", (info.getGender()==ClientInfo.MALE?
                                        "Male":"Female")) +
                                        " | Age: " + String.format("%1$-30s", info.getAge())+" |");
        System.out.println(
                        "| License Number: " + String.format("%1$-19s", info.getLicenseNumber()) +
                                        " | No Claims: " + String.format("%1$-24s", info.getNoClaims()+" years") +
                                        " | Penalty Points: " + String.format("%1$-19s", info.getPoints())+" |");
        System.out.println("|                                     |                                     |                                     |");
        System.out.println("|=================================================================================================================|");
    }

    /**
     * Display a quotation nicely - note that the assumption is that the quotation will follow
     * immediately after the profile (so the top of the quotation box is missing).
     *
     * @param quotation
     */
    public static void displayQuotation(Quotation quotation) {
        System.out.println(
                        "| Company: " + String.format("%1$-26s", quotation.getCompany()) +
                                        " | Reference: " + String.format("%1$-24s", quotation.getReference()) +
                                        " | Price: " + String.format("%1$-28s",
                                        NumberFormat.getCurrencyInstance().format(quotation.getPrice()))+" |");
        System.out.println("|=================================================================================================================|");
    }

    /**
     * Test Data
     */
    public static final ClientInfo[] clients = {
                    new ClientInfo("Niki Collier", ClientInfo.FEMALE, 43, 0, 5, "PQR254/1"),
                    new ClientInfo("Old Geeza", ClientInfo.MALE, 65, 0, 2, "ABC123/4"),
                    new ClientInfo("Hannah Montana", ClientInfo.FEMALE, 16, 10, 0, "HMA304/9"),
                    new ClientInfo("Rem Collier", ClientInfo.MALE, 44, 5, 3, "COL123/3"),
                    new ClientInfo("Jim Quinn", ClientInfo.MALE, 55, 4, 7, "QUN987/4"),
                    new ClientInfo("Donald Duck", ClientInfo.MALE, 35, 5, 2, "XYZ567/9")
    };
}
