package org.flowutils.collections.properties;

import java.util.List;

/**
 * A set of named values that may have a backing set to fall back to when retrieving values.
 */
public interface InheritableProperties extends Properties {

    /**
     * @param defaultProperties a set of properties that will be used to retrieve values if no value is available in these properties.
     *                          The default properties are added last in the list of defaults.
     */
    void addDefaults(ReadableProperties defaultProperties);

    /**
     * @param index position in the defaults to add these new defaults at.
     * @param defaultProperties a set of properties that will be used to retrieve values if no value is available in these properties.
     */
    void addDefaults(int index, ReadableProperties defaultProperties);

    /**
     * @param defaultProperties default properties to remove from the default fallback list.
     */
    void removeDefaults(ReadableProperties defaultProperties);

    /**
     * Remove all default fallback properties.
     */
    void removeAllDefaults();

    /**
     * @return list of properties instances used to retrieve values when they are not found in this property instance,
     * or empty list if no fallback properties instance is used.
     */
    List<ReadableProperties> getDefaults();

}
