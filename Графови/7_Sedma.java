import java.util.*;

class AdjacencyListGraph<T> {
    private Map<T, Map<T, Integer>> adjacencyList;

    public AdjacencyListGraph() {
        this.adjacencyList = new HashMap<>();
    }

    public void addVertex(T vertex) {
        if (!adjacencyList.containsKey(vertex)) {
            adjacencyList.put(vertex, new HashMap<>());
        }
    }
    
    public void addEdge(T source, T destination, int weight) {
        addVertex(source);
        addVertex(destination);

        adjacencyList.get(source).put(destination, weight);
        adjacencyList.get(destination).put(source, weight); // for undirected graph
    }

    public void removeEdge(T source, T destination) {
        if (adjacencyList.containsKey(source)) {
            adjacencyList.get(source).remove(destination);
        }
        if (adjacencyList.containsKey(destination)) {
            adjacencyList.get(destination).remove(source); // for undirected graph
        }
    }

    public Map<T, Integer> getNeighbors(T vertex) {
        return adjacencyList.getOrDefault(vertex, new HashMap<>());
    }

    public Map<T, Integer> shortestPath(T startVertex) {
        Map<T, Integer> distances = new HashMap<>();
        PriorityQueue<T> queue = new PriorityQueue<>(Comparator.comparingInt(distances::get));
        Set<T> explored = new HashSet<>();

        for (T vertex : adjacencyList.keySet()) {
            distances.put(vertex, Integer.MAX_VALUE);
        }
        distances.put(startVertex, 0);

        queue.add(startVertex);

        while (!queue.isEmpty()) {
            T current = queue.poll();
            explored.add(current);

            for (Map.Entry<T, Integer> neighborEntry : adjacencyList.get(current).entrySet()) {
                T neighbor = neighborEntry.getKey();
                int newDist = distances.get(current) + neighborEntry.getValue();

                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    if (!explored.contains(neighbor)) {
                        queue.add(neighbor);
                    }
                }
            }
        }
        return distances;
    }
}

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        AdjacencyListGraph<String> graph = new AdjacencyListGraph<>();

        graph.addVertex("Start");

        for(int i = 0; i < n; i++)
            graph.addEdge("Start", sc.next(), sc.nextInt());

        int m = sc.nextInt(), max = Integer.MIN_VALUE;

        if(m != 0) {
            String first_word = sc.next(), next_word = "";
            for (int i = 0; i < m; i++) {
                String second_word = sc.next();
                if (i + 1 != m) {
                    next_word = sc.next();
                    while (next_word.equals(first_word)) {
                        String current = sc.next();
                        if (graph.getNeighbors("Start").get(current) > graph.getNeighbors("Start").get(second_word))
                            second_word = current;
                        i++;
                        if(i+1 == m)
                            break;
                        next_word = sc.next();
                    }
                }
                int value = graph.getNeighbors("Start").get(first_word);
                graph.removeEdge("Start", first_word);
                graph.addEdge("Start", first_word, value + graph.getNeighbors("Start").get(second_word));
                first_word = next_word;
            }
        }

        Map<String, Integer> map = graph.shortestPath("Start");

        for(Map.Entry<String, Integer> entry : map.entrySet())
            if(entry.getValue() > max) max = entry.getValue();

        System.out.println(max);
    }
}