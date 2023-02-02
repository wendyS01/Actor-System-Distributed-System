package service;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import service.actor.Init;
import service.actor.Quoter;
import service.girlpower.GPQService;

/*
 * GPQ Service send register message to the broker
 * */
public class Main {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        ActorSystem system = ActorSystem.create();
        //Initialize the Quoter Class
        ActorRef ref = system.actorOf(Props.create(Quoter.class), "girlpower");
        //Initialize the DDQService
        ref.tell(new Init(new GPQService()), null);
        //DDQ Service send register message to the broker
        ActorSelection selection = system.actorSelection("akka.tcp://default@"+host+":2551/user/broker");
        selection.tell("register", ref);
    }
}