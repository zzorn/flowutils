package org.flowutils.editable.object;

import org.flowutils.Symbol;
import org.flowutils.editable.Range;
import org.flowutils.editable.Type;
import org.flowutils.editable.function.Function;

/**
 * Contains information about a property of some object type.
 */
public interface Property {

    /**
     * @return unique id of this property within the type it is defined in.
     */
    Symbol getId();

    /**
     * @return user readable name of the property.
     */
    String getName();

    /**
     * @return user readable description of the property, e.g. for tooltips.
     */
    String getDescription();

    /**
     * @return filename or resource handle of the icon to use for this property.
     */
    String getIconId();

    /**
     * @return the type that this property is defined in.
     */
    ObjectType getObjectType();

    /**
     * @return the type of value that this property accepts.
     */
    Type getType();

    /**
     * @return the allowed range for the property value.
     */
    Range getRange();

    /**
     * @return default value for new instances.
     */
    Object getDefaultValue();

    /**
     * @return the value calculator that is currently used to calculate the value for this property, or null if no
     *         calculator is used.
     */
    ValueCalculator getCalculator();

    /**
     * @param function the function that should be used to calculate the value for this property, or null if no
     *                 function should be used.
     *                 A ValueCalculator instance is created automatically and the function is wrapped in it.
     */
    void setCalculatorFunction(Function function);

    // TODO: Determine how to do value updates

    // TODO: Add change listener support


    void set(Object editedObject, Object value);

    void setBoolean(Object editedObject, boolean value);
    void setByte(Object editedObject, byte value);
    void setShort(Object editedObject, short value);
    void setInt(Object editedObject, int value);
    void setLong(Object editedObject, long value);
    void setFloat(Object editedObject, float value);
    void setDouble(Object editedObject, double value);

    Object get(Object editedObject);

    boolean getBoolean(Object editedObject);
    byte getByte(Object editedObject);
    short getShort(Object editedObject);
    int getInt(Object editedObject);
    long getLong(Object editedObject);
    float getFloat(Object editedObject);
    double getDouble(Object editedObject);


    /**
     * @return wrapped property for the specified object instance.
     */
    PropertyWrap wrap(Object objectInstance);


}
