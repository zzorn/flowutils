package org.flowutils.interpolator.interpolators;


import org.flowutils.interpolator.InterpolatorBase;

/**
 * Uses the x^5 function to fade in and out.
 */
public final class Pow5Interpolator extends InterpolatorBase {

    public static final Pow5Interpolator IN_OUT = new Pow5Interpolator();

    @Override public double interpolate(double t) {
        if (t < 0.5) {
            // Move t to 0..1 range.
            t *= 2.0;

            // Return quint
            final double t2 = t * t;
            return t2 * t2 * t * 0.5;
        } else {
            // Move t to 0..1 range
            t -= 0.5;
            t *= 2.0;

            // Flip t to 1..0 range
            t = 1.0 - t;

            // Return flipped over quint
            final double t2 = t * t;
            return 1.0 - t2 * t2 * t * 0.5;
        }
    }
}
