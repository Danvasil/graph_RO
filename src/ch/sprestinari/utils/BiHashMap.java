package ch.sprestinari.utils;

import java.util.HashMap;
import java.util.Map;

public class BiHashMap<R, C, V> {

    private final Map<R, Map<C, V>> mMap;

    public BiHashMap() {
        mMap = new HashMap<R, Map<C, V>>();
    }

    public boolean containsKey(R key) {
        return mMap.containsKey(key);
    }

    /**
     * Associates the specified value with the specified keys in this map (optional operation). If the map previously
     * contained a mapping for the key, the old value is replaced by the specified value.
     *
     * @param key1
     *            the first key
     * @param key2
     *            the second key
     * @param value
     *            the value to be set
     * @return the value previously associated with (key1,key2), or <code>null</code> if none
     * @see Map#put(Object, Object)
     */
    public V put(R key1, C key2, V value) {
        Map<C, V> map;
        if (mMap.containsKey(key1)) {
            map = mMap.get(key1);
        } else {
            map = new HashMap<C, V>();
            mMap.put(key1, map);
        }

        return map.put(key2, value);
    }

    /**
     * Returns the value to which the specified key is mapped, or <code>null</code> if this map contains no mapping for
     * the key.
     *
     * @param key1
     *            the first key whose associated value is to be returned
     * @param key2
     *            the second key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or <code>null</code> if this map contains no mapping for
     *         the key
     * @see Map#get(Object)
     */
    public V get(R key1, C key2) {
        if (mMap.containsKey(key1)) {
            return mMap.get(key1).get(key2);
        } else {
            return null;
        }
    }

    /**
     * Returns <code>true</code> if this map contains a mapping for the specified key
     *
     * @param key1
     *            the first key whose presence in this map is to be tested
     * @param key2
     *            the second key whose presence in this map is to be tested
     * @return Returns true if this map contains a mapping for the specified key
     * @see Map#containsKey(Object)
     */
    public boolean containsKeys(R key1, C key2) {
        return mMap.containsKey(key1) && mMap.get(key1).containsKey(key2);
    }

    public void clear() {
        mMap.clear();
    }

    public Map<C, V> getRow(R rowKey) {
        return mMap.get(rowKey);
    }

    public Map<R, V> getColumn(C columnKey) {
        Map<R, V> column = new HashMap<>();
        for (Map.Entry<R, Map<C, V>> currentRow : mMap.entrySet()) {
            for (Map.Entry<C, V> currentColumn : currentRow.getValue().entrySet()) {
                if (currentColumn.getKey() == columnKey) {
                    column.put(currentRow.getKey(), currentColumn.getValue());
                }
            }
        }

        return column;
    }

    public BiHashMap<R, C, V> inverse() {
        BiHashMap<R, C, V> newMatrix = new BiHashMap<>();

        for (Map.Entry<R, Map<C, V>> currentRow : mMap.entrySet()) {
            for (Map.Entry<C, V> currentColumn : currentRow.getValue().entrySet()) {
                newMatrix.put((R) currentColumn.getKey(), (C) currentRow.getKey(), currentColumn.getValue());
            }
        }

        return newMatrix;
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        // Print column headers
        for (Map.Entry<R, Map<C, V>> row : mMap.entrySet()) {
            int columnCount = 0;
            sb.append(String.format("%4s |", ""));
            for (Map.Entry<C, V> column : row.getValue().entrySet()) {
                sb.append(String.format("%4s |", column.getKey()));
                columnCount++;
            }
            sb.append('\n');
            sb.append("— ".repeat(3));
            sb.append("— ".repeat(3 * columnCount + 1));
            sb.append('\n');
            break;
        }

        // Print rows
        mMap.forEach((k1, k2VMap) -> {
            sb.append(String.format("%4s |", k1));
            k2VMap.forEach((k2, v) -> {
                sb.append(String.format("%4s |", v));
            });
            sb.append('\n');
        });

        return sb.toString();
    }
}