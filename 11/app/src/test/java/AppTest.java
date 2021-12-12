import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    final String sampleInput = "5483143223\n" +
            "2745854711\n" +
            "5264556173\n" +
            "6141336146\n" +
            "6357385478\n" +
            "4167524645\n" +
            "2176841721\n" +
            "6882881134\n" +
            "4846848554\n" +
            "5283751526";

    @Test
    void appPassesSampleInputPart1() {
        App classUnderTest = new App();
        assertEquals(1656,
                classUnderTest.calculateTotalFlashes100Steps(sampleInput));
    }

    @Test
    void appPassesSampleInputPart2() {
        App classUnderTest = new App();
        assertEquals(195,
                classUnderTest.calculateFirstSynchronizedFlash(sampleInput));
    }
}