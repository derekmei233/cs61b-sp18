package Maps;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.util.NoSuchElementException;

/**
 * reimplement of BST
 * source: https://algs4.cs.princeton.edu/32bst/BST.java.html
 */
public class BSTMap<K extends Comparable<K>, V> {
    // bc: key == null
    // default operation for key == root
    private Node root;

    private class Node {
        private K key;
        private V val;
        private Node left, right;
        private int size;

        public Node(K key, V val, int size) {
            this.key = key;
            this.val = val;
            this.size = size;
        }
    }
    public BSTMap() {}

    // size operations
    public boolean isEmpty() {
        return size() == 0;
    }
    public int size() {
        return size(root);
    }
    public int size(Node x) {
        if (x == null) return 0;
        else return x.size;
    }

    // get and contains operations
    public boolean contains(K key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != null;
    }
    public V get(K key) {
        return get(root, key);
    }
    private V get(Node n, K key) {
        if (key == null) throw new IllegalArgumentException("calls get() with a null key");
        if (n == null) return null;
        int cmp = key.compareTo(n.key);
        if (cmp < 0) return get(n.left, key);
        if (cmp > 0) return get(n.right, key);
        else return n.val;
    }
    // put operations
    public void put(K key, V val) {
        if (key == null) throw new IllegalArgumentException("calls put() with a null key");
        if (val == null) {
            delete(key);
            return;
        }
        root = put(root, key, val);
        assert check();
    }
    private Node put(Node n, K key, V val) {
        // revise nodes after put
        if (n == null) return new Node(key, val, 1);
        int cmp = key.compareTo(n.key);
        if (cmp < 0) n.left = put(n.left, key, val);
        if (cmp > 0) n.right = put(n.right, key, val);
        else n.val = val;
        n.size = 1 + size(n.left) + size(n.right);
        return n;
    }
    // delete operation
    public void deleteMin() {
        if (isEmpty()) throw new NoSuchElementException("Symbol table underflow");
        root = deleteMin(root);
        assert check();
    }
    private Node deleteMin(Node n) {
        if (n.left == null) return n.right;
        n.left = deleteMin(n.left);
        n.size = 1 + size(n.left) + size(n.right);
        return n;
    }
    public void deleteMax() {
        if (isEmpty()) throw new NoSuchElementException("symbol table underflow");
        root = deleteMax(root);
        assert check();
    }
    private Node deleteMax(Node n) {
        if (n.right == null) return n.left;
        n.right = deleteMax(n.right);
        n.size = 1 + size(n.left) + size(n.right);
        return n;
    }
    public void delete(K key) {
        if (key == null) throw new IllegalArgumentException("calls delete with a null key");
        root = delete(root, key);
        assert check();
    }
    private Node delete(Node n, K key) {
        if (n == null) return null;
        int cmp = n.key.compareTo(key);
        if (cmp < 0) n.left = delete(n.left, key);
        else if (cmp > 0) n.right = delete(n.right, key);
        else {
            Node x = n;
            n = min(x.right);
            n.right = deleteMin(x.right);
        }
        n.size = 1 + size(n.left) + size(n.right);
        return n;
    }
    // min and max operations
    public K min() {
        if (isEmpty()) throw new NoSuchElementException("call min() with empty symbol table");
        return min(root).key;
    }
    private Node min(Node n) {
        if (n.left == null) return n;
        else return min(n.left);
    }
    public K max() {
        if (isEmpty()) throw new NoSuchElementException("call max() with empty symbol table");
        return max(root).key;
    }
    private Node max(Node n) {
        if (n.right == null) return n;
        else return max(n.right);
    }
    // floor and ceiling operations
    public K floor(K key) {
        if (key == null) throw new IllegalArgumentException("argument to floor() is null");
        if (isEmpty()) throw new NoSuchElementException("calls floor()with empty symbol table");
        Node x = floor(root, key);
        if (x == null) throw new NoSuchElementException("argument to floor() is too small");
        else return x.key;
    }
    private Node floor(Node n, K key) {
        // find n.key just smaller than key
        if (n == null) return null;
        int cmp = key.compareTo(n.key);
        if (cmp == 0) return n;
        if (cmp < 0) return floor(n.left, key);
        Node t = floor(n.right, key);
        if (t != null) return t;
        else return n;
    }
    public K floor2(K key) {
        K x = floor2(root, key, null);
        if (x == null) throw new NoSuchElementException("argument to floor() is too small");
        else return x;
    }
    private K floor2(Node n, K key, K best) {
        if (n == null) return null;
        int cmp = key.compareTo(n.key);
        if (cmp == 0) return n.key;
        else if (cmp < 0) return floor2(n.left, key, best);
        else return floor2(n.right, key, n.key);
    }
    // find key just larger than key
    public K ceiling(K key) {
        if (key == null) throw new IllegalArgumentException("argument to ceiling is null");
        if (isEmpty()) throw new NoSuchElementException("calls ceiling() with enpty symbol table");
        Node x = ceiling(root, key);
        if (x == null) throw new NoSuchElementException("argument to floor() is too large");
        else return x.key;
    }
    private Node ceiling(Node n, K key) {
        if (n == null) return null;
        int cmp = key.compareTo(n.key);
        if (cmp == 0) return n;
        if (cmp < 0) {
            Node t = ceiling(n.left, key);
            if (t != null) return t;
            else return n;
        }
        return ceiling(n.right, key);
    }
    public K select(int rank) {
        if (rank < 0 || rank >= size()) {
            throw new IllegalArgumentException("argument to select() is invalid" + rank);
        }
        return select(root, rank);
    }
    private K select(Node n, int rank) {
        if (n == null) return null;
        int leftSize = size(n.left);
        if (leftSize > rank) return select(n.left, rank);
        else if (leftSize < rank) return select(n.right, rank - leftSize - 1); // node itself
        else return n.key;
    }
    public int rank(K key) {
        if (key == null) throw new IllegalArgumentException("argument to rank() is null");
        return rank(key, root);
    }
    private int rank(K key, Node x) {
        if (x == null) return 0;
        int cmp = key.compareTo(x.key);
        if ( cmp < 0) return rank(key, x.left);
        else if (cmp > 0) return 1 + size(x.left) + rank(key, x.right);
        else return size(x.left);
    }
    // Iterator
    public Iterable<K> keys() {
        if (isEmpty()) return new Queue<K>();
        return keys(min(), max());
    }
    public Iterable<K> keys(K lo, K hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to keys() is null");
        if (hi == null) throw new IllegalArgumentException("second argument to keys() is null");
        Queue<K> queue = new Queue<K>();
        keys(root, queue, lo, hi);
        return queue;
    }
    private void keys(Node n, Queue<K> queue, K lo, K hi) {
        if (n == null) return;
        int cmplo = lo.compareTo(n.key);
        int cmphi = hi.compareTo(n.key);
        if (cmplo < 0) keys(n.left, queue, lo, hi);
        if (cmplo <= 0 && cmphi >= 0) queue.enqueue(n.key);
        if (cmphi > 0) keys(n.right, queue, lo, hi);
    }
    // size and height and level
    public int size(K lo, K hi) {
        if (lo == null) throw new IllegalArgumentException("first argument to size() is null");
        if (hi == null) throw new IllegalArgumentException("second argumen to size() is null");
        if (lo.compareTo(hi) > 0) return 0;
        if (contains(hi)) return rank(hi) - rank(lo) + 1;
        else return rank(hi) - rank(lo);
    }
    public int height() {
        return height(root);
    }
    private int height(Node x) {
        if (x == null) return -1;
        return 1 + Math.max(height(x.left), height(x.right));
    }
    public Iterable<K> levelOrder() {
        Queue<K> keys = new Queue<K>();
        Queue<Node> queue = new Queue<Node>();
        queue.enqueue(root);
        while (!queue.isEmpty()) {
            Node x = queue.dequeue();
            if (x == null) continue;
            keys.enqueue(x.key);
            queue.enqueue(x.left);
            queue.enqueue(x.right);
        }
        return keys;
    }
    // check integrity
    private boolean check() {
        if (!isBST()) StdOut.println("Not in symmetric order");
        if (isSizeConsistent()) StdOut.println("Subtree counts not consistent");
        if (!isRankConsistent()) StdOut.println("Ranks not consistent");
        return isBST() && isSizeConsistent() && isRankConsistent();
    }
    private boolean isBST() {
        return isBST(root, null, null);
    }
    private boolean isBST(Node n, K min, K max) {
        if (n == null) return true;
        if (min != null && n.key.compareTo(min) <= 0) return false;
        if (max != null && n.key.compareTo(max) >= 0) return false;
        return isBST(n.left, min, n.key) && isBST(n.right, n.key, max);
    }
    private boolean isSizeConsistent() {
        return isSizeConsistent(root);
    }
    private boolean isSizeConsistent(Node n) {
        if (n == null) return true;
        if (n.size != size(n.left) + size(n.right) + 1) return false;
        return isSizeConsistent(n.left) && isSizeConsistent(n.right);
    }
    private boolean isRankConsistent() {
        for (int i = 0; i < size(); i++) {
            if (i != rank(select(i))) {
                return false;
            }
        }
        for (K key: keys()) {
            if (key.compareTo(select(rank(key))) != 0) {
                return false;
            }
        }
        return true;
    }
}
