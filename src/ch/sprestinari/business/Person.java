package ch.sprestinari.business;

import ch.sprestinari.model.Node;

import java.util.Date;

public class Person extends Node {
    private Date birth;
    private String city;
    /**
     * @return the nomPersonne
     */
    public Person(String nom) {
        super(nom);
    }
    public Person(String nom, String city) {
        super(nom);
        this.city=city;
    }

    public void addFriend(String edgeName, Person person, double weight){
        this.exitingEdgeList.putIfAbsent(edgeName, new IsFriend(edgeName, person,weight));
    }

    public void watch(String edgeName, StreamingSite streamingSite, double weight){
      this.exitingEdgeList.putIfAbsent(edgeName, new IsWatching(edgeName, streamingSite,weight));
    }

}
