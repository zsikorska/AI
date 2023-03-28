package algorithms;

import graph.Graph;
import simulation.Result;
import simulation.ResultTabuSearch;

import java.time.LocalTime;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class TabuSearch {
    private static final int MAX_ITERATIONS = 2;
    private static final int MAX_TABU_LIST_SIZE = 10;

    public static ResultTabuSearch findSolution(Graph graph, String startStop, List<String> stops, LocalTime startTime, String criterium) {

        ArrayList<String> currentSolution = new ArrayList<>(stops);
        double currentCost = findSolution(graph, startStop, currentSolution, startTime, criterium).getCost();

        ArrayList<String> bestSolution = currentSolution;
        double bestCost = currentCost;

        Queue<ArrayList<String>> tabu = new ArrayDeque<>();
        int iteration = 0;

        while(iteration < MAX_ITERATIONS) {
            ArrayList<ArrayList<String>> neighbours = makeNeighbours(currentSolution);
            for(ArrayList<String> neighbour : neighbours) {
                if(!tabu.contains(neighbour)) {
                    double neighbourCost = findSolution(graph, startStop, neighbour, startTime, criterium).getCost();
                    if(neighbourCost < currentCost) {
                        currentSolution = neighbour;
                        currentCost = neighbourCost;
                    }
                }
                tabu.add(neighbour);
                if(tabu.size() > MAX_TABU_LIST_SIZE) {
                    tabu.remove();
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
}
