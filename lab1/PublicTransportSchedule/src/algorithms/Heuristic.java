package algorithms;

import graph.Vertex;

public class Heuristic {

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


}
