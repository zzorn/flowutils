package org.flowutils.editable;

import org.flowutils.editable.object.Member;

/**
 * A named instance in a module.
 * Can be used as a global variable or constant.
 */
public interface Variable<T> extends Member, TypedValue<T> {

}
