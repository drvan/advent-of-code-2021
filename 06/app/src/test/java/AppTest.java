import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    final String sampleInput = "3,4,3,1,2";

    @Test
    void appPassesSampleInputPart1() {
        App classUnderTest = new App();
        assertEquals(5934, classUnderTest.lanternfishCounter(sampleInput));
    }

    @Test
    void appPassesSampleInputPart2() {
        App classUnderTest = new App();
        assertEquals(26984457539L, classUnderTest.lanternfishCounter256(sampleInput));
    }
}