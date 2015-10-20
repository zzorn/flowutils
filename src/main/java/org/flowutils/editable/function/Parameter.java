package org.flowutils.editable.function;

import org.flowutils.editable.Identified;
import org.flowutils.editable.Range;
import org.flowutils.editable.Type;

/**
 * Information about a function parameter.
 */
public interface Parameter<T> extends Identified {

    T getDefaultValue();

    Type<T> getType();

    Range<T> getRange();

    boolean isOptional();


}
