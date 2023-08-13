
/*
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TSP {

    private static int[][] graph;
    private static int numCities;
    private static int[] path;
    private static boolean[] visited;
    private static int[] bestPath;
    private int bestPathLength;
    private int upperBound;


    public TSP(int[][] graph) {
        this.graph = graph;
        this.numCities = graph.length;
        this.path = new int[numCities + 1];
        this.visited = new boolean[numCities];
        this.bestPath = new int[numCities + 1];
        this.bestPathLength = Integer.MAX_VALUE;
    }

    public void branchAndBound() {
        path[0] = 0; // starting point
        visited[0] = true;
        path[1] = 0;
        upperBound = Integer.MAX_VALUE;
        branchAndBound(2);
    }

    private void branchAndBound(int pathLength) {
        if (pathLength == numCities) {
            int pathLengthCandidate = calculatePathLength();
            if (pathLengthCandidate < bestPathLength) {
                bestPathLength = pathLengthCandidate;
                for (int i = 0; i < numCities + 1; i++) {
                    bestPath[i] = path[i];
                }
            }
            return;
        }

        for (int i = 0; i < numCities; i++) {
            if (!visited[i]) {
                upperBound = Math.min(upperBound, bestPathLength);
                path[pathLength] = i;
                visited[i] = true;
                if (pathLength + calculateLowerBound() < upperBound) {
                    branchAndBound(pathLength + 1);
                }
                visited[i] = false;
            }
        }
    }

    private int calculatePathLength() {
        int length = 0;
        for (int i = 0; i < numCities; i++) {
            length += graph[path[i]][path[i + 1]];
        }
        return length;
    }
    // continuando el código anterior

    private int calculateLowerBound() {
        int lowerBound = 0;
        List<Integer> notVisited = new ArrayList<>();
        for (int i = 0; i < numCities; i++) {
            if (!visited[i]) {
                notVisited.add(i);
            }
        }

        for (int i = 0; i < notVisited.size(); i++) {
            lowerBound += minimum(notVisited.get(i));
        }

        return lowerBound;
    }

    private int minimum(int city) {
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < numCities; i++) {
            if (!visited[i]) {
                min = Math.min(min, graph[city][i]);
            }
        }
        return min;
    }

    public int[] getBestPath() {
        return bestPath;
    }

    public int getBestPathLength() {
        return bestPathLength;
    }


    public static void main(String[] args) {
        int[][] graph = new int[][] {
                {0, 3, 1, 5, 8},
                {3, 0, 6, 7, 9},
                {1, 6, 0, 4, 2},
                {5, 7, 4, 0, 3},
                {8, 9, 2, 3, 0}
        };



        TSP tsp = new TSP(graph);
        tsp.branchAndBound();

        for (int i = 0; i < numCities; i++) {
            for (int j = 0; j < numCities; j++) {
                System.out.print(graph[i][j] + " ");
            }
            System.out.println();
        }

        System.out.println("Best path: " + Arrays.toString(tsp.getBestPath()));
        System.out.println("Best path length: " + tsp.getBestPathLength());
    }
}


 */
import java.util.*;

public class TSP {
    private int[][] graph;
    private int[] bestPath;
    private boolean[] visited;
    private int bestPathLength;
    private int numberOfCities;
    private int startingCity;

    public TSP(int[][] graph, int startingCity) {
        this.graph = graph;
        this.startingCity = startingCity;
        this.numberOfCities = graph.length;
        this.bestPath = new int[numberOfCities + 1];
        this.visited = new boolean[numberOfCities];
        this.bestPathLength = Integer.MAX_VALUE;
        this.bestPath[0] = startingCity;
    }

    public void solve() {
        PriorityQueue<Node> heap = new PriorityQueue<>();
        heap.offer(new Node(startingCity, 0));
        int[] dist = new int[numberOfCities];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[startingCity] = 0;
        int visitedCities = 0; // Aqui se agrega la variable
        while (!heap.isEmpty()) {
            Node current = heap.poll();
            int currentCity = current.city;
            int currentDist = current.dist;

            if (visited[currentCity]) {
                continue;
            }

            visited[currentCity] = true;
            visitedCities++; // y se aumenta en cada visita

            for (int i = 0; i < numberOfCities; i++) {
                if (!visited[i]) {
                    int newDist = currentDist + graph[currentCity][i];
                    if (newDist < dist[i]) {
                        dist[i] = newDist;
                        heap.offer(new Node(i, dist[i]));
                    }
                }
            }
        }
        findPath(startingCity, 0,visitedCities); // se envia la variable como parametro
    }

    private void findPath(int currentCity, int currentPathLength,int visitedCities) {
        if (currentPathLength > bestPathLength) {
            return;
        }
        bestPath[visitedCities] = currentCity;
        if (visitedCities == numberOfCities-1 && currentCity == startingCity) {
            bestPathLength = currentPathLength + graph[currentCity][startingCity];
            for (int i = 0; i < numberOfCities; i++) {
                bestPath[i] = bestPath[i + 1];
            }
        } else {
            for (int i = 0; i < numberOfCities; i++) {
                if (!visited[i]) {
                    findPath(i, currentPathLength + graph[currentCity][i],visitedCities);
                    visited[i] = false;
                }
            }
        }
    }


    public int[] getBestPath() {
        return bestPath;
    }

    public int getBestPathLength() {
        return bestPathLength;
    }

    private class Node implements Comparable<Node> {
        int city;
        int dist;

        Node(int city, int dist) {
            this.city = city;
            this.dist = dist;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.dist, other.dist);
        }
    }

    public static void main(String[] args) {
        int[][] graph = new int[][]{
                {0, 3, 1, 5, 8},
                {3, 0, 6, 7, 9},
                {1, 6, 0, 4, 2},
                {5, 7, 4, 0, 3},
                {8, 9, 2, 3, 0}
        };
        TSP tsp = new TSP(graph, 0);
        tsp.solve();
        System.out.println("Best path length: " + tsp.getBestPathLength());
        System.out.println("Best path: " + Arrays.toString(tsp.getBestPath()));
    }
}