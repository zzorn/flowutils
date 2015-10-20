package org.flowutils.collections.props;

import org.flowutils.Symbol;

import java.util.Map;

/**
 * Represents a set of named values that can be written to.
 */
public interface WritableProps {

    /**
     * Sets the specified property to the specified value.
     */
    void set(Symbol id, Object value);

    /**
     * Sets the specified property to the specified value.
     */
    void set(String id, Object value);

    /**
     * Sets all of the properties provided in the input map.
     */
    void setAll(Map<Symbol, Object> parameters);

    /**
     * Sets all of the properties from the provided input parameters (including any default properties).
     */
    void setAll(ReadableProps parameters);

    /**
     * Removes the specified property, if possible.
     * @return the previous value associated with the specified id, or null if none (or null value).
     */
    <T> T remove(Symbol id);

    /**
     * Removes the specified property, if possible.
     * @return the previous value associated with the specified id, or null if none (or null value).
     */
    <T> T remove(String id);

    /**
     * Removes all properties from this set, if possible.
     */
    void removeAll();


}
