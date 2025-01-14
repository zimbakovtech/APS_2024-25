import java.util.*;

class BNode<E> {

    public E info;
    public BNode<E> left;
    public BNode<E> right;

    static int LEFT = 1;
    static int RIGHT = 2;

    public BNode<E> parent;
    public BNode(E info, BNode<E> parent) {
        this.parent = parent;
        this.info = info;
        left = null;
        right = null;
    }
    public BNode(E info) {
        this.parent = null;
        this.info = info;
        left = null;
        right = null;
    }
}


class BTree<E> {

    public BNode<E> root;

    public BTree() {
        root = null;
    }

    public void makeRoot(E elem) {
        root = new BNode<E>(elem);
    }

    public BNode<E> addChild(BNode<E> node, int where, E elem) {

        BNode<E> tmp = new BNode<E>(elem, node);

        if (where == BNode.LEFT) {
            if (node.left != null)  // Left child already exists
                return null;
            node.left = tmp;
        } else {
            if (node.right != null) // Right child already exists
                return null;
            node.right = tmp;
        }

        return tmp;
    }
}



public class Main {

    static BNode<String> findNode(BNode<String> root, String value) {
        if(root == null)
            return null;

        if(root.info.equals(value))
            return root;

        BNode<String> levo = findNode(root.left, value);

        if(levo != null)
            return levo;
        return findNode(root.right, value);
    }

    static int count(BNode<String> root) {
        if(root == null)
            return 0;
        if(root.left != null && root.right != null)
            return 1 + count(root.left) + count(root.right);
        if(root.left != null)
            return count(root.left);
        if(root.right != null)
            return count(root.right);
        return 0;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt(), q = sc.nextInt();
        BTree<String> tree = new BTree<>();
        sc.next();
        tree.makeRoot(sc.next());
        for(int i = 1; i < n + q; i++) {
            String s = sc.next();
            String parent = sc.next();
            if(s.equals("add")) {
                String child = sc.next();
                String where = sc.next();
                if(where.equals("LEFT"))
                    tree.addChild(findNode(tree.root, parent), 1, child);
                else
                    tree.addChild(findNode(tree.root, parent), 2, child);
            }
            else {
                System.out.println(count(findNode(tree.root, parent)));
            }
        }
    }
}