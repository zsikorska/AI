import algorithms.AStar;
import graph.Edge;
import graph.Graph;
import algorithms.Dijkstra;
import graph.Vertex;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static Graph readGraph(String filename) throws FileNotFoundException {
        Graph graph = new Graph();
        Scanner scanner = new Scanner(new File(filename));
        scanner.nextLine(); // skip header

        while(scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] tokens = line.split(",");

            Vertex startVertex;
            if(!graph.containsVertex(tokens[5])) {
                startVertex = parseStartVertex(tokens);
                graph.addVertex(startVertex);
            }
            else{
                startVertex = graph.getVertex(tokens[5]);
            }

            if(!graph.containsVertex(tokens[6])) {
                Vertex endVertex = parseEndVertex(tokens);
                graph.addVertex(endVertex);
            }

            Edge edge = parseEdge(tokens);
            startVertex.addNeigbour(edge);

        }
        return graph;
    }

    public static Edge parseEdge(String[] tokens) {
        //id,company,line,departure_time,arrival_time,start_stop,end_stop,start_stop_lat,start_stop_lon,end_stop_lat,end_stop_lon
        int id = Integer.parseInt(tokens[0]);
        String company = tokens[1];
        String line = tokens[2];
        LocalTime departureTime = parseTime(tokens[3]);
        LocalTime arrivalTime = parseTime(tokens[4]);
        String startStop = tokens[5];
        String endStop = tokens[6];
        double startLatitude = Double.parseDouble(tokens[7]);
        double startLongitude = Double.parseDouble(tokens[8]);
        double endLatitude = Double.parseDouble(tokens[9]);
        double endLongitude = Double.parseDouble(tokens[10]);

        return new Edge(id, company, line, startStop, departureTime, endStop, arrivalTime);
    }

    public static Vertex parseStartVertex(String[] tokens) {
        String name = tokens[5];
        double startLatitude = Double.parseDouble(tokens[7]);
        double startLongitude = Double.parseDouble(tokens[8]);

        return new Vertex(name, startLatitude, startLongitude);
    }

    public static Vertex parseEndVertex(String[] tokens) {
        String name = tokens[6];
        double endLatitude = Double.parseDouble(tokens[9]);
        double endLongitude = Double.parseDouble(tokens[10]);

        return new Vertex(name, endLatitude, endLongitude);
    }

    public static LocalTime parseTime(String time) {
        // change hour bigger than 24 to 24 format
        String[] tokens = time.split(":");
        int hour = Integer.parseInt(tokens[0]);
        int minute = Integer.parseInt(tokens[1]);
        if(hour >= 24) {
            hour = hour - 24;
        }
        return LocalTime.of(hour, minute);
    }

    public static void printPath(ArrayList<Edge> path) {
        for (Edge edge : path) {
            System.out.println(edge.getId() + " " + edge.getLine() + " " + edge.getStartStop() + " " + edge.getDepartureTime() + " " + edge.getEndStop() + " " + edge.getArrivalTime());
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        Graph graph = readGraph("connection_graph.csv");
        //graph.printGraph();

        double startTime = System.nanoTime();
        //System.out.println(Dijkstra.findShortestPath("Nowowiejska", "FAT", LocalTime.of(1, 0), graph));
        //System.out.println(AStar.findShortestPath("Nowowiejska", "FAT", LocalTime.of(1, 0), graph));
        //ArrayList<Edge> path = Dijkstra.findShortestPath("Nowowiejska", "FAT", LocalTime.of(1, 0), graph);
        ArrayList<Edge> path = AStar.findShortestPath("Nowowiejska", "Piastowska", LocalTime.of(12, 0), graph);
        double endTime = System.nanoTime();
        printPath(path);
        System.out.println("Time: " + (endTime - startTime) / 1000000000 + "s");

    }
}
