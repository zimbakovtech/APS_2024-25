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

                    if (!explored.contains(neighbor))
                        queue.add(neighbor);
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
        String[] keys = new String[n];
        Map<String, String> dictionary = new HashMap<>();
        AdjacencyListGraph<String> graph = new AdjacencyListGraph<>();

        for(int i = 0; i < n; i++) {
            String currentSource = sc.next(), currentDestination = sc.next();
            dictionary.put(currentSource, currentDestination);
            keys[i] = currentSource;
        }

        int m = sc.nextInt(), sum = 0;

        for(int i = 0; i < m; i++) {
            String source = sc.next();
            String destination = sc.next();
            graph.addEdge(source, destination, sc.nextInt());
        }

        for(int i = 0; i < n; i++) {
            String source = keys[i];
            String destination = dictionary.get(source);
            Map<String, Integer> map = graph.shortestPath(source);
            sum += map.get(destination);
        }

        System.out.println(sum);
    }
}