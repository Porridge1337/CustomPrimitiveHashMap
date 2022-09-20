package collections.map;

import java.util.Comparator;
import java.util.Set;

public interface CustomMap<K, V> {

    boolean put(final K key, final V value);

    boolean remove(K key);

    void removeAll();

    V get(K key);

    Set<K> keySet();

    int size();

}
