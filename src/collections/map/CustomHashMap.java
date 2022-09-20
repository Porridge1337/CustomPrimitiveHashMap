package collections.map;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class CustomHashMap<K, V> implements CustomMap {

    private Node<K, V>[] hashTable;
    private int size = 0;
    private float threshold;

    public CustomHashMap() {
        this.hashTable = new Node[16];
        threshold = hashTable.length * 0.75f;
    }


    @Override
    public boolean put(final Object key, final Object value) {
        if (size + 1 >= threshold) {
            threshold *= 2;
            arrayDoubling();
        }
        Node<K, V> newNode = new Node(key, value);
        int index = newNode.hash();
        if (hashTable[index] == null) {
            return addNode(index, newNode);
        }

        List<Node<K, V>> nodeList = hashTable[index].getNodes();

        for (Node<K, V> node : nodeList) {
            if (keyExistButValueNew(node, newNode, value) ||
                    collisionProcessing(node, newNode, nodeList)) return true;
        }

        return false;
    }


    @Override
    public boolean remove(final Object key) {
        int index = hash((K) key);
        if (hashTable[index] == null) return false;
        if (hashTable[index].getNodes().size() == 1) {
            size--;
            hashTable[index] = null;
            return true;
        }
        List<Node<K, V>> nodeList = hashTable[index].getNodes();
        for (Node<K, V> node : nodeList) {
            if (key.equals(node.getKey())) {
                nodeList.remove(node);
                return true;
            }
        }
        return false;
    }

    @Override
    public void removeAll() {
        Node<K, V>[] tab;
        if ((tab = hashTable) != null && size > 0) {
            size = 0;
            for (int i = 0; i < tab.length; i++) {
                tab[i] = null;
            }
        }
    }

    @Override
    public Object get(Object key) {
        int index = hash((K) key);
        if (index < hashTable.length && hashTable[index] != null) {
            if (hashTable[index].getNodes().size() == 1) {
                return hashTable[index].getNodes().get(0).getValue();
            }
            List<Node<K, V>> list = hashTable[index].getNodes();
            for (Node<K, V> node : list) {
                if (key.equals(node.getKey())) {
                    return node.getValue();
                }
            }
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Object[] getSortedArray() {
        return new Object[0];
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new TreeSet<>();
        for (int i = 0; i < hashTable.length; i++) {
            if (hashTable[i] != null) {
                keys.add(hashTable[i].getNodes().get(0).getKey());
                System.out.println(hashTable[i].getNodes().get(0).getKey());
            }
        }
        return keys;
    }

    private boolean keyExistButValueNew(final Node<K, V> nodeFromList, final Node<K, V> newNode, final Object value) {
        if (newNode.getKey().equals(nodeFromList.getKey()) && !newNode.getValue().equals(nodeFromList.getValue())) {
            nodeFromList.setValue((V) value);
            return true;
        }
        return false;
    }

    private boolean collisionProcessing(final Node<K, V> nodeFromList,
                                        final Node<K, V> newNode,
                                        final List<Node<K, V>> nodes) {
        if (newNode.hashCode() == nodeFromList.hashCode() &&
                !Objects.equals(newNode.key, nodeFromList.key) &&
                !Objects.equals(newNode.value, nodeFromList.value)) {
            nodes.add(newNode);
            size++;
            return true;
        }
        return false;
    }

    private void arrayDoubling() {
        Node<K, V>[] oldHashTable = hashTable;
        hashTable = new Node[oldHashTable.length * 2];
        size = 0;
        for (Node<K, V> node : oldHashTable) {
            if (node != null) {
                for (Node<K, V> n : node.getNodes()) {
                    put(n.key, n.value);
                }
            }
        }
    }

    private boolean addNode(int index, Node<K, V> newNode) {
        hashTable[index] = new Node<>(null, null);
        hashTable[index].getNodes().add(newNode);
        this.size++;
        return true;

    }

    private int hash(final K value) {
        return Math.abs(Objects.hash(value)) % hashTable.length;
    }


    private class Node<K, V> {
        private List<Node<K, V>> nodes;
        private int hash;
        private K key;
        private V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            nodes = new LinkedList<Node<K, V>>();
        }

        public List<Node<K, V>> getNodes() {
            return nodes;
        }

        private int hash() {
            return hashCode() % hashTable.length;
        }

        private K getKey() {
            return key;
        }


        private V getValue() {
            return value;
        }

        private void setValue(V value) {
            this.value = value;
        }

        @Override
        public int hashCode() {
            hash = Math.abs(Objects.hash(key));
            return hash;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Node<?, ?> node = (Node<?, ?>) o;
            return Objects.equals(key, node.key);
        }

    }

}
