/**
 * Class to demonstrate how generic methods work in Java.
 */
package myMap61B;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.List;

public class MapHelper {
    // Here we should use generic methods. Since se don't need to instantiate this class
    public static <K, V> V get(Map61B<K, V> sim, K key) {
        // K, V only works here.
        if (sim.containsKey(key)) {
            return sim.get(key);
        }
        return null;
    }
    // to make sure K is subtype of Comparable<K>, and K has Tocompare() method.
    // extends will not change K, just a declaration.
    public static <K extends Comparable<K>, V>  K maxKey(Map61B<K, V> sim) {
        List<K> keylist = sim.keys();
        K largest = keylist.get(0);
        for (K k: keylist) {
            if (k.compareTo(largest) > 0) {
                largest = k;
            }
        }
        return largest;
    }


    @Test
    public void testGet() {
        Map61B<String, Integer> m = new ArrayMap<String, Integer>();
        m.put("horse", 3);
        m.put("fish", 9);
        m.put("house", 10);

        Integer actual = MapHelper.get(m, "fish");
        Integer expected = 9;
        assertEquals(expected, actual);
        assertEquals(null, MapHelper.get(m, "awead"));
    }
    @Test
    public void testMaxKey() {
        Map61B<String, Integer> m = new ArrayMap<String, Integer>();
        m.put("horse", 3);
        m.put("fish", 9);
        m.put("house", 10);

        String actual = MapHelper.maxKey(m);
        String expected = "house";
        assertEquals(expected, actual);
    }
}
