package algorithms;

import graph.Edge;
import graph.Graph;
import graph.Vertex;
import simulation.Result;

import java.time.LocalTime;
import java.util.*;

public class AStarFaster {
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
                Edge edge = binarySearch(edges, currentTime);

                int newCost = costSoFar.get(current) + countTimeDifference(currentTime, edge.getDepartureTime())
                        + countTimeDifference(edge.getDepartureTime(), edge.getArrivalTime());
                if (!costSoFar.containsKey(next) || newCost < costSoFar.get(next)) {
                    costSoFar.put(next, newCost);
                    frontier.add(Map.entry(next, newCost + Heuristic.haversineDistanceHeuristic(graph.getVertex(next), endVertex)));
                    cameFrom.put(next, edge);
                    currentTimes.put(next, edge.getArrivalTime());
                }
            }
        }

        return new Result(startStop, endStop, startTime, null, -1, counter);
    }

    public static int countTimeDifference(LocalTime startTime, LocalTime endTime) {
        int difference = 0;
        if (!startTime.isAfter(endTime)) {
            difference = (endTime.getHour() - startTime.getHour()) * 60 + endTime.getMinute() - startTime.getMinute();
        } else {
            difference = (24 - startTime.getHour() + endTime.getHour()) * 60 + endTime.getMinute() - startTime.getMinute();
        }
        return difference;
    }

    public static Edge binarySearch(ArrayList<Edge> edges, LocalTime currentTime) {
        int left = 0;
        int right = edges.size() - 1;

        if (edges.get(right).getDepartureTime().isBefore(currentTime))
            return edges.get(left);

        int middle = (left + right) / 2;
        while (left < right) {
            if (edges.get(middle).getDepartureTime().isBefore(currentTime))
                left = middle + 1;
            else
                right = middle;
            middle = (left + right) / 2;
        }
        return edges.get(middle);
    }

}
