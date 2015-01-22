package org.flowutils.random;

/**
 * MurmurHash3 implementation for a single 64 bit value.
 * (https://code.google.com/p/smhasher/wiki/MurmurHash3)
 */
public final class MurmurHash3 implements RandomHash {

    /**
     * Just an arbitrary value to use instead of a zero seed, as the hash function doesn't work with zero seeds.
     */
    private static final long SEED_TO_USE_INSTEAD_OF_ZERO = 9837421349L;

    @Override public long hash(long value) {

        // If the input is zero, replace it with an arbitrary non-zero constant
        if (value == 0) value = SEED_TO_USE_INSTEAD_OF_ZERO;

        value ^= value >> 33;
        value *= 0xff51afd7ed558ccdL;
        value ^= value >> 33;
        value *= 0xc4ceb9fe1a85ec53L;
        return value ^ (value >> 33);
    }
}
