import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    final String sampleInput = "start-A\n" +
            "start-b\n" +
            "A-c\n" +
            "A-b\n" +
            "b-d\n" +
            "A-end\n" +
            "b-end";

    @Test
    void appPassesSampleInputPart1() {
        App classUnderTest = new App();
        assertEquals(10,
                classUnderTest.countCaveSystemPaths(sampleInput));
    }

    @Test
    void appPassesSampleInputPart2() {
        App classUnderTest = new App();
        assertEquals(36,
                classUnderTest.countCaveSystemPathsVisitOneSmallCaveTwice(sampleInput));
    }
}