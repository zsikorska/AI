import graph.Edge;
import graph.Graph;
import algorithms.Dijkstra;

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
            Edge edge = parseEdge(tokens);
            graph.addEdge(edge);
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

        return new Edge(id, company, line, startStop, departureTime, endStop, arrivalTime, startLatitude, startLongitude, endLatitude, endLongitude);
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

        ArrayList<Edge> path = Dijkstra.findShortestPath("Rdestowa", "Piastowska", LocalTime.of(12, 0), graph);
        printPath(path);

    }
}
