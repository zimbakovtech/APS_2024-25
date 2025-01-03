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
        String[] indeksi = new String[n]; // cuvame indeksi na site teminja/stringovi
        AdjacencyMatrixGraph<String> graph = new AdjacencyMatrixGraph<>(n);
        
        // dodavame teminja
        for(int i = 0; i < n; i++) {
            String current = sc.next();
            indeksi[i] = current;
            graph.addVertex(i, current);
        }

        int m = sc.nextInt();

        // dodavame rebra
        for(int i = 0; i < m; i++) {
            String source = sc.next();
            String destination = sc.next();
            int sourceIndex = 0, destinationIndex = 0;
            for(int j = 0; j < n; j++) {
                // ako go najdes dadeniot string vo nizata, j-ot e indeksot
                if(indeksi[j].equals(source))
                    sourceIndex = j;
                // istoto tuka
                if(indeksi[j].equals(destination))
                    destinationIndex = j;
            }
            // namesto stringovite, gi pustame indeksite za da dodademe rebro pomegju tie dve teminja
            // so sc.nextInt() dodavame tezina na rebroto
            graph.addEdge(sourceIndex, destinationIndex, sc.nextInt());
        }

        // na zavrseniot graf, pustame kruskal koj vrakja lista od rabovi
        List<Edge> rabovi = graph.kruskal();
        int sum = 0;

        // gi pominuvame site rebra od vratenata lista, i gi dodavame tezinite vo sum
        for(Edge e : rabovi)
            sum += e.getWeight();

        System.out.println(sum);
    }
}
