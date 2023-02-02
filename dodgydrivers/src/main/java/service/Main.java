package service;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import service.actor.Init;
import service.actor.Quoter;
import service.dodgydrivers.DDQService;

/*
 * DDQ Service send register message to the broker
 * */
public class Main {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        //Initialize the Quoter Class
        ActorSystem system = ActorSystem.create();
        ActorRef ref = system.actorOf(Props.create(Quoter.class), "dodgydrivers");
        //Initialize the DDQService
        ref.tell(new Init(new DDQService()), null);
        //DDQ Service send register message to the broker
        ActorSelection selection = system.actorSelection("akka.tcp://default@"+host+":2551/user/broker");
        selection.tell("register", ref);
    }
}
