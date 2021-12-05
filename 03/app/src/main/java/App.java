import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
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

        System.out.println("Part 1: " + new App().calculatePowerConsumption(input));
        System.out.println("Part 2: " + new App().calculateLifeSupportRating(input));
    }

    public int calculatePowerConsumption(String input) {
        String[] lines = input.split(System.lineSeparator());

        int[] bitCounters = new int[lines[0].length()];
        Arrays.fill(bitCounters, 0);

        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < lines[i].length(); j++) {
                if (lines[i].charAt(j) == '1') {
                    bitCounters[j]++;
                }
            }
        }

        String gammaRateString = "";
        String epsilonRateString = "";

        for (int i = 0; i < bitCounters.length; i++) {
            if (lines.length - bitCounters[i] < lines.length/2) {
                gammaRateString += "1";
                epsilonRateString += "0";
            } else {
                gammaRateString += "0";
                epsilonRateString += "1";
            }
        }

        return Integer.parseInt(gammaRateString, 2) * Integer.parseInt(epsilonRateString, 2);
    }

    public int calculateLifeSupportRating(String input) {
        String[] lines = input.split(System.lineSeparator());

        ArrayList<String> workingArray = new ArrayList<String>();
        ArrayList<String> onesArray = new ArrayList<String>();
        ArrayList<String> zeroesArray = new ArrayList<String>();

        for (int i = 0; i < lines.length; i++) {
            workingArray.add(lines[i]);
        }

        // Calculate the oxygen generator rating
        String oxygenGeneratorRatingString = "";
        for (int i = 0; i < lines[0].length(); i++) {

            if (workingArray.size() == 1) {
                oxygenGeneratorRatingString = workingArray.get(0);
                break;
            }

            for (int j = 0; j < workingArray.size(); j++) {
                if (workingArray.get(j).charAt(i) == '1') {
                    onesArray.add(workingArray.get(j));
                } else {
                    zeroesArray.add(workingArray.get(j));
                }
            }

            workingArray.clear();
            if (onesArray.size() >= zeroesArray.size()) {
                oxygenGeneratorRatingString += '1';
                workingArray.addAll(onesArray);
            } else {
                oxygenGeneratorRatingString += '0';
                workingArray.addAll(zeroesArray);
            }

            onesArray.clear();
            zeroesArray.clear();
        }

        for (int i = 0; i < lines.length; i++) {
            workingArray.add(lines[i]);
        }

        // Calculate the CO2 scrubber rating
        String co2ScrubberRatingString = "";
        for (int i = 0; i < lines[0].length(); i++) {

            if (workingArray.size() == 1) {
                co2ScrubberRatingString = workingArray.get(0);
                break;
            }

            for (int j = 0; j < workingArray.size(); j++) {
                if (workingArray.get(j).charAt(i) == '1') {
                    onesArray.add(workingArray.get(j));
                } else {
                    zeroesArray.add(workingArray.get(j));
                }
            }

            workingArray.clear();
            if (onesArray.size() < zeroesArray.size()) {
                co2ScrubberRatingString += '1';
                workingArray.addAll(onesArray);
            } else {
                co2ScrubberRatingString += '0';
                workingArray.addAll(zeroesArray);
            }

            onesArray.clear();
            zeroesArray.clear();
        }

        return Integer.parseInt(oxygenGeneratorRatingString, 2) * Integer.parseInt(co2ScrubberRatingString, 2);
    }
}