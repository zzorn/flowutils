package org.flowutils.random;

/**
 * XorShift random number generator.
 *
 * Uses the xorshift variant xorshift128+ described in http://xorshift.di.unimi.it/
 * See http://xorshift.di.unimi.it/xorshift128plus.c for original c code.
 */
public final class XorShift extends BaseRandomSequence {

    private static final RandomHash seedHasher = new MurmurHash3();

    // Random number generator state (2 * 64 bits)
    private long seed0;
    private long seed1;

    /**
     * Creates a new random number generator with a seed based on the current time.
     */
    public XorShift() {
    }

    /**
     * @param seed initial seed for the random number generator.
     */
    public XorShift(long seed) {
        super(seed);
    }

    @Override protected void setHashedSeed(long seed) {

        seed0 = seed;

        // Derive second seed from the original using a hash function
        seed1 = seedHasher.hash(seedHasher.hash(seed));

        // Replace zero seeds with arbitrary seeds
        if (seed0 == 0) seed0 = 9045324987L;
        if (seed1 == 0) seed1 = 3912353188L;
    }

    @Override public long nextLong() {
        long s1 = seed0;
        long s0 = seed1;

        seed0 = s0;
        s1 ^= s1 << 23;
        seed1 = (s1 ^ s0 ^ (s1 >>> 17) ^ (s0 >>> 26));
        return seed1 + s0;
    }

}
