package service.actor;

import akka.actor.AbstractActor;
import service.core.Quotation;
import service.core.QuotationService;
import service.messages.QuotationRequest;
import service.messages.QuotationResponse;

public class Quoter extends AbstractActor {
    private QuotationService service;
    //receive the QuotationRequest message from the broker
    // or init message to initialize DDQ Service
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                        .match(QuotationRequest.class,
                                        msg -> {
                                            //Get the clientInfo from this QuotationRequest Message
                                            Quotation quotation = service.generateQuotation(msg.getClientInfo());
                                            //Generate a QuotationResponse message and send it back to the broker
                                            getSender().tell(new QuotationResponse(msg.getId(), quotation), getSelf());
                                        })
                        .match(Init.class,
                                        msg->{
                                            //to initialize DDQ Service
                                            service = msg.getQuotationService();
                                        })
                        .build();
    }
}
