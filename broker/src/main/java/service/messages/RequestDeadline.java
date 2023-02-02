package service.messages;

import akka.actor.ActorRef;

//Make a java bean of RequestDeadline Class
public class RequestDeadline implements MySerializable {
    //property declaration
    private int SEED_ID;
    private ActorRef clientRef;
    //constructors
    public RequestDeadline(int SEED_ID,ActorRef clientRef) {
        this.SEED_ID = SEED_ID;
        this.clientRef = clientRef;
    }
    //getters and setters
    public int getSEED_ID() {
        return SEED_ID;
    }

    public void setSEED_ID(int SEED_ID) {
        this.SEED_ID = SEED_ID;
    }

    public ActorRef getClientRef() { return clientRef; }

    public void setClientRef(ActorRef clientRef) {
        this.clientRef = clientRef;
    }
}
