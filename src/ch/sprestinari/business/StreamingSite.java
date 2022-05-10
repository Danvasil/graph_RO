package ch.sprestinari.business;

import ch.sprestinari.model.Node;

public class StreamingSite extends Node {
    private String url;

    /*** @return the nomPersonne*/
    public StreamingSite(String nom) {
        super(nom);
    }

    public StreamingSite(String name, String url) {
        super(name);
        this.url = url;
    }
}