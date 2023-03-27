package algorithms;

import graph.Edge;
import graph.Graph;
import graph.Vertex;
import simulation.Result;

import java.time.LocalTime;
import java.util.*;


public class AStarLines {
    public static Result findShortestPath(String startStop, String endStop, LocalTime startTime, Graph graph) {
        PriorityQueue<Map.Entry<Edge, Double>> frontier = new PriorityQueue<>(Map.Entry.comparingByValue());
        Map<Edge, Edge> cameFrom = new HashMap<>();
        Map<Edge, Integer> costSoFar = new HashMap<>();
        Map<Edge, LocalTime> currentTimes = new HashMap<>();
        Map<Edge, String> currentLines = new HashMap<>();
        Edge startEdge = new Edge(-1, "", "", "", startTime, startStop, startTime);
        frontier.add(Map.entry(startEdge, 0.0));
        cameFrom.put(startEdge, null);
        costSoFar.put(startEdge, 0);
        currentTimes.put(startEdge, startTime);
        currentLines.put(startEdge, "");
        Vertex endVertex = graph.getVertex(endStop);
        int counter = 0;

        while (!frontier.isEmpty()) {
            counter++;
            Edge currentEdge = frontier.poll().getKey();

            if (currentEdge.getEndStop().equals(endStop)) {
                ArrayList<Edge> path = new ArrayList<>();
                double cost = costSoFar.get(currentEdge);
                while (!currentEdge.equals(startEdge)) {
                    path.add(currentEdge);
                    currentEdge = cameFrom.get(currentEdge);
                }
                Collections.reverse(path);
                return new Result(startStop, endStop, startTime, path, cost, counter);
            }

            LocalTime currentTime = currentTimes.get(currentEdge);
            String currentLine = currentLines.get(currentEdge);
            for (ArrayList<Edge> edges : graph.getVertex(currentEdge.getEndStop()).getNeighbours().values()) {
                for (Edge edge : edges) {
                    updateChangeOfLine(edge, currentLine, currentEdge);
                    String next = edge.getEndStop();
                    int newCost = costSoFar.get(currentEdge) + edge.getChangeOfLine();
                    if (!costSoFar.containsKey(edge) || newCost < costSoFar.get(edge)) {
                        costSoFar.put(edge, newCost);
                        frontier.add(Map.entry(edge, newCost +
                                Heuristic.haversineDistanceHeuristic(graph.getVertex(next), endVertex) +
                                AStar.countTimeDifference(currentTime, edge.getDepartureTime())));
                        cameFrom.put(edge, currentEdge);
                        currentTimes.put(edge, edge.getArrivalTime());
                        currentLines.put(edge, edge.getLine());
                    }
                }
            }
        }
        return new Result(startStop, endStop, startTime, null, -1, counter);
    }

    public static void updateChangeOfLine(Edge edge, String currentLine, Edge currentEdge) {
        if (edge.getLine().equals(currentLine) && (edge.getId() == currentEdge.getId() + 1))
            edge.setChangeOfLine(0);
        else
            edge.setChangeOfLine(100000);
    }




}
