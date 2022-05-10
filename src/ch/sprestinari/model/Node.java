package ch.sprestinari.model;

import java.util.*;

public class Node {
    private Integer dijkstraWeight;
    private Node dijkstraPred;
    private String name;
    protected Map<String, Edge> exitingEdgeList = new HashMap<>();
    private boolean mark;
    private Integer level;


    public Map<String, TripletDijkstra> getVpcc() {
        return vpcc;
    }

    public void setVpcc(Map<String, TripletDijkstra> vpcc) {
        this.vpcc = vpcc;
    }

    private Map<String, TripletDijkstra> vpcc;

    public Node(String name) {
        this.name = name;
    }

    public Collection<Edge> getExitingEdges() {
        return exitingEdgeList.values();
    }

    public void addExitingEdge(String edgeName, Integer weight, Node destination) {
        exitingEdgeList.putIfAbsent(edgeName, new Edge(edgeName, weight, destination));
    }

    // Pas demandé par le prof
    public void replaceExitingEdge(String edgeName, Integer weight, Node destination) {
        if (exitingEdgeList.get(edgeName) != null) {
            exitingEdgeList.put(edgeName, new Edge(edgeName, weight, destination));
        }
    }

    public void deleteExitingEdge(String edgeName) {
        exitingEdgeList.remove(edgeName);
    }

    // Supprime les arcs allant vers la destination précisée
    public void deleteExitingEdges(Node destination) {
        Iterator<Edge> iterator = exitingEdgeList.values().iterator();
        while (iterator.hasNext()) {
            Edge current = iterator.next();
            if (current.getDestination() == destination) {
                iterator.remove();
            }
        }
    }

    public String getName() {
        return name;
    }

    public boolean isMarked() {
        return mark;
    }

    public void setMark(boolean mark) {
        this.mark = mark;
    }

    public Integer getLevel() {return this.level;}

    public Node setLevel(Integer level) {
        this.level = level;
        return this;
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
        var sb = new StringBuilder();

        sb.append(String.format("w++(%s) = ", name));
        sb.append("{");
        exitingEdgeList.forEach((edgeName, edge) -> {
            sb.append(edge).append(", ");
        });
        if (exitingEdgeList.size() > 0) {
            sb.replace(sb.length() - 2, sb.length(), "");
        }
        sb.append("}");

        return sb.toString();
    }

    public boolean nodeExistInVpcc(Node node) {
        return null != this.vpcc.get(node.getName());
    }
    public void addToVPCC(TripletDijkstra triplet) {
        this.vpcc.putIfAbsent(triplet.getName(), triplet);
    }
}
