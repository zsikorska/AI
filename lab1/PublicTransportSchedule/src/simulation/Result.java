package simulation;

import graph.Edge;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Iterator;

public class Result {

    private final String startStop;
    private final String endStop;
    private final LocalTime time;
    private final ArrayList<Edge> path;
    private final double cost;
    private final int loopIterations;

    public Result(String startStop, String endStop, LocalTime time, ArrayList<Edge> path, double cost, int loopIterations) {
        this.startStop = startStop;
        this.endStop = endStop;
        this.time = time;
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

    public ArrayList<Edge> getPath() {
        return path;
    }

    public double getCost() {
        return cost;
    }

    public int getLoopIterations() {
        return loopIterations;
    }

    public void printPath() {
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

}
