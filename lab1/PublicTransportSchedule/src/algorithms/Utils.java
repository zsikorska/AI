package algorithms;

import graph.Edge;
import graph.Vertex;

import java.time.LocalTime;
import java.util.ArrayList;

public class Utils {

    public static double euclideanDistanceHeuristic(Vertex nextVertex, Vertex endVertex) {
        double x1 = nextVertex.getLatitude();
        double y1 = nextVertex.getLongitude();
        double x2 = endVertex.getLatitude();
        double y2 = endVertex.getLongitude();

        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    public static double haversineDistanceHeuristic(Vertex nextVertex, Vertex endVertex) {
        double lat1 = Math.toRadians(nextVertex.getLatitude());
        double lon1 = Math.toRadians(nextVertex.getLongitude());
        double lat2 = Math.toRadians(endVertex.getLatitude());
        double lon2 = Math.toRadians(endVertex.getLongitude());


        double R = 6371;
        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(lat1) * Math.cos(lat2) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;

        return distance / 50 * 60;
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

    public static int ifChangeOfLine(Edge edge, String currentLine, Edge currentEdge) {
        if (edge.getLine().equals(currentLine) && (edge.getId() == currentEdge.getId() + 1))
            return 0;
        else
            return 100000;
    }

}
