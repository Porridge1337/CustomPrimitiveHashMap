package collections.map;

public interface CustomMap<K, V> {

    boolean put(final K key, final V value);

    boolean remove(K key);

    void removeAll();

    V get(K key);

    int size();

}
