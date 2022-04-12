package ch.sprestinari.application;

import ch.sprestinari.business.IsFriend;
import ch.sprestinari.business.Person;
import ch.sprestinari.model.Graph;
import ch.sprestinari.model.Node;

public class MainFacebook {
    public static void main(String[] args) {

        Graph grapheAmitie = new Graph("amitie");
        Person jean = new Person("Jean", "Neuchâtel");
        Person paul = new Person("Paul", "Bienne");
        Person carlos = new Person("Carlos", "CHX");
        Person julie = new Person("Julie", "CHX");
        grapheAmitie.addNode(jean);
        grapheAmitie.addNode(paul);
        grapheAmitie.addNode(carlos);
        grapheAmitie.addNode(julie);
        //System.out.println("test type " + jean.getClass());
        jean.addFriend("a1", paul, 34);
        jean.addFriend("a2", carlos, 25);
        carlos.addFriend("a3", julie, 25);
        jean.addFriend("a4", carlos, 5);
        //jean.aime(....)
        //jean.cuisine(....)
        System.out.println(grapheAmitie.toString());
        for (Node n:  grapheAmitie.limitedWidthWayTyped(grapheAmitie.findNode("Jean"), IsFriend.class, 2)) {
            System.out.println(n.getName());
        }
    }
}