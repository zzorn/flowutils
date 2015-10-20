package org.flowutils.editable;

import org.flowutils.Symbol;

/**
 * Something with an identifier and optional user readable description and icon id.
 */
public interface Identified {

    /**
     * @return id of this thing.
     */
    Symbol getId();

    /**
     * @return user readable name.
     */
    String getName();

    /**
     * @return user readable description, e.g. for tooltips.
     */
    String getDescription();

    /**
     * @return filename or resource handle of the icon to use for this.
     */
    String getIconId();

}
