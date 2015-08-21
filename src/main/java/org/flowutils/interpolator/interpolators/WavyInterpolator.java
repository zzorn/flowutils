package org.flowutils.interpolator.interpolators;

import org.flowutils.Check;
import org.flowutils.MathUtils;
import org.flowutils.interpolator.InterpolatorBase;

/**
 * Multi-period cosine interpolation.
 */
public final class WavyInterpolator extends InterpolatorBase {

    private int waveCount;
    private double waveAmplitude;

    /**
     * Uses four waves and 0.5 amplitude.
     */
    public WavyInterpolator() {
        this(4);
    }

    /**
     * Uses 0.5 amplitude.
     * @param waveCount number of waves to use in the transition.
     */
    public WavyInterpolator(int waveCount) {
        this(waveCount, 0.5);
    }

    /**
     * @param waveCount number of waves to use in the transition.
     * @param waveAmplitude amplitude of the waves, from 0 to 1.
     */
    public WavyInterpolator(int waveCount, double waveAmplitude) {
        setWaveCount(waveCount);
        setWaveAmplitude(waveAmplitude);
    }

    public int getWaveCount() {
        return waveCount;
    }

    /**
     * @param waveCount number of waves to use in the transition.
     */
    public void setWaveCount(int waveCount) {
        Check.positiveOrZero(waveCount, "waveCount");
        this.waveCount = waveCount;
    }

    public double getWaveAmplitude() {
        return waveAmplitude;
    }

    /**
     * @param waveAmplitude amplitude of the waves, from 0 to 1.
     */
    public void setWaveAmplitude(double waveAmplitude) {
        Check.normalNumber(waveAmplitude, "waveAmplitude");
        this.waveAmplitude = waveAmplitude;
    }

    @Override public double interpolate(double t) {
        final double baseLine = 0.5 - 0.5 * Math.cos(t * 0.5 * MathUtils.Tau);   // __/~~   0..1
        final double amplitude = 0.5 - 0.5 * Math.cos(t * MathUtils.Tau);        // _/~\_   0..1
        final double waves = Math.cos((t * (waveCount+0.5) * MathUtils.Tau));    // /\/\/  -1..1
        return baseLine + amplitude * waves * waveAmplitude;                     // _,/´¨   0..1
    }
}
