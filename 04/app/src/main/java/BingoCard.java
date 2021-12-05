import java.util.HashMap;
import java.util.Map;

public class BingoCard {

  private class BingoSpace {
    final int col;
    final int row;
    boolean marked = false;

    public BingoSpace(int row, int col) {
      this.row = row;
      this.col = col;
    }
  }

  private HashMap<Integer, BingoSpace> cardMap = new HashMap<Integer, BingoSpace>();

  private int[] rowCounter = {0, 0, 0, 0, 0};
  private int[] colCounter = {0, 0, 0, 0, 0};

  private boolean solved = false;

  public BingoCard(String board) {

    String[] lines = board.split(System.lineSeparator());

    for (int row = 0; row < 5; row++) {
      String[] numbers = lines[row].trim().split("\\s+");
      for (int col = 0; col < 5; col++) {
        cardMap.put(Integer.parseInt(numbers[col]), new BingoSpace(row, col));
      }
    }
  }

  public int callNumber(int number) {
    if (solved) {
      return 0;
    }

    BingoSpace bingoSpace = this.cardMap.get(number);

    // Check if we have a space that has this number
    if (bingoSpace == null) {
      return 0;
    } else {
      bingoSpace.marked = true;
      this.rowCounter[bingoSpace.row]++;
      this.colCounter[bingoSpace.col]++;
      if (this.rowCounter[bingoSpace.row] == 5 || this.colCounter[bingoSpace.col] == 5) {
        // BINGO
        int unmarkedSum = 0;
        for (Map.Entry<Integer,BingoSpace> bingoSpaceEntry : this.cardMap.entrySet()) {
          if (bingoSpaceEntry.getValue().marked == false) {
            unmarkedSum += bingoSpaceEntry.getKey();
          }
        }
        this.solved = true;
        return unmarkedSum * number;
      }

      this.cardMap.put(number, bingoSpace);
      return 0;
    }
  }

  public String toString() {
    int cardArray[][] = new int[5][5];

    for (Map.Entry<Integer,BingoSpace> bingoSpaceEntry : this.cardMap.entrySet()) {
      cardArray[bingoSpaceEntry.getValue().row][bingoSpaceEntry.getValue().col] = bingoSpaceEntry.getKey();
    }

    String returnString = "";
    for (int row = 0; row < 5; row++) {
      for (int col = 0; col < 5; col++) {
        returnString += cardArray[row][col] + " ";
      }
      returnString += "\n";
    }

    return returnString;
  }

}
