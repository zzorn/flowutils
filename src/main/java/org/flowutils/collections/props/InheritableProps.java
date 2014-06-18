package org.flowutils.collections.props;

import org.flowutils.Symbol;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A set of named values that may have a backing set to fall back to when retrieving values.
 */
public interface InheritableProps extends Props {

    /**
     * @param defaultProperties a set of properties that will be used to retrieve values if no value is available in these properties.
     *                          The default properties are added last in the list of defaults.
     */
    void addDefaults(ReadableProps defaultProperties);

    /**
     * @param index position in the defaults to add these new defaults at.
     * @param defaultProperties a set of properties that will be used to retrieve values if no value is available in these properties.
     */
    void addDefaults(int index, ReadableProps defaultProperties);

    /**
     * @param defaultProperties default properties to remove from the default fallback list.
     */
    void removeDefaults(ReadableProps defaultProperties);

    /**
     * Remove all default fallback properties.
     */
    void removeAllDefaults();

    /**
     * @return list of properties instances used to retrieve values when they are not found in this property instance,
     * or empty list if no fallback properties instance is used.
     */
    List<ReadableProps> getDefaults();

    /**
     * @return the identifiers of the properties available in this properties instance.
     *         Does not look at default fallback properties instances.
     *         The returned collection may be immutable, and may reflect the current state of the properties.
     */
    Set<Symbol> getNonDefaultIdentifiers();

    /**
     * @return the properties in this properties instance, not including any defaults.
     *         The returned map may be immutable, and may reflect the current state of the properties.
     */
    Map<Symbol, Object> getNonDefaultProperties();

}
