package org.flowutils.editable.dynamic;

import org.flowutils.Symbol;
import org.flowutils.editable.Range;
import org.flowutils.editable.Type;
import org.flowutils.editable.object.ObjectType;
import org.flowutils.editable.object.PropertyWrap;
import org.flowutils.time.Time;

/**
 *
 */
public class DynamicPropertyWrap<T> implements PropertyWrap<T> {

    @Override public Object getObjectInstance() {
        // IMPLEMENT: Implement getObjectInstance
        return null;
    }

    @Override public ObjectType getObjectType() {
        // IMPLEMENT: Implement getObjectType
        return null;
    }

    @Override public Type<T> getType() {
        // IMPLEMENT: Implement getType
        return null;
    }

    @Override public Range<T> getRange() {
        // IMPLEMENT: Implement getRange
        return null;
    }

    @Override public boolean isEditable() {
        // IMPLEMENT: Implement isEditable
        return false;
    }

    @Override public T get() {
        // IMPLEMENT: Implement get
        return null;
    }

    @Override public boolean getBoolean() {
        // IMPLEMENT: Implement getBoolean
        return false;
    }

    @Override public byte getByte() {
        // IMPLEMENT: Implement getByte
        return 0;
    }

    @Override public short getShort() {
        // IMPLEMENT: Implement getShort
        return 0;
    }

    @Override public int getInt() {
        // IMPLEMENT: Implement getInt
        return 0;
    }

    @Override public long getLong() {
        // IMPLEMENT: Implement getLong
        return 0;
    }

    @Override public float getFloat() {
        // IMPLEMENT: Implement getFloat
        return 0;
    }

    @Override public double getDouble() {
        // IMPLEMENT: Implement getDouble
        return 0;
    }

    @Override public void set(T value) {
        // IMPLEMENT: Implement set

    }

    @Override public void setBoolean(boolean value) {
        // IMPLEMENT: Implement setBoolean

    }

    @Override public void setByte(byte value) {
        // IMPLEMENT: Implement setByte

    }

    @Override public void setShort(short value) {
        // IMPLEMENT: Implement setShort

    }

    @Override public void setInt(int value) {
        // IMPLEMENT: Implement setInt

    }

    @Override public void setLong(long value) {
        // IMPLEMENT: Implement setLong

    }

    @Override public void setFloat(float value) {
        // IMPLEMENT: Implement setFloat

    }

    @Override public void setDouble(double value) {
        // IMPLEMENT: Implement setDouble

    }

    @Override public Symbol getId() {
        // IMPLEMENT: Implement getId
        return null;
    }

    @Override public String getName() {
        // IMPLEMENT: Implement getName
        return null;
    }

    @Override public String getDescription() {
        // IMPLEMENT: Implement getDescription
        return null;
    }

    @Override public String getIconId() {
        // IMPLEMENT: Implement getIconId
        return null;
    }

    @Override public void update(Time time) {
        // IMPLEMENT: Implement update

    }
}
