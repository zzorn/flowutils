package org.flowutils.editable.dynamic;

import org.flowutils.Symbol;
import org.flowutils.collections.props.PropsMap;
import org.flowutils.editable.object.ObjectType;
import org.flowutils.editable.object.ObjectWrap;
import org.flowutils.editable.object.PropertyWrap;
import org.flowutils.time.Time;

import java.util.Collection;

/**
 * An object that can have properties added and removed on the fly.
 */
public class DynamicObject extends PropsMap implements ObjectWrap {

    @Override public ObjectType getType() {
        // IMPLEMENT: Implement getType
        return null;
    }

    @Override public Object getInstance() {
        // IMPLEMENT: Implement getInstance
        return null;
    }

    @Override public PropertyWrap getProperty(Symbol propertyId) {
        // IMPLEMENT: Implement getProperty
        return null;
    }

    @Override public Collection<PropertyWrap> getProperties() {
        // IMPLEMENT: Implement getProperties
        return null;
    }

    @Override public void update(Time time) {
        // IMPLEMENT: Implement update

    }
}
