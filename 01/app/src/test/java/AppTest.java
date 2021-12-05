import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    final String sampleInput = "199\n" +
            "200\n" +
            "208\n" +
            "210\n" +
            "200\n" +
            "207\n" +
            "240\n" +
            "269\n" +
            "260\n" +
            "263";

    @Test
    void appPassesSampleInputPart1() {
        App classUnderTest = new App();
        assertEquals(classUnderTest.countDepthMeasurementIncreases(sampleInput), 7);
    }

    @Test
    void appPassesSampleInputPart2() {
        App classUnderTest = new App();
        assertEquals(classUnderTest.countDepthMeasurementIncreasesSlidingWindow(sampleInput), 5);
    }
}
