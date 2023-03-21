package algorithms;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.time.LocalTime;
import java.util.*;

public class Dijkstra {
    public static ArrayList<Edge> findShortestPath(String startStop, String endStop, LocalTime startTime, Graph graph) {
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
                break;
            }
            for (String next : graph.getVertex(current).getNeighbours().keySet()) {
                currentTime = currentTimes.get(current);
                // sort edges by time difference
                ArrayList<Edge> edges = graph.getVertex(current).getNeighbours().get(next);
                updateTimeDifference(edges, currentTime);
                sortEdgesByTimeDifference(edges);
                // choose edge with minimal time difference
                Edge edge = edges.get(0);

                int newCost = costSoFar.get(current) + countCost(currentTime, edge.getDepartureTime())
                        + countCost(edge.getDepartureTime(), edge.getArrivalTime());
                if (!costSoFar.containsKey(next) || newCost < costSoFar.get(next)) {
                    costSoFar.put(next, newCost);
                    frontier.add(Map.entry(next, newCost));
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

    public static void updateTimeDifference(ArrayList<Edge> edges, LocalTime currentTime) {
        for (Edge edge : edges) {
            edge.setTimeDifference(countCost(currentTime, edge.getDepartureTime()));
        }
    }

    public static void sortEdgesByTimeDifference(ArrayList<Edge> edges) {
        edges.sort(Comparator.comparingInt(Edge::getTimeDifference));
    }


}
