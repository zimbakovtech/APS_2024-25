import java.util.Scanner;
import java.util.Iterator;
import java.util.NoSuchElementException;

interface Tree<E> {
    // //////////Accessors ////////////

    public Node<E> root();

    public Node<E> parent(Node<E> node);

    public int childCount(Node<E> node);

    // //////////Transformers ////////////
    public void makeRoot(E elem);

    public Node<E> addChild(Node<E> node, E elem);

    public void remove(Node<E> node);

    // //////////Iterator ////////////
    public Iterator<E> children(Node<E> node);

    // //////Inner interface for tree nodes ////////
    public interface Node<E> {

        public E getElement();

        public void setElement(E elem);

    }
}

class SLLTree<E> implements Tree<E> {

    // SLLNode is the implementation of the Node interface
    static class SLLNode<P> implements Tree.Node<P> {

        // Holds the links to the needed nodes
        SLLNode<P> parent, sibling, firstChild;

        // Hold the data
        P element;

        public SLLNode(P o) {
            element = o;
            parent = sibling = firstChild = null;
        }

        public P getElement() {
            return element;
        }

        public void setElement(P o) {
            element = o;
        }

    }

    protected SLLNode<E> root;

    public SLLTree() {
        root = null;
    }

    public Tree.Node<E> root() {
        return root;
    }

    public Tree.Node<E> parent(Tree.Node<E> node) {
        return ((SLLNode<E>) node).parent;
    }

    public int childCount(Tree.Node<E> node) {
        SLLNode<E> tmp = ((SLLNode<E>) node).firstChild;
        int num = 0;
        while (tmp != null) {
            tmp = tmp.sibling;
            num++;
        }
        return num;
    }

    public void makeRoot(E elem) {
        root = new SLLNode<E>(elem);
    }

    public Tree.Node<E> addChild(Tree.Node<E> node, E elem) {
        SLLNode<E> tmp = new SLLNode<E>(elem);
        SLLNode<E> curr = (SLLNode<E>) node;
        tmp.sibling = curr.firstChild;
        curr.firstChild = tmp;
        tmp.parent = curr;
        return tmp;
    }

    public void remove(Tree.Node<E> node) {
        SLLNode<E> curr = (SLLNode<E>) node;
        if (curr.parent != null) {
            if (curr.parent.firstChild == curr) {
                // The node is the first child of its parent
                // Reconnect the parent to the next sibling
                curr.parent.firstChild = curr.sibling;
            } else {
                // The node is not the first child of its parent
                // Start from the first and search the node in the sibling list
                // and remove it
                SLLNode<E> tmp = curr.parent.firstChild;
                while (tmp.sibling != curr) {
                    tmp = tmp.sibling;
                }
                tmp.sibling = curr.sibling;
            }
        } else {
            root = null;
        }
    }

    class SLLTreeIterator<T> implements Iterator<T> {

        SLLNode<T> start, current;

        public SLLTreeIterator(SLLNode<T> node) {
            start = node;
            current = node;
        }

        public boolean hasNext() {
            return (current != null);
        }

        public T next() throws NoSuchElementException {
            if (current != null) {
                SLLNode<T> tmp = current;
                current = current.sibling;
                return tmp.getElement();
            } else {
                throw new NoSuchElementException();
            }
        }

        public void remove() {
            if (current != null) {
                current = current.sibling;
            }
        }
    }

    public Iterator<E> children(Tree.Node<E> node) {
        return new SLLTreeIterator<E>(((SLLNode<E>) node).firstChild);
    }

}


public class Main {

    static int countLeaves(SLLTree.SLLNode<Integer> node) {
        if (node == null) {
            return 0;
        }
        if (node.firstChild == null) {
            return 1;
        }

        int leafCount = 0;
        
        SLLTree.SLLNode<Integer> curr = node.firstChild;
        while (curr != null) {
            leafCount += countLeaves(curr);
            curr = curr.sibling; 
        }

        return leafCount;
    }

    static SLLTree.SLLNode<Integer> findNode(SLLTree.SLLNode<Integer> root, int num) {
        if (root == null) return null;

        if (root.element == num) return root;

        SLLTree.SLLNode<Integer> result = findNode(root.firstChild, num);
        if (result != null) return result;

        return findNode(root.sibling, num);
    }


    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SLLTree<Integer> tree = new SLLTree<>();
        int n = sc.nextInt(), q = sc.nextInt();
        sc.next();
        tree.makeRoot(sc.nextInt());

        for (int i = 1; i < n+q; i++) {
            String s = sc.next();
            int num = sc.nextInt();
            SLLTree.SLLNode<Integer> current = findNode(tree.root, num);
            if(s.equals("add")) {
                int child = sc.nextInt();
                tree.addChild(current, child);
            } else {
                System.out.println(countLeaves(current));
            }
        }
    }
}