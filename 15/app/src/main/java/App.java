import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.PriorityQueue;

public class App {
    public static void main(String[] args) {
        String input = "";
        try {
            URL url = Thread.currentThread().getContextClassLoader().getResource("input");
            Path path = Paths.get(url.getPath());
            input = Files.readString(path);
        } catch (IOException e) {
            System.out.println("input file could not be read: " + e.getMessage());
            System.exit(1);
        }

        System.out.println("Part 1: " + new App().lowestTotalRisk(input));
        System.out.println("Part 2: " + new App().lowestTotalRiskFullMap(input));
    }

    public record Point(int x, int y) {}
    public record Edge(Point node, int cost) {}

    public int lowestTotalRisk(String input) {
        String[] lines = input.split(System.lineSeparator());
        
        int xmax = lines[0].length();

        // Populate input as a graph, stored in an adjacency map
        HashMap<Point, Integer> pointMap = new HashMap<Point, Integer>();

        for (int i = 0; i < lines.length; i++) {
            String[] splitLine = lines[i].trim().split("");
            for (int j = 0; j < xmax; j++) {
                pointMap.put(new Point(j, i), Integer.parseInt(splitLine[j]));
            }
        }

        HashMap<Point, ArrayList<Edge>> adjacencyMap = new HashMap<Point, ArrayList<Edge>>();

        for (Point point: pointMap.keySet()) {
            // Look up, right, down, left, add cost to go to each
            ArrayList<Edge> adjacencyList = new ArrayList<Edge>();

            Point up = new Point(point.x, point.y - 1);
            Point right = new Point(point.x + 1, point.y);
            Point down = new Point(point.x, point.y + 1);
            Point left = new Point(point.x - 1, point.y);

            if (pointMap.containsKey(up)) {
                adjacencyList.add(new Edge(up, pointMap.get(up)));
            }
            if (pointMap.containsKey(right)) {
                adjacencyList.add(new Edge(right, pointMap.get(right)));
            }
            if (pointMap.containsKey(down)) {
                adjacencyList.add(new Edge(down, pointMap.get(down)));
            }
            if (pointMap.containsKey(left)) {
                adjacencyList.add(new Edge(left, pointMap.get(left)));
            }

            adjacencyMap.put(point, adjacencyList);
        }

        // Stealing some data structures from my 'grokking algorithms' book
        HashMap<Point, Integer> costs = new HashMap<Point, Integer>();

        // Create a comparator for our Edge record
        PriorityQueue<Edge> costPriorityQueue = new PriorityQueue<Edge>(Comparator.comparingInt(Edge::cost));


        for (Point point: pointMap.keySet()) {
            costs.put(point, Integer.MAX_VALUE);
        }

        // Not sure if this is needed, but the cost from 0,0 to itself is 0
        costs.put(new Point(0, 0), 0);        

        // Init data structures for our starting node at 0, 0
        for (Edge edge: adjacencyMap.get(new Point(0, 0))) {
            costs.put(edge.node, edge.cost);
            costPriorityQueue.add(edge);
        }

        Point lowestCostPoint = costPriorityQueue.poll().node;
        while (lowestCostPoint != null) {
            int cost = costs.get(lowestCostPoint);
            ArrayList<Edge> neighbors = adjacencyMap.get(lowestCostPoint);

            for (Edge neighbor: neighbors) {
                int costThroughNeighbor = cost + neighbor.cost;
                if (costs.get(neighbor.node) > costThroughNeighbor) {
                    costs.put(neighbor.node, costThroughNeighbor);
                    costPriorityQueue.add(new Edge(neighbor.node, costThroughNeighbor));
                }
            }

            if (costPriorityQueue.peek() != null) {
                lowestCostPoint = costPriorityQueue.poll().node;
            } else {
                lowestCostPoint = null;
            }
        }

        Point endPoint = new Point(xmax - 1, lines.length - 1);

        return costs.get(endPoint);
    }

    public int lowestTotalRiskFullMap(String input) {
        String[] lines = input.split(System.lineSeparator());
        
        int xmax = lines[0].length();
        int ymax = lines.length;

        // Populate input as a graph, stored in an adjacency map
        HashMap<Point, Integer> pointMap = new HashMap<Point, Integer>();

        for (int i = 0; i < lines.length; i++) {
            String[] splitLine = lines[i].trim().split("");
            for (int j = 0; j < xmax; j++) {
                pointMap.put(new Point(j, i), Integer.parseInt(splitLine[j]));
            }
        }


        // Expand map to 5x5
        HashMap<Point, Integer> addPointMap = new HashMap<Point, Integer>();

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i == 0 && j == 0) {
                    continue;
                }
                for (Point point: pointMap.keySet()) {
                    int pointValue = pointMap.get(point) + i + j;
                    if (pointValue > 9) {
                        // System.out.println("pointValue at " + point + " to be placed at " + new Point(point.x + xmax*j, point.y + ymax*i) + " is " + pointMap.get(point) + " + " + i + " + " + j + " = " + pointValue + " so turning it into " + (1 + (pointValue % 9)));
                        pointValue = 1 + (pointValue % 10);
                    }

                    addPointMap.put(new Point(point.x + xmax*j, point.y + ymax*i), pointValue);
                }
            }
        }

        pointMap.putAll(addPointMap);

        // System.out.println(pointMap.get(new Point(22, 28)));

        // for (int i = 0; i < 50; i++) {
        //     for (int j = 0; j < 50; j++) {
        //         System.out.print(pointMap.get(new Point(j, i)));
        //     }
        //     System.out.println();
        // }

        HashMap<Point, ArrayList<Edge>> adjacencyMap = new HashMap<Point, ArrayList<Edge>>();

        for (Point point: pointMap.keySet()) {
            // Look up, right, down, left, add cost to go to each
            ArrayList<Edge> adjacencyList = new ArrayList<Edge>();

            Point up = new Point(point.x, point.y - 1);
            Point right = new Point(point.x + 1, point.y);
            Point down = new Point(point.x, point.y + 1);
            Point left = new Point(point.x - 1, point.y);

            if (pointMap.containsKey(up)) {
                adjacencyList.add(new Edge(up, pointMap.get(up)));
            }
            if (pointMap.containsKey(right)) {
                adjacencyList.add(new Edge(right, pointMap.get(right)));
            }
            if (pointMap.containsKey(down)) {
                adjacencyList.add(new Edge(down, pointMap.get(down)));
            }
            if (pointMap.containsKey(left)) {
                adjacencyList.add(new Edge(left, pointMap.get(left)));
            }

            adjacencyMap.put(point, adjacencyList);
        }

        // Stealing some data structures from my 'grokking algorithms' book
        HashMap<Point, Integer> costs = new HashMap<Point, Integer>();

        // Create a comparator for our Edge record
        PriorityQueue<Edge> costPriorityQueue = new PriorityQueue<Edge>(Comparator.comparingInt(Edge::cost));

        for (Point point: pointMap.keySet()) {
            costs.put(point, Integer.MAX_VALUE);
        }

        // Not sure if this is needed, but the cost from 0,0 to itself is 0
        costs.put(new Point(0, 0), 0);        

        // Init data structures for our starting node at 0, 0
        for (Edge edge: adjacencyMap.get(new Point(0, 0))) {
            costs.put(edge.node, edge.cost);
            costPriorityQueue.add(edge);
        }

        Point lowestCostPoint = costPriorityQueue.poll().node;
        while (lowestCostPoint != null) {
            int cost = costs.get(lowestCostPoint);
            ArrayList<Edge> neighbors = adjacencyMap.get(lowestCostPoint);

            for (Edge neighbor: neighbors) {
                int costThroughNeighbor = cost + neighbor.cost;
                if (costs.get(neighbor.node) > costThroughNeighbor) {
                    costs.put(neighbor.node, costThroughNeighbor);
                    costPriorityQueue.add(new Edge(neighbor.node, costThroughNeighbor));
                }
            }

            if (costPriorityQueue.peek() != null) {
                lowestCostPoint = costPriorityQueue.poll().node;
            } else {
                lowestCostPoint = null;
            }
        }

        Point endPoint = new Point(xmax*5 - 1, ymax*5 - 1);

        return costs.get(endPoint);
    }

}