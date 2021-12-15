import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    final String sampleInput = "NNCB\n" +
            "\n" +
            "CH -> B\n" +
            "HH -> N\n" +
            "CB -> H\n" +
            "NH -> C\n" +
            "HB -> C\n" +
            "HC -> B\n" +
            "HN -> C\n" +
            "NN -> C\n" +
            "BH -> H\n" +
            "NC -> B\n" +
            "NB -> B\n" +
            "BN -> B\n" +
            "BB -> N\n" +
            "BC -> B\n" +
            "CC -> N\n" +
            "CN -> C";

    @Test
    void appPassesSampleInputPart1() {
        App classUnderTest = new App();
        assertEquals(1588,
                classUnderTest.mostSubtractLeastPolymer10(sampleInput));
    }

    @Test
    void appPassesSampleInputPart2() {
        App classUnderTest = new App();
        assertEquals(2188189693529L,
                classUnderTest.mostSubtractLeastPolymer40(sampleInput));
    }
}