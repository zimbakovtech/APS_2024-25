import java.util.*;
import java.io.BufferedReader;
import java.io.InputStreamReader;

class BNode<E> {
    public E info;
    public BNode<E> left;
    public BNode<E> right;

    static final int LEFT = 1;
    static final int RIGHT = 2;

    public BNode() {
    }
}

class BTree<E> {

    public BNode<E> root;

    public BTree() {
        root = null;
    }
    public void makeRootNode(BNode<E> node) {
        root = node;
    }

    public BNode<E> addChildNode(BNode<E> node, int where, BNode<E> tmp) {

        if (where == BNode.LEFT) {
            if (node.left != null)  // veke postoi element
                return null;
            node.left = tmp;
        } else {
            if (node.right != null) // veke postoi element
                return null;
            node.right = tmp;
        }

        return tmp;
    }

}

@SuppressWarnings("unchecked")

public class Main {

    static BNode<String> lowestCommonAncestor(BNode<String> root, String start, String end) {
        if (root == null)
            return null;

        if (root.info.equals(start) || root.info.equals(end))
            return root;

        BNode<String> left = lowestCommonAncestor(root.left, start, end);
        BNode<String> right = lowestCommonAncestor(root.right, start, end);

        if (left == null && right == null)
            return null;

        if (left == null)
            return right;
        if (right == null)
            return left;

        return root;
    }


    static int calculate(BNode<String> root, String target) {
        if(root == null)
            return -1;
        if (root.info.equals(target))
            return 0;

        int d1 = 2 + calculate(root.left, target);
        int d2 = 2 + calculate(root.right, target);

        if(d1 % 2 == 0)
            return d1;
        if(d2 % 2 == 0)
            return d2;

        return -1;
    }
    
    static int shortest(String from, String to, BTree<String> tree) {

        BNode<String> parent = lowestCommonAncestor(tree.root, from, to);

        return calculate(parent, from) + calculate(parent, to);
    }

    public static void main(String[] args) throws Exception{
        int i, j, k;
        int index;
        String action;

        String line;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        BNode<String>[] nodes = new BNode[N];
        BTree<String> tree = new BTree<String>();

        for (i=0;i<N;i++)
            nodes[i] = new BNode<String>();

        for (i = 0; i < N; i++) {
            line = br.readLine();
            st = new StringTokenizer(line);
            index = Integer.parseInt(st.nextToken());
            nodes[index].info = st.nextToken();
            action = st.nextToken();
            if (action.equals("LEFT")) {
                tree.addChildNode(nodes[Integer.parseInt(st.nextToken())], BNode.LEFT, nodes[index]);
            } else if (action.equals("RIGHT")) {
                tree.addChildNode(nodes[Integer.parseInt(st.nextToken())], BNode.RIGHT, nodes[index]);
            } else {
                // this node is the root
                tree.makeRootNode(nodes[index]);
            }
        }
        int cases = Integer.parseInt(br.readLine());
        for (int l = 0; l < cases; l++) {
            String split[] = br.readLine().split(" +");
            String from = split[0];
            String to = split[1];

            int value = shortest(from, to, tree);

            if(value < 0)
                System.out.println("ERROR");
            else
                System.out.println(value);
        }
    }
}
