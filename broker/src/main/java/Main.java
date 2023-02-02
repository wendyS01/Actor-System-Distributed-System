import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import service.actor.Broker;

public class Main {
    public static void main(String[] args) {
        //set up the Actor System
        ActorSystem system = ActorSystem.create();
        //Initialize the Broker to handle messages to the broker
        system.actorOf(Props.create(Broker.class), "broker");
        System.out.println("Broker start");
    }
}
