package algorithms;

import graph.Graph;
import simulation.Result;
import simulation.ResultTabuSearch;

import java.time.LocalTime;
import java.util.*;

public class TabuSearch {
    private static final int MAX_ITERATIONS = 20;
    private static final double ASPIRATION_FACTOR = 1.1;
    private static final double NEIGHBOUR_SAMPLING_FACTOR = 0.5;

    public static ResultTabuSearch findSolution(Graph graph, String startStop, List<String> stops, LocalTime startTime, String criterium) {

        ArrayList<String> currentSolution = new ArrayList<>(stops);
        double currentCost = findSolution(graph, startStop, currentSolution, startTime, criterium).getCost();

        ArrayList<String> bestSolution = currentSolution;
        double bestCost = currentCost;

        Queue<ArrayList<String>> tabu = new ArrayDeque<>();
        int maxTabuListSize = (stops.size() - 1) * stops.size() / 2;

        Map<ArrayList<String>, Double> aspiration = new HashMap<>();
        double aspirationThreshold = currentCost * ASPIRATION_FACTOR;
        int iteration = 0;

        while(iteration < MAX_ITERATIONS) {
            ArrayList<ArrayList<String>> neighbours = makeNeighbours(currentSolution);

            for(ArrayList<String> key : aspiration.keySet()) {
                if(!neighbours.contains(key)) {
                    neighbours.add(key);
                }
            }

            neighbours = makeSamplingNeighbours(startStop, neighbours, graph);

            for(ArrayList<String> neighbour : neighbours) {
                if(!tabu.contains(neighbour) || aspiration.containsKey(neighbour)) {
                    double neighbourCost = findSolution(graph, startStop, neighbour, startTime, criterium).getCost();
                    if(neighbourCost < currentCost) {
                        currentSolution = neighbour;
                        currentCost = neighbourCost;

                        aspirationThreshold = currentCost * ASPIRATION_FACTOR;

                        Iterator<Map.Entry<ArrayList<String>, Double>> it = aspiration.entrySet().iterator();
                        while(it.hasNext()) {
                            Map.Entry<ArrayList<String>, Double> entry = it.next();
                            if(entry.getValue() > aspirationThreshold) {
                                it.remove();
                            }
                        }
                    }

                    tabu.add(neighbour);
                    if(tabu.size() > maxTabuListSize) {
                        tabu.remove();
                    }

                    if(neighbourCost < aspirationThreshold && !aspiration.containsKey(neighbour)) {
                        aspiration.put(neighbour, neighbourCost);
                    }
                }
            }
            if(currentCost < bestCost) {
                bestSolution = currentSolution;
                bestCost = currentCost;
            }
            iteration++;
        }

        return findSolution(graph, startStop, bestSolution, startTime, criterium);
    }

    public static ArrayList<ArrayList<String>> makeNeighbours(ArrayList<String> stops) {
        ArrayList<ArrayList<String>> neighbours = new ArrayList<>();
        int numberOfNeighbours = stops.size();

        for(int i = 0; i < numberOfNeighbours; i++) {
            for(int j = i + 1; j < numberOfNeighbours; j++) {
                ArrayList<String> neighbour = new ArrayList<>(stops);
                swap(neighbour, i, j);
                neighbours.add(neighbour);
            }
        }
        return neighbours;
    }

    public static void swap(ArrayList<String> stops, int i, int j){
        String temp = stops.get(i);
        stops.set(i, stops.get(j));
        stops.set(j, temp);
    }

    public static ResultTabuSearch findSolution(Graph graph, String startStop, ArrayList<String> stops, LocalTime startTime, String criterium) {
        String currentStop = startStop;
        LocalTime currentTime = startTime;
        ResultTabuSearch result = new ResultTabuSearch(startStop, "", startTime, stops, new ArrayList<>(), 0, 0);
        Result tempResult;
        stops.add(startStop);

        for (String stop : stops) {
            if (criterium.equals("t")) {
                tempResult = AStarFaster.findShortestPath(currentStop, stop, currentTime, graph);
            } else {
                tempResult = AStarLines.findShortestPath(currentStop, stop, currentTime, graph);
            }
            result.getPath().add(tempResult.getPath());
            result.setCost(result.getCost() + tempResult.getCost());
            result.setLoopIterations(result.getLoopIterations() + tempResult.getLoopIterations());
            currentStop = stop;
            currentTime = tempResult.getPath().get(tempResult.getPath().size() - 1).getArrivalTime();
        }
        stops.remove(startStop);
        result.setEndStop(currentStop);
        return result;

    }

    public static double countPathDistance(String startStop, ArrayList<String> stops, Graph graph) {
        double distance = 0;
        String currentStop = startStop;
        stops.add(startStop);

        for (String stop : stops) {
            distance += Utils.euclideanDistanceHeuristic(graph.getVertex(currentStop), graph.getVertex(stop));
            currentStop = stop;
        }
        stops.remove(startStop);
        return distance;
    }

    public static PriorityQueue<Map.Entry<ArrayList<String>, Double>> makeSamplingQueue(String startStop, ArrayList<ArrayList<String>> neighbours,  Graph graph) {
        PriorityQueue<Map.Entry<ArrayList<String>, Double>> samplingQueue = new PriorityQueue<>(Comparator.comparingDouble(Map.Entry::getValue));
        for (ArrayList<String> neighbour : neighbours) {
            samplingQueue.add(Map.entry(neighbour, countPathDistance(startStop, neighbour, graph)));
        }
        return samplingQueue;
    }

    public static ArrayList<ArrayList<String>> makeSamplingNeighbours(String startStop, ArrayList<ArrayList<String>> neighbours, Graph graph) {
        ArrayList<ArrayList<String>> samplingNeighbours = new ArrayList<>();
        PriorityQueue<Map.Entry<ArrayList<String>, Double>> samplingQueue = makeSamplingQueue(startStop, neighbours, graph);
        double numberOfNeighbours = Math.ceil(neighbours.size() * NEIGHBOUR_SAMPLING_FACTOR);
        for (int i = 0; i < numberOfNeighbours; i++) {
            samplingNeighbours.add(samplingQueue.poll().getKey());
        }
        return samplingNeighbours;
    }
}
