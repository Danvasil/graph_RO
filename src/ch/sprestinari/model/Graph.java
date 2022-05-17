package ch.sprestinari.model;

import java.util.*;

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

    public void addEdge(String sourceName, String destName, String edgeName, Integer weight) {
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
                    if (edge.getClass() == nodeType) {
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

    public void resetLevel(List<Node> nodes) {
        nodes.forEach(node -> node.setLevel(null));
    }

    public void dijkstra(Node start) {
        System.out.println("RUNNING DIJKSTRA...\n");
        //variables temporaires de traitement
        this.reInitAll();

        Node current_n = null;
        Node dest_n;
        Edge tmp_edge;
        Integer current_w;
        List<Node> priorityList = new ArrayList<Node>();
        start.setDijkstraWeight(0);
        priorityList.add(start);
        start.setVpcc(new HashMap<String, TripletDijkstra>());

        while (!priorityList.isEmpty()) {
            Collections.sort(priorityList, new DijkstraComparator());
            current_n = (Node) priorityList.remove(0);

            TripletDijkstra triplet = new TripletDijkstra(current_n.getName(),
                current_n.getDijkstraWeight(), current_n.getDijkstraPred());
            System.out.println(triplet);
            start.addToVPCC(triplet);

            for (Iterator ita = current_n.getExitingEdges().iterator(); ita.hasNext(); ) {
                tmp_edge = (Edge) ita.next();
                dest_n = tmp_edge.getDestination();
                current_w = current_n.getDijkstraWeight() + tmp_edge.getWeight();

                if (dest_n.getDijkstraWeight() == Integer.MAX_VALUE) {
                    priorityList.add(dest_n);
                }
                if (current_w < dest_n.getDijkstraWeight()) {
                    dest_n.setDijkstraWeight(current_w);
                    dest_n.setDijkstraPred(current_n);
                }

            }
        }
        System.out.println("Dijkstra fini" + start.getVpcc().size());
    }

    private void reInitAll() {
        List<Node> listNode = new ArrayList<Node>(nodeList.values());

        for (Node n : listNode) {
            //MAX_VALUE = infini
            n.setDijkstraWeight(Integer.MAX_VALUE);
            n.setDijkstraPred(null);
        }

    }

    private AbstractSequentialList<Node> shortestPath(Node startNode, Node destinationNode) throws Exception {
        if (startNode.getVpcc().isEmpty()) {
            this.dijkstra(startNode);
        }
        if (null == destinationNode) {
            throw new Exception("Pas de chemin car la destination est null");
        }
        if (!startNode.nodeExistInVpcc(destinationNode)) {
            throw new Exception(String.format("Aucun chemin possible entre %s et %s", startNode.getName(), destinationNode.getName()));
        }
        var path = new LinkedList<Node>();
        while (destinationNode != null) {
            path.addFirst(destinationNode);
            destinationNode = destinationNode.getDijkstraPred();
        }
        return path;
    }

    public void printShortestWay(Node startNode, Node destinationNode) throws Exception {
        var path = this.shortestPath(startNode, destinationNode);
        var eol = System.getProperty("line.separator");
        StringBuilder sb = new StringBuilder();
        sb.append("Path cost is ").append(destinationNode.getDijkstraWeight()).append(eol);
        sb.append("Step ares: ").append(eol);
        for (Node step : path) {
            sb.append("    ").append(step.getName());
        }
        sb.append(eol);
        System.out.println(sb.toString());
    }

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