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

        System.out.println("Part 1: " + new App().calculateTotalFlashes100Steps(input));
        System.out.println("Part 2: " + new App().calculateFirstSynchronizedFlash(input));
    }

    public record Point(int x, int y) {};

    public class Octopus {
        int energyLevel;
        boolean flashed = false;

        public Octopus(int energyLevel) {
            this.energyLevel = energyLevel;
        }
    }

    public static void flash(HashMap<Point, Octopus> octopuses, Point point) {
        Octopus octopus = octopuses.get(point);
        octopus.flashed = true;
        octopuses.put(point, octopus);

        Point[] points = {
            new Point(point.x - 1, point.y - 1), // top left
            new Point(point.x, point.y - 1), // top
            new Point(point.x + 1, point.y - 1), // top right
            new Point(point.x - 1, point.y), // left
            new Point(point.x + 1, point.y), // right
            new Point(point.x - 1, point.y + 1), // bottom left
            new Point(point.x, point.y + 1), // bottom
            new Point(point.x + 1, point.y + 1), // bottom right
        };

        for (int i = 0; i < points.length; i++) {
            if (octopuses.containsKey(points[i]))
            {
                octopus = octopuses.get(points[i]);
                octopus.energyLevel += 1;
                octopuses.put(points[i], octopus);
                if (octopus.energyLevel > 9 && octopus.flashed == false) {
                    flash(octopuses, points[i]);
                }
            }
        }
    }

    public int calculateTotalFlashes100Steps(String input) {
        String[] lines = input.split(System.lineSeparator());
        
        int xmax = lines[0].length();

        HashMap<Point, Octopus> octopuses = new HashMap<Point, Octopus>();

        for (int i = 0; i < lines.length; i++) {
            String[] splitLine = lines[i].trim().split("");
            for (int j = 0; j < xmax; j++) {
                octopuses.put(new Point(j, i), new Octopus(Integer.parseInt(splitLine[j])));
            }
        }

        int flashCounter = 0;
        
        for (int i = 0; i < 100; i++) {
            // Iterate through all points, increment by 1
            for (Point point: octopuses.keySet()) {
                Octopus octopus = octopuses.get(point);
                octopuses.put(point, new Octopus(octopus.energyLevel + 1));
            }

            // Iterate through all points, if point is > 9, flash(point)
            for (Point point: octopuses.keySet()) {
                Octopus octopus = octopuses.get(point);
                if (octopus.energyLevel == 10 && octopus.flashed == false) {
                    flash(octopuses, point);
                }
            }

            // Iterate through all points again, resetting any > 9 and counting it as a flash
            for (Point point: octopuses.keySet()) {
                Octopus octopus = octopuses.get(point);
                if (octopus.energyLevel > 9) {
                    flashCounter++;
                    octopuses.put(point, new Octopus(0));
                }
            }
        }

        return flashCounter;
    }

    public static void printOctopusMap(HashMap<Point, Octopus> octopuses) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(octopuses.get(new Point(j, i)).energyLevel + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public int calculateFirstSynchronizedFlash(String input) {
        String[] lines = input.split(System.lineSeparator());
        
        int xmax = lines[0].length();

        HashMap<Point, Octopus> octopuses = new HashMap<Point, Octopus>();

        for (int i = 0; i < lines.length; i++) {
            String[] splitLine = lines[i].trim().split("");
            for (int j = 0; j < xmax; j++) {
                octopuses.put(new Point(j, i), new Octopus(Integer.parseInt(splitLine[j])));
            }
        }

        int flashCounter = 0;
        int iterationCounter = 0;
        
        while (flashCounter != 100) {
            flashCounter = 0;
            iterationCounter++;
            // Iterate through all points, increment by 1
            for (Point point: octopuses.keySet()) {
                Octopus octopus = octopuses.get(point);
                octopuses.put(point, new Octopus(octopus.energyLevel + 1));
            }

            // Iterate through all points, if point is > 9, flash(point)
            for (Point point: octopuses.keySet()) {
                Octopus octopus = octopuses.get(point);
                if (octopus.energyLevel == 10 && octopus.flashed == false) {
                    flash(octopuses, point);
                }
            }

            // Iterate through all points again, resetting any > 9 and counting it as a flash
            for (Point point: octopuses.keySet()) {
                Octopus octopus = octopuses.get(point);
                if (octopus.energyLevel > 9) {
                    flashCounter++;
                    octopuses.put(point, new Octopus(0));
                }
            }
        }

        return iterationCounter;
    }

}