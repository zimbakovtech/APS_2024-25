import java.util.*;

class Edge {
    private int fromVertex, toVertex;
    private int weight;
    public Edge(int from, int to, int weight) {
        this.fromVertex = from;
        this.toVertex = to;
        this.weight = weight;
    }

    public int getFrom() {
        return this.fromVertex;
    }
    public int getTo() {
        return this.toVertex;
    }
    public int getWeight() {
        return this.weight;
    }
}

class AdjacencyMatrixGraph<T> {
    private int numVertices;
    private int[][] matrix;
    private T[] vertices;

    @SuppressWarnings("unchecked")
    public AdjacencyMatrixGraph(int numVertices) {
        this.numVertices = numVertices;
        matrix = new int[numVertices][numVertices];
        for(int i=0;i<numVertices;i++) {
            for(int j=0;j<numVertices;j++) {
                matrix[i][j] = 0;
            }
        }
        vertices = (T[]) new Object[numVertices];
    }

    public void addVertex(int index, T data) {
        vertices[index] = data;
    }

    public void addEdge(int source, int destination, int weight) {
        matrix[source][destination] = weight;
    }

    public boolean isEdge(int source, int destination) {
        return matrix[source][destination] !=0;
    }

    private List<Edge> getAllEdges() {
        List<Edge> edges = new ArrayList<>();

        for(int i=0;i<numVertices;i++) {
            for(int j=0;j<numVertices;j++) {
                if(isEdge(i,j)) {
                    edges.add(new Edge(i, j, matrix[i][j]));
                }
            }
        }

        return edges;
    }

    private void union(int u, int v, int[] trees) {
        int findWhat, replaceWith;
        if(u<v) {
            findWhat = trees[v];
            replaceWith = trees[u];
        } else {
            findWhat = trees[u];
            replaceWith = trees[v];
        }

        for(int i=0;i<trees.length;i++) {
            if(trees[i] == findWhat) {
                trees[i] = replaceWith;
            }
        }
    }

    public List<Edge> kruskal() {
        List<Edge> mstEdges = new ArrayList<>();
        List<Edge> allEdges = getAllEdges();

        allEdges.sort(Comparator.comparingInt(Edge::getWeight));

        int trees[] = new int[numVertices];

        for(int i=0;i<numVertices;i++)
            trees[i] = i;

        for(Edge e: allEdges) {
            if(trees[e.getFrom()] != trees[e.getTo()]) {
                mstEdges.add(e);

                union(e.getFrom(), e.getTo(), trees);
            }
        }

        return mstEdges;
    }
}

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        String[] indeksi = new String[n];
        AdjacencyMatrixGraph<String> graph = new AdjacencyMatrixGraph<>(n);

        for(int i = 0; i < n; i++) {
            String current = sc.next();
            indeksi[i] = current;
            graph.addVertex(i, current);
        }

        int m = sc.nextInt();

        for(int i = 0; i < m; i++) {
            String pocetnoTeme = sc.next(); // Vnesuvame pocetno teme
            String krajnoTeme = sc.next(); // Vnsuvame krajno teme
            int weight = sc.nextInt(); // Vnesuvame tezina na rebro megju niv
            int pocetnoTemeIndex = 0, krajnoTemeIndex = 0;
            for(int j = 0; j < n; j++) {
                if(indeksi[j].equals(pocetnoTeme))
                    pocetnoTemeIndex = j; // ako go najdes temeto, j-ot e negoviot indeks
                if(indeksi[j].equals(krajnoTeme))
                    krajnoTemeIndex = j; // ako go najdes temeto, j-ot e negoviot indeks
            }
            // dodavame rebro:
            graph.addEdge(pocetnoTemeIndex, krajnoTemeIndex, weight);
        }

        List<Edge> rebra = graph.kruskal(); // vrakja niza od rebra
        int suma = 0;

        for(Edge rebro : rebra) // za sekoe rebro od vratenite rebra
            suma += rebro.getWeight(); // za sekoe rebro dodadi mu ja tezinata na sumata

        System.out.println(suma);
    }
}
