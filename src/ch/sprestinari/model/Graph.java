package ch.sprestinari.model;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Graph {
    private String name;
    private Map<String, Node> nodeList = new HashMap<>();

    public Graph(String name) {
        this.name = name;
    }

    public void addNode(String nodeName) {
        nodeList.putIfAbsent(nodeName, new Node(nodeName));
    }

    public void addNode(Node node) {
        nodeList.putIfAbsent(node.getName(), node);
    }

    public void deleteNode(String nodeName) {
        // Il faut aussi supprimer tous les noeuds entrants. Pour faire cela,
        // on parcourt la liste des noeuds et on supprime tous leurs arcs sortant
        // vers le noeud que l'on veut supprimer.
        Node nodeToDelete = nodeList.get(nodeName);
        nodeList.forEach((name, node) -> {
            node.deleteExitingEdges(nodeToDelete);
        });
        /* Alternative pour parcourir une map :
        for (Map.Entry<String, Node> entry : nodeList.entrySet()) {
            entry.getValue().deleteExitingEdges(nodeToDelete);
        } */

        nodeList.remove(nodeName);
    }

    public Node findNode(String nodeName) {
        return nodeList.get(nodeName);
    }

    public void addEdge(String sourceName, String destName, String edgeName, double weight) {
        Node source = findNode(sourceName);
        Node dest = findNode(destName);
        if (source == null || dest == null) {
            return;
        }

        source.addExitingEdge(edgeName, weight, dest);
    }

    public void addEdge(String sourceName, String destName, String edgeName) {
        addEdge(sourceName, destName, edgeName, 0);
    }

    public void deleteEdge(String nodeName, String edgeName) {
        Node node = findNode(nodeName);
        if (node == null) {
            return;
        }

        node.deleteExitingEdge(edgeName);
    }

    public List<Node> widthWay(Node startNode) {
        LinkedList<Node> memory = new LinkedList<>();
        List<Node> path = new LinkedList<>();

        startNode.setMark(true);
        memory.addFirst(startNode);

        while (!memory.isEmpty()) {
            Node current = memory.removeLast();
            path.add(current);

            for (Edge edge : current.getExitingEdges()) {
                Node destination = edge.getDestination();
                if (!destination.isMarked()) {
                    destination.setMark(true);
                    memory.addFirst(destination);
                }
            }
        }

        unmark(path);
        return path;
    }

    public List<Node> limitedWidthWay(Node startNode, int limit) {
        // La file
        var memory = new LinkedList<Node>();
        // Le chemin emprunté
        var path = new LinkedList<Node>();
        // Le premier est marqué
        startNode.setLevel(0);
        startNode.setMark(true);
        // Ajouter au début de la liste le noeud racine
        memory.addFirst(startNode);
        while (!memory.isEmpty()) {
            var current = memory.removeLast();
            path.add(current);
            if (current.getLevel() < limit) {
                for (var edge : current.getExitingEdges()) {
                    var destination = edge.getDestination();
                    if (!destination.isMarked()) {
                        destination.setMark(true);
                        memory.addFirst(destination);
                        destination.setLevel(current.getLevel() + 1);
                    }
                }
            }
        }
        unmark(path);
        resetLevel(path);
        return path;
    }

    public List<Node> limitedWidthWayTyped(Node startNode, Class nodeType, int limit) {
        // La file
        var memory = new LinkedList<Node>();
        // Le chemin emprunté
        var path = new LinkedList<Node>();
        // Le premier est marqué
        startNode.setLevel(0);
        startNode.setMark(true);
        // Ajouter au début de la liste le noeud racine
        memory.addFirst(startNode);
        while (!memory.isEmpty()) {
            var current = memory.removeLast();
            path.add(current);
            if (current.getLevel() < limit) {
                for (var edge : current.getExitingEdges()) {
                    if (edge.getClass() == nodeType){
                        var destination = edge.getDestination();
                        if (!destination.isMarked()) {
                            destination.setMark(true);
                            memory.addFirst(destination);
                            destination.setLevel(current.getLevel() + 1);
                        }
                    }

                }
            }
        }
        unmark(path);
        resetLevel(path);
        return path;
    }

    public List<Node> depthWay(Node startNode) {
        LinkedList<Node> memory = new LinkedList<>();
        List<Node> path = new LinkedList<>();

        startNode.setMark(true);
        memory.addLast(startNode);

        while (!memory.isEmpty()) {
            Node current = memory.removeLast();
            path.add(current);

            for (Edge edge : current.getExitingEdges()) {
                Node destination = edge.getDestination();
                if (!destination.isMarked()) {
                    destination.setMark(true);
                    memory.addLast(destination);
                }
            }
        }

        unmark(path);
        return path;
    }

    public void unmark(List<Node> nodes) {
        nodes.forEach(node -> node.setMark(false));
    }

    public void resetLevel(List<Node> nodes) {nodes.forEach(node -> node.setLevel(null));}

    @Override
    public String toString() {
        var sb = new StringBuilder();

        sb.append("Graph: ").append(name).append("\n\n");

        nodeList.forEach((k, node) -> {
            sb.append(node);
            sb.append("\n");
        });

        return sb.toString();
    }
}
