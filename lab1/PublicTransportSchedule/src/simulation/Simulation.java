package simulation;

import algorithms.AStar;
import algorithms.AStarLines;
import algorithms.Dijkstra;
import graph.Graph;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Random;

public class Simulation {

    private final Graph graph;
    private final Random random = new Random();

    public Simulation(Graph graph) {
        this.graph = graph;
    }

    public ArrayList<String> getRandomStops(int numberOfStops) {
        ArrayList<String> stops = new ArrayList<>();
        ArrayList<String> vertexes = graph.getVertexesNames();
        for (int i = 0; i < numberOfStops; i++) {
            stops.add(vertexes.get(random.nextInt(vertexes.size())));
        }
        return stops;
    }

    public ArrayList<LocalTime> getRandomTimes(int numberOfTrips) {
        ArrayList<LocalTime> times = new ArrayList<>();
        for (int i = 0; i < numberOfTrips; i++) {
            times.add(LocalTime.of(random.nextInt(24), random.nextInt(60)));
        }
        return times;
    }

    public void simulate(int numberOfTrips, int numberOfRepetitions) {
        ArrayList<String> startStops = getRandomStops(numberOfTrips);
        ArrayList<String> endStops = getRandomStops(numberOfTrips);
        ArrayList<LocalTime> times = getRandomTimes(numberOfTrips);

        for (int i = 0; i < numberOfTrips; i++) {
            System.out.println("##############  Trip " + (i + 1) + "  ##############");
            System.out.println("Start stop: " + startStops.get(i));
            System.out.println("End stop: " + endStops.get(i));
            System.out.println("Time: " + times.get(i));
            System.out.println();

            dijkstraSimulation(startStops.get(i), endStops.get(i), times.get(i), numberOfRepetitions);
            aStarSimulation(startStops.get(i), endStops.get(i), times.get(i), numberOfRepetitions);
            //aStarLinesSimulation(startStops.get(i), endStops.get(i), times.get(i), numberOfRepetitions);
        }
    }

    public void dijkstraSimulation(String startStop, String endStop, LocalTime time, int numberOfRepetitions){
        double startTime;
        double endTime;
        double totalTime = 0;

        System.out.println("#######  Dijkstra Time  #######");

        for (int i = 0; i < numberOfRepetitions; i++) {
            startTime = System.currentTimeMillis();
            Result result = Dijkstra.findShortestPath(startStop, endStop, time, graph);
            endTime = System.currentTimeMillis();
            totalTime += endTime - startTime;

            if(i == 0){
                result.printPath();
                System.out.println("Cost: " + result.getCost());
                System.out.println("Loop iterations: " + result.getLoopIterations());
            }
        }
        double averageTime = totalTime / numberOfRepetitions;
        System.out.println("Average time: " + averageTime + "ms");
        System.out.println();

    }

    public void aStarSimulation(String startStop, String endStop, LocalTime time, int numberOfRepetitions){
        double startTime;
        double endTime;
        double totalTime = 0;

        System.out.println("#######  A* Time  #######");

        for (int i = 0; i < numberOfRepetitions; i++) {
            startTime = System.currentTimeMillis();
            Result result = AStar.findShortestPath(startStop, endStop, time, graph);
            endTime = System.currentTimeMillis();
            totalTime += endTime - startTime;

            if(i == 0){
                result.printPath();
                System.out.println("Cost: " + result.getCost());
                System.out.println("Loop iterations: " + result.getLoopIterations());
            }
        }
        double averageTime = totalTime / numberOfRepetitions;
        System.out.println("Average time: " + averageTime + "ms");
        System.out.println();

    }

    public void aStarLinesSimulation(String startStop, String endStop, LocalTime time, int numberOfRepetitions){
        double startTime;
        double endTime;
        double totalTime = 0;

        System.out.println("#######  A* Line Changes  #######");

        for (int i = 0; i < numberOfRepetitions; i++) {
            startTime = System.currentTimeMillis();
            Result result = AStarLines.findShortestPath(startStop, endStop, time, graph);
            endTime = System.currentTimeMillis();
            totalTime += endTime - startTime;

            if(i == 0){
                result.printPath();
                System.out.println("Cost: " + result.getCost());
                System.out.println("Loop iterations: " + result.getLoopIterations());
            }
        }
        double averageTime = totalTime / numberOfRepetitions;
        System.out.println("Average time: " + averageTime + "ms");
        System.out.println();

    }


    public void bigSimulation(int numberOfTrips, int numberOfRepetitions) {
        ArrayList<String> startStops = getRandomStops(numberOfTrips);
        ArrayList<String> endStops = getRandomStops(numberOfTrips);
        ArrayList<LocalTime> times = getRandomTimes(numberOfTrips);

        dijkstraBigSimulation(numberOfTrips, numberOfRepetitions, startStops, endStops, times);
        aStarBigSimulation(numberOfTrips, numberOfRepetitions, startStops, endStops, times);
        //aStarLinesBigSimulation(numberOfTrips, numberOfRepetitions, startStops, endStops, times);
    }

    public void dijkstraBigSimulation(int numberOfTrips, int numberOfRepetitions, ArrayList<String> startStops,
                                      ArrayList<String> endStops, ArrayList<LocalTime> times) {
        double startTime;
        double endTime;
        double totalTime = 0;
        double averageTime = 0;
        double cost = 0;
        double loopIterations = 0;

        System.out.println("#######  Dijkstra Time  #######");
        for (int i = 0; i < numberOfTrips; i++) {
            for (int j = 0; j < numberOfRepetitions; j++) {
                startTime = System.currentTimeMillis();
                Result result = Dijkstra.findShortestPath(startStops.get(i), endStops.get(i), times.get(i), graph);
                endTime = System.currentTimeMillis();
                totalTime += endTime - startTime;

                if(j == 0){
                    cost += result.getCost();
                    loopIterations += result.getLoopIterations();
                }
            }
            averageTime += totalTime / numberOfRepetitions;
        }
        System.out.println("Average cost: " + cost / numberOfTrips);
        System.out.println("Average loop iterations: " + loopIterations / numberOfTrips);
        averageTime /= numberOfTrips;
        System.out.println("Average time: " + averageTime + "ms");
        System.out.println();

    }

    public void aStarBigSimulation(int numberOfTrips, int numberOfRepetitions, ArrayList<String> startStops,
                                   ArrayList<String> endStops, ArrayList<LocalTime> times) {
        double startTime;
        double endTime;
        double totalTime = 0;
        double averageTime = 0;
        double cost = 0;
        double loopIterations = 0;

        System.out.println("#######  A* Time  #######");
        for (int i = 0; i < numberOfTrips; i++) {
            for (int j = 0; j < numberOfRepetitions; j++) {
                startTime = System.currentTimeMillis();
                Result result = AStar.findShortestPath(startStops.get(i), endStops.get(i), times.get(i), graph);
                endTime = System.currentTimeMillis();
                totalTime += endTime - startTime;

                if(j == 0){
                    cost += result.getCost();
                    loopIterations += result.getLoopIterations();
                }
            }
            averageTime += totalTime / numberOfRepetitions;
        }
        System.out.println("Average cost: " + cost / numberOfTrips);
        System.out.println("Average loop iterations: " + loopIterations / numberOfTrips);
        averageTime /= numberOfTrips;
        System.out.println("Average time: " + averageTime + "ms");
        System.out.println();

    }

    public void aStarLinesBigSimulation(int numberOfTrips, int numberOfRepetitions, ArrayList<String> startStops,
                                        ArrayList<String> endStops, ArrayList<LocalTime> times) {
        double startTime;
        double endTime;
        double totalTime = 0;
        double averageTime = 0;
        double cost = 0;
        double loopIterations = 0;

        System.out.println("#######  A* Line Changes  #######");
        for (int i = 0; i < numberOfTrips; i++) {
            for (int j = 0; j < numberOfRepetitions; j++) {
                startTime = System.currentTimeMillis();
                Result result = AStarLines.findShortestPath(startStops.get(i), endStops.get(i), times.get(i), graph);
                endTime = System.currentTimeMillis();
                totalTime += endTime - startTime;

                if (j == 0) {
                    cost += result.getCost();
                    loopIterations += result.getLoopIterations();
                }
            }
            averageTime += totalTime / numberOfRepetitions;
        }
        System.out.println("Average cost: " + cost / numberOfTrips);
        System.out.println("Average loop iterations: " + loopIterations / numberOfTrips);
        averageTime /= numberOfTrips;
        System.out.println("Average time: " + averageTime + "ms");
        System.out.println();
    }


}
