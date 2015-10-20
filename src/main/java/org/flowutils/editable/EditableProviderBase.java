package org.flowutils.editable;

import org.flowutils.editable.module.Module;
import org.flowutils.time.Time;

/**
 *
 */
public abstract class EditableProviderBase implements EditableProvider {

    private final Module rootModule;

    public EditableProviderBase() {
        this(null);
    }

    public EditableProviderBase(Module rootModule) {
        if (rootModule == null) {
            rootModule = createModules();
        }

        this.rootModule = rootModule;
    }

    @Override public final Module getRootModule() {
        return rootModule;
    }

    @Override public void update(Time time) {
        if (rootModule != null) rootModule.update(time);
    }

    /**
     * @return root module with all modules for this editable provider, or null if no modules.
     */
    protected Module createModules() {
        return null;
    }
}
