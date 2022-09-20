package collections.map;

import java.util.Comparator;

public interface CustomMap<K, V> extends Comparable<K> {

    boolean put(final K key, final V value);

    boolean remove(K key);

    void removeAll();

    V get(K key);

    int size();

    K[] getSortedArray();
}
