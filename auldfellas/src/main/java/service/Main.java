package service;

import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import service.actor.Init;
import service.actor.Quoter;
import service.auldfellas.AFQService;

/*
* AFQ Service send register message to the broker
* */
public class Main {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        ActorSystem system = ActorSystem.create();
        //Initialize the Quoter Class
        ActorRef ref = system.actorOf(Props.create(Quoter.class), "auldfellas");
        //Initialize the AFQService
        ref.tell(new Init(new AFQService()), null);
        //AFQ Service send register message to the broker
        ActorSelection selection = system.actorSelection("akka.tcp://default@"+host+":2551/user/broker");
        selection.tell("register", ref);
        System.out.println("auldfellas send message to broker");
    }
}
