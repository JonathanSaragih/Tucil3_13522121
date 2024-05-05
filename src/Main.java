package src;
import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class Main {
    private static JTextField startWordField;
    private static JTextField endWordField;
    private static JComboBox<String> algorithmComboBox;
    private static JTextArea resultArea;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Word Ladder Solver");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(Color.WHITE); // Set background color

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.setBackground(Color.WHITE); // Set background color

        inputPanel.add(new JLabel("Start Word:"));
        startWordField = new JTextField();
        inputPanel.add(startWordField);

        inputPanel.add(new JLabel("End Word:"));
        endWordField = new JTextField();
        inputPanel.add(endWordField);

        inputPanel.add(new JLabel("Algorithm:"));
        String[] algorithms = { "UCS", "Greedy", "AStar" };
        algorithmComboBox = new JComboBox<>(algorithms);
        inputPanel.add(algorithmComboBox);

        JButton findPathButton = new JButton("Find Path");
        findPathButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                findPath();
            }
        });
        inputPanel.add(findPathButton);

        frame.add(inputPanel, BorderLayout.NORTH);

        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setBackground(Color.LIGHT_GRAY); // Set background color
        resultArea.setFont(new Font("Arial", Font.PLAIN, 12)); // Set font
        frame.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private static void findPath() {
        String startWord = startWordField.getText().trim().toLowerCase(); // Convert to lowercase
        String endWord = endWordField.getText().trim().toLowerCase(); // Convert to lowercase
        String algorithm = (String) algorithmComboBox.getSelectedItem();

        // Validate input
        if (!isValidWord(startWord) || !isValidWord(endWord) || startWord.length() != endWord.length()) {
            JOptionPane.showMessageDialog(null,
                    "Please enter valid start and end words (only alphabetical characters with the same length are allowed).",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Display "Searching..." message
        resultArea.setText("Searching...");

        // Solve Word Ladder problem
        try {
            long startTime = System.currentTimeMillis(); // Start time
            WordLadderSolver solver = new WordLadderSolver(DictionaryLoader.loadDictionary("test\\words_alpha.txt"));
            List<String> path = solver.findPath(startWord, endWord, algorithm);
            long endTime = System.currentTimeMillis(); // End time

            // Display result
            if (path.isEmpty()) {
                resultArea.append("\nNo path found.");
            } else {
                resultArea.append("\nPath found: " + String.join(" -> ", path) + "\n");
                resultArea.append("Nodes visited: " + path.size() + "\n");
            }

            // Display time taken
            resultArea.append("Time taken: " + (endTime - startTime) + " ms\n");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Failed to load dictionary: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private static boolean isValidWord(String word) {
        return word.matches("[a-z]+"); // Check if the word contains only alphabetical characters
    }
}

//javac src/Main.java src/WordLadderSolver.java src/DictionaryLoader.java
//java src.Main