package ch.sprestinari.business;

import ch.sprestinari.model.Edge;

public class IsFriend extends Edge {
    //navigation uni-directionnelle
    public IsFriend(String nom, Person personneDest, Integer i) {
// public Arc(String n, Noeud dest, Noeud nsrc, double p)
        super(nom, i, personneDest);
    }
    //navigation bi-directionnelle PAS POUR MTNNNN
   // public EstAmi(String nom, Personne personneSrc, Personne personneDest, double i) {
// public Arc(String n, Noeud dest, Noeud nsrc, double p)
     //   super(nom,personneDest,personneSrc,i);
    //}
}