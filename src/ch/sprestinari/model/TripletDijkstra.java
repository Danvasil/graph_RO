package ch.sprestinari.model;

public class TripletDijkstra {
    private String name;
    private Integer dijkstraWeight;
    private Node dijkstraPred;

    public TripletDijkstra(String name, Integer dijkstraWeight, Node dijkstraPred) {
        this.name = name;
        this.dijkstraWeight = dijkstraWeight;
        this.dijkstraPred = dijkstraPred;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDijkstraWeight() {
        return dijkstraWeight;
    }

    public void setDijkstraWeight(Integer dijkstraWeight) {
        this.dijkstraWeight = dijkstraWeight;
    }

    public Node getDijkstraPred() {
        return dijkstraPred;
    }

    public void setDijkstraPred(Node dijkstraPred) {
        this.dijkstraPred = dijkstraPred;
    }

    @Override
    public String toString() {
        return "TripletDijkstra{" +
            "name='" + name + '\'' +
            ", dijkstraWeight=" + dijkstraWeight.intValue() +
            ", dijkstraPred=" + dijkstraPred +
            '}';
    }
}
