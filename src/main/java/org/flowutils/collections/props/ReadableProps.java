package org.flowutils.collections.props;

import org.flowutils.Symbol;

import java.util.Map;
import java.util.Set;

/**
 * Represents a set of named values that can be read from.
 */
public interface ReadableProps {

    /**
     * @return value of the specified property.
     */
    <T> T get(Symbol id);

    /**
     * @return value of the specified property.
     */
    <T> T get(String id);

    /**
     * @return value of the specified property, or the default value if the property is not available.
     */
    <T> T get(Symbol id, T defaultValue);

    /**
     * @return value of the specified property, or the default value if the property is not available.
     */
    <T> T get(String id, T defaultValue);

    /**
     * @return true if this set of properties has the specified property.
     */
    boolean has(Symbol id);

    /**
     * @return true if this set of properties has the specified property.
     */
    boolean has(String id);

    /**
     * @param identifiersOut set to collect the identifiers in.  If null, a new set is created and returned.
     *                       NOTE! Any existing identifiers in the set are not removed.
     * @return the provided set with all the identifiers of all the properties available in this properties instance and all defaults.
     */
    Set<Symbol> getIdentifiers(Set<Symbol> identifiersOut);

    /**
     * @param out map to collect the properties in.  NOTE!  Any existing mappings are NOT removed!  If null, a new map is created.
     * @return all of the properties, as a map.  Uses and returns the specified out map if not null.
     *         The returned map is mutable and an independent copy from the properties.
     *         All properties including the ones provided by default properties instances are collected.
     */
    Map<Symbol, Object> getAll(Map<Symbol, Object> out);


}
