import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    final String sampleInput = "1163751742\n" +
            "1381373672\n" +
            "2136511328\n" +
            "3694931569\n" +
            "7463417111\n" +
            "1319128137\n" +
            "1359912421\n" +
            "3125421639\n" +
            "1293138521\n" +
            "2311944581";

    @Test
    void appPassesSampleInputPart1() {
        App classUnderTest = new App();
        assertEquals(40,
                classUnderTest.lowestTotalRisk(sampleInput));
    }

    @Test
    void appPassesSampleInputPart2() {
        App classUnderTest = new App();
        assertEquals(315,
                classUnderTest.lowestTotalRiskFullMap(sampleInput));
    }
}