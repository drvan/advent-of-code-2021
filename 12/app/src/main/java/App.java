import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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

        System.out.println("Part 1: " + new App().countCaveSystemPaths(input));
        System.out.println("Part 2: " + new App().countCaveSystemPathsVisitOneSmallCaveTwice(input));
    }

    public static int traverse(HashMap<String, ArrayList<String>> adjacencyMap, String start, String end, ArrayList<String> pathsTravelled) {
        ArrayList<String> newPathsTravelled = new ArrayList<String>(pathsTravelled);
        
        newPathsTravelled.add(start);
        int pathSum = 0;
        ArrayList<String> possiblePaths = adjacencyMap.get(start);

        ArrayList<String> possiblePathsCopy = new ArrayList<String>(possiblePaths);

        ArrayList<String> ineligiblePaths = new ArrayList<String>();
        for (String path: possiblePathsCopy) {
            if (path.equals(path.toLowerCase())) {
                // this is a small cave
                if (newPathsTravelled.contains(path)) {
                    // we've travelled to this path before, we can't travel it again
                    ineligiblePaths.add(path);
                }
            }
        }

        possiblePathsCopy.removeAll(ineligiblePaths);

        if (possiblePathsCopy.contains(end)){
            pathSum++;
            possiblePathsCopy.remove(end);
        }

        for (String path: possiblePathsCopy) {
            pathSum += traverse(adjacencyMap, path, end, newPathsTravelled);
        }

        return pathSum;
    }

    public int countCaveSystemPaths(String input) {
        String[] lines = input.split(System.lineSeparator());

        HashMap<String, ArrayList<String>> adjacencyMap = new HashMap<String, ArrayList<String>>();

        for (String line: lines) {
            String start = line.split("-")[0];
            String end = line.split("-")[1];

            // Create a new list for start->end or add to existing
            if (adjacencyMap.containsKey(start)) {
                ArrayList<String> arrayList = adjacencyMap.get(start);
                arrayList.add(end);
                adjacencyMap.put(start, arrayList);
            } else {
                ArrayList<String> newList = new ArrayList<String>();
                newList.add(end);
                adjacencyMap.put(start, newList);
            }

            // Repeat for the other direction (end->start)
            if (adjacencyMap.containsKey(end)) {
                ArrayList<String> arrayList = adjacencyMap.get(end);
                arrayList.add(start);
                adjacencyMap.put(end, arrayList);
            } else {
                ArrayList<String> newList = new ArrayList<String>();
                newList.add(start);
                adjacencyMap.put(end, newList);
            }

        }

        return traverse(adjacencyMap, "start", "end", new ArrayList<String>());

    }

    public static int traverseVisitOneSmallCaveTwice(HashMap<String, ArrayList<String>> adjacencyMap, String start, String end, ArrayList<String> pathsTravelled) {
        ArrayList<String> newPathsTravelled = new ArrayList<String>(pathsTravelled);
        
        newPathsTravelled.add(start);
        int pathSum = 0;
        ArrayList<String> possiblePaths = adjacencyMap.get(start);

        ArrayList<String> possiblePathsCopy = new ArrayList<String>(possiblePaths);

        ArrayList<String> ineligiblePaths = new ArrayList<String>();
        for (String path: possiblePathsCopy) {
            if (path.equals(path.toLowerCase())) {
                // this is a small cave
                if (newPathsTravelled.contains(path)) {
                    // we've travelled to this path before, we can't travel it again
                    // ... unless we haven't traveled to any other small cave twice
                    if (visitedSmallCaveTwice(newPathsTravelled)) {
                        ineligiblePaths.add(path);
                    }
                }
            }
        }

        possiblePathsCopy.removeAll(ineligiblePaths);

        if (possiblePathsCopy.contains(end)){
            pathSum++;
            possiblePathsCopy.remove(end);
        }

        for (String path: possiblePathsCopy) {
            pathSum += traverseVisitOneSmallCaveTwice(adjacencyMap, path, end, newPathsTravelled);
        }

        return pathSum;
    }

    private static boolean visitedSmallCaveTwice(ArrayList<String> newPathsTravelled) {
        ArrayList<String> arrayList = new ArrayList<String>();

        for (String path: newPathsTravelled) {
            if (path.equals(path.toLowerCase())) {
                arrayList.add(path);
            }
        }

        // arrayList now contains all small letters
        HashSet<String> hashSet = new HashSet<String>(arrayList);

        // if the set size (removed duplicates) does not equal the arraylist (contains dupliates) then we've
        // visited a small cave twice
        return hashSet.size() != arrayList.size();
    }

    public int countCaveSystemPathsVisitOneSmallCaveTwice(String input) {
        String[] lines = input.split(System.lineSeparator());

        HashMap<String, ArrayList<String>> adjacencyMap = new HashMap<String, ArrayList<String>>();

        for (String line: lines) {
            String start = line.split("-")[0];
            String end = line.split("-")[1];

            // Create a new list for start->end or add to existing
            if (!end.equals("start")) {
                if (adjacencyMap.containsKey(start)) {
                    ArrayList<String> arrayList = adjacencyMap.get(start);
                    arrayList.add(end);
                    adjacencyMap.put(start, arrayList);
                } else {
                    ArrayList<String> newList = new ArrayList<String>();
                    newList.add(end);
                    adjacencyMap.put(start, newList);
                }    
            }

            // Repeat for the other direction (end->start) unless start == start
            if (!start.equals("start")) {
                if (adjacencyMap.containsKey(end)) {
                    ArrayList<String> arrayList = adjacencyMap.get(end);
                    arrayList.add(start);
                    adjacencyMap.put(end, arrayList);
                } else {
                    ArrayList<String> newList = new ArrayList<String>();
                    newList.add(start);
                    adjacencyMap.put(end, newList);
                }
            }
        }

        return traverseVisitOneSmallCaveTwice(adjacencyMap, "start", "end", new ArrayList<String>());

    }

}