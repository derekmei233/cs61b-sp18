package lab9;

import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;

/**
 * Implementation of interface Map61B with BST as core data structure.
 *
 * @author Your name here
 */
public class BSTMap<K extends Comparable<K>, V> implements Map61B<K, V> {

    private class Node {
        /* (K, V) pair stored in this Node. */
        private K key;
        private V value;

        /* Children of this Node. */
        private Node left;
        private Node right;

        private Node(K k, V v) {
            key = k;
            value = v;
        }
    }

    private Node root;  /* Root node of the tree. */
    private int size; /* The number of key-value pairs in the tree */

    /* Creates an empty BSTMap. */
    public BSTMap() {
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /** Returns the value mapped to by KEY in the subtree rooted in P.
     *  or null if this map contains no mapping for the key.
     */
    private V getHelper(K key, Node p) {
        if (p == null) {
            return null;
        }
        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            return getHelper(key, p.left);
        } else if (cmp > 0) {
            return getHelper(key, p.right);
        } else {
            return p.value;
        }
    }

    /** Returns the value to which the specified key is mapped, or null if this
     *  map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("invalid key in get(): null");
        }
        return getHelper(key, root);
    }

    /** Returns a BSTMap rooted in p with (KEY, VALUE) added as a key-value mapping.
      * Or if p is null, it returns a one node BSTMap containing (KEY, VALUE).
     */
    private Node putHelper(K key, V value, Node p) {
        if (p == null) {
            size += 1;
            Node t = new Node(key, value);
            return t;
        }
        int cmp = key.compareTo(p.key);
        if (cmp < 0) {
            p.left = putHelper(key, value, p.left);
        } else if (cmp > 0) {
            p.right = putHelper(key, value, p.right);
        } else {
            p.value = value;
        }
        return p;
    }

    /** Inserts the key KEY
     *  If it is already present, updates value to be VALUE.
     */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("invalid key in put(): null");
        }
        if (value == null) {
            throw new IllegalArgumentException("invalid value in put(): null");
        }
        if (root == null) {
            root = new Node(key, value);
            size = 1;
        }
        putHelper(key, value, root);
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    private void keySetHelper(Node n, Set<K> keyset) {
        if (n.left == null) {
            keyset.add(n.key);
        } else {
            keySetHelper(n.left, keyset);
            keyset.add(n.key);
        }
        if (n.right != null) {
            keySetHelper(n.right, keyset);
        }
    }
    @Override
    public Set<K> keySet() {
        Set<K> keyset = new HashSet<>();
        keySetHelper(root, keyset);
        return keyset;
    }

    /** Removes KEY from the tree if present
     *  returns VALUE removed,
     *  null on failed removal.
     */
    private Node removeHelper(K key, Node n) {
        if (n == null) {
            return null;
        }
        int cmp = key.compareTo(n.key);
        if (cmp < 0) {
            return n.left = removeHelper(key, n.left);
        } else if (cmp > 0) {
            return n.right = removeHelper(key, n.right);
        } else {
            if (n.right == null) {
                n = n.left;
            } else if (n.left == null) {
                n = n.right;
            } else {
                Node t = n;
                n = min(t.right);
                n.right = deleteMin(t.right);
                n.left = t.left;
            }
            size -= 1;
            return n;
        }
    }
    private Node min(Node n) {
        if (n.left != null) {
            return min(n.left);
        } else {
            return n;
        }
    }
    private Node deleteMin(Node n) {
        if (n.left == null) {
            return n.right;
        }
        n.left = deleteMin(n.left);
        return n;
    }
    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("invalid key in remove(): null");
        }
        if (root == null) {
            return null;
        }
        V result = get(key);
        root = removeHelper(key, root);
        return result;
    }

    /** Removes the key-value entry for the specified key only if it is
     *  currently mapped to the specified value.  Returns the VALUE removed,
     *  null on failed removal.
     **/
    @Override
    public V remove(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("invalid key in remove(): null");
        }
        if (value == null) {
            throw new IllegalArgumentException("invlid value in remove(): null");
        }
        if (value == remove(key)) {
            return value;
        }
        return null;
    }

    @Override
    public Iterator<K> iterator() {
        return  keySet().iterator();
    }
}
