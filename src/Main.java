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
        frame.getContentPane().setBackground(Color.WHITE);

        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        inputPanel.setBackground(Color.WHITE);

        inputPanel.add(new JLabel("Start Word:"));
        startWordField = new JTextField();
        inputPanel.add(startWordField);

        inputPanel.add(new JLabel("End Word:"));
        endWordField = new JTextField();
        inputPanel.add(endWordField);

        inputPanel.add(new JLabel("Algorithm:"));
        String[] algorithms = { "UCS", "Greedy Best First Search", "AStar" };
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
        resultArea.setBackground(Color.LIGHT_GRAY);
        resultArea.setFont(new Font("Arial", Font.PLAIN, 12));
        frame.add(new JScrollPane(resultArea), BorderLayout.CENTER);

        frame.setVisible(true);
    }

    private static void findPath() {
        String startWord = startWordField.getText().trim().toLowerCase();
        String endWord = endWordField.getText().trim().toLowerCase();
        String algorithm = (String) algorithmComboBox.getSelectedItem();

        if (!isValidWord(startWord) || !isValidWord(endWord) || startWord.length() != endWord.length()) {
            JOptionPane.showMessageDialog(null,
                    "Please enter valid start and end words (only alphabetical characters with the same length are allowed).",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        resultArea.setText("Searching...");

        try {
            long startTime = System.currentTimeMillis();
            WordLadderSolver solver = new WordLadderSolver(DictionaryLoader.loadDictionary("src\\words.txt"));
            List<String> path = solver.findPath(startWord, endWord, algorithm);
            long endTime = System.currentTimeMillis();

            if (path.isEmpty()) {
                resultArea.setText("No path found.");
            } else {
                resultArea.setText("Path found: " + String.join(" -> ", path) + "\n");
            }
            resultArea.append("Nodes visited: " + solver.getNodesVisited() + "\n");
            resultArea.append("Time taken: " + (endTime - startTime) + " ms\n");
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Failed to load dictionary: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private static boolean isValidWord(String word) {
        return word.matches("[a-z]+");
    }
}
