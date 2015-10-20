package org.flowutils.editable;

import org.flowutils.updating.Updating;

import java.util.List;

/**
 * Keeps track of registered EditableProviders.
 */
public interface EditableContext extends Updating {

    /**
     * @return list of all registered editable providers.
     */
    List<EditableProvider> getEditableProviders();

    /**
     * @param editableProvider provider to add to this context.
     *
     */
    void addEditableProvider(EditableProvider editableProvider);
}
