package org.flowutils.mapping;

/**
 * Maps a source value of some type S to an output value of a type O.
 */
public interface Mapper<S, O> {

    /**
     * @param sourceValue source value to convert to an output value.  May be null.
     * @return the source value converted to an output value.
     */
    O convert(S sourceValue);

}
