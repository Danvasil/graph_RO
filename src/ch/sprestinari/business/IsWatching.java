package ch.sprestinari.business;

import ch.sprestinari.model.Edge;

public class IsWatching extends Edge {

    public IsWatching(String nom, StreamingSite streamingSite, Integer i) {
        super(nom, i, streamingSite);
    }
}
