package org.flowutils.interpolator.interpolators;


import org.flowutils.interpolator.InterpolatorBase;

/**
 * Simple cosine based interpolation.
 */
public final class SineInterpolator extends InterpolatorBase {

    public static final SineInterpolator IN_OUT = new SineInterpolator();

    @Override public double interpolate(double t) {
        return 0.5 - 0.5 * Math.cos(t * Math.PI);
    }
}
