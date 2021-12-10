import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    final String sampleInput = "2199943210\n" +
            "3987894921\n" +
            "9856789892\n" +
            "8767896789\n" +
            "9899965678";

    @Test
    void appPassesSampleInputPart1() {
        App classUnderTest = new App();
        assertEquals(15, classUnderTest.calculateTotalRiskLevelLowPoints(sampleInput));
    }

    @Test
    void appPassesSampleInputPart2() {
        App classUnderTest = new App();
        assertEquals(1134,
                classUnderTest.calculateBasinTotal(sampleInput));
    }
}