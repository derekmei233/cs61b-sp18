package lab9;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 *  A hash table-backed Map implementation. Provides amortized constant time
 *  access to elements via get(), remove(), and put() in the best case.
 *
 *  @author Your name here
 */
public class MyHashMap<K, V> implements Map61B<K, V> {

    private static final int DEFAULT_SIZE = 16;
    private static final double MAX_LF = 0.75;

    private ArrayMap<K, V>[] buckets;
    private int size;

    private int loadFactor() {
        return size / buckets.length;
    }

    public MyHashMap() {
        buckets = new ArrayMap[DEFAULT_SIZE];
        this.clear();
    }

    /* Removes all of the mappings from this map. */
    @Override
    public void clear() {
        this.size = 0;
        for (int i = 0; i < this.buckets.length; i += 1) {
            this.buckets[i] = new ArrayMap<>();
        }
    }

    /** Computes the hash function of the given key. Consists of
     *  computing the hashcode, followed by modding by the number of buckets.
     *  To handle negative numbers properly, uses floorMod instead of %.
     */
    private int hash(K key) {
        if (key == null) {
            return 0;
        }
        int numBuckets = buckets.length;
        return Math.floorMod(key.hashCode(), numBuckets);
    }

    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        int target = hash(key);
        if (buckets[target].containsKey(key)) {
            return buckets[target].get(key);
        } else {
            return null;
        }
    }
    private void resize() {
        int newsize = size;
        ArrayMap<K, V>[] newbuckets = buckets;
        buckets = new ArrayMap[2 * newbuckets.length];
        this.clear();
        int j;
        for (ArrayMap<K, V> newbucket : newbuckets) {
            for (K key : newbucket) {
                j = hash(key);
                buckets[j].put(key, newbucket.get(key));
            }
        }
        size = newsize;
    }

    /* Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("invalid key in put(): null");
        }
        if (value == null) {
            throw new IllegalArgumentException("invalid value in put(): null");
        }
        int target = hash(key);
        if (buckets[target].containsKey(key)) {
            buckets[target].put(key, value);
        } else {
            size++;
            if (loadFactor() >= MAX_LF) {
                resize();
                int newtarget = hash(key);
                buckets[newtarget].put(key, value);
            } else {
                buckets[target].put(key, value);
            }
        }
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    //////////////// EVERYTHING BELOW THIS LINE IS OPTIONAL ////////////////

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        Set<K> myset = new HashSet<>();
        for (ArrayMap<K, V> am: buckets) {
            myset.addAll(am.keySet());
        }
        return myset;
    }

    /* Removes the mapping for the specified key from this map if exists.
     * Not required for this lab. If you don't implement this, throw an
     * UnsupportedOperationException. */
    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("invalid key ijn remove(): null");
        }
        int target = hash(key);
        if (buckets[target].containsKey(key)) {
            return buckets[target].remove(key);
        } else {
            return null;
        }
    }

    /* Removes the entry for the specified key only if it is currently mapped to
     * the specified value. Not required for this lab. If you don't implement this,
     * throw an UnsupportedOperationException.*/
    @Override
    public V remove(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("invalid key ijn remove(): null");
        }
        int target = hash(key);
        if (buckets[target].containsKey(key) && buckets[target].get(key) == value) {
            return buckets[target].remove(key, value);
        } else {
            return null;
        }
    }

    @Override
    public Iterator<K> iterator() {
        return keySet().iterator();
    }
}
