package algorithms;

import graph.Edge;
import graph.Graph;

import java.time.LocalTime;
import java.util.*;

public class AStar {
    public static ArrayList<Edge> findShortestPath(String startStop, String endStop, LocalTime startTime, Graph graph) {
        PriorityQueue<Map.Entry<String, Double>> frontier = new PriorityQueue<>(Map.Entry.comparingByValue());
        frontier.add(Map.entry(startStop, 0.0));
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
                break;
            }
            // TODO: sort edges and make loop on neigbour nodes instead of edges (it require change of structure of graph)
            for (Edge edge : graph.getGraph().get(current)) {
                String next = edge.getEndStop();
                currentTime = currentTimes.get(current);
                int newCost = costSoFar.get(current) + countCost(currentTime, edge.getDepartureTime())
                        + countCost(edge.getDepartureTime(), edge.getArrivalTime());
                if (!costSoFar.containsKey(next) || newCost < costSoFar.get(next)) {
                    costSoFar.put(next, newCost);
                    // TODO: write geo data in vertex
                    frontier.add(Map.entry(next, newCost + heuristic(edge, 51.09375561, 16.98037615)));
                    cameFrom.put(next, edge);
                    currentTimes.put(next, edge.getArrivalTime());
                }
            }
        }

        // return path
        String current = endStop;
        ArrayList<Edge> path = new ArrayList<>();
        while (!Objects.equals(current, startStop)) {
            path.add(cameFrom.get(current));
            current = cameFrom.get(current).getStartStop();
        }
        Collections.reverse(path);
        return path;
    }

    private static int countCost(LocalTime startTime, LocalTime arrivalTime) {
        int cost = 0;
        if (!startTime.isAfter(arrivalTime)) {
            cost = (arrivalTime.getHour() - startTime.getHour()) * 60 + arrivalTime.getMinute() - startTime.getMinute();
        } else {
            cost = (24 - startTime.getHour() + arrivalTime.getHour()) * 60 + arrivalTime.getMinute() - startTime.getMinute();
        }
        return cost;
    }

    private static double heuristic(Edge edge, double endLatitude, double endLongitude) {
        // euclidean distance
        double x1 = edge.getEndLatitude();
        double y1 = edge.getEndLongitude();
        double result = Math.sqrt(Math.pow(endLatitude - x1, 2) + Math.pow(endLongitude - y1, 2));
        // convert result from degrees to km
        result = result * 111.32;
        result = result / 50;
        return result * 60;

    }
}
