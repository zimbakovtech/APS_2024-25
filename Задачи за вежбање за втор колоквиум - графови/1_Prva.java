import java.util.*;

class AdjacencyListGraph<T> {
    private final Map<T, Set<T>> adjacencyList;

    public AdjacencyListGraph() {
        this.adjacencyList = new HashMap<>();
    }

    public void addVertex(T vertex) {
        if (!adjacencyList.containsKey(vertex))
            adjacencyList.put(vertex, new HashSet<>());
    }

    public void addEdge(T source, T destination) {
        addVertex(source);
        addVertex(destination);

        adjacencyList.get(source).add(destination);
    }

    public Set<T> getNeighbors(T vertex) {
        return adjacencyList.getOrDefault(vertex, new HashSet<>());
    }

    // DFS ima set "visited" koj gi sodrzi site teminja koi se poseteni od pocetno teme, sto znaci ribite od pocetnoto teme moze da odat do visited.size()-1 ezera
    public int DFS(T startVertex) {
        Set<T> visited = new HashSet<>();
        DFSUtil(startVertex, visited);
        return visited.size()-1;
    }

    private void DFSUtil(T vertex, Set<T> visited) {
        visited.add(vertex);

        for (T neighbor : getNeighbors(vertex)) {
            if (!visited.contains(neighbor)) {
                DFSUtil(neighbor, visited);
            }
        }
    }
}

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(), m = sc.nextInt();
        AdjacencyListGraph<Integer> graph = new AdjacencyListGraph<>();

        for(int i = 0; i < m; i++)
            graph.addEdge(sc.nextInt(), sc.nextInt());

        System.out.println(graph.DFS(sc.nextInt()));
    }
}
