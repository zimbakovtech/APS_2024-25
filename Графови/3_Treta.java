import java.util.*;

class AdjacencyListGraph<T> {
    private Map<T, Set<T>> adjacencyList;

    public AdjacencyListGraph() {
        this.adjacencyList = new HashMap<>();
    }

    // Add a vertex to the graph
    public void addVertex(T vertex) {
        if (!adjacencyList.containsKey(vertex)) {
            adjacencyList.put(vertex, new HashSet<>());
        }
    }

    // Remove a vertex from the graph
    public void removeVertex(T vertex) {
        // Remove the vertex from all adjacency lists
        for (Set<T> neighbors : adjacencyList.values()) {
            neighbors.remove(vertex);
        }
        // Remove the vertex's own entry in the adjacency list
        adjacencyList.remove(vertex);
    }

    // Add an edge to the graph
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
        visited.add(vertex);

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
        AdjacencyListGraph<Integer> graph = new AdjacencyListGraph<>();
        int n = sc.nextInt(), m = sc.nextInt();

        for(int i = 0; i < m; i++)
            graph.addEdge(sc.nextInt(), sc.nextInt());

        graph.removeVertex(sc.nextInt());

        graph.countGroups();
    }
}