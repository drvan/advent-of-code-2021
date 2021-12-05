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

        System.out.println("Part 1: " + new App().bingoSubsystem(input));
        System.out.println("Part 2: " + new App().bingoSubsystemWinLast(input));
    }

    public int bingoSubsystem(String input) {
        // Split everything by double newlines
        String[] tokens = input.split("\n\n");

        // The first line is the numbers to be drawn
        String[] drawNumbers = tokens[0].split(",");

        // Every token after that is an individual bingo card
        BingoCard[] bingoCards = new BingoCard[tokens.length - 1];

        for (int i = 1; i < tokens.length; i++) {
            bingoCards[i - 1] = new BingoCard(tokens[i]);
        }

        for (int i = 0; i < drawNumbers.length; i++) {
            for (int j = 0; j < bingoCards.length; j++) {
                int score =  bingoCards[j].callNumber(Integer.parseInt(drawNumbers[i]));
                if (score > 0) {
                    return score;
                }
            }
        }

        // This is only returned if no card won
        return 0;
    }

    public int bingoSubsystemWinLast(String input) {
        // Split everything by double newlines
        String[] tokens = input.split("\n\n");

        // The first line is the numbers to be drawn
        String[] drawNumbers = tokens[0].split(",");

        // Every token after that is an individual bingo card
        BingoCard[] bingoCards = new BingoCard[tokens.length - 1];

        int lastScore = 0;

        for (int i = 1; i < tokens.length; i++) {
            bingoCards[i - 1] = new BingoCard(tokens[i]);
        }

        for (int i = 0; i < drawNumbers.length; i++) {
            for (int j = 0; j < bingoCards.length; j++) {
                int score =  bingoCards[j].callNumber(Integer.parseInt(drawNumbers[i]));
                if (score > 0) {
                    lastScore = score;
                }
            }
        }

        return lastScore;
    }
}