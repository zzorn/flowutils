package org.flowutils.collections;

import java.lang.ref.SoftReference;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Simple cache using soft references.
 * Thread safe.
 * Can be overridden to implement the retrieve method.
 */
public class MemoryCache<K, V> {

    private final ConcurrentMap<K, SoftReference<V>> cache = new ConcurrentHashMap<>();

    /**
     * Store a value in the cache
     * @param key key
     * @param value value to store.  May be garbage collected if there are no references to it and memory runs low.
     */
    public final void put(K key, V value) {
        cache.put(key, new SoftReference<V>(value));
    }

    /**
     * @return true if the cache contains a non-null value for the key and it has not yet been garbage collected.
     */
    public final boolean contains(K key) {
        final SoftReference<V> vSoftReference = cache.get(key);
        return vSoftReference != null && vSoftReference.get() != null;
    }

    /**
     * @return value for the specified key.
     * If none is available in the cache, the retrieve method is used to try to retrieve and cache a value.
     */
    public final V get(K key) {
        V value = getWithoutRetrieving(key);

        if (value == null) {
            // Try to retrieve value
            value = retrieve(key);

            // Store value if we got it
            if (value != null ) {
                put(key, value);
            }
        }

        return value;
    }

    /**
     * @param key key to get the current value for
     * @return the value for the specified key, or null if there is no value of if it has been garbage collected.
     * Does not try to use the retrieve method to fetch or create a missing value.
     */
    public final V getWithoutRetrieving(K key) {
        final SoftReference<V> vSoftReference = cache.get(key);
        if (vSoftReference != null) {
            return vSoftReference.get();
        }
        else {
            return null;
        }
    }

    /**
     * Clears all cached values.
     */
    public final void clear() {
        cache.clear();
    }

    /**
     * Tries to retrieve or create a value for the given key.  Can be overridden and used to load/fetch data.
     * Returns null by default.
     * @param key key to get data for
     * @return  the data for the specified key, or null if it could not be created or retrieved.
     */
    protected V retrieve(K key) {
        return null;
    }


}
