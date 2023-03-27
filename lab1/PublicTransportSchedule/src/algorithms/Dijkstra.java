package algorithms;

import graph.Edge;
import graph.Graph;
import simulation.Result;

import java.time.LocalTime;
import java.util.*;

public class Dijkstra {
    public static Result findShortestPath(String startStop, String endStop, LocalTime startTime, Graph graph) {
        PriorityQueue<Map.Entry<String, Integer>> frontier = new PriorityQueue<>(Map.Entry.comparingByValue());
        frontier.add(Map.entry(startStop, 0));
        Map<String, Edge> cameFrom = new HashMap<>();
        Map<String, Integer> costSoFar = new HashMap<>();
        Map<String, LocalTime> currentTimes = new HashMap<>();
        cameFrom.put(startStop, null);
        costSoFar.put(startStop, 0);
        currentTimes.put(startStop, startTime);
        LocalTime currentTime;
        int counter = 0;

        while (!frontier.isEmpty()) {
            counter++;
            String current = frontier.poll().getKey();
            if (current.equals(endStop)) {
                ArrayList<Edge> path = new ArrayList<>();
                int cost = costSoFar.get(current);
                while (!Objects.equals(current, startStop)) {
                    path.add(cameFrom.get(current));
                    current = cameFrom.get(current).getStartStop();
                }
                Collections.reverse(path);
                return new Result(startStop, endStop, startTime, path, cost, counter);
            }
            for (String next : graph.getVertex(current).getNeighbours().keySet()) {
                currentTime = currentTimes.get(current);

                ArrayList<Edge> edges = graph.getVertex(current).getNeighbours().get(next);
                Edge edge = getEdgeWithSmallestTimeDifference(edges, currentTime);

                int newCost = costSoFar.get(current) + countTimeDifference(currentTime, edge.getDepartureTime())
                        + countTimeDifference(edge.getDepartureTime(), edge.getArrivalTime());
                if (!costSoFar.containsKey(next) || newCost < costSoFar.get(next)) {
                    costSoFar.put(next, newCost);
                    frontier.add(Map.entry(next, newCost));
                    cameFrom.put(next, edge);
                    currentTimes.put(next, edge.getArrivalTime());
                }
            }
        }

        return new Result(startStop, endStop, startTime, null, -1, counter);
    }

    private static int countTimeDifference(LocalTime startTime, LocalTime arrivalTime) {
        int difference = 0;
        if (!startTime.isAfter(arrivalTime)) {
            difference = (arrivalTime.getHour() - startTime.getHour()) * 60 + arrivalTime.getMinute() - startTime.getMinute();
        } else {
            difference = (24 - startTime.getHour() + arrivalTime.getHour()) * 60 + arrivalTime.getMinute() - startTime.getMinute();
        }
        return difference;
    }

    public static Edge getEdgeWithSmallestTimeDifference(ArrayList<Edge> edges, LocalTime currentTime) {
        Edge edge = edges.get(0);
        int cost = countTimeDifference(currentTime, edge.getDepartureTime());
        if(cost > countTimeDifference(currentTime, edges.get(edges.size() - 1).getDepartureTime())) {
            for (Edge e : edges) {
                int newCost = countTimeDifference(currentTime, e.getDepartureTime());
                if (newCost < cost) {
                    return e;
                }
            }
        }
        return edge;
    }


}
