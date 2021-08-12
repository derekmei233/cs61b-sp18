package Map61B;

import java.util.List;

public interface Map61B<K, V> {
    public boolean containsKey(K key);
    public void put(K key, V value);
    public V get(K key);
    public int size();
    public List<K> keys();
}
