import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
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

        System.out.println("Part 1: " + new App().calculateTotalRiskLevelLowPoints(input));
        System.out.println("Part 2: " + new App().calculateBasinTotal(input));
    }

    public record Point(int x, int y) {}

    public int calculateTotalRiskLevelLowPoints(String input) {
        String[] lines = input.split(System.lineSeparator());
        
        int xmax = lines[0].length();
        int ymax = lines.length;

        HashMap<Point, Integer> heights = new HashMap<Point, Integer>();

        for (int i = 0; i < lines.length; i++) {
            String[] splitLine = lines[i].trim().split("");
            for (int j = 0; j < xmax; j++) {
                // heights[i*10+j] = new Height(j, i, Integer.parseInt(splitLine[j]));
                heights.put(new Point(j, i), Integer.parseInt(splitLine[j]));
            }
        }

        int totalRiskScore = 0;
        for (Point point: heights.keySet()){
            boolean lowest = true;
            int height = heights.get(point);
            // up
            if (point.y != 0) {
                int above = heights.get(new Point(point.x, point.y - 1));
                if (above <= height) {
                    lowest = false;
                    continue;
                }
            }

            // right
            if (point.x != xmax - 1) {
                int right = heights.get(new Point(point.x + 1, point.y));
                if (right <= height) {
                    lowest = false;
                    continue;
                }
            }

            // down
            if (point.y != ymax - 1) {
                int down = heights.get(new Point(point.x, point.y + 1));
                if (down <= height) {
                    lowest = false;
                    continue;
                }
            }

            // left
            if (point.x != 0) {
                int left = heights.get(new Point(point.x - 1, point.y));
                if (left <= height) {
                    lowest = false;
                    continue;
                }
            }

            if (lowest) {
                totalRiskScore += height + 1;
            }

        }

        return totalRiskScore;

    }

    public record Height(int measurement, boolean seen) {}

    public int sumBasinSize(HashMap<Point, Height> heights, Point point) {
        // if this point is in eligible (e.g. out of bounds, at a 9, already counted) return 0
        // otherwise return 1 plus every direction

        // Get the height at that point
        Height height = heights.get(point);
        if (height == null) {
            return 0;
        }

        if (height.measurement == 9) {
            return 0;
        }

        if (height.seen == true) {
            return 0;
        }

        heights.put(point, new Height(height.measurement, true));

        // return 1 + up + right + down + left
        return 1 + 
            sumBasinSize(heights, new Point(point.x, point.y - 1)) + 
            sumBasinSize(heights, new Point(point.x + 1, point.y)) +
            sumBasinSize(heights, new Point(point.x, point.y + 1)) +
            sumBasinSize(heights, new Point(point.x - 1, point.y));
    }

    public int calculateBasinTotal(String input) {
        String[] lines = input.split(System.lineSeparator());
        
        int xmax = lines[0].length();
        int ymax = lines.length;

        HashMap<Point, Height> heights = new HashMap<Point, Height>();

        for (int i = 0; i < lines.length; i++) {
            String[] splitLine = lines[i].trim().split("");
            for (int j = 0; j < xmax; j++) {
                heights.put(new Point(j, i), new Height(Integer.parseInt(splitLine[j]), false));
            }
        }

        ArrayList<Integer> basinSizes = new ArrayList<Integer>();

        // Find the lowest points first
        for (Point point: heights.keySet()){
            Height height = heights.get(point);
            // up
            if (point.y != 0) {
                Height above = heights.get(new Point(point.x, point.y - 1));
                if (above.measurement <= height.measurement) {
                    continue;
                }
            }

            // right
            if (point.x != xmax - 1) {
                Height right = heights.get(new Point(point.x + 1, point.y));
                if (right.measurement <= height.measurement) {
                    continue;
                }
            }

            // down
            if (point.y != ymax - 1) {
                Height down = heights.get(new Point(point.x, point.y + 1));
                if (down.measurement <= height.measurement) {
                    continue;
                }
            }

            // left
            if (point.x != 0) {
                Height left = heights.get(new Point(point.x - 1, point.y));
                if (left.measurement <= height.measurement) {
                    continue;
                }
            }

            basinSizes.add(this.sumBasinSize(heights, point));

        }

        Collections.sort(basinSizes);

        return basinSizes.get(basinSizes.size() - 1) * basinSizes.get(basinSizes.size() - 2) * basinSizes.get(basinSizes.size() - 3);
    }
}