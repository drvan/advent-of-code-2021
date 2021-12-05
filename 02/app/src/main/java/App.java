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

        System.out.println("Part 1: " + new App().findMultipliedDepthAndHorizontalPosition(input));
        System.out.println("Part 2: " + new App().findMultipliedDepthAndHorizontalPositionWithAim(input));
    }

    public int findMultipliedDepthAndHorizontalPosition(String input) {
        String[] lines = input.split(System.lineSeparator());

        int depth = 0;
        int horizontalPosition = 0;

        for (int i = 0; i < lines.length; i++) {
            String[] line = lines[i].split(" ");
            switch (line[0]) {
                case "forward":
                    horizontalPosition += Integer.parseInt(line[1]);
                    break;
                case "down":
                    depth += Integer.parseInt(line[1]);
                    break;
                case "up":
                    depth -= Integer.parseInt(line[1]);
                    break;
                default:
                    System.err.println("Invalid command");
                    System.exit(1);
                    break;
            }
        }

        return depth * horizontalPosition;
    }

    public int findMultipliedDepthAndHorizontalPositionWithAim(String input) {
        String[] lines = input.split(System.lineSeparator());

        int aim = 0;
        int depth = 0;
        int horizontalPosition = 0;

        for (int i = 0; i < lines.length; i++) {
            String[] line = lines[i].split(" ");
            switch (line[0]) {
                case "forward":
                    horizontalPosition += Integer.parseInt(line[1]);
                    depth += aim * Integer.parseInt(line[1]);
                    break;
                case "down":
                    aim += Integer.parseInt(line[1]);
                    break;
                case "up":
                    aim -= Integer.parseInt(line[1]);
                    break;
                default:
                    System.err.println("Invalid command");
                    System.exit(1);
                    break;
            }
        }

        return depth * horizontalPosition;
    }
}