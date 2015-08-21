package org.flowutils.interpolator.interpolators;

import org.flowutils.Check;
import org.flowutils.interpolator.InterpolatorBase;

/**
 * Uses the x^exponent function to fade in and out.
 */
public final class PowInterpolator extends InterpolatorBase {

    private double exponent;

    /**
     * Uses a 2 exponent by default.
     */
    public PowInterpolator() {
        this(2);
    }

    /**
     * @param exponent exponent to use for the interpolation curve.
     */
    public PowInterpolator(double exponent) {
        setExponent(exponent);
    }

    public double getExponent() {
        return exponent;
    }

    /**
     * @param exponent exponent to use for the interpolation curve.
     */
    public void setExponent(double exponent) {
        this.exponent = exponent;
    }

    @Override public double interpolate(double t) {
        if (t < 0.5) {
            // Move t to 0..1 range.
            t *= 2.0;

            // Return power
            return Math.pow(t, exponent) * 0.5;
        } else {
            // Move t to 0..1 range
            t -= 0.5;
            t *= 2.0;

            // Flip t to 1..0 range
            t = 1.0 - t;

            // Return flipped over power
            return 1.0 - Math.pow(t, exponent) * 0.5;
        }
    }
}
