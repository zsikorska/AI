package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class Vertex {
    private final String name;
    private final double latitude;
    private final double longitude;
    private final HashMap<String, ArrayList<Edge>> neighbors = new HashMap<>();

    public Vertex() {
        this.name = "";
        this.latitude = 0;
        this.longitude = 0;
    }

    public Vertex(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public HashMap<String, ArrayList<Edge>> getNeighbours() {
        return neighbors;
    }

    public boolean addNeigbour(Edge edge) {
        if (edge.getStartStop().equals(name)){
            if (neighbors.containsKey(edge.getEndStop())) {
                neighbors.get(edge.getEndStop()).add(edge);
            }
            else {
                ArrayList<Edge> edges = new ArrayList<>();
                edges.add(edge);
                neighbors.put(edge.getEndStop(), edges);
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertex vertex = (Vertex) o;
        return name.equals(vertex.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, latitude, longitude, neighbors);
    }
}
