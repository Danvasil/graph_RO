package ch.sprestinari.application;

import ch.sprestinari.business.IsFriend;
import ch.sprestinari.business.IsWatching;
import ch.sprestinari.business.Person;
import ch.sprestinari.business.StreamingSite;
import ch.sprestinari.model.Graph;
import ch.sprestinari.model.Node;

import java.util.List;

public class MainStreaming {
    public static void main(String[] args) {
        Graph grapheFriendshipAndStreaming = new Graph("amitie");

        Person paul = new Person("Paul", "Neuchâtel");
        Person jean = new Person("Jean", "Neuchâtel");
        Person albert = new Person("Albert", "Lausanne");
        Person lucie = new Person("Lucie", "Neuchâtel");
        Person julie = new Person("Julie", "Cernier");
        Person alfred = new Person("Alfred", "Lausanne");

        grapheFriendshipAndStreaming.addNode(paul);
        grapheFriendshipAndStreaming.addNode(jean);
        grapheFriendshipAndStreaming.addNode(albert);
        grapheFriendshipAndStreaming.addNode(lucie);
        grapheFriendshipAndStreaming.addNode(julie);
        grapheFriendshipAndStreaming.addNode(alfred);

        // friendShip
        paul.addFriend("a1", lucie,100);
        paul.addFriend("a2", jean, 2);
        paul.addFriend("a3",julie,80);
        jean.addFriend("a4",alfred,9);
        julie.addFriend("a5",albert,50);

        // watch
        StreamingSite netflix = new StreamingSite("NetFlix", "netflix.com");
        StreamingSite primeVideo = new StreamingSite("Amazon Prime Video", "primevideo.com");
        StreamingSite disney = new StreamingSite("Disney +", "disneyplus.com");

        grapheFriendshipAndStreaming.addNode(netflix);
        grapheFriendshipAndStreaming.addNode(primeVideo);
        grapheFriendshipAndStreaming.addNode(disney);

        //ajout des préférences de Streaming
        paul.watch("s1",netflix,100000);
        paul.watch("s2",primeVideo,10);
        lucie.watch("s3",primeVideo,20);
        lucie.watch("s4",netflix,1000);
        alfred.watch("s5",netflix,10000);
        albert.watch("s6",primeVideo,10);
        albert.watch("s6",netflix,50);

        System.out.println(grapheFriendshipAndStreaming.toString());
        System.out.println();
        System.out.println("Q1");
        for (Node n:  grapheFriendshipAndStreaming.limitedWidthWayTyped(grapheFriendshipAndStreaming.findNode("Paul"), IsWatching.class, 1)) {
            System.out.print(n.getName()+", ");
        }

        System.out.println();

        System.out.println("Q2");
        for (Node n:  grapheFriendshipAndStreaming.limitedWidthWayTyped(grapheFriendshipAndStreaming.findNode("Paul"), IsFriend.class, 2)) {
            if (!grapheFriendshipAndStreaming.limitedWidthWayTyped(n, IsWatching.class, 1).isEmpty())
                {System.out.print(n.getName() +", ");}
        }

        System.out.println();

        System.out.println("Q3");

        for (Node p: (List<Node>)grapheFriendshipAndStreaming.limitedWidthWayTyped(grapheFriendshipAndStreaming.findNode("Paul"),IsFriend.class,1)){
            if (((Person)p).getCity().equals("Neuchâtel")){
                if ((grapheFriendshipAndStreaming.limitedWidthWayTyped(p, IsWatching.class, 1)).contains((StreamingSite) grapheFriendshipAndStreaming.findNode("Amazon Prime Video"))){
                    System.out.print(p.getName() + ", ");
                }
            }
        }

    }
}
