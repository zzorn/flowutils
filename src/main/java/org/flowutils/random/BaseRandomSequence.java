package org.flowutils.random;

import org.flowutils.Check;

import java.util.List;

import static org.flowutils.Check.notNull;

/**
 * Common functionality for Random number generators.
 */
public abstract class BaseRandomSequence implements RandomSequence {

    private static final MurmurHash3 DEFAULT_SEED_HASHING_FUNCTION = new MurmurHash3();

    private final RandomHash seedHashingFunction;

    private boolean haveExtraGaussian;
    private double extraGaussian;

    /**
     * Uses the default hashing function for the seed (MurmurHash3).
     * Seeds the random number generator using the nanosecond time counter.
     */
    protected BaseRandomSequence() {
        this(System.nanoTime());
    }

    /**
     * Uses the default hashing function for the seed (MurmurHash3).
     *
     * @param seed random seed to use.
     */
    protected BaseRandomSequence(long seed) {
        this(seed, DEFAULT_SEED_HASHING_FUNCTION);
    }

    /**
     * @param seed random seed to use.
     * @param seedHashingFunction hashing function to use on a user provided seed before it is used for the random
     *                            number generator.  Helps make random sequences with adjacent seeds more independent
     *                            for many random number generators.
     */
    protected BaseRandomSequence(long seed, RandomHash seedHashingFunction) {
        notNull(seedHashingFunction, "seedHashingFunction");

        this.seedHashingFunction = seedHashingFunction;

        setSeed(seed);
    }

    @Override public final void setSeed(long seed, long ... additionalSeeds) {
        // Hash the user provided seeds to spread them out over the long space.
        // Combine several seeds by hashing and xoring them together

        long hashedSeed = seedHashingFunction.hash(seed);

        for (long additionalSeed : additionalSeeds) {
            hashedSeed = seedHashingFunction.hash(hashedSeed);
            hashedSeed ^= seedHashingFunction.hash(additionalSeed);
        }

        hashedSeed = seedHashingFunction.hash(hashedSeed);

        // Clear any cached generated gaussian
        haveExtraGaussian = false;

        setHashedSeed(hashedSeed);
    }

    @Override public final void setSeed(float seed, float... additionalSeeds) {
        // Hash the user provided seeds to spread them out over the long space.
        // Combine several seeds by hashing and xoring them together
        // Maps float bits to integers

        long hashedSeed = seedHashingFunction.hash(Float.floatToIntBits(seed));

        for (float additionalSeed : additionalSeeds) {
            hashedSeed = seedHashingFunction.hash(hashedSeed);
            hashedSeed ^= seedHashingFunction.hash(Float.floatToIntBits(additionalSeed));
        }

        hashedSeed = seedHashingFunction.hash(hashedSeed);

        // Clear any cached generated gaussian
        haveExtraGaussian = false;

        setHashedSeed(hashedSeed);
    }

    @Override public final void setSeed(double seed, double... additionalSeeds) {
        // Hash the user provided seeds to spread them out over the long space.
        // Combine several seeds by hashing and xoring them together
        // Maps double bits to longs

        long hashedSeed = seedHashingFunction.hash(Double.doubleToLongBits(seed));

        for (double additionalSeed : additionalSeeds) {
            hashedSeed = seedHashingFunction.hash(hashedSeed);
            hashedSeed ^= seedHashingFunction.hash(Double.doubleToLongBits(additionalSeed));
        }

        hashedSeed = seedHashingFunction.hash(hashedSeed);

        // Clear any cached generated gaussian
        haveExtraGaussian = false;

        setHashedSeed(hashedSeed);
    }

    @Override public final boolean nextBoolean() {
        return nextFloat() < 0.5f;
    }

    @Override public final boolean nextBoolean(double probabilityForTrue) {
        return nextDouble() < probabilityForTrue;
    }

    @Override public final byte nextByte() {
        return (byte) (nextLong() >> (64-8)); // Use signed right shift
    }

    @Override public final void nextBytes(byte[] outputArray) {
        nextBytes(outputArray, 0, outputArray.length);
    }

    @Override public final void nextBytes(byte[] outputArray, int startIndex, int count) {
        Check.inRange(startIndex, "startIndex", 0, outputArray.length);
        Check.inRange(count, "count", 0, outputArray.length - startIndex);

        // NOTE: Could be optimized a bit by using all 8 bytes of the random long.
        for (int i = startIndex; i < startIndex + count; i++) {
            outputArray[i] = nextByte();
        }
    }

    @Override public final int nextInt() {
        return (int) (nextLong() >> 32); // Use signed right shift
    }

    @Override public final int nextInt(int max) {
        return (int)nextLong(max);
    }

    @Override public final int nextInt(int min, int max) {
        if (max < min) Check.greaterOrEqual(max, "max", min, "min");

        // Scale to min .. max range
        return min + nextInt(max - min);
    }

    @Override public final long nextLong(long max) {
        if (max < 0) throw new IllegalArgumentException("max should not be negative, but it was " + max);

        // Handle special case of zero
        if (max == 0) return 0;

        // Randomize
        long result = nextLong();

        // Only return positive values
        if (result < 0) result = -result;
        if (result == Long.MIN_VALUE) result = 0; // Need to be tested separately, as -Long.MIN_VALUE == Long.MIN_VALUE.

        // Return remainder after dividing with max
        return result % max;
    }

    @Override public final long nextLong(long min, long max) {
        if (max < min) throw new IllegalArgumentException("max ("+max+") should be greater or equal to min ("+min+")");

        return min + nextLong(max - min);
    }

    @Override public final float nextFloat() {
        return nextBits(24) / ((float)(1 << 24));
    }

    @Override public final float nextFloat(float max) {
        if (max < 0) throw new IllegalArgumentException("max should not be negative, but it was " + max);

        return nextFloat() * max;
    }

    @Override public float nextFloat(float min, float max) {
        if (max < min) throw new IllegalArgumentException("max ("+max+") should be greater or equal to min ("+min+")");

        return min + nextFloat() * (max - min);
    }

    @Override public final double nextDouble() {
        return nextBits(53) / (double)(1L << 53);
    }

    @Override public final double nextDouble(double max) {
        if (max < 0) throw new IllegalArgumentException("max should not be negative, but it was " + max);

        return nextDouble() * max;
    }

    @Override public final double nextDouble(double min, double max) {
        if (max < min) throw new IllegalArgumentException("max ("+max+") should be greater or equal to min ("+min+")");

        return min + nextDouble() * (max - min);
    }

    @Override public final float nextGaussianFloat() {
        return (float) nextGaussian();
    }

    @Override public final float nextGaussianFloat(float mean, float standardDeviation) {
        return ((float) nextGaussian()) * standardDeviation + mean;
    }

    @Override public final double nextGaussian() {
        if (haveExtraGaussian) {
            // Use earlier extra gaussian if available
            haveExtraGaussian = false;
            return extraGaussian;
        } else {
            double linearRandom1;
            double linearRandom2;
            double squaredDistance;
            do {
                // Generate two linear random numbers in -1 .. 1 range
                linearRandom1 = 2 * nextDouble() - 1;
                linearRandom2 = 2 * nextDouble() - 1;
                squaredDistance = linearRandom1 * linearRandom1 + linearRandom2 * linearRandom2;
            } while (squaredDistance >= 1 || squaredDistance == 0);

            double multiplier = StrictMath.sqrt(-2 * StrictMath.log(squaredDistance) / squaredDistance);

            double gaussian = linearRandom1 * multiplier;

            // Store the extra random gaussian for future use
            extraGaussian = linearRandom2 * multiplier;
            haveExtraGaussian = true;

            return gaussian;
        }
    }

    @Override public final double nextGaussian(double mean, double standardDeviation) {
        return nextGaussian() * standardDeviation + mean;
    }

    @Override public <T> T nextElement(List<T> elements) {
        notNull(elements, "elements");
        return elements.get(nextInt(elements.size()));
    }

    protected final RandomHash getSeedHashingFunction() {
        return seedHashingFunction;
    }

    private long nextBits(int numberOfBits) {
        return nextLong() >>> (64 - numberOfBits); // Use unsigned right shift, most significant bits will be zero
                                                   // and the number will be positive if numberOfBits > 0
    }

    /**
     * Called with a seed that has been hashed, so that sequential user provided seeds will not be close to each other.
     * @param seed seed to use for the random number generator.
     */
    protected abstract void setHashedSeed(long seed);
}
