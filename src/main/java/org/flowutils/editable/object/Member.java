package org.flowutils.editable.object;

import org.flowutils.Symbol;
import org.flowutils.editable.Identified;
import org.flowutils.updating.Updating;

/**
 * Something contained in a module.
 */
public interface Member extends Identified, Updating {

    /**
     * @return the parent that this Member is hosted in.
     */
    Member getParent();

    /**
     * @return the sub member with the specified id, if it exists, an exception otherwise.
     */
    Member getMember(Symbol memberId);

}
