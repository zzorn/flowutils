package org.flowutils.interpolator.interpolators;


import org.flowutils.interpolator.InterpolatorBase;

/**
 * Uses the x^2 function to fade in and out.
 */
public final class Pow2Interpolator extends InterpolatorBase {

    public static final Pow2Interpolator IN_OUT = new Pow2Interpolator();

    @Override public double interpolate(double t) {
        if (t < 0.5) {
            // Move t to 0..1 range.
            t *= 2.0;

            // Return quad
            return t * t * 0.5;
        } else {
            // Move t to 0..1 range
            t -= 0.5;
            t *= 2.0;

            // Flip t to 1..0 range
            t = 1.0 - t;

            // Return flipped over quad
            return 1.0 - t * t * 0.5;
        }
    }
}
