import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

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

        System.out.println("Part 1: " + new App().trickShotMaxHeight(input));
        System.out.println("Part 2: " + new App().countAllOnTargetVelocities(input));
    }

    public int trickShotMaxHeight(String input) {
        // String xrange = input.substring(13).split(", ")[0];
        String yrange = input.substring(13).split(", ")[1];

        // int xmin = Integer.parseInt(xrange.substring(2).split("\\.\\.")[0]);
        // int xmax = Integer.parseInt(xrange.substring(2).split("\\.\\.")[1]);
        int ymin = Integer.parseInt(yrange.substring(2).split("\\.\\.")[0]);
        // int ymax = Integer.parseInt(yrange.substring(2).split("\\.\\.")[1]);

        // Sum of all integers from 1 to (|ymin| - 1)
        return ((Math.abs(ymin) - 1)*(Math.abs(ymin) - 1 + 1))/2;
    }

    public int countAllOnTargetVelocities(String input) {
        String xrange = input.substring(13).split(", ")[0];
        String yrange = input.substring(13).split(", ")[1];

        int xmin = Integer.parseInt(xrange.substring(2).split("\\.\\.")[0]);
        int xmax = Integer.parseInt(xrange.substring(2).split("\\.\\.")[1]);
        int ymin = Integer.parseInt(yrange.substring(2).split("\\.\\.")[0]);
        int ymax = Integer.parseInt(yrange.substring(2).split("\\.\\.")[1]);

        // x constraint: 1 <= x <= xmax (include xmax)
        // y constraint: ymin <= y <= |ymin| - 1

        // holds step and velocities that will place it in the target during that step
        HashMap<Integer, ArrayList<Integer>> xStepMap = new HashMap<Integer, ArrayList<Integer>>();
        HashMap<Integer, ArrayList<Integer>> yStepMap = new HashMap<Integer, ArrayList<Integer>>();

        // holds the step that x will be in range from that t forward
        // HashMap<Integer, ArrayList<Integer>> xConvergeMap = new HashMap<Integer, ArrayList<Integer>>();
        ArrayList<Integer> xConvergeList = new ArrayList<Integer>();

        for (int x = 1; x <= xmax; x++) {
            int sum = ((x*(x + 1))/2);
            if (sum >= xmin && sum <= xmax) {
                xConvergeList.add(x);
                // This converges to the target range after x steps
            }
            if (sum > xmax) {
                break;
            }
        }

        // So there's a bug here, where x velocities that converge will never break the loop
        // however, drag will continue to reduce sum past 0, until it underflows, 
        // changing sum to a positive value where it'll successfully break the loop
        for (int x = 0; x <= xmax; x++) {
            int sum = 0;
            for (int t = 0; ;t++) {
                sum += x - t;
                if (sum >= xmin && sum <= xmax) {
                    if (xStepMap.containsKey(t + 1)) {
                        ArrayList<Integer> arrayList = xStepMap.get(t + 1);
                        arrayList.add(x);
                        xStepMap.put(t + 1, arrayList);
                    } else {
                        ArrayList<Integer> arrayList = new ArrayList<Integer>();
                        arrayList.add(x);
                        xStepMap.put(t + 1, arrayList);
                    }
                } else if (sum > xmax) {
                    break;
                }
            }
        }


        for (int y = ymin; y <= Math.abs(ymin) - 1; y++) {
            int sum = 0;
            int velocity = -Math.abs(y);
            int t = 0;
            if (y >= 0) {
                t = y*2 + 1;
                velocity--;
            }

            for ( ; ;t++) {
                sum += velocity;
                velocity--;
                if (sum >= ymin && sum <= ymax) {
                    if (yStepMap.containsKey(t + 1)) {
                        ArrayList<Integer> arrayList = yStepMap.get(t + 1);
                        arrayList.add(y);
                        yStepMap.put(t + 1, arrayList);
                    } else {
                        ArrayList<Integer> arrayList = new ArrayList<Integer>();
                        arrayList.add(y);
                        yStepMap.put(t + 1, arrayList);
                    }
                } else if (sum < ymin) {
                    sum = 0;
                    break;
                }
            }
        }

        HashSet<String> velocities = new HashSet<String>();
        for (int t: yStepMap.keySet()) {
            if (xStepMap.containsKey(t)) {
                ArrayList<Integer> xList = xStepMap.get(t);
                ArrayList<Integer> yList = yStepMap.get(t);
                for (int x: xList) {
                    for (int y: yList) {
                        velocities.add(x + "," + y);
                    }
                }
            }

            for (int x: xConvergeList) {
                if (t >= x){
                    ArrayList<Integer> yList = yStepMap.get(t);
                    for (int y: yList) {
                        velocities.add(x + "," + y);
                    }
                }
            }

        }

        return velocities.size();
    }

}