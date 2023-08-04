package de.comparus.opensource.longmap;

import java.lang.reflect.Array;
import java.util.Arrays;

public class LongMapImpl<V> implements LongMap<V> {

    private static final int CAPACITY = 16;
    private static final int MAX_CAPACITY = 1000;
    private static final int INIT_ELEMENTS_COUNT = 100;
    private final Node<V>[] cells;
    private int size = 0;
    private long[] keys;
    private V[] values;

    public LongMapImpl(Class<V> clazz) {
        this(clazz, CAPACITY);
    }

    @SuppressWarnings("unchecked")
    public LongMapImpl(Class<V> clazz, int capacity) {
        if (capacity < CAPACITY) {
            capacity = CAPACITY;
        } else if (capacity > MAX_CAPACITY) {
            capacity = MAX_CAPACITY;
        }
        this.cells = (Node<V>[]) new Node[capacity];
        this.keys = new long[INIT_ELEMENTS_COUNT];
        this.values = (V[]) Array.newInstance(clazz, INIT_ELEMENTS_COUNT);
    }

    public V put(long key, V value) {
        Node<V> node = new Node<>(key, value, null);

        int cell = getHash(key);

        Node<V> existingNode = cells[cell];
        if (existingNode == null) {
            cells[cell] = node;
            addKeyAndValue(key, value);
        } else {
            while (existingNode.next != null) {
                if (existingNode.key.equals(key)) {
                    replaceValue(existingNode.value, value);
                    existingNode.value = value;
                    return value;
                }
                existingNode = existingNode.next;
            }
            if (existingNode.key.equals(key)) {
                replaceValue(existingNode.value, value);
                existingNode.value = value;
            } else {
                existingNode.next = node;
                addKeyAndValue(key, value);
            }
        }

        return value;
    }

    public V get(long key) {
        int cell = getHash(key);
        Node<V> existingNode = cells[cell];
        V value = null;
        while (existingNode != null) {
            if (existingNode.key == key) {
                value = existingNode.value;
                break;
            }
            existingNode = existingNode.next;
        }

        return value;
    }

    public V remove(long key) {
        int cell = getHash(key);
        Node<V> existingNode = cells[cell];
        V value = null;
        Node<V> previousNode = null;
        while (existingNode != null) {
            if (existingNode.key == key) {
                if (previousNode == null) {
                    cells[cell] = existingNode.next;
                } else {
                    previousNode.next = existingNode.next;
                }
                value = existingNode.value;
                break;
            }
            previousNode = existingNode;
            existingNode = existingNode.next;
        }

        if (value != null) {
            removeKeyAndValue(key);
        }

        return value;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean containsKey(long key) {
        return Arrays.stream(keys).anyMatch(k -> k == key);
    }

    public boolean containsValue(V value) {
        return Arrays.stream(values).anyMatch(value::equals);
    }

    public long[] keys() {
        return Arrays.copyOf(keys, size);
    }

    public V[] values() {
        return Arrays.copyOf(values, size);
    }

    public long size() {
        return size;
    }

    public void clear() {
        size = 0;
        Arrays.fill(cells, null);
        Arrays.fill(keys, 0);
        Arrays.fill(values, null);
    }

    private void removeKeyAndValue(long key) {
        for (int i = 0; i < size; i++) {
            if (keys[i] == key) {
                if (i < size - 1) {
                    System.arraycopy(keys, i + 1, keys, i, keys.length - i - 1);
                    System.arraycopy(values, i + 1, values, i, values.length - i - 1);
                }
                keys[size - 1] = 0;
                values[size - 1] = null;
                size--;
                break;
            }
        }
    }

    private void addKeyAndValue(long key, V value) {
        if (keys.length == size) {
            int newCapacity = size + (size >> 1);
            keys = Arrays.copyOf(keys, newCapacity);
            values = Arrays.copyOf(values, newCapacity);
        }
        keys[size] = key;
        values[size] = value;
        size++;
    }

    private void replaceValue(V existValue, V newValue) {
        for (int i = 0; i < size; i++) {
            if (existValue.equals(values[i])) {
                values[i] = newValue;
            }
        }
    }

    private static int getHash(long key) {
        return Long.hashCode(key) & (CAPACITY - 1);
    }

    private static class Node<V> {
        private final Long key;

        private V value;
        private Node<V> next;

        public Node(Long key, V value, Node<V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
