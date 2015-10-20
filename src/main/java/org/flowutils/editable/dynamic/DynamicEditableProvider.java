package org.flowutils.editable.dynamic;

import org.flowutils.editable.EditableProviderBase;
import org.flowutils.editable.Type;
import org.flowutils.editable.module.Module;
import org.flowutils.editable.object.ObjectWrap;

/**
 * Provides dynamic editables.
 */
public class DynamicEditableProvider extends EditableProviderBase {

    public DynamicEditableProvider() {
    }

    public DynamicEditableProvider(Module rootModule) {
        super(rootModule);
    }

    @Override public Type getTypeOfObject(Object objectInstance) {
        if (objectInstance instanceof DynamicObject) {
            final DynamicObject dynamicObject = (DynamicObject) objectInstance;

        }

        // IMPLEMENT: Implement getTypeOfObject
        return null;
    }

    @Override public ObjectWrap getObjectWrap(Object object) {
        // IMPLEMENT: Implement getObjectWrap
        return null;
    }
}
