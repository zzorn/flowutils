package org.flowutils.random;

import java.util.List;

/**
 * Interface for random number generators.
 *
 * Not thread safe by default.
 */
public interface RandomSequence {

    /**
     * (Re)Initialize the random sequence with one or more long seeds.
     * The same seeds will always result in the same random sequence.
     */
    void setSeed(long seed, long ... additionalSeeds);

    /**
     * (Re)Initialize the random sequence with one or more float seeds.
     * The same seeds will always result in the same random sequence.
     */
    void setSeed(float seed, float ... additionalSeeds);

    /**
     * (Re)Initialize the random sequence with one or more double seeds.
     * The same seeds will always result in the same random sequence.
     */
    void setSeed(double seed, double ... additionalSeeds);

    /**
     * @return random boolean.
     */
    boolean nextBoolean();

    /**
     * @param probabilityForTrue probability in range 0 .. 1.
     *                           0 == 0% chance for true,
     *                           1 == 100% chance for true.
     * @return random boolean with the specified probability for true.
     */
    boolean nextBoolean(double probabilityForTrue);

    /**
     * @return random byte.
     */
    byte nextByte();

    /**
     * Fills the specified output array with random bytes.
     */
    void nextBytes(byte[] outputArray);

    /**
     * Fills a part of the specified output array with random bytes.
     *
     * @param outputArray array to write to
     * @param startIndex start index to write from (inclusive)
     * @param count number of bytes to write.
     */
    void nextBytes(byte[] outputArray, int startIndex, int count);

    /**
     * @return random integer between MIN_INTEGER and MAX_INTEGER.
     */
    int nextInt();

    /**
     * @return random integer between 0 (inclusive) and max (exclusive).
     */
    int nextInt(int max);

    /**
     * @return random integer between min (inclusive) and max (exclusive).
     */
    int nextInt(int min, int max);

    /**
     * @return random integer between MIN_LONG and MAX_LONG.
     */
    long nextLong();

    /**
     * @return random long between 0 (inclusive) and max (exclusive).
     */
    long nextLong(long max);

    /**
     * @return random long between min (inclusive) and max (exclusive).
     */
    long nextLong(long min, long max);

    /**
     * @return random float between 0 (inclusive) and 1 (exclusive).
     */
    float nextFloat();

    /**
     * @return random float between 0 (inclusive) and max (exclusive).
     */
    float nextFloat(float max);

    /**
     * @return random float between min (inclusive) and max (exclusive).
     */
    float nextFloat(float min, float max);

    /**
     * @return random double between 0 (inclusive) and 1 (exclusive).
     */
    double nextDouble();

    /**
     * @return random double between 0 (inclusive) and max (exclusive).
     */
    double nextDouble(double max);

    /**
     * @return random double between min (inclusive) and max (exclusive).
     */
    double nextDouble(double min, double max);

    /**
     * @return random gaussian distributed float value with mean 0 and standard deviation 1.
     */
    float nextGaussianFloat();

    /**
     * @return random gaussian distributed float value with the specified mean and standard deviation.
     */
    float nextGaussianFloat(float mean, float standardDeviation);

    /**
     * @return random gaussian distributed value with mean 0 and standard deviation 1.
     */
    double nextGaussian();

    /**
     * @return random gaussian distributed value with the specified mean and standard deviation.
     */
    double nextGaussian(double mean, double standardDeviation);

    /**
     * @return random element from the specified list.
     */
    <T> T nextElement(List<T> elements);

    /**
     * @return random element from the specified array.
     */
    <T> T nextElement(T[] elements);

}
