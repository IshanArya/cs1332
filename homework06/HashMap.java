import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Your implementation of HashMap.
 *
 * @author Ishan Arya
 * @version 1.0
 * @userid iarya3
 * @GTID 903399427
 */
public class HashMap<K, V> {

    // DO NOT MODIFY OR ADD NEW GLOBAL/INSTANCE VARIABLES
    public static final int INITIAL_CAPACITY = 13;
    public static final double MAX_LOAD_FACTOR = 0.67;
    private MapEntry<K, V>[] table;
    private int size;

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code INITIAL_CAPACITY}.
     * <p>
     * Do not use magic numbers!
     * <p>
     * Use constructor chaining.
     */
    public HashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code initialCapacity}.
     * <p>
     * You may assume {@code initialCapacity} will always be positive.
     *
     * @param initialCapacity initial capacity of the backing array
     */
    public HashMap(int initialCapacity) {
        table = new MapEntry[initialCapacity];
    }

    /**
     * Adds the given key-value pair to the HashMap.
     * If an entry in the HashMap already has this key, replace the entry's
     * value with the new one passed in.
     * <p>
     * In the case of a collision, use linear probing as your resolution
     * strategy.
     * <p>
     * At the start of the method, you should check to see if the array would
     * violate the max load factor after adding the data (regardless of
     * duplicates). For example, let's say the array is of length 5 and the
     * current size is 3 (LF = 0.6). For this example, assume that no elements
     * are removed in between steps. If another entry is attempted to be added,
     * before doing anything else, you should check whether (3 + 1) / 5 = 0.8
     * is larger than the max LF. It is, so you would trigger a resize before
     * you even attempt to add the data or figure out if it's a duplicate.
     * <p>
     * When regrowing, resize the length of the backing table to
     * 2 * old length + 1. You must use the resizeBackingTable method to do so.
     * <p>
     * Return null if the key was not already in the map. If it was in the map,
     * return the old value associated with it.
     *
     * @param key   key to add into the HashMap
     * @param value value to add into the HashMap
     * @return null if the key was not already in the map. If it was in the
     * map, return the old value associated with it
     * @throws IllegalArgumentException if key or value is null
     */
    public V put(K key, V value) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }
        if (value == null) {
            throw new IllegalArgumentException("Value cannot be null.");
        }

        //Check LF
        if ((double) (size + 1) / table.length > MAX_LOAD_FACTOR) {
            resizeBackingTable(2 * table.length + 1);
        }

        boolean foundFirstRemoved = false;
        int firstRemoved = 0;
        for (int i = 0; i < table.length; i++) {
            int keyIndex = Math.abs((key.hashCode() + i) % table.length);

            if (!foundFirstRemoved) {
                if (table[keyIndex] == null) {
                    table[keyIndex] = new MapEntry<>(key, value);
                    size++;
                    return null;
                }
                if (table[keyIndex].isRemoved()) {
                    firstRemoved = keyIndex;
                    foundFirstRemoved = true;
                }
            }

            if (table[keyIndex] != null
                    && table[keyIndex].getKey().equals(key)) {
                if (table[keyIndex].isRemoved()) {
                    break;
                }

                V removedValue = table[keyIndex].getValue();
                table[keyIndex].setValue(value);
                return removedValue;
            }
        }
        size++;
        table[firstRemoved] = new MapEntry<>(key, value);
        return null;

    }

    /**
     * Removes the entry with a matching key from the HashMap.
     *
     * @param key the key to remove
     * @return the value previously associated with the key
     * @throws IllegalArgumentException         if key is null
     * @throws java.util.NoSuchElementException if the key does not exist
     */
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }
        for (int i = 0; i < table.length; i++) {
            int keyIndex = Math.abs((key.hashCode() + i) % table.length);
            MapEntry<K, V> current = table[keyIndex];
            if (current == null) {
                break;
            }
            if (current.getKey().equals(key)) {
                if (current.isRemoved()) {
                    break;
                }
                current.setRemoved(true);
                size--;
                return current.getValue();
            }

        }
        throw new NoSuchElementException("Key not found in HashMap.");
    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key the key to search for
     * @return the value associated with the given key
     * @throws IllegalArgumentException         if key is null
     * @throws java.util.NoSuchElementException if the key is not in the map
     */
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }
        for (int i = 0; i < table.length; i++) {
            int keyIndex = Math.abs((key.hashCode() + i) % table.length);
            MapEntry<K, V> current = table[keyIndex];
            if (current == null) {
                break;
            }
            if (current.getKey().equals(key)) {
                if (current.isRemoved()) {
                    break;
                }
                return current.getValue();
            }

        }
        throw new NoSuchElementException("Key does not exist in HashMap.");
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key the key to search for
     * @return whether or not the key is in the map
     * @throws IllegalArgumentException if key is null
     */
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }
        for (int i = 0; i < table.length; i++) {
            int keyIndex = Math.abs((key.hashCode() + i) % table.length);
            MapEntry<K, V> current = table[keyIndex];
            if (current == null) {
                break;
            }
            if (current.getKey().equals(key)) {
                if (current.isRemoved()) {
                    break;
                }
                return true;
            }

        }
        return false;
    }

    /**
     * Returns a Set view of the keys contained in this map.
     * Use {@code java.util.HashSet}.
     *
     * @return set of keys in this map
     */
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for (MapEntry<K, V> x : table) {
            if (x != null && !x.isRemoved()) {
                keySet.add(x.getKey());
            }
        }
        return keySet;
    }

    /**
     * Returns a List view of the values contained in this map.
     * <p>
     * Use {@code java.util.ArrayList} or {@code java.util.LinkedList}.
     * <p>
     * You should iterate over the table in order of increasing index and add
     * entries to the List in the order in which they are traversed.
     *
     * @return list of values in this map
     */
    public List<V> values() {
        List<V> valueList = new LinkedList<>();
        for (MapEntry<K, V> x : table) {
            if (x != null && !x.isRemoved()) {
                valueList.add(x.getValue());
            }
        }
        return valueList;
    }

    /**
     * Resize the backing table to {@code length}.
     * <p>
     * Disregard the load factor for this method. So, if the passed in length is
     * smaller than the current capacity, and this new length causes the table's
     * load factor to exceed MAX_LOAD_FACTOR, you should still resize the table
     * to the specified length and leave it at that capacity.
     * <p>
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     * <p>
     * Remember that you cannot just simply copy the entries over to the new
     * array.
     * <p>
     * Also, since resizing the backing table is working with the non-duplicate
     * data already in the table, you shouldn't need to check for duplicates.
     *
     * @param length new length of the backing table
     * @throws IllegalArgumentException if length is less than the number of
     *                                  items in the hash map
     */
    public void resizeBackingTable(int length) {
        if (length < size) {
            throw new IllegalArgumentException(
                    "Length cannot be less than size: size="
                            + size + " length=" + length
            );
        }
        MapEntry<K, V>[] oldTable = table;
        table = new MapEntry[length];
        for (MapEntry<K, V> x : oldTable) {
            if (x != null && !x.isRemoved()) {
                for (int i = 0; i < table.length; i++) {
                    int keyIndex = Math.abs((x.getKey().hashCode() + i)
                            % table.length);

                    if (table[keyIndex] == null) {
                        table[keyIndex] = x;
                        break;
                    }
                }
            }

        }
    }

    /**
     * Clears the table and resets it to {@code INITIAL_CAPACITY}.
     */
    public void clear() {
        table = new MapEntry[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the number of elements in the map.
     * <p>
     * DO NOT USE OR MODIFY THIS METHOD!
     *
     * @return number of elements in the HashMap
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * DO NOT USE THIS METHOD IN YOUR CODE. IT IS FOR TESTING ONLY.
     *
     * @return the backing array of the data structure, not a copy.
     */
    public MapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

}