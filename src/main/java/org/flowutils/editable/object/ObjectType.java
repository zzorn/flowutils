package org.flowutils.editable.object;

import org.flowutils.Symbol;
import org.flowutils.editable.Type;

import java.util.List;

/**
 * A type that has properties.
 */
public interface ObjectType<T> extends Type<T> {

    /**
     * @return the properties available for the this object type.
     */
    List<Property> getProperties();

    /**
     * @return the property with the specified id, or null if not available.
     */
    Property getProperty(Symbol propertyId);

    /**
     * @param object object instance to wrap.
     * @return a wrapping around the specified object, that allows access to the metadata and properties of the object.
     */
    ObjectWrap getObjectWrap(Object object);

}
