import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    final String sampleInput = "6,10\n" +
            "0,14\n" +
            "9,10\n" +
            "0,3\n" +
            "10,4\n" +
            "4,11\n" +
            "6,0\n" +
            "6,12\n" +
            "4,1\n" +
            "0,13\n" +
            "10,12\n" +
            "3,4\n" +
            "3,0\n" +
            "8,4\n" +
            "1,10\n" +
            "2,14\n" +
            "8,10\n" +
            "9,0\n" +
            "\n" +
            "fold along y=7\n" +
            "fold along x=5";

    @Test
    void appPassesSampleInputPart1() {
        App classUnderTest = new App();
        assertEquals(17,
                classUnderTest.countVisibleDotsAfterFirstFold(sampleInput));
    }

    @Test
    void appPassesSampleInputPart2() {
        App classUnderTest = new App();
        assertEquals("\n" +
                "#####\n" +
                "#...#\n" +
                "#...#\n" +
                "#...#\n" +
                "#####\n",
                classUnderTest.generateThermalImagingCameraCode(sampleInput));
    }
}