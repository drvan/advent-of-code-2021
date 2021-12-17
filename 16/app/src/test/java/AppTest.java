import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class AppTest {

    final String sampleInput1 = "8A004A801A8002F478";
    final String sampleInput2 = "620080001611562C8802118E34";
    final String sampleInput3 = "C0015000016115A2E0802F182340";
    final String sampleInput4 = "A0016C880162017C3686B18A3D4780";

    final String sampleInput5 = "C200B40A82";
    final String sampleInput6 = "04005AC33890";
    final String sampleInput7 = "880086C3E88112";
    final String sampleInput8 = "CE00C43D881120";
    final String sampleInput9 = "D8005AC2A8F0";
    final String sampleInput10 = "F600BC2D8F";
    final String sampleInput11 = "9C005AC2F8F0";
    final String sampleInput12 = "9C0141080250320F1802104A08";

    @Test
    void appPassesSampleInputPart1() {
        App classUnderTest = new App();
        assertEquals(16,
                classUnderTest.sumAllPacketVersionNumbers(sampleInput1));
        assertEquals(12,
                classUnderTest.sumAllPacketVersionNumbers(sampleInput2));
        assertEquals(23,
                classUnderTest.sumAllPacketVersionNumbers(sampleInput3));
        assertEquals(31,
                classUnderTest.sumAllPacketVersionNumbers(sampleInput4));
    }

    @Test
    void appPassesSampleInputPart2() {
        App classUnderTest = new App();
        assertEquals(3,
                classUnderTest.evaluateTotalPacketExpression(sampleInput5));
        assertEquals(54,
                classUnderTest.evaluateTotalPacketExpression(sampleInput6));
        assertEquals(7,
                classUnderTest.evaluateTotalPacketExpression(sampleInput7));
        assertEquals(9,
                classUnderTest.evaluateTotalPacketExpression(sampleInput8));
        assertEquals(1,
                classUnderTest.evaluateTotalPacketExpression(sampleInput9));
        assertEquals(0,
                classUnderTest.evaluateTotalPacketExpression(sampleInput10));
        assertEquals(0,
                classUnderTest.evaluateTotalPacketExpression(sampleInput11));
        assertEquals(1,
                classUnderTest.evaluateTotalPacketExpression(sampleInput12));
    }
}