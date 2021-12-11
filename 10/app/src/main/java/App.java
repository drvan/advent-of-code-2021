import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

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

        System.out.println("Part 1: " + new App().calculateSyntaxErrorScore(input));
        System.out.println("Part 2: " + new App().calculateIncompleteLineMiddleScore(input));
    }

    // Yeah yeah, I know I could just use a map here and remove ~40% of the code

    public int calculateSyntaxErrorScore(String input) {
        String[] lines = input.split(System.lineSeparator());

        int totalSyntaxErrorScore = 0;
        for (String line: lines) {
            Stack<String> stack = new Stack<String>();
            String[] characters = line.trim().split("");
            boolean corrupted = false;
            for (String character: characters) {
                if (corrupted) {
                    break;
                }
                switch (character) {
                    case "(":  
                    case "[":  
                    case "{": 
                    case "<": 
                        stack.push(character);
                        break;
                    case ")":  
                    case "]":  
                    case "}":  
                    case ">":
                        String expectedOpener = stack.pop();
                        String expected = "";
                        switch (expectedOpener) {
                            case "(":
                                expected = ")";
                                break;
                            case "[": 
                                expected = "]";
                                break;
                            case "{":
                                expected = "}";
                                break;
                            case "<":
                                expected = ">";
                                break;
                        }
                        if (!character.equals(expected)) {
                            corrupted = true;
                            switch (character) {
                                case ")":
                                    totalSyntaxErrorScore += 3;
                                    break;
                                case "]":
                                    totalSyntaxErrorScore += 57;
                                    break;
                                case "}":
                                    totalSyntaxErrorScore += 1197;
                                    break;
                                case ">":
                                    totalSyntaxErrorScore += 25137;
                                    break;
                            }
                        }      
                        break;
                    default:
                        System.out.println("Invalid character encountered.");
                        System.exit(1);
                }
            }
        }

        return totalSyntaxErrorScore;
    }

    public long calculateIncompleteLineMiddleScore(String input) {
        String[] lines = input.split(System.lineSeparator());

        ArrayList<Long> incompleteLineScores = new ArrayList<Long>();
        
        for (String line: lines) {
            Stack<String> stack = new Stack<String>();
            String[] characters = line.trim().split("");
            boolean corrupted = false;
            for (String character: characters) {
                if (corrupted) {
                    break;
                }
                switch (character) {
                    case "(":  
                    case "[":  
                    case "{": 
                    case "<": 
                        stack.push(character);
                        break;
                    case ")":  
                    case "]":  
                    case "}":  
                    case ">":
                        String expectedOpener = stack.pop();
                        String expected = "";
                        switch (expectedOpener) {
                            case "(":
                                expected = ")";
                                break;
                            case "[": 
                                expected = "]";
                                break;
                            case "{":
                                expected = "}";
                                break;
                            case "<":
                                expected = ">";
                                break;
                        }
                        if (!character.equals(expected)) {
                            corrupted = true;
                        }      
                        break;
                    default:
                        System.out.println("Invalid character encountered.");
                        System.exit(1);
                }
            }

            if (!corrupted) {
                // If we made it here, the line isn't corrupted but is incomplete
                long lineScore = 0;
                int stackSize = stack.size();
                for (int i = 0; i < stackSize; i++) {
                    String character = stack.pop();
                    lineScore *= 5;
                    switch (character) {
                        case "(":
                            lineScore += 1;
                            break;
                        case "[": 
                            lineScore += 2;
                            break;
                        case "{":
                            lineScore += 3;
                            break;
                        case "<":
                            lineScore += 4;
                            break;
                    }
                }
                incompleteLineScores.add(lineScore);
            }

        }

        Collections.sort(incompleteLineScores);
        System.out.println(incompleteLineScores);
        System.out.println(incompleteLineScores.get((incompleteLineScores.size() - 1)/2));
        return incompleteLineScores.get((incompleteLineScores.size() - 1)/2);
    }
}