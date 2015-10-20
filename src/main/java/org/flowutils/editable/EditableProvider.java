package org.flowutils.editable;

import org.flowutils.editable.module.Module;
import org.flowutils.editable.object.ObjectWrap;
import org.flowutils.updating.Updating;

/**
 * Provides some type of editables.
 */
public interface EditableProvider extends Updating {

    /**
     * @return root module of a tree of modules containing types, values and functions provided by this EditableProvider.
     */
    Module getRootModule();

    /**
     * @return the type of the specified editable object, or null if it is not an editable object.
     */
    Type getTypeOfObject(Object objectInstance);

    /**
     * @param object object instance to wrap.
     * @return a wrapping around the specified object, that allows access to the metadata and properties of the object.
     */
    ObjectWrap getObjectWrap(Object object);

}
