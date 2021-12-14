import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

        System.out.println("Part 1: " + new App().countVisibleDotsAfterFirstFold(input));
        System.out.println("Part 2: " + new App().generateThermalImagingCameraCode(input));
    }

    public record Point(int x, int y) {}

    public int countVisibleDotsAfterFirstFold(String input) {
        // Split input into instructions and coordinates
        String coordinates = input.split("\n\n")[0];
        String instructions = input.split("\n\n")[1];

        HashSet<Point> points = new HashSet<Point>(); 

        // Populate coordinates into a set of points
        for (String coordinate: coordinates.split("\n")) {
            points.add(new Point(Integer.parseInt(coordinate.trim().split(",")[0]), Integer.parseInt(coordinate.trim().split(",")[1])));
        }

        // Get the first instruction and apply it to the set
        String instruction = instructions.split("fold along ")[1].trim();
        points = fold(points, instruction.split("=")[0], Integer.parseInt(instruction.split("=")[1]));

        return points.size();
    }

    public HashSet<Point> fold(HashSet<Point> points, String direction, int value) {

        HashSet<Point> removeSet = new HashSet<Point>();
        HashSet<Point> addSet = new HashSet<Point>();

        if (direction.equals("x")) {
            for (Point point: points) {
                if (point.x > value) {
                    int delta = point.x - value;
                    addSet.add(new Point(value - delta, point.y));
                    removeSet.add(point);
                }
            }
        } else if (direction.equals("y")) {
            for (Point point: points) {
                if (point.y > value) {
                    int delta = point.y - value;
                    addSet.add(new Point(point.x, value - delta));
                    removeSet.add(point);
                }
            }
        }

        points.addAll(addSet);
        points.removeAll(removeSet);

        return points;
    }

    public String generateThermalImagingCameraCode(String input) {
        // Split input into instructions and coordinates
        String coordinates = input.split("\n\n")[0];
        String instructions = input.split("\n\n")[1];

        HashSet<Point> points = new HashSet<Point>(); 

        // Populate coordinates into a set of points
        for (String coordinate: coordinates.split("\n")) {
            points.add(new Point(Integer.parseInt(coordinate.trim().split(",")[0]), Integer.parseInt(coordinate.trim().split(",")[1])));
        }

        // Apply all instructions
        for (String instruction: instructions.split("\n")) {
            String foldLine = instruction.split("fold along ")[1].trim();
            points = fold(points, foldLine.split("=")[0], Integer.parseInt(foldLine.split("=")[1]));
        }

        // Find the largest x and y
        int xmax = 0;
        int ymax = 0;

        for (Point point: points) {
            if (xmax < point.x) {
                xmax = point.x;
            }
            if (ymax < point.y) {
                ymax = point.y;
            }
        }

        String returnString = "\n";
        for (int y = 0; y <= ymax; y++) {
            for (int x = 0; x <= xmax; x++) {
                if (points.contains(new Point(x, y))) {
                    returnString += "#";
                } else {
                    returnString += ".";
                }
            }
            returnString += "\n";
        }

        return returnString;
    }

}