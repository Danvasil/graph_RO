package ch.sprestinari.model;

public class Edge {
    private String name;
    private Integer weight;
    private Node destination;

    public Edge(String name, Integer weight, Node destination) {
        this.name = name;
        this.weight = weight;
        this.destination = destination;
    }

    public Node getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return String.format("(%s, %s, %d)", name, destination.getName(), weight);
    }

    public String getName() {
        return name;
    }

    public Integer getWeight() {
        return weight;
    }
}
