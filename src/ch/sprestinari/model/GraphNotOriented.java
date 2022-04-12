package ch.sprestinari.model;

import ch.sprestinari.utils.BiHashMap;

public class GraphNotOriented {
    private BiHashMap<String, String, String> matrix;

    public GraphNotOriented() {
        this.matrix = new BiHashMap<>();
    }

    public void addEdge(String source, String destination, String value) {
        matrix.put(source, destination, value);
        matrix.put(destination, source, value);
    }

    public String findNode(String nodeName) {
        if (matrix.containsKey(nodeName)) {
            return nodeName;
        } else {
            return null;
        }
    }

    //...
}
