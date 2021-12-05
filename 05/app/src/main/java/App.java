import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

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

        System.out.println("Part 1: " + new App().countLinesOverlap(input));
        System.out.println("Part 2: " + new App().countLinesOverlapWithDiagonals(input));
    }

    private String[] pointsFromLine(String line) {
        String firstCoordinates = line.split("->")[0].trim();
        String secondCoordinates = line.split("->")[1].trim();

        int x1 = Integer.parseInt(firstCoordinates.split(",")[0]);
        int y1 = Integer.parseInt(firstCoordinates.split(",")[1]);
        int x2 = Integer.parseInt(secondCoordinates.split(",")[0]);
        int y2 = Integer.parseInt(secondCoordinates.split(",")[1]);

        if (x1 == x2) {
            int step = (y2 > y1) ? 1 : -1;
            int numPoints = Math.abs(y2 - y1) + 1;
            String[] points = new String[numPoints];

            for (int i = 0; i < numPoints; i++) {
                points[i] = x1 + "," + (y1 + (i*step));
            }
            return points;
        } else if (y1 == y2) {
            int step = (x2 > x1) ? 1 : -1;
            int numPoints = Math.abs(x2 - x1) + 1;
            String[] points = new String[numPoints];

            for (int i = 0; i < numPoints; i++) {
                points[i] = (x1 + (i*step)) + "," + y1;
            }
            return points;
        } else {
            return new String[0];
        }
    }

    private String[] pointsFromLineWithDiagonals(String line) {
        String firstCoordinates = line.split("->")[0].trim();
        String secondCoordinates = line.split("->")[1].trim();

        int x1 = Integer.parseInt(firstCoordinates.split(",")[0]);
        int y1 = Integer.parseInt(firstCoordinates.split(",")[1]);
        int x2 = Integer.parseInt(secondCoordinates.split(",")[0]);
        int y2 = Integer.parseInt(secondCoordinates.split(",")[1]);

        if (x1 == x2) {
            int step = (y2 > y1) ? 1 : -1;
            int numPoints = Math.abs(y2 - y1) + 1;
            String[] points = new String[numPoints];

            for (int i = 0; i < numPoints; i++) {
                points[i] = x1 + "," + (y1 + (i*step));
            }
            return points;
        } else if (y1 == y2) {
            int step = (x2 > x1) ? 1 : -1;
            int numPoints = Math.abs(x2 - x1) + 1;
            String[] points = new String[numPoints];

            for (int i = 0; i < numPoints; i++) {
                points[i] = (x1 + (i*step)) + "," + y1;
            }
            return points;
        } else {
            int xstep = (x2 > x1) ? 1 : -1;
            int ystep = (y2 > y1) ? 1 : -1;
            int numPoints = Math.abs(x2 - x1) + 1;
            String[] points = new String[numPoints];

            for (int i = 0; i < numPoints; i++) {
                points[i] = (x1 + (i*xstep)) + "," + (y1 + (i*ystep));
            }
            return points;
        }
    }

    public int countLinesOverlap(String input) {
        String[] lines = input.split(System.lineSeparator());

        HashMap<String, Integer> coordinateMap = new HashMap<String, Integer>();

        for (int i = 0; i < lines.length; i++) {
            String[] points = this.pointsFromLine(lines[i]);
            for (int j = 0; j < points.length; j++) {
                if (coordinateMap.containsKey(points[j])) {
                    int value = coordinateMap.get(points[j]);
                    coordinateMap.put(points[j], (value + 1));
                } else {
                    coordinateMap.put(points[j], 1);
                }
            }
        }

        int overlapCounter = 0;
        for (int value : coordinateMap.values()) {
            if (value > 1) {
                overlapCounter++;
            }
        }

        return overlapCounter;
    }

    public int countLinesOverlapWithDiagonals(String input) {
        String[] lines = input.split(System.lineSeparator());

        HashMap<String, Integer> coordinateMap = new HashMap<String, Integer>();

        for (int i = 0; i < lines.length; i++) {
            String[] points = this.pointsFromLineWithDiagonals(lines[i]);
            for (int j = 0; j < points.length; j++) {
                if (coordinateMap.containsKey(points[j])) {
                    int value = coordinateMap.get(points[j]);
                    coordinateMap.put(points[j], (value + 1));
                } else {
                    coordinateMap.put(points[j], 1);
                }
            }
        }

        int overlapCounter = 0;
        for (int value : coordinateMap.values()) {
            if (value > 1) {
                overlapCounter++;
            }
        }

        return overlapCounter;
    }

}