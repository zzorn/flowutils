package org.flowutils.editable;

import org.flowutils.Symbol;

/**
 * Common functionality for identified things.
 */
public abstract class IdentifiedBase implements Identified {

    private final Symbol id;
    private String name;
    private String description;
    private String iconId;

    public IdentifiedBase(Symbol id) {
        this(id, id.getString());
    }

    public IdentifiedBase(Symbol id, String name) {
        this(id, name, null);
    }

    public IdentifiedBase(Symbol id, String name, String description) {
        this(id, name, description, null);
    }

    public IdentifiedBase(Symbol id, String name, String description, String iconId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.iconId = iconId;
    }

    @Override public final Symbol getId() {
        return id;
    }

    @Override public final String getName() {
        return name;
    }

    @Override public final String getDescription() {
        return description;
    }

    @Override public final String getIconId() {
        return iconId;
    }

    protected final void setName(String name) {
        this.name = name;
    }

    protected final void setDescription(String description) {
        this.description = description;
    }

    protected final void setIconId(String iconId) {
        this.iconId = iconId;
    }
}
