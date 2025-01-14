import java.util.*;

class BNode<E extends Comparable<E>> {

    public E info;
    public BNode<E> left;
    public BNode<E> right;

    public BNode(E info) {
        this.info = info;
        left = null;
        right = null;
    }

    public BNode(E info, BNode<E> left, BNode<E> right) {
        this.info = info;
        this.left = left;
        this.right = right;
    }

}

class BinarySearchTree<E extends Comparable<E>> {
    private BNode<E> root;

    public BinarySearchTree() {
        root = null;
    }
    public void insert(E x) {
        root = insert(x, root);
    }
    
    public BNode<E> find(E x) {
        return find(x, root);
    }
    private BNode<E> insert(E x, BNode<E> t) {
        if (t == null) {
            t = new BNode<E>(x, null, null);
        } else if (x.compareTo(t.info) < 0) {
            t.left = insert(x, t.left);
        } else if (x.compareTo(t.info) > 0) {
            t.right = insert(x, t.right);
        } else ;  // Duplicate; do nothing
        return t;
    }
    
    private BNode<E> find(E x, BNode<E> t) {
        if (t == null)
            return null;

        if (x.compareTo(t.info) < 0) {
            return find(x, t.left);
        } else if (x.compareTo(t.info) > 0) {
            return find(x, t.right);
        } else {
            return t;    // Match
        }
    }
    
    public BNode<E> getRoot() {
        return root;
    }
}

public class Main {

    static int depth(BNode<Integer> root, BNode<Integer> node) {
        if(root == null)
            return -200;
        if(root == node)
            return 1;
        return Math.max(1 + depth(root.left, node), 1 + depth(root.right, node));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(), m = sc.nextInt();
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();

        for(int i = 0; i < n + m; i++) {
            String input = sc.next();
            int value = sc.nextInt();
            if(input.equals("insert"))
                bst.insert(value);
            else
                System.out.println(depth(bst.getRoot(), bst.find(value)));
        }
    }
}