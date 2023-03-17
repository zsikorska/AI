package graph;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
    private final HashMap<String, ArrayList<Edge>> neighbors;

    public Graph() {
        this.neighbors = new HashMap<>();
    }

    public Graph(HashMap<String, ArrayList<Edge>> neighbors) {
        this.neighbors = neighbors;
    }

    public HashMap<String, ArrayList<Edge>> getGraph() {
        return neighbors;
    }

    public void addEdge(Edge edge) {
        if (neighbors.containsKey(edge.getStartStop())) {
            neighbors.get(edge.getStartStop()).add(edge);
        } else {
            ArrayList<Edge> edges = new ArrayList<>();
            edges.add(edge);
            neighbors.put(edge.getStartStop(), edges);
        }
    }


    @Override
    public String toString() {
        return "graph.Graph{" +
                "neighbors=" + neighbors +
                '}';
    }

    public void printGraph(){
        System.out.println("Graph:");
        for (String key : neighbors.keySet()) {
            System.out.println(key + ": " + neighbors.get(key) + "\n");
        }

    }
}
