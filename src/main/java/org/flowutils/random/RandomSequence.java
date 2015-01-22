package org.flowutils.random;

/**
 * Simplified interface for random number generators.
 *
 * Not thread safe by default.
 */
public interface RandomSequence {

    void setSeed(long seed, long ... additionalSeeds);
    void setSeed(float seed, float ... additionalSeeds);
    void setSeed(double seed, double ... additionalSeeds);

    boolean nextBoolean();
    boolean nextBoolean(double probabilityForTrue);

    byte nextByte();
    void nextBytes(byte[] outputArray);
    void nextBytes(byte[] outputArray, int startIndex, int count);

    int nextInt();
    int nextInt(int max);
    int nextInt(int min, int max);

    long nextLong();
    long nextLong(long max);
    long nextLong(long min, long max);

    float nextFloat();
    float nextFloat(float max);
    float nextFloat(float min, float max);

    double nextDouble();
    double nextDouble(double max);
    double nextDouble(double min, double max);

    float nextGaussianFloat();
    float nextGaussianFloat(float mean, float standardDeviation);

    double nextGaussian();
    double nextGaussian(double mean, double standardDeviation);


}
