import akka.actor.ActorRef;
import akka.actor.ActorSelection;
import akka.actor.ActorSystem;
import akka.actor.Props;
import service.actor.Client;

public class Main {
    public static void main(String[] args) {
        String host = "127.0.0.1";
        //setting up for the actor system
        ActorSystem system = ActorSystem.create();
        ActorSelection selection = system.actorSelection("akka.tcp://default@"+host+":2551/user/broker");
        //initialize the client class and register broker with client
        ActorRef ref = system.actorOf(Props.create(Client.class), "client");
        selection.tell("client coming",ref);
        System.out.println("send the connect message from client to the broker");
    }
}
