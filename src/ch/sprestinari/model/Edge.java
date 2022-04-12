package ch.sprestinari.model;

public class Edge {
    private String name;
    private double weight;
    private Node destination;

    public Edge(String name, double weight, Node destination) {
        this.name = name;
        this.weight = weight;
        this.destination = destination;
    }

    public Node getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s, %.0f)", name, destination.getName(), weight);
    }

    public String getName() {
        return name;
    }
}
