package org.flowutils.interpolator.interpolators;


import org.flowutils.interpolator.InterpolatorBase;

/**
 * Uses the x^4 function to fade in and out.
 */
public final class Pow4Interpolator extends InterpolatorBase {

    public static final Pow4Interpolator IN_OUT = new Pow4Interpolator();

    @Override public double interpolate(double t) {
        if (t < 0.5) {
            // Move t to 0..1 range.
            t *= 2.0;

            // Return quart
            final double t2 = t * t;
            return t2 * t2 * 0.5;
        } else {
            // Move t to 0..1 range
            t -= 0.5;
            t *= 2.0;

            // Flip t to 1..0 range
            t = 1.0 - t;

            // Return flipped over quart
            final double t2 = t * t;
            return 1.0 - t2 * t2 * 0.5;
        }
    }
}
