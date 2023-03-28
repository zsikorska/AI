import algorithms.AStarFaster;
import algorithms.AStarLines;
import algorithms.Dijkstra;
import algorithms.AStar;
import graph.Edge;
import graph.Graph;
import graph.Vertex;
import simulation.Result;
import simulation.Simulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;
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
        scanner.close();
        graph.sortAllEdges();
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
        if (path == null) {
            System.out.println("Destination is not reachable. No path found.");
        }
        else if (path.isEmpty()) {
            System.out.println("End stop is also start stop. No path to find.");
        }
        else {
            System.out.println("Path found: ");
            Iterator<Edge> iterator = path.iterator();
            Edge edge = iterator.next();
            String previousLine = "";
            String output = "";
            String endStop = "";
            LocalTime arrivalTime = null;

            if (iterator.hasNext()) {
                previousLine = edge.getLine();
                output = edge.getLine() + ": " + edge.getStartStop() + " (" + edge.getDepartureTime() + ")  - ";
                endStop = edge.getEndStop();
                arrivalTime = edge.getArrivalTime();
            }

            while (iterator.hasNext()) {
                edge = iterator.next();
                if (!edge.getLine().equals(previousLine)) {
                    output = output + " " + endStop + " (" + arrivalTime + ")";
                    System.out.println(output);
                    output = edge.getLine() + ": " + edge.getStartStop() + " (" + edge.getDepartureTime() + ")  - ";
                    previousLine = edge.getLine();
                }
                endStop = edge.getEndStop();
                arrivalTime = edge.getArrivalTime();
            }
            output = output + " " + endStop + " (" + arrivalTime + ")";
            System.out.println(output);
            System.out.println();
        }
    }

    public static void findPath(String startStop, String endStop, LocalTime startTime, String criterium, Graph graph) {
        if(!graph.containsVertex(startStop) || !graph.containsVertex(endStop)) {
            System.out.println("Wrong stop name");
            return;
        }

        double beginTime;
        double endTime;
        double totalTime = 0;
        System.out.println("Start stop: " + startStop);
        System.out.println("End stop: " + endStop);
        System.out.println("Time: " + startTime);
        System.out.println();

        if(criterium.equals("t")){
            beginTime = System.currentTimeMillis();
            Result result = AStarFaster.findShortestPath(startStop, endStop, startTime, graph);
            endTime = System.currentTimeMillis();
            totalTime += endTime - beginTime;
            result.printPath();
            System.out.println("Cost: " + result.getCost());
            System.out.println("Loop iterations: " + result.getLoopIterations());
            System.out.println("Computation time: " + totalTime + "ms");
            System.out.println();
        }
        else if(criterium.equals("p")){
            beginTime = System.currentTimeMillis();
            Result result = AStarLines.findShortestPath(startStop, endStop, startTime, graph);
            endTime = System.currentTimeMillis();
            totalTime += endTime - beginTime;
            result.printPath();
            System.out.println("Cost: " + result.getCost());
            System.out.println("Loop iterations: " + result.getLoopIterations());
            System.out.println("Time: " + totalTime + "ms");
            System.out.println();
        }
        else
            System.out.println("Wrong criterium");
    }

    public static void main(String[] args) throws FileNotFoundException {
        Graph graph = readGraph("connection_graph.csv");
        //graph.printGraph();

        //double startTime = System.nanoTime();
        //ArrayList<Edge> path = Dijkstra.findShortestPath("Nowowiejska", "Piastowska", LocalTime.of(12, 0), graph).getPath();
        //ArrayList<Edge> path = AStarFaster.findShortestPath("Nowowiejska", "Piastowska", LocalTime.of(12, 0), graph).getPath();
        //ArrayList<Edge> path = AStar.findShortestPath("Nowowiejska", "TYNIECKA (pętla)", LocalTime.of(1, 0), graph).getPath();
        //ArrayList<Edge> path = AStarLines.findShortestPath("Bagatela", "Bajana", LocalTime.of(12, 23), graph).getPath();
        //ArrayList<Edge> path = AStarLines.findShortestPath("Bagatela", "Piastowska", LocalTime.of(6, 0), graph).getPath();
        //ArrayList<Edge> path = AStarLines.findShortestPath("Nowowiejska", "Piastowska", LocalTime.of(12, 0), graph).getPath();
        //ArrayList<Edge> path = AStarFaster.findShortestPath("Jaszkotle - kościół", "Kiełczów - WODROL", LocalTime.of(20, 14), graph).getPath();
        //double endTime = System.nanoTime();
        //printPath(path);
        //System.out.println("Time: " + (endTime - startTime) / 1000000000 + "s");

        Simulation simulation = new Simulation(graph);
        //simulation.simulate(10,1);
        simulation.bigSimulation(10,100);

        //findPath("Nowowiejska", "Piastowska", LocalTime.of(12, 0), "p", graph);

    }
}
