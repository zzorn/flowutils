package org.flowutils.editable.object;

import org.flowutils.editable.TypedValue;
import org.flowutils.updating.Updating;

/**
 * A wrapper around a property of a specific object instance.
 */
public interface PropertyWrap<T> extends TypedValue<T>, Updating {

    /**
     * @return underlying object instance that has this property.
     */
    Object getObjectInstance();

    /**
     * @return the type of the underlying object.
     */
    ObjectType getObjectType();

}
