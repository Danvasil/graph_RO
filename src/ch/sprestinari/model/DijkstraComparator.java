package ch.sprestinari.model;
import java.util.Comparator;

public class DijkstraComparator implements Comparator<Node> {
    @Override
    public int compare(Node o1, Node o2) {
        return Integer.compare(o1.getDijkstraWeight(), o2.getDijkstraWeight());
    }

    /*       if (n1.getDijkstraWeight() < n2.getDijkstraWeight()) return -1;
            else if(n1.getDijkstraWeight() < n2.getDijkstraWeight()) return 1;
            else return 0;
    */
    }

