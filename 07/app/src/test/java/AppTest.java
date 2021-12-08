import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    final String sampleInput = "16,1,2,0,4,2,7,1,2,14";

    @Test
    void appPassesSampleInputPart1() {
        App classUnderTest = new App();
        assertEquals(37, classUnderTest.cheapestFuelPosition(sampleInput));
    }

    @Test
    void appPassesSampleInputPart2() {
        App classUnderTest = new App();
        assertEquals(168, classUnderTest.cheapestFuelPositionCrabEngineering(sampleInput));
    }
}