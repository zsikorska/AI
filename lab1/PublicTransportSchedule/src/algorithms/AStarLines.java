package algorithms;

import graph.Edge;
import graph.Graph;
import graph.Vertex;

import java.time.LocalTime;
import java.util.*;

import static algorithms.AStar.sortEdgesByTimeDifference;
import static algorithms.AStar.updateTimeDifference;

public class AStarLines {
    public static ArrayList<Edge> findShortestPath(String startStop, String endStop, LocalTime startTime, Graph graph) {
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

        while (!frontier.isEmpty()) {
            Edge currentEdge = frontier.poll().getKey();

            if (currentEdge.getEndStop().equals(endStop)) {
                ArrayList<Edge> path = new ArrayList<>();
                while (!currentEdge.equals(startEdge)) {
                    path.add(currentEdge);
                    currentEdge = cameFrom.get(currentEdge);
                }
                Collections.reverse(path);
                return path;
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
                                Heuristic.euclideanDistanceHeuristic(graph.getVertex(next), endVertex) +
                                AStar.countCost(currentTime, edge.getDepartureTime())));
                        cameFrom.put(edge, currentEdge);
                        currentTimes.put(edge, edge.getArrivalTime());
                        currentLines.put(edge, edge.getLine());
                    }
                }
            }
        }
        return null;
    }

    public static void updateChangeOfLine(Edge edge, String currentLine, Edge currentEdge) {
        if (edge.getLine().equals(currentLine) && (edge.getId() == currentEdge.getId() + 1))
            edge.setChangeOfLine(0);
        else
            edge.setChangeOfLine(100000);
    }




}
