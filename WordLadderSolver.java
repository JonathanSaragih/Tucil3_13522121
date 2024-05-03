





import java.util.*;

public class WordLadderSolver {
    private Set<String> dictionary;

    // Constructor yang menginisialisasi solver dengan dictionary yang diberikan
    public WordLadderSolver(Set<String> dictionary) {
        this.dictionary = dictionary;
    }

    // Metode ini menentukan algoritma yang akan digunakan berdasarkan input
    // pengguna
    public List<String> findPath(String start, String end, String algorithm) {
        switch (algorithm.toLowerCase()) {
            case "ucs":
                return uniformCostSearch(start, end);
            case "greedy":
                return greedyBestFirstSearch(start, end);
            case "astar":
                return aStar(start, end);
            default:
                return new ArrayList<>(); // Mengembalikan list kosong jika algoritma tidak dikenali
        }
    }

    // UCS mencari jalur dengan biaya terendah tanpa memperhatikan estimasi ke goal
    private List<String> uniformCostSearch(String start, String end) {
        // PriorityQueue untuk menjaga node dengan biaya terendah selalu di depan
        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(n -> n.cost));
        Map<String, Node> allNodes = new HashMap<>();
        Node startNode = new Node(start, null, 0);
        openSet.add(startNode);
        allNodes.put(start, startNode);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();

            // Jika kata saat ini adalah kata akhir, reconstruksi dan kembalikan path
            if (current.word.equals(end)) {
                return reconstructPath(current);
            }

            // Mengiterasi setiap neighbor dari kata saat ini
            for (String neighbor : getNeighbors(current.word)) {
                int newCost = current.cost + 1;
                Node neighborNode = allNodes.get(neighbor);

                // Cek jika node sudah ada dan apakah path baru lebih murah
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

        return new ArrayList<>(); // Mengembalikan list kosong jika tidak ada path yang ditemukan
    }

    // Greedy BFS menggunakan heuristic untuk memprioritaskan pencarian menuju goal
    private List<String> greedyBestFirstSearch(String start, String end) {
        // PriorityQueue yang memprioritaskan node berdasarkan heuristic mereka ke goal
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

        return new ArrayList<>(); // Mengembalikan list kosong jika tidak ada path yang ditemukan
    }

    // A* menggabungkan biaya sejauh ini dan heuristic ke goal untuk menentukan node
    // selanjutnya
    private List<String> aStar(String start, String end) {
        // PriorityQueue yang memprioritaskan node berdasarkan cost total (biaya +
        // heuristic)
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

        return new ArrayList<>(); // Mengembalikan list kosong jika tidak ada path yang ditemukan
    }

    // Menghasilkan semua possible neighbors dengan mengubah satu huruf pada satu
    // waktu
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

    // Heuristic function untuk Greedy dan A*: menghitung jumlah karakter yang
    // berbeda
    private int heuristic(String word, String end) {
        int mismatchCount = 0;
        for (int i = 0; i < word.length(); i++) {
            if (word.charAt(i) != end.charAt(i))
                mismatchCount++;
        }
        return (int) ((double) mismatchCount / word.length() * 100); // Skala mismatch count
    }

    // Rekonstruksi path dari end ke start berdasarkan node parent
    private List<String> reconstructPath(Node node) {
        LinkedList<String> path = new LinkedList<>();
        while (node != null) {
            path.addFirst(node.word);
            node = node.parent;
        }
        return path;
    }

    // Inner class untuk menyimpan informasi tentang setiap node dalam pencarian
    static class Node {
        String word;
        Node parent;
        int cost; // Biaya dari awal hingga node ini, untuk UCS dan A* (g)

        Node(String word, Node parent, int cost) {
            this.word = word;
            this.parent = parent;
            this.cost = cost;
        }
    }
}
