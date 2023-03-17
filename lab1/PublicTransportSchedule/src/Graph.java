import java.util.HashMap;

public class Graph {
    private final HashMap<String, Edge> neighbors;

    public Graph() {
        this.neighbors = new HashMap<>();
    }

    public Graph(HashMap<String, Edge> neighbors) {
        this.neighbors = neighbors;
    }

    public HashMap<String, Edge> getNeighbors() {
        return neighbors;
    }
}
