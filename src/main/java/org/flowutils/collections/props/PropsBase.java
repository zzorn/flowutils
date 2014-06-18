package org.flowutils.collections.props;

import org.flowutils.Symbol;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.flowutils.Check.notNull;

/**
 * Common functionality for Properties implementations.
 *
 * The setAll(InputProperties) and getIdentifiers() methods are not very efficiently implemented, and are recommended to be overridden.
 */
public abstract class PropsBase implements Props {

    protected PropsBase() {
    }

    /**
     * @param initialValues initial property values.
     */
    protected PropsBase(Map<Symbol, Object> initialValues) {
        setAll(initialValues);
    }

    @Override public final <T> T get(String id) {
        return get(Symbol.get(id));
    }

    @Override public <T> T get(Symbol id, T defaultValue) {
        if (has(id)) return get(id);
        else return defaultValue;
    }

    @Override public final <T> T get(String id, T defaultValue) {
        return get(Symbol.get(id), defaultValue);
    }

    @Override public final boolean has(String id) {
        return has(Symbol.get(id));
    }

    @Override public final Object set(String id, Object value) {
        return set(Symbol.get(id), value);
    }

    @Override public final <T> T remove(String id) {
        return remove(Symbol.get(id));
    }

    @Override public final void setAll(Map<Symbol, Object> parameters) {
        notNull(parameters, "parameters");

        for (Map.Entry<Symbol, Object> entry : parameters.entrySet()) {
            set(entry.getKey(), entry.getValue());
        }
    }

    // NOTE: Not very efficient, override if performance is important
    @Override public void setAll(ReadableProps parameters) {
        setAll(parameters.getAll(null));
    }

    // NOTE: Not very efficient, override if performance is important
    @Override public Set<Symbol> getIdentifiers(Set<Symbol> identifiersOut) {
        if (identifiersOut == null) identifiersOut = new HashSet<>();

        identifiersOut.addAll(getAll(null).keySet());

        return identifiersOut;
    }
}
