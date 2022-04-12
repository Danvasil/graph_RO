package ch.sprestinari.application;

import ch.sprestinari.model.Graph;
import ch.sprestinari.model.Node;

public class Main {
    public static void main(String[] args) {
        Graph g = new Graph("G1");
        g.addNode("A");
        g.addNode("B");
        g.addNode("C");
        g.addNode("D");
        g.addNode("E");
        g.addEdge("A", "C", "u1", 1);
        g.addEdge("A", "B", "u2", 1);
        g.addEdge("A", "D", "u3", 2);
        // Tout ce qui est après n'est pas visible
        g.addEdge("B", "D", "u4", 1);
        g.addEdge("C", "D", "u5", 1);
        g.addEdge("D", "E", "u6", 1);
        g.addEdge("C", "E", "u7", 1);
        Node a = g.findNode("A");
        for (Node n: g.limitedWidthWay(a, 1)) {
            System.out.println(n.getName());
        }
    }
}
