import java.util.*;

class AdjacencyListGraph<T> {
    private final Map<T, Set<T>> adjacencyList;

    public AdjacencyListGraph() {
        this.adjacencyList = new HashMap<>();
    }

    public void addVertex(T vertex) {
        if (!adjacencyList.containsKey(vertex)) {
            adjacencyList.put(vertex, new HashSet<>());
        }
    }

    public void addEdge(T source, T destination) {
        addVertex(source);
        addVertex(destination);

        adjacencyList.get(source).add(destination);
        adjacencyList.get(destination).add(source); // for undirected graph
    }

    public Set<T> getNeighbors(T vertex) {
        return adjacencyList.getOrDefault(vertex, new HashSet<>());
    }

    private void DFSUtil(T vertex, Set<T> visited) {
        // Mark the current node as visited and print it
        visited.add(vertex);
        //System.out.print(vertex + " ");

        // Recur for all the vertices adjacent to this vertex
        for (T neighbor : getNeighbors(vertex)) {
            if (!visited.contains(neighbor)) {
                DFSUtil(neighbor, visited);
            }
        }
    }

    public void countGroups() {
        Set<T> visited = new HashSet<>();
        int counter = 0;

        for( T s: adjacencyList.keySet()) {
            if (!visited.contains(s)){
                counter++;
                DFSUtil(s, visited);
            }
        }
        System.out.println(counter);
    }
}

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();

        AdjacencyListGraph<String> graph = new AdjacencyListGraph<>();

        for (int i = 0; i < n; i++) {
            String node = sc.next();
            graph.addVertex(node);
        }

        int m = sc.nextInt();

        for (int i = 0; i < m; i++)
            graph.addEdge(sc.next(), sc.next());

        graph.countGroups();
    }
}