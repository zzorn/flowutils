package org.flowutils.editable.object;

import org.flowutils.Symbol;
import org.flowutils.collections.props.Props;
import org.flowutils.updating.Updating;

import java.util.Collection;

/**
 * Encapsulates an object with properties.
 */
public interface ObjectWrap extends Props, Updating {

    /**
     * @return the type of this object.
     */
    ObjectType getType();

    /**
     * @return the underlying object instance.
     */
    Object getInstance();

    /**
     * @return interface that can be used to get and set the specified property of this object, as well as get metadata for it.
     */
    PropertyWrap getProperty(Symbol propertyId);

    /**
     * @return interfaces that can be used to get and set the properties of this object, as well as get metadata for them.
     */
    Collection<PropertyWrap> getProperties();

}
