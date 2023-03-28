package simulation;

import graph.Edge;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;

public class ResultTabuSearch {

    private String startStop;
    private String endStop;
    private LocalTime time;

    private ArrayList<String> stops;
    private ArrayList<ArrayList<Edge>> path;
    private double cost;
    private int loopIterations;

    public ResultTabuSearch(String startStop, String endStop, LocalTime time, ArrayList<String> stops, ArrayList<ArrayList<Edge>> path, double cost, int loopIterations) {
        this.startStop = startStop;
        this.endStop = endStop;
        this.time = time;
        this.stops = stops;
        this.path = path;
        this.cost = cost;
        this.loopIterations = loopIterations;
    }

    public String getStartStop() {
        return startStop;
    }

    public String getEndStop() {
        return endStop;
    }

    public LocalTime getTime() {
        return time;
    }

    public ArrayList<ArrayList<Edge>> getPath() {
        return path;
    }

    public double getCost() {
        return cost;
    }

    public int getLoopIterations() {
        return loopIterations;
    }

    public void setStartStop(String startStop) {
        this.startStop = startStop;
    }

    public void setEndStop(String endStop) {
        this.endStop = endStop;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public void setPath(ArrayList<ArrayList<Edge>> path) {
        this.path = path;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public void setLoopIterations(int loopIterations) {
        this.loopIterations = loopIterations;
    }

    public void printPath() {

        for(ArrayList<Edge> path : path) {
            if (path == null) {
                System.out.println("Destination is not reachable. No path found.");
            } else if (path.isEmpty()) {
                System.out.println("End stop is also start stop. No path to find.");
            } else {
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
    }


    public ArrayList<String> getStops() {
        return stops;
    }

    public void setStops(ArrayList<String> stops) {
        this.stops = stops;
    }
}
