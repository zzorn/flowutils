package org.flowutils.interpolator.interpolators;


import org.flowutils.interpolator.InterpolatorBase;

/**
 * Linear interpolation.
 */
public final class LinearInterpolator extends InterpolatorBase {

    public static final LinearInterpolator IN_OUT = new LinearInterpolator();

    @Override public double interpolate(double t) {
        return t;
    }
}
