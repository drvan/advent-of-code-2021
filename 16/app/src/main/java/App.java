import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

public class App {
    public static void main(String[] args) {
        String input = "";
        try {
            URL url = Thread.currentThread().getContextClassLoader().getResource("input");
            Path path = Paths.get(url.getPath());
            input = Files.readString(path);
        } catch (IOException e) {
            System.out.println("input file could not be read: " + e.getMessage());
            System.exit(1);
        }

        System.out.println("Part 1: " + new App().sumAllPacketVersionNumbers(input));
        System.out.println("Part 2: " + new App().evaluateTotalPacketExpression(input));
    }

    private class Packet {
        int packetVersion;
        int packetTypeId;
        long literalValue;
        int lengthTypeId;
        int subPacketLength;
        int numberSubPackets;
        int packetSize;
        ArrayList<Packet> subPackets = new ArrayList<Packet>();

        public Packet(String packet) {
            this.packetVersion = Integer.parseInt(packet.substring(0, 3), 2);
            this.packetTypeId = Integer.parseInt(packet.substring(3, 6), 2);
            this.packetSize = 6;

            if (this.packetTypeId == 4) {
                // literal packet
                String[] literalBits = packet.substring(6).split("");
                String literalValueString = "";
                for (int i = 0; i < literalBits.length;) {
                    if (literalBits[i].equals("0")) {
                        for (int j = 0; j < 4; j++) {
                            literalValueString += literalBits[i + j + 1];
                        }
                        this.packetSize += 5;
                        break;
                    } else {
                        for (int j = 0; j < 4; j++) {
                            literalValueString += literalBits[i + j + 1];
                        }
                        i += 5;
                        this.packetSize += 5;
                    }
                }
                this.literalValue = Long.parseLong(literalValueString, 2);
            } else {
                // operator packet
                this.lengthTypeId = Integer.parseInt(packet.substring(6, 7), 2);
                this.packetSize += 1;

                if (this.lengthTypeId == 0) {
                    // total length in bits
                    this.subPacketLength = Integer.parseInt(packet.substring(7, 22), 2);
                    this.packetSize += 15;
                    int bitsRead = 0;
                    while (bitsRead < this.subPacketLength) {
                        Packet subPacket = new Packet(packet.substring(22 + bitsRead));
                        this.subPackets.add(subPacket);
                        bitsRead += subPacket.packetSize;
                    }
                    this.packetSize += bitsRead;
                } else {
                    // total number of sub packets
                    this.numberSubPackets = Integer.parseInt(packet.substring(7, 18), 2);
                    this.packetSize += 11;
                    int subPacketsRead = 0;
                    int bitsRead = 0;
                    while (subPacketsRead < this.numberSubPackets) {
                        Packet subPacket = new Packet(packet.substring(18 + bitsRead));
                        this.subPackets.add(subPacket);
                        subPacketsRead++;
                        bitsRead += subPacket.packetSize;
                    }
                    this.packetSize += bitsRead;
                }
            }
        }
    }

    public static int sumPacketVersions(Packet packet) {
        int packetVersionSum = packet.packetVersion;

        for (Packet subPacket : packet.subPackets) {
            packetVersionSum += sumPacketVersions(subPacket);
        }

        return packetVersionSum;
    }

    public static String toBinaryString(String packet) {
        HashMap<String, String> hexToBinaryMap = new HashMap<String, String>();
        hexToBinaryMap.put("0", "0000");
        hexToBinaryMap.put("1", "0001");
        hexToBinaryMap.put("2", "0010");
        hexToBinaryMap.put("3", "0011");
        hexToBinaryMap.put("4", "0100");
        hexToBinaryMap.put("5", "0101");
        hexToBinaryMap.put("6", "0110");
        hexToBinaryMap.put("7", "0111");
        hexToBinaryMap.put("8", "1000");
        hexToBinaryMap.put("9", "1001");
        hexToBinaryMap.put("A", "1010");
        hexToBinaryMap.put("B", "1011");
        hexToBinaryMap.put("C", "1100");
        hexToBinaryMap.put("D", "1101");
        hexToBinaryMap.put("E", "1110");
        hexToBinaryMap.put("F", "1111");

        String returnString = "";
        for (String character : packet.split("")) {
            returnString += hexToBinaryMap.get(character);
        }
        return returnString;
    }

    public int sumAllPacketVersionNumbers(String input) {
        String binaryPacket = toBinaryString(input.trim());
        Packet packet = new Packet(binaryPacket);

        return sumPacketVersions(packet);
    }

    public static long evaluatePacketExpression(Packet packet) {
        if (packet.subPackets.size() == 0) {
            return packet.literalValue;
        } else {
            switch (packet.packetTypeId) {
                case 0:
                    // sum
                    long sum = 0;
                    for (Packet subPacket : packet.subPackets) {
                        sum += evaluatePacketExpression(subPacket);
                    }
                    return sum;
                case 1:
                    // product
                    long product = 1;
                    for (Packet subPacket : packet.subPackets) {
                        product *= evaluatePacketExpression(subPacket);
                    }
                    return product;
                case 2:
                    // minimum
                    long minimum = Long.MAX_VALUE;
                    for (Packet subPacket : packet.subPackets) {
                        long value = evaluatePacketExpression(subPacket);
                        if (value < minimum) {
                            minimum = value;
                        }
                    }
                    return minimum;
                case 3:
                    // maximum
                    long maximum = -1;
                    for (Packet subPacket : packet.subPackets) {
                        long value = evaluatePacketExpression(subPacket);
                        if (value > maximum) {
                            maximum = value;
                        }
                    }
                    return maximum;
                case 5:
                    // greater than
                    long firstSubPacketValueGt = evaluatePacketExpression(packet.subPackets.get(0));
                    long secondSubPacketValueGt = evaluatePacketExpression(packet.subPackets.get(1));
                    if (firstSubPacketValueGt > secondSubPacketValueGt) {
                        return 1L;
                    } else {
                        return 0L;
                    }
                case 6:
                    // less than
                    long firstSubPacketValueLt = evaluatePacketExpression(packet.subPackets.get(0));
                    long secondSubPacketValueLt = evaluatePacketExpression(packet.subPackets.get(1));
                    if (firstSubPacketValueLt < secondSubPacketValueLt) {
                        return 1L;
                    } else {
                        return 0L;
                    }
                case 7:
                    // equal to
                    long firstSubPacketValueEq = evaluatePacketExpression(packet.subPackets.get(0));
                    long secondSubPacketValueEq = evaluatePacketExpression(packet.subPackets.get(1));
                    if (firstSubPacketValueEq == secondSubPacketValueEq) {
                        return 1L;
                    } else {
                        return 0L;
                    }
                default:
                    System.out.println("something has gone horribly wrong");
                    return 0L;
            }
        }
    }

    public long evaluateTotalPacketExpression(String input) {
        String binaryPacket = toBinaryString(input.trim());
        Packet packet = new Packet(binaryPacket);

        return evaluatePacketExpression(packet);
    }

}