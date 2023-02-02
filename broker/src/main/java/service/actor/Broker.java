package service.actor;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import scala.concurrent.duration.Duration;
import service.messages.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Broker extends AbstractActor {
    //declare a hashmap to save the ApplicationResponse message
    static HashMap<Integer, ApplicationResponse> cache = new HashMap<>();
    //save all the actor refs which provide quotation service in a list
    static List<ActorRef> actorRefs = new ArrayList<>();
    static int SEED_ID = 0;
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                        .match(String.class,
                                        msg -> {
                                            if (msg.equals("register")) {
                                                //save the actorRefs who send the register message to the broker
                                                actorRefs.add(getSender());
                                                System.out.println(getSender()+"register");
                                            } else if (msg.equals("client coming")) {
                                                //once get the message of "client coming" from the client
                                                //tell the client that it can send the ApplicationRequest message
                                                getSender().tell("start",getSelf());
                                                System.out.println("send back message from broker to client");
                                            }
                                        })
                        .match(ApplicationRequest.class,msg -> {
                            //check if the cache has already saved this client's information
                            if(!cache.containsKey(msg.getId())) {
                                //if there is no information in cache of this client, then make a Application
                                // Response message for this client
                                cache.put(msg.getId(),new ApplicationResponse(msg.getId(), msg.getClientInfo()));
                                System.out.println("Create ApplicationResponse");
                            }
                            //send every ActorRef the Quotation Request Message of this client
                            for (ActorRef ref : actorRefs) {
                                ref.tell(new QuotationRequest(msg.getId(), msg.getClientInfo()), getSelf());
                            }
                            //wait for quotations sent back to the broker for a given period
                            System.out.println("send the quotationRequest");
                            getContext().system().scheduler().scheduleOnce(
                                            Duration.create(2, TimeUnit.SECONDS),
                                            getSelf(),
                                            new RequestDeadline(msg.getId(),getSender()),
                                            getContext().dispatcher(), null);
                        })
                        .match(QuotationResponse.class,quotationResponse -> {
                            //get quotation response from each service
                            //check if the quotation response id is the same as the one in cache
                            if(cache.get(quotationResponse.getId()) != null) {
                                //add corresponding quotations in the relevant QuotationResponse Message
                                cache.get(quotationResponse.getId()).quotations.add(quotationResponse.getQuotation());
                            }
                        })
                        .match(RequestDeadline.class,requestDeadline -> {
                            System.out.println("Request deadline");
                            //once get the request deadline message
                            //return quotation response message to the client
                            int key = requestDeadline.getSEED_ID();
                            ApplicationResponse applicationResponse_final = cache.get(key);
                            ActorRef actorRef = requestDeadline.getClientRef();
                            actorRef.tell(applicationResponse_final,getSelf());
                        }).build();
    }
}
