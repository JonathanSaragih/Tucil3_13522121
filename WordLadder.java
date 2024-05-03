




import java.io.*;
import java.util.*;

public class WordLadder {
    public static void main(String[] args) {
        Scanner scanner = null;
        try {
            // Load the dictionary from a text file
            Set<String> dictionary = DictionaryLoader.loadDictionary("words_alpha.txt");

            scanner = new Scanner(System.in);
            System.out.println("--------------------------------------------------");
            System.out.println("Welcome to the Word Ladder Solver!");
            System.out.println("Type 'exit' at any prompt to quit the application.");
            System.out.println("--------------------------------------------------");

            while (true) {
                System.out.print("\nEnter start word or type 'exit' to quit: ");
                String start = scanner.nextLine().trim().toLowerCase();
                if (start.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting program. Thank you for using Word Ladder Solver!");
                    break;
                }

                System.out.print("Enter end word or type 'exit' to quit: ");
                String end = scanner.nextLine().trim().toLowerCase();
                if (end.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting program. Thank you for using Word Ladder Solver!");
                    break;
                }

                if (start.length() != end.length()) {
                    System.out.println("Error: Start and end words must be the same length. Please try again.");
                    continue;
                }

                System.out.println("\nAvailable Algorithms:");
                System.out.println("  1. UCS     - Uniform Cost Search");
                System.out.println("  2. Greedy  - Greedy Best First Search");
                System.out.println("  3. AStar   - A* Search");
                System.out.print("Choose algorithm by number or name (UCS, Greedy, AStar) or type 'exit' to quit: ");
                String algorithmInput = scanner.nextLine().trim();
                if (algorithmInput.equalsIgnoreCase("exit")) {
                    System.out.println("Exiting program. Thank you for using Word Ladder Solver!");
                    break;
                }

                String algorithm;
                switch (algorithmInput.toLowerCase()) {
                    case "1":
                    case "ucs":
                        algorithm = "ucs";
                        break;
                    case "2":
                    case "greedy":
                        algorithm = "greedy";
                        break;
                    case "3":
                    case "astar":
                        algorithm = "astar";
                        break;
                    default:
                        System.out.println(
                                "Invalid algorithm choice. Please choose a valid number (1, 2, or 3) or name (UCS, Greedy, AStar).");
                        continue;
                }

                WordLadderSolver solver = new WordLadderSolver(dictionary);
                long startTime = System.currentTimeMillis();
                List<String> path = solver.findPath(start, end, algorithm);
                long endTime = System.currentTimeMillis();

                if (path == null || path.isEmpty()) {
                    System.out.println("\nNo path found.");
                } else {
                    System.out.println("\nPath found: " + String.join(" -> ", path));
                    System.out.println("Nodes visited: " + path.size());
                    System.out.println("Time taken: " + (endTime - startTime) + " ms");
                }
            }

        } catch (IOException e) {
            System.out.println("Failed to load dictionary: " + e.getMessage());
        } finally {
            if (scanner != null) {
                scanner.close();
            }
        }
    }
}
