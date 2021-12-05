import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    final String sampleInput = "00100\n" +
            "11110\n" +
            "10110\n" +
            "10111\n" +
            "10101\n" +
            "01111\n" +
            "00111\n" +
            "11100\n" +
            "10000\n" +
            "11001\n" +
            "00010\n" +
            "01010";

    @Test
    void appPassesSampleInputPart1() {
        App classUnderTest = new App();
        assertEquals(198, classUnderTest.calculatePowerConsumption(sampleInput));
    }

    @Test
    void appPassesSampleInputPart2() {
        App classUnderTest = new App();
        assertEquals(230, classUnderTest.calculateLifeSupportRating(sampleInput));
    }
}