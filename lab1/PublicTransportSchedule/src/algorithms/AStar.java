package algorithms;

import graph.Edge;
import graph.Graph;
import graph.Vertex;
import simulation.Result;

import java.time.LocalTime;
import java.util.*;

public class AStar {
    public static Result findShortestPath(String startStop, String endStop, LocalTime startTime, Graph graph) {
        PriorityQueue<Map.Entry<String, Double>> frontier = new PriorityQueue<>(Map.Entry.comparingByValue());
        frontier.add(Map.entry(startStop, 0.0));
        Map<String, Edge> cameFrom = new HashMap<>();
        Map<String, Integer> costSoFar = new HashMap<>();
        Map<String, LocalTime> currentTimes = new HashMap<>();
        cameFrom.put(startStop, null);
        costSoFar.put(startStop, 0);
        currentTimes.put(startStop, startTime);
        LocalTime currentTime;
        Vertex endVertex = graph.getVertex(endStop);
        int counter = 0;

        while (!frontier.isEmpty()) {
            counter++;
            String current = frontier.poll().getKey();

            if (current.equals(endStop)) {
                ArrayList<Edge> path = new ArrayList<>();
                double cost = costSoFar.get(current);
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
                Edge edge = Utils.getEdgeWithSmallestTimeDifference(edges, currentTime);

                int newCost = costSoFar.get(current) + Utils.countTimeDifference(currentTime, edge.getDepartureTime())
                        + Utils.countTimeDifference(edge.getDepartureTime(), edge.getArrivalTime());
                if (!costSoFar.containsKey(next) || newCost < costSoFar.get(next)) {
                    costSoFar.put(next, newCost);
                    frontier.add(Map.entry(next, newCost + Utils.haversineDistanceHeuristic(graph.getVertex(next), endVertex)));
                    cameFrom.put(next, edge);
                    currentTimes.put(next, edge.getArrivalTime());
                }
            }
        }

        return new Result(startStop, endStop, startTime, null, -1, counter);
    }
}
