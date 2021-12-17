import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    final String sampleInput = "target area: x=20..30, y=-10..-5";

    @Test
    void appPassesSampleInputPart1() {
        App classUnderTest = new App();
        assertEquals(45,
                classUnderTest.trickShotMaxHeight(sampleInput));
    }

    @Test
    void appPassesSampleInputPart2() {
        App classUnderTest = new App();
        assertEquals(112,
                classUnderTest.countAllOnTargetVelocities(sampleInput));
    }
}