import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    final String sampleInput = "forward 5\n" +
            "down 5\n" +
            "forward 8\n" +
            "up 3\n" +
            "down 8\n" +
            "forward 2";

    @Test
    void appPassesSampleInputPart1() {
        App classUnderTest = new App();
        assertEquals(150, classUnderTest.findMultipliedDepthAndHorizontalPosition(sampleInput));
    }

    @Test
    void appPassesSampleInputPart2() {
        App classUnderTest = new App();
        assertEquals(900, classUnderTest.findMultipliedDepthAndHorizontalPositionWithAim(sampleInput));
    }
}