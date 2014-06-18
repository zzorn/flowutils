package org.flowutils.collections.props;

import org.flowutils.Symbol;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.flowutils.Check.notNull;

/**
 * Map backed implementation.
 * Thread safe.
 */
public final class PropsMap extends PropsBase implements InheritableProps {

    private final ConcurrentHashMap<Symbol, Object> values = new ConcurrentHashMap<>();
    private List<ReadableProps> defaults;

    private transient Set<Symbol> containedIdsReadOnly;
    private transient Map<Symbol, Object> valuesReadOnly;

    /**
     * Creates a new PropertiesMap instance.
     */
    public PropsMap() {
        this(null, null);
    }

    /**
     * Creates a new PropertiesMap instance.
     *
     * @param initialProperties initial properties to set.
     */
    public PropsMap(Map<Symbol, Object> initialProperties) {
        this(initialProperties, null);
    }

    /**
     * Creates a new PropertiesMap instance.
     *
     * @param defaultProperties default fallback properties to use.
     */
    public PropsMap(ReadableProps defaultProperties) {
        this(null, defaultProperties);
    }

    /**
     * Creates a new PropertiesMap instance.
     *
     * @param initialProperties initial properties to set, or null for none.
     * @param defaultProperties default fallback properties to use, or null for none.
     */
    public PropsMap(Map<Symbol, Object> initialProperties, ReadableProps defaultProperties) {
        if (initialProperties != null) setAll(initialProperties);
        if (defaultProperties != null) addDefaults(defaultProperties);
    }

    @Override public <T> T get(Symbol id) {
        if (defaults == null) {
            notNull(id, "id");

            // If we don't have any default properties, we can skip all the availability checking.
            return (T) values.get(id);
        }
        else {
            return get(id, null);
        }
    }

    @Override public <T> T get(Symbol id, T defaultValue) {
        notNull(id, "id");

        // Use value from this properties instance if available
        final T value = (T) values.get(id);
        if (value != null || values.containsKey(id)) {
            return value;
        }
        else {
            if (defaults != null) {
                // Search defaults
                for (ReadableProps aDefault : defaults) {
                    final T valueFromDefault = aDefault.get(id);
                    if (valueFromDefault != null || aDefault.has(id)) {
                        return valueFromDefault;
                    }
                }
            }

            // No value found, use provided default
            return defaultValue;
        }
    }

    @Override public boolean has(Symbol id) {
        notNull(id, "id");

        if (values.containsKey(id)) {
            // Found in these properties
            return true;
        }
        else {
            if (defaults != null) {
                // Check if any default has it
                for (ReadableProps aDefault : defaults) {
                    if (aDefault.has(id)) return true;
                }
            }

            // Not found
            return false;
        }
    }

    @Override public Set<Symbol> getIdentifiers(Set<Symbol> identifiersOut) {
        if (identifiersOut == null) identifiersOut = new HashSet<>();

        // Collect ids from defaults
        if (defaults != null) {
            for (ReadableProps aDefault : defaults) {
                aDefault.getIdentifiers(identifiersOut);
            }
        }

        // Collect own ids
        identifiersOut.addAll(values.keySet());

        return identifiersOut;
    }

    @Override public Map<Symbol, Object> getAll(Map<Symbol, Object> out) {
        if (out == null) {
            out = new HashMap<>();
        }

        // Collect values from defaults, in correct order
        if (defaults != null) {
            for (int i = defaults.size() - 1; i >= 0; i--) {
                defaults.get(i).getAll(out);
            }
        }

        // Collect values from this properties instance
        out.putAll(values);

        return out;
    }

    @Override public Map<Symbol, Object> getNonDefaultProperties() {
        if (valuesReadOnly == null) {
            valuesReadOnly = Collections.unmodifiableMap(values);
        }
        return valuesReadOnly;
    }

    @Override public Set<Symbol> getNonDefaultIdentifiers() {
        if (containedIdsReadOnly == null) {
            containedIdsReadOnly = Collections.unmodifiableSet(values.keySet());
        }

        return containedIdsReadOnly;
    }

    @Override public Object set(Symbol id, Object value) {
        notNull(id, "id");

        return values.put(id, value);
    }

    @Override public void setAll(ReadableProps parameters) {
        notNull(parameters, "parameters");

        parameters.getAll(values);
    }

    @Override public <T> T remove(Symbol id) {
        return (T) values.remove(id);
    }

    @Override public void removeAll() {
        values.clear();
    }

    @Override public void addDefaults(ReadableProps defaultProperties) {
        notNull(defaultProperties, "defaultProperties");

        if (defaults == null) {
            defaults = new CopyOnWriteArrayList<>();
        }

        defaults.add(defaultProperties);
    }

    @Override public void addDefaults(int index, ReadableProps defaultProperties) {
        notNull(defaultProperties, "defaultProperties");

        if (defaults == null) {
            defaults = new CopyOnWriteArrayList<>();
        }

        defaults.add(index, defaultProperties);
    }

    @Override public void removeDefaults(ReadableProps defaultProperties) {
        if (defaults != null) {
            defaults.remove(defaultProperties);

            if (defaults.isEmpty()) {
                defaults = null;
            }
        }
    }

    @Override public void removeAllDefaults() {
        defaults = null;
    }

    @Override public List<ReadableProps> getDefaults() {
        if (defaults == null) {
            return Collections.emptyList();
        }
        else {
            return Collections.unmodifiableList(defaults);
        }
    }
}
