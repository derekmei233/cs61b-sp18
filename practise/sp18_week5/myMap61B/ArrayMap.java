package myMap61B;


import org.junit.Test;
// very important here to declare static
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ArrayMap<K, V> implements Map61B<K, V>, Iterable<K> {
    //
    private K[] keys;
    private V[] values;
    int size;
    private class KeyIterator implements Iterator<K> {
        private int pos;
        public KeyIterator() {
            pos = 0;
        }
        public boolean hasNext() {
            return pos < size;
        }
        public K next() {
            K returnVal = keys[pos];
            pos += 1;
            return returnVal;
        }
    }
    public Iterator<K> iterator() {
        return new KeyIterator();
    }

    public ArrayMap() {
        keys = (K[]) new Object[8];
        values = (V[]) new Object[8];
        size = 0;
    }
    /** Return the index of teh given key if it exists,
     *  -1 otherwise */
    private int keyIndex(K key) {
        for (int i = 0; i < size; i++) {
            if (keys[i].equals(key)) {
                return i;
            }
        }
        return -1;
    }
    @Override
    public boolean containsKey(K key) {
        int index = keyIndex(key);
        return index > -1;
    }
    @Override
    public void put(K key, V value) {
        int index = keyIndex(key);
        if (index == -1) {
            keys[size] = key;
            values[size] = value;
            size += 1;
        } else {
            values[index] = value;
        }
    }
    @Override
    public V get(K key) {
        int index = keyIndex(key);
        try {

            if (index == -1) {
                //Object -> Throwable -> Exception
                throw new IllegalArgumentException("The key provided " + key + " is not in map");
            }
        } catch (Exception e) {
            System.out.println("index out of boundary" + e);
        }
        return values[index];
    }
    @Override
    public int size() {
        return size;
    }
    @Override
    public List<K> keys() {
        // declarer using abstract datatype and instantiate using specific implementation.
        List<K> keylist = new ArrayList<>();
        for (int i = 0; i < size; i += 1) {
            keylist.add(keys[i]);
        }
        return keylist;
    }
    @Test
    public void test() {
        ArrayMap<Integer, Integer> am = new ArrayMap<Integer, Integer>();
        am.put(2, 5);
        int expected = 5;
        // no assertEquals version for Integer -> need to witten to long or object
        assertEquals((Integer) expected, am.get(2));
    }

    public static void main(String[] args) {
        ArrayMap<String, Integer> m = new ArrayMap<String, Integer>();
        m.put("horse", 3);
        m.put("fish", 5);
        m.put("house", 10);
    }

}
