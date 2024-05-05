package src;

import java.util.*;

public class WordLadderSolver {
    private Set<String> dictionary;

    public WordLadderSolver(Set<String> dictionary) {
        this.dictionary = dictionary;
    }

    public List<String> findPath(String start, String end, String algorithm) {
        switch (algorithm.toLowerCase()) {
            case "ucs":
                return uniformCostSearch(start, end);
            case "greedy":
                return greedyBestFirstSearch(start, end);
            case "astar":
                return aStar(start, end);
            default:
                return new ArrayList<>(); 
        }
    }

    private List<String> uniformCostSearch(String start, String end) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(n -> n.cost));
        Map<String, Node> allNodes = new HashMap<>();
        Node startNode = new Node(start, null, 0);
        openSet.add(startNode);
        allNodes.put(start, startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.word.equals(end)) {
                return reconstructPath(current);
            }

            for (String neighbor : getNeighbors(current.word)) {
                int newCost = current.cost + 1;
                Node neighborNode = allNodes.get(neighbor);

                if (neighborNode == null || newCost < neighborNode.cost) {
                    if (neighborNode == null) {
                        neighborNode = new Node(neighbor, current, newCost);
                        allNodes.put(neighbor, neighborNode);
                        openSet.add(neighborNode);
                    } else {
                        openSet.remove(neighborNode);
                        neighborNode.parent = current;
                        neighborNode.cost = newCost;
                        openSet.add(neighborNode);
                    }
                }
            }
        }

        return new ArrayList<>(); 
    }

    private List<String> greedyBestFirstSearch(String start, String end) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(n -> heuristic(n.word, end)));
        Map<String, Node> allNodes = new HashMap<>();
        Node startNode = new Node(start, null, 0);
        openSet.add(startNode);
        allNodes.put(start, startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.word.equals(end)) {
                return reconstructPath(current);
            }

            for (String neighbor : getNeighbors(current.word)) {
                if (!allNodes.containsKey(neighbor)) {
                    Node neighborNode = new Node(neighbor, current, current.cost + 1);
                    openSet.add(neighborNode);
                    allNodes.put(neighbor, neighborNode);
                }
            }
        }

        return new ArrayList<>(); 
    }

    private List<String> aStar(String start, String end) {
        PriorityQueue<Node> openSet = new PriorityQueue<>(
                Comparator.comparingInt(n -> n.cost + heuristic(n.word, end)));
        Map<String, Node> allNodes = new HashMap<>();
        Node startNode = new Node(start, null, 0);
        openSet.add(startNode);
        allNodes.put(start, startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            if (current.word.equals(end)) {
                return reconstructPath(current);
            }

            for (String neighbor : getNeighbors(current.word)) {
                int newCost = current.cost + 1;
                Node neighborNode = allNodes.get(neighbor);

                if (neighborNode == null || newCost < neighborNode.cost) {
                    if (neighborNode == null) {
                        neighborNode = new Node(neighbor, current, newCost);
                        allNodes.put(neighbor, neighborNode);
                        openSet.add(neighborNode);
                    } else {
                        openSet.remove(neighborNode);
                        neighborNode.parent = current;
                        neighborNode.cost = newCost;
                        openSet.add(neighborNode);
                    }
                }
            }
        }

        return new ArrayList<>();
    }

    private List<String> getNeighbors(String word) {
        List<String> neighbors = new ArrayList<>();
        char[] chars = word.toCharArray();
        for (int i = 0; i < word.length(); i++) {
            char oldChar = chars[i];
            for (char c = 'a'; c <= 'z'; c++) {
                if (c == oldChar)
                    continue;
                chars[i] = c;
                String newWord = new String(chars);
                if (dictionary.contains(newWord)) {
                    neighbors.add(newWord);
                }
            }
            chars[i] = oldChar;
        }
        return neighbors;
    }

    private int heuristic(String word, String end) {
        int mismatchCount = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != end.charAt(i))
                mismatchCount++;
        }
        return mismatchCount;
    }

    private List<String> reconstructPath(Node node) {
        LinkedList<String> path = new LinkedList<>();
        while (node != null) {
            path.addFirst(node.word);
            node = node.parent;
        }
        return path;
    }

    static class Node {
        String word;
        Node parent;
        int cost; 
        Node(String word, Node parent, int cost) {
            this.word = word;
            this.parent = parent;
            this.cost = cost;
        }
    }
}
