package graph;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
    private final HashMap<String, Vertex> vertexes;

    public Graph() {
        this.vertexes = new HashMap<>();
    }

    public Graph(HashMap<String, Vertex> vertexes) {
        this.vertexes = vertexes;
    }

    public HashMap<String, Vertex> getGraph() {
        return vertexes;
    }

    public boolean addVertex(Vertex vertex) {
        if (!vertexes.containsKey(vertex.getName())) {
            vertexes.put(vertex.getName(), vertex);
            return true;
        }
        return false;
    }

    public boolean containsVertex(String name) {
        return vertexes.containsKey(name);
    }

    public Vertex getVertex(String name) {
        return vertexes.get(name);
    }

    public void sortAllEdges() {
        for (String key : vertexes.keySet()) {
            vertexes.get(key).sortEdges();
        }
    }

    @Override
    public String toString() {
        return "graph.Graph{" +
                "vertexes=" + vertexes +
                '}';
    }

    public void printGraph(){
        System.out.println("Graph:");
        for (String key : vertexes.keySet()) {
            System.out.println(key + ": " + vertexes.get(key) + "\n");
        }

    }

    public ArrayList<String> getVertexesNames() {
        return new ArrayList<>(vertexes.keySet());
    }
}
