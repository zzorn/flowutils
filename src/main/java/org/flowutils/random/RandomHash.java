package org.flowutils.random;

/**
 * Interface for hash functions that produce a pseudorandom value based on one input.
 */
public interface RandomHash {

    /**
     * @return hash value for the specified input value.
     */
    long hash(long input);

}
