package org.flowutils.editable;

/**
 * Root interface for values with a type and range that can be changed.
 */
public interface TypedValue<T> extends Identified {

    Type<T> getType();

    Range<T> getRange();

    /**
     * @return true if the value can be changed.
     */
    boolean isEditable();

    T get();

    boolean getBoolean();
    byte getByte();
    short getShort();
    int getInt();
    long getLong();
    float getFloat();
    double getDouble();

    void set(T value);

    void setBoolean(boolean value);
    void setByte(byte value);
    void setShort(short value);
    void setInt(int value);
    void setLong(long value);
    void setFloat(float value);
    void setDouble(double value);

}
