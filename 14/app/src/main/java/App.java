import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

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

        System.out.println("Part 1: " + new App().mostSubtractLeastPolymer10(input));
        System.out.println("Part 2: " + new App().mostSubtractLeastPolymer40(input));
    }

    public int mostSubtractLeastPolymer10(String input) {
        String template = input.split("\n\n")[0].trim();
        String insertionRules = input.split("\n\n")[1].trim();

        LinkedList<String> polymer = new LinkedList<String>();
        HashMap<String, String> insertionRulesMap = new HashMap<String, String>();
        
        for (String element: template.split("")) {
            polymer.add(element);
        }

        for (String rule: insertionRules.split("\n")) {
            insertionRulesMap.put(rule.split(" -> ")[0], rule.split(" -> ")[1]);
        }

        for (int i = 0; i < 10; i++) {
            int polymerSize = polymer.size();
            for (int j = 0; j < polymerSize - 1; j += 2) {
                String element = insertionRulesMap.get(polymer.get(j) + polymer.get(j+1));
                polymer.add(j + 1, element);
                polymerSize++;
            }
        }

        HashMap<String, Integer> elementCounter = new HashMap<String, Integer>();
        
        for (String element: polymer) {
            if (elementCounter.containsKey(element)) {
                elementCounter.put(element, elementCounter.get(element) + 1);
            } else {
                elementCounter.put(element, 1);
            }
        }

        ArrayList<Integer> elementCounterList = new ArrayList<Integer>(elementCounter.values());
        Collections.sort(elementCounterList);

        return elementCounterList.get(elementCounterList.size() - 1) - elementCounterList.get(0);
    }

    public long mostSubtractLeastPolymer40(String input) {
        String template = input.split("\n\n")[0].trim();
        String insertionRules = input.split("\n\n")[1].trim();

        HashMap<String, Long> polymerSequenceCounter = new HashMap<String, Long>();
        HashMap<String, String> insertionRulesMap = new HashMap<String, String>();
        HashMap<String, Long> elementCounter = new HashMap<String, Long>();

        String[] polymerElements = template.split("");

        for (int i = 0; i < polymerElements.length - 1; i++) {
            if (polymerSequenceCounter.containsKey(polymerElements[i] + polymerElements[i+1])) {
                polymerSequenceCounter.put(polymerElements[i] + polymerElements[i+1], polymerSequenceCounter.get(polymerElements[i] + polymerElements[i+1])+1);
            } else {
                polymerSequenceCounter.put(polymerElements[i] + polymerElements[i+1], 1L);
            }
        }
        
        for (String rule: insertionRules.split("\n")) {
            insertionRulesMap.put(rule.split(" -> ")[0], rule.split(" -> ")[1]);
        }

        for (String element: template.split("")) {
            if (elementCounter.containsKey(element)) {
                elementCounter.put(element, elementCounter.get(element) + 1);
            } else {
                elementCounter.put(element, 1L);
            }
        }

        for (int i = 0; i < 40; i++) {
            HashMap<String, Long> newPolymerSequenceCounter = new HashMap<String, Long>();
            for (String sequence: polymerSequenceCounter.keySet()) {
                long numberSequences = polymerSequenceCounter.get(sequence);
                String insertionElement = insertionRulesMap.get(sequence);
                String firstSequence = sequence.split("")[0] + insertionElement;
                String secondSequence = insertionElement + sequence.split("")[1];

                if (newPolymerSequenceCounter.containsKey(firstSequence)) {
                    newPolymerSequenceCounter.put(firstSequence, newPolymerSequenceCounter.get(firstSequence) + numberSequences);
                } else {
                    newPolymerSequenceCounter.put(firstSequence, numberSequences);
                }

                if (newPolymerSequenceCounter.containsKey(secondSequence)) {
                    newPolymerSequenceCounter.put(secondSequence, newPolymerSequenceCounter.get(secondSequence) + numberSequences);
                } else {
                    newPolymerSequenceCounter.put(secondSequence, numberSequences);
                }

                if (elementCounter.containsKey(insertionElement)) {
                    elementCounter.put(insertionElement, elementCounter.get(insertionElement) + numberSequences);
                } else {
                    elementCounter.put(insertionElement, 1L);
                }
            }

            polymerSequenceCounter.clear();
            polymerSequenceCounter.putAll(newPolymerSequenceCounter);
        }

        ArrayList<Long> elementCounterList = new ArrayList<Long>(elementCounter.values());
        Collections.sort(elementCounterList);

        return elementCounterList.get(elementCounterList.size() - 1) - elementCounterList.get(0);
    }
    
}