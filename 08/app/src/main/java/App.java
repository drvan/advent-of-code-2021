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

        System.out.println("Part 1: " + new App().count1478(input));
        System.out.println("Part 2: " + new App().sumOutputDisplayNumbers(input));
    }

    public int count1478(String input) {
        String[] lines = input.split(System.lineSeparator());

        int counter = 0;
        for (int i = 0; i < lines.length; i++) {
            String[] splitInput = lines[i].split("\\|");
            String[] outputDigits = splitInput[1].split(" ");
            for (int j = 0; j < outputDigits.length; j++) {
                if (outputDigits[j].length() == 2 || outputDigits[j].length() == 3 || outputDigits[j].length() == 4 || outputDigits[j].length() == 7) {
                    counter++;
                }
            }
        }

        return counter;
    }

    public static ArrayList<HashSet<String>> findSetsWithLength(int length, ArrayList<HashSet<String>> list) {
        ArrayList<HashSet<String>> setsWithLength = new ArrayList<HashSet<String>>();

        for (int i = 0; i < list.size(); i++){
            if (list.get(i).size() == length) {
                setsWithLength.add(new HashSet<String>(list.get(i)));
            }
        }

        return setsWithLength;
    }

    public int sumOutputDisplayNumbers(String input) {
        String[] lines = input.split(System.lineSeparator());
        int totalSum = 0;

        for (int i = 0; i < lines.length; i++) {
            String[] splitInput = lines[i].split("\\|");
            String[] uniqueSignalPatternStrings = splitInput[0].split(" ");
            String[] outputDigitsStrings = splitInput[1].trim().split(" ");

            // Create a list of sets, parse each unique signal pattern into a set of characters, add to list
            ArrayList<HashSet<String>> uniqueSignalPatterns = new ArrayList<HashSet<String>>();
            for (int j = 0; j < uniqueSignalPatternStrings.length; j++) {
                HashSet<String> newPattern = new HashSet<String>();
                String[] uniqueSignalPatternChars = uniqueSignalPatternStrings[j].split("");
                for (int k = 0; k < uniqueSignalPatternChars.length; k++) {
                    newPattern.add(uniqueSignalPatternChars[k]);
                }
                uniqueSignalPatterns.add(newPattern);
            }

            // Create a hash map that maps sets to their actual digit, e.g. set['ab'] = "1";
            HashMap<HashSet<String>, String> patternToDigitMap = new HashMap<HashSet<String>, String>();

            // Create a hash map that maps segments to sets of prospective segments, e.g. set["a"] = {b, c}
            HashMap<String, HashSet<String>> segmentMap = new HashMap<String, HashSet<String>>();

            // Start with 1, 4, 7
            ArrayList<HashSet<String>> length2 = findSetsWithLength(2, uniqueSignalPatterns);
            patternToDigitMap.put(new HashSet<String>(length2.get(0)), "1");
            ArrayList<HashSet<String>> length4 = findSetsWithLength(4, uniqueSignalPatterns);
            patternToDigitMap.put(new HashSet<String>(length4.get(0)), "4");
            ArrayList<HashSet<String>> length3 = findSetsWithLength(3, uniqueSignalPatterns);
            patternToDigitMap.put(new HashSet<String>(length3.get(0)), "7");

            // Create a hashSet for the possibilities in E
            HashSet<String> possibleESegment = new HashSet<String>();

            // We know that segments 'c' and 'f' can be either of the segments of length 2
            segmentMap.put("c", length2.get(0));
            segmentMap.put("f", length2.get(0));

            // segment 7 - segment 1 leaves segment a
            length3.get(0).removeAll(length2.get(0));
            segmentMap.put("a", length3.get(0));
            
            // Get all patterns of length 6 - these correspond to digits 0, 6, and 9
            ArrayList<HashSet<String>> length6 = findSetsWithLength(6, uniqueSignalPatterns);

            // Iterate over length6
            for (int j = 0; j < length6.size(); j++) {
                // Find the difference of digit 8 (all segments) and each length 6.
                // If the remaining segment exists in segmentMap["c"], then we know it's digit 6
                ArrayList<HashSet<String>> length7 = findSetsWithLength(7, uniqueSignalPatterns);
                length7.get(0).removeAll(length6.get(j));
                length4.get(0).removeAll(length7.get(0));

                if (segmentMap.get("c").containsAll(length7.get(0))) {
                    // we can identify segment c now, subtract this from segment f to find f
                    // also, we know that the current length6 corresponds to digit 6
                    segmentMap.put("c", length7.get(0));
                    HashSet<String> segmentF = segmentMap.get("f");
                    segmentF.removeAll(length7.get(0));
                    segmentMap.put("f", segmentF);
                    
                    patternToDigitMap.put(new HashSet<String>(length6.get(j)), "6");
                } else {
                    possibleESegment.addAll(length7.get(0));
                }
            }

            // Remove segments "c" and "f" from 4 to leave one remaining, which is the segment for "b"
            length4.get(0).removeAll(segmentMap.get("c"));
            length4.get(0).removeAll(segmentMap.get("f"));
            
            // What remains at this point is segment b
            segmentMap.put("b", length4.get(0));

            // Get original segment 4 and remove known to identify segment for "d"
            length4 = findSetsWithLength(4, uniqueSignalPatterns);
            length4.get(0).removeAll(segmentMap.get("b"));
            length4.get(0).removeAll(segmentMap.get("c"));
            length4.get(0).removeAll(segmentMap.get("f"));

            // What remains at this point is segment d
            segmentMap.put("d", length4.get(0));

            // Figure out e segment based on identifying segment d
            possibleESegment.removeAll(length4.get(0));
            segmentMap.put("e", possibleESegment);

            // Finally, reduce the 7 segment display down to just g
            HashSet<String> gSegment = new HashSet<String>();
            gSegment.addAll(segmentMap.get("a"));
            gSegment.addAll(segmentMap.get("b"));
            gSegment.addAll(segmentMap.get("c"));
            gSegment.addAll(segmentMap.get("d"));
            gSegment.addAll(segmentMap.get("e"));
            gSegment.addAll(segmentMap.get("f"));

            ArrayList<HashSet<String>> length7 = findSetsWithLength(7, uniqueSignalPatterns);
            length7.get(0).removeAll(gSegment);
            segmentMap.put("g", length7.get(0));

            // Now we know all the mappings, construct the missing patternToDigit entries
            HashSet<String> newPattern = new HashSet<String>();

            // 0 is a b c e f g
            newPattern.addAll(segmentMap.get("a"));
            newPattern.addAll(segmentMap.get("b"));
            newPattern.addAll(segmentMap.get("c"));
            newPattern.addAll(segmentMap.get("e"));
            newPattern.addAll(segmentMap.get("f"));
            newPattern.addAll(segmentMap.get("g"));

            patternToDigitMap.put(new HashSet<String>(newPattern), "0");

            newPattern.clear();

            // 2 is a c d e g
            newPattern.addAll(segmentMap.get("a"));
            newPattern.addAll(segmentMap.get("c"));
            newPattern.addAll(segmentMap.get("d"));
            newPattern.addAll(segmentMap.get("e"));
            newPattern.addAll(segmentMap.get("g"));

            patternToDigitMap.put(new HashSet<String>(newPattern), "2");

            newPattern.clear();

            // 3 is a c d f g
            newPattern.addAll(segmentMap.get("a"));
            newPattern.addAll(segmentMap.get("c"));
            newPattern.addAll(segmentMap.get("d"));
            newPattern.addAll(segmentMap.get("f"));
            newPattern.addAll(segmentMap.get("g"));

            patternToDigitMap.put(new HashSet<String>(newPattern), "3");

            newPattern.clear();

            // 5 is a b d f g
            newPattern.addAll(segmentMap.get("a"));
            newPattern.addAll(segmentMap.get("b"));
            newPattern.addAll(segmentMap.get("d"));
            newPattern.addAll(segmentMap.get("f"));
            newPattern.addAll(segmentMap.get("g"));

            patternToDigitMap.put(new HashSet<String>(newPattern), "5");

            newPattern.clear();

            // 9 is a b c d f g
            newPattern.addAll(segmentMap.get("a"));
            newPattern.addAll(segmentMap.get("b"));
            newPattern.addAll(segmentMap.get("c"));
            newPattern.addAll(segmentMap.get("d"));
            newPattern.addAll(segmentMap.get("f"));
            newPattern.addAll(segmentMap.get("g"));

            patternToDigitMap.put(new HashSet<String>(newPattern), "9");

            newPattern.clear();
            
            // 8 is a b c d e f g
            newPattern.addAll(segmentMap.get("a"));
            newPattern.addAll(segmentMap.get("b"));
            newPattern.addAll(segmentMap.get("c"));
            newPattern.addAll(segmentMap.get("d"));
            newPattern.addAll(segmentMap.get("e"));
            newPattern.addAll(segmentMap.get("f"));
            newPattern.addAll(segmentMap.get("g"));

            patternToDigitMap.put(new HashSet<String>(newPattern), "8");

            newPattern.clear();

            // Now we can decode the output digits and concatenate them together
            // Create a list of sets, parse each output digit into a set of characters, add to list
            ArrayList<HashSet<String>> outputDigits = new ArrayList<HashSet<String>>();
            for (int j = 0; j < outputDigitsStrings.length; j++) {
                HashSet<String> newDigit = new HashSet<String>();
                String[] outputDigitsChars = outputDigitsStrings[j].split("");
                for (int k = 0; k < outputDigitsChars.length; k++) {
                    newDigit.add(outputDigitsChars[k]);
                }
                outputDigits.add(newDigit);
            }

            String display = "";
            for (int j = 0; j < outputDigits.size(); j++) {
                display += patternToDigitMap.get(outputDigits.get(j));
            }

            totalSum += Integer.parseInt(display);
        }

        return totalSum;
    }
}