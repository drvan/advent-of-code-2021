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

        System.out.println("Part 1: " + new App().countDepthMeasurementIncreases(input));
        System.out.println("Part 2: " + new App().countDepthMeasurementIncreasesSlidingWindow(input));
    }

    public int countDepthMeasurementIncreases(String input) {
        String[] lines = input.split(System.lineSeparator());

        int increases = 0;

        for (int i = 0; i < lines.length; i++) {
            if (i == 0) {
                continue;
            }

            try {
                int first = Integer.parseInt(lines[i - 1]);
                int second = Integer.parseInt(lines[i]);
                if (second > first) {
                    increases++;
                }
            } catch (NumberFormatException e) {
                System.out.println("could not convert string to int: " + e.getMessage());
                System.exit(1);
            }
        }

        return increases;
    }

    public int countDepthMeasurementIncreasesSlidingWindow(String input) {
        String[] lines = input.split(System.lineSeparator());

        int increases = 0;

        for (int i = 0; i < lines.length - 3; i++) {

            try {
                int firstSum = Integer.parseInt(lines[i]) + Integer.parseInt(lines[i + 1])
                        + Integer.parseInt(lines[i + 2]);
                int secondSum = Integer.parseInt(lines[i + 1]) + Integer.parseInt(lines[i + 2])
                        + Integer.parseInt(lines[i + 3]);

                if (secondSum > firstSum) {
                    increases++;
                }
            } catch (NumberFormatException e) {
                System.out.println("could not convert string to int: " + e.getMessage());
                System.exit(1);
            }
        }

        return increases;
    }

}
