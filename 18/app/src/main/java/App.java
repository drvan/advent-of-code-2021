import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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

        System.out.println("Part 1: " + new App().magnitudeFinalSum(input));
        System.out.println("Part 2: " + new App().largestMagnitudeAnyTwo(input));
    }

    class Node {
        Node x = null;
        Node y = null;
        Node parent = null;
        Integer value = null;

        Node(String input, Node parent) {
            this.parent = parent;
            if (input.matches("^\\d+$")) {
                this.value = Integer.parseInt(input);
                return;
            }

            // if the first character is an open bracket, remove it and the corresponding closing bracket
            if (input.charAt(0) == '[') {
                input = input.substring(1, input.length() - 1);
            }
            
            // Find the comma on the same level
            int splitPosition = -1;
            int nestDepth = 0;
            for (int i = 0; i < input.length(); i++) {
                char character = input.charAt(i);
                switch (character) {
                    case '[':
                        nestDepth++;
                        break;
                    case ']':
                        nestDepth--;
                        break;
                    case ',':
                        if (nestDepth == 0) {
                            splitPosition = i;
                        }
                    default:
                        break;
                }
            }

            this.x = new Node(input.substring(0, splitPosition), this);
            this.y = new Node(input.substring(splitPosition + 1), this);
        }

        public String toString() {
            if (value != null) {
                return value.toString();
            } else {
                return "[" + x.toString() + "," + y.toString() + "]";
            }
        }
    }

    public boolean explode(Node node, int depth) {
        // traverse tree, explode node, and return true if explode happened, false if none left to explode
        if ((node.x != null) && (node.y != null)) {
            if (explode(node.x, depth + 1)) {
                return true;
            }
            if (explode(node.y, depth + 1)) {
                return true;
            };

            if ((node.x.value != null) && (node.y.value != null) && (depth >= 4)) {
                // I'm at a node where left and right can be exploded

                addToLeft(node.x, node.x.value);
                addToRight(node.y, node.y.value);
                node.x = null;
                node.y = null;
                node.value = 0;

                return true;
            }

        }
        return false;
    }

    public boolean split(Node node) {
        // traverse tree, split first node encountered that meets split criteria, return true if split occured
        if ((node.x != null) && (node.y != null)) {
            if (split(node.x)) {
                return true;
            }
            if (split(node.y)) {
                return true;
            }

        } else {
            if (node.value >= 10) {
                node.x = new Node(String.valueOf((int)(Math.floor(((double)node.value)/2))), node);
                node.y = new Node(String.valueOf((int)(Math.ceil(((double)node.value)/2))), node);
                node.value = null;
    
                return true;
            }
        }

        return false;
    }

    public boolean addToLeft(Node node, int value) {
        // Traverse up until no more parents or there is a left node available
        while (node.parent != null) {
            if (node.parent.y == node) {
                // i am the right child 
                node = node.parent.x;

                // now traverse down to the right most node
                while (node.value == null) {
                    node = node.y;
                }
        
                node.value += value;
                return true;
            } 
            node = node.parent;
        }

        return false;
    }

    public boolean addToRight(Node node, int value) {
        // Traverse up until no more parents or there is a right node available
        while (node.parent != null) {
            if (node.parent.x == node) {
                // i am the left child 
                node = node.parent.y;

                // now traverse down to the left most node
                while (node.value == null) {
                    node = node.x;
                }
        
                node.value += value;
                return true;
            } 
            node = node.parent;
        }

        return false;
    }

    public int magnitude(Node node) {
        if (node.x == null && node.y == null) {
            return node.value;
        } else {
            return (3*magnitude(node.x)) + (2*magnitude(node.y));
        }

    }

    public Node reduce(Node node) {
        boolean done = false;
        while (!done) {
            boolean exploded = explode(node, 0);
            if (exploded) {
                continue;
            } else {
                boolean split = split(node);
                if (!split) {
                    break;
                }
            }
        }

        return node;
    }

    public int magnitudeFinalSum(String input) {
        String[] lines = input.split("\n");

        Node node = new Node("[" + lines[0].trim() + "," + lines[1].trim() + "]", null);;
        node = reduce(node);

        for (int i = 2; i < lines.length; i++) {
            node = new Node("[" + node + "," + lines[i].trim() + "]", null);
            node = reduce(node);
        }

        return magnitude(node);
    }

    public int largestMagnitudeAnyTwo(String input) {
        String[] lines = input.split("\n");

        int highestMagnitude = 0;
        for (int i = 0; i < lines.length; i++) {
            for (int j = 0; j < lines.length; j++) {
                if (i == j) continue;
                Node node = new Node("[" + lines[i].trim() + "," + lines[j].trim() + "]", null);
                node = reduce(node);
                int currentMagnitude = magnitude(node);
                if (currentMagnitude > highestMagnitude) {
                    highestMagnitude = currentMagnitude;
                }
            }
        }

        return highestMagnitude;
    }

}