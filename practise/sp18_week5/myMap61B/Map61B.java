package myMap61B;

import java.util.List;

public interface Map61B<K, V> {
    boolean containsKey(K key);
    void put(K key, V value);
    V get(K key);
    int size();
    List<K> keys();
}
