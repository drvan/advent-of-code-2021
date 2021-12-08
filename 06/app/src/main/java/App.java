import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

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

        System.out.println("Part 1: " + new App().lanternfishCounter(input));
        System.out.println("Part 2: " + new App().lanternfishCounter256(input));
    }

    public int lanternfishCounter(String input) {
        ArrayList<Integer> lanternfish = new ArrayList<Integer>();

        String[] splitInput = input.trim().split(",");
        for (int i = 0; i < splitInput.length; i++) {
            lanternfish.add(Integer.parseInt(splitInput[i]));
        }

        for (int i = 0; i < 80; i++) {
            int lanternfishSize = lanternfish.size();
            for(int j = 0; j < lanternfishSize; j++) {
                int timer = lanternfish.get(j);
                if (timer == 0) {
                    lanternfish.add(8);
                    lanternfish.set(j, 6);
                } else {
                    lanternfish.set(j, timer - 1);
                }
            }
        }

        return lanternfish.size();
    }

    public long lanternfishCounter256(String input) {
        long[] lanternfishCounters = {0, 0, 0, 0, 0, 0, 0, 0, 0};

        // Parse the input and populate the initial state of counters
        String[] splitInput = input.trim().split(",");
        for (int i = 0; i < splitInput.length; i++) {
            lanternfishCounters[Integer.parseInt(splitInput[i])]++;
        }

        for (int i = 0; i < 256; i++) {
            long newLanternfishCounter = 0;
            for (int j = 0; j < 8; j++) {
                if (j == 0) {
                    // The number of fish at 0 will produce equal amounts new 8s and new 6s
                    newLanternfishCounter = lanternfishCounters[j];
                }
                // Overwrite lower counters with the counter above it
                lanternfishCounters[j] = lanternfishCounters[j+1];
            }
            lanternfishCounters[6] += newLanternfishCounter;
            lanternfishCounters[8] = newLanternfishCounter;
        }

        // Sum the total of all counters
        long totalLanternfish = 0;
        for (int i = 0; i < 9; i++) {
            totalLanternfish += lanternfishCounters[i];
        }

        return totalLanternfish;
    }
}