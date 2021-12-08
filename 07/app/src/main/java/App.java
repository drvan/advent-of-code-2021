import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

        System.out.println("Part 1: " + new App().cheapestFuelPosition(input));
        System.out.println("Part 2: " + new App().cheapestFuelPositionCrabEngineering(input));
    }

    public static int calculateFuelUsage(int[] positions, int alignedPosition) {
        int totalFuelUsage = 0;
        for (int i = 0; i < positions.length; i++) {
            totalFuelUsage += Math.abs(positions[i] - alignedPosition);
        }

        return totalFuelUsage;
    }

    public int cheapestFuelPosition(String input) {
        String[] positionStrings = input.trim().split(",");
        int[] positions = new int[positionStrings.length];

        for (int i = 0; i < positions.length; i++) {
            positions[i] = Integer.parseInt(positionStrings[i]);
        }
        
        int max = 0;
        for (int i = 0; i < positions.length; i++) {
            if (max < positions[i]) {
                max = positions[i];
            }
        }

        int cheapestFuelTotal = calculateFuelUsage(positions, 0);

        for (int i = 1; i <= max; i++) {
            int newFuelTotal = calculateFuelUsage(positions, i);
            if (newFuelTotal < cheapestFuelTotal) {
                cheapestFuelTotal = newFuelTotal;
            }
        }

        return cheapestFuelTotal;
    }

    public static int calculateFuelUsageCrabEngineering(int[] positions, int alignedPosition) {
        int totalFuelUsage = 0;
        for (int i = 0; i < positions.length; i++) {
            int n = Math.abs(positions[i] - alignedPosition);
            totalFuelUsage += (n*(n+1))/2;
        }

        return totalFuelUsage;
    }

    public int cheapestFuelPositionCrabEngineering(String input) {
        String[] positionStrings = input.trim().split(",");
        int[] positions = new int[positionStrings.length];

        for (int i = 0; i < positions.length; i++) {
            positions[i] = Integer.parseInt(positionStrings[i]);
        }
        
        int max = 0;
        for (int i = 0; i < positions.length; i++) {
            if (max < positions[i]) {
                max = positions[i];
            }
        }

        int cheapestFuelTotal = calculateFuelUsageCrabEngineering(positions, 0);

        for (int i = 1; i <= max; i++) {
            int newFuelTotal = calculateFuelUsageCrabEngineering(positions, i);
            if (newFuelTotal < cheapestFuelTotal) {
                cheapestFuelTotal = newFuelTotal;
            }
        }

        return cheapestFuelTotal;
    }
}