package org.flowutils.interpolator.interpolators;


import org.flowutils.Check;
import org.flowutils.MathUtils;
import org.flowutils.SimplexGradientNoise;
import org.flowutils.interpolator.InterpolatorBase;
import org.flowutils.random.RandomSequence;
import org.flowutils.random.XorShift;

/**
 * Noise based interpolation.
 */
public final class NoiseInterpolator extends InterpolatorBase {

    private static final RandomSequence RANDOM_SEQUENCE = new XorShift();

    private double frequency;
    private double noiseShape;
    private double noiseAmplitude;

    /**
     * Creates a new NoiseTween with random noiseShape and a frequency of about four noise waves in the interpolate.
     */
    public NoiseInterpolator() {
        this(4);
    }

    /**
     * Creates a new NoiseTween with random noiseShape and the specified frequency.
     * @param frequency frequency of the noise.  Determines number of noise waves visible in the interpolate.
     */
    public NoiseInterpolator(double frequency) {
        this(frequency, 0.5);
    }

    /**
     * @param frequency frequency of the noise.  Determines number of noise waves visible in the interpolate.
     * @param noiseAmplitude amplitude of the noise added.  Defaults to 0.5.  Determines how much the interpolation function differs from a basic cosine interpolation.
     */
    public NoiseInterpolator(double frequency, double noiseAmplitude) {
        this(frequency, noiseAmplitude, RANDOM_SEQUENCE.nextDouble()*10000);
    }

    /**
     * @param frequency frequency of the noise.  Determines number of noise waves visible in the interpolate.
     * @param noiseAmplitude amplitude of the noise added.  Defaults to 0.5.  Determines how much the interpolation function differs from a basic cosine interpolation.
     * @param noiseShape shifts the noise shape.  Use e.g. to create different noise patterns for different interpolators.
     *               Randomized by default.
     */
    public NoiseInterpolator(double frequency, double noiseAmplitude, double noiseShape) {
        setFrequency(frequency);
        setNoiseAmplitude(noiseAmplitude);
        setNoiseShape(noiseShape);
    }

    /**
     * @param frequency frequency of the noise.  Determines number of noise waves visible in the interpolate.
     */
    public void setFrequency(double frequency) {
        Check.positive(frequency, "frequency");
        this.frequency = frequency;
    }

    /**
     * @param noiseShape shifts the noise shape.  Use e.g. to create different noise patterns for different interpolators.
     *                   Randomized by default.
     */
    public void setNoiseShape(double noiseShape) {
        Check.normalNumber(noiseShape, "noiseShape");
        this.noiseShape = noiseShape;
    }

    public double getNoiseAmplitude() {
        Check.normalNumber(noiseAmplitude, "noiseAmplitude");
        return noiseAmplitude;
    }

    /**
     * @param noiseAmplitude amplitude of the noise added.  Defaults to 0.5.  Determines how much the interpolation function differs from a basic cosine interpolation.
     */
    public void setNoiseAmplitude(double noiseAmplitude) {
        this.noiseAmplitude = noiseAmplitude;
    }

    public double getFrequency() {
        return frequency;
    }

    public double getNoiseShape() {
        return noiseShape;
    }

    @Override public double interpolate(double t) {
        final double baseLine = 0.5 - 0.5 * Math.cos(t * 0.5 * MathUtils.Tau);           // __/¨¨   0 to 1
        final double waveAmplitude = 0.5 - 0.5 * Math.cos(t * MathUtils.Tau);            // _/¨\_   0 to 1
        final double noise = SimplexGradientNoise.sdnoise2(t * frequency, noiseShape);   // /\|\/  -1 to 1
        return baseLine + waveAmplitude * noise * noiseAmplitude;                        // _,/'¨   0 to 1
    }
}
