import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    final String sampleInput = "0,9 -> 5,9\n" +
            "8,0 -> 0,8\n" +
            "9,4 -> 3,4\n" +
            "2,2 -> 2,1\n" +
            "7,0 -> 7,4\n" +
            "6,4 -> 2,0\n" +
            "0,9 -> 2,9\n" +
            "3,4 -> 1,4\n" +
            "0,0 -> 8,8\n" +
            "5,5 -> 8,2";

    @Test
    void appPassesSampleInputPart1() {
        App classUnderTest = new App();
        assertEquals(5, classUnderTest.countLinesOverlap(sampleInput));
    }

    @Test
    void appPassesSampleInputPart2() {
        App classUnderTest = new App();
        assertEquals(12, classUnderTest.countLinesOverlapWithDiagonals(sampleInput));
    }
}