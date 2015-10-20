package org.flowutils.editable;

import org.flowutils.Check;
import org.flowutils.time.Time;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public final class EditableContextImpl implements EditableContext {

    private final List<EditableProvider> editableProviders = new ArrayList<>();

    @Override public List<EditableProvider> getEditableProviders() {
        return editableProviders;
    }

    @Override public void addEditableProvider(EditableProvider editableProvider) {
        Check.notNull(editableProvider, "editableProvider");
        Check.notContained(editableProvider, editableProviders, "editableProviders");

        editableProviders.add(editableProvider);
    }

    @Override public void update(Time time) {
        for (EditableProvider editableProvider : editableProviders) {
            editableProvider.update(time);
        }
    }
}
