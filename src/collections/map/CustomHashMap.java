package collections.map;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/**
 * Хэш таблица основанная на интерфейсе CustomMap.
 * Эта реализация имеет базовые методы.
 *
 * @param <K> - ключ, ассоциированное значение которого должно быть возвращено
 * @param <V> - значение, которому сопоставлен указанный ключ
 */
public class CustomHashMap<K extends Comparable<K>, V> implements CustomMap {

    private Node<K, V>[] hashTable;
    private int size = 0;
    private float threshold;

    public CustomHashMap() {
        this.hashTable = new Node[16];
        threshold = hashTable.length * 0.75f;
    }

    /**
     * Связывает указанное значение с указанным ключём в MAP-е.
     * Если MAP-а ранее содержала сопоставление для ключа, старое значение заменяется указанным значением.
     *
     * @param key   Параметр ключа
     * @param value Значение, которое будет связано с указанным ключом
     * @return
     */
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

    /**
     * Удаление записи по ключу в MAP-е
     *
     * @param key Параметр ключа
     * @return возвращает результат выполненной операции. True - при успешном выполнении, False - при отрицательном
     */
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

    /**
     * Удаление всех сопоставленных данных по ключу\значению
     */
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

    /**
     * Возвращает значение MAP-ы по ключу
     *
     * @param key, параметр ключа.
     * @return value, значение
     */
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

    /**
     * Метод, возвращающий множество ключей в остортированном виде MAP-ы
     *
     * @return множество Set с отсортированным содержимым
     */
    @Override
    public List<K> keys() {
        List<K> arr = new ArrayList<>(size);
        for (int i = 0; i < hashTable.length; i++) {
            if (hashTable[i] != null) {
                if (hashTable[i].getNodes().size() == 1) {
                    arr.add(hashTable[i].getNodes().get(0).getKey());
                } else {
                    List<Node<K, V>> list = hashTable[i].getNodes();
                    for (Node<K, V> node : list) {
                        arr.add(node.getKey());
                    }
                }
            }
        }
        bubble(arr);
        return arr;

    }

    /**
     * Метод, который возвращает колличество сопоставлений ключ-значение
     *
     * @return колличество сопоставлений ключ-значение
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Приватный метод, реализует добавление новой ноды в хэш-таблицу.
     *
     * @param index   индекс, который присваивается ноде. Высчитывается путём деления присвоенного
     *                хэш-кода на длину массива хэш таблицы
     * @param newNode новая нода, которая добавляется в хэш-таблицу
     * @return возвращет true при успешном выполнении.
     */
    private boolean addNode(int index, Node<K, V> newNode) {
        hashTable[index] = new Node<>(null, null);
        hashTable[index].getNodes().add(newNode);
        this.size++;
        return true;

    }


    /**
     * Реализация сортировки пузырьком
     *
     * @param keys
     */
    private void bubble(List<K> keys) {
        boolean isSorted = false;
        K temp;
        while (!isSorted) {
            isSorted = true;
            for (int i = 0; i < keys.size() - 1; i++) {
                if (compareTwoValues(keys.get(i), keys.get(i + 1))) {
                    temp = keys.get(i + 1);
                    keys.set(i + 1, keys.get(i));
                    keys.set(i, temp);
                    isSorted = false;
                }
            }
        }
    }

    /**
     * Сравнение ключей
     *
     * @param value1
     * @param value2
     * @return возвращает true - если первое значение больше второго
     */
    private boolean compareTwoValues(K value1, K value2) {
        return value1.compareTo(value2) > 0;
    }

    /**
     * Приватный метод, который перезаписывает значение, если таковой имеется в хеш-таблице.
     *
     * @param nodeFromList Нода которая берётся для сравнения из хэш-таблицы
     * @param newNode      новая нода с которой сравнивается
     * @param value        значение, которое заменяется, если ноды одинаковые
     * @return true - при успешном выполнении, false - при отсутствии дубликата.
     */
    private boolean keyExistButValueNew(final Node<K, V> nodeFromList, final Node<K, V> newNode, final Object value) {
        if (newNode.getKey().equals(nodeFromList.getKey()) && !newNode.getValue().equals(nodeFromList.getValue())) {
            nodeFromList.setValue((V) value);
            return true;
        }
        return false;
    }

    /**
     * приватный метод, служит для проверки коллизий и если таковые имеются - добавляет в связянный списко
     * того же бакета.
     *
     * @param nodeFromList Нода которая берётся для сравнения
     * @param newNode      новая нода с которой сравнивается
     * @param nodes        связанный список
     * @return true - если коллизии выявились, false - коллизии отсутсвуют
     */
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

    /**
     * приватный метод, который расширяет хэш-таблицу в случае её заполнения
     */
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

    private int hash(final K value) {
        return Math.abs(Objects.hash(value)) % hashTable.length;
    }

    /**
     * Узел. Содержит внутри себя связанный список узлов в пределах одного бакета(корзины) и в случае коллизии
     * добавляется в связанный список.
     * Нода первого уровня - является заглушкой
     * Узлы содержат хэш, ключ и значение.
     *
     * @param <K> key, параметр ключа.
     * @param <V> value, значение.
     */
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
