package org.flowutils.interpolator.interpolators;


import org.flowutils.Check;
import org.flowutils.interpolator.InterpolatorBase;

/**
 * Flips from zero to one at the specified threshold.
 */
public final class FlipInterpolator extends InterpolatorBase {

    private double threshold;

    /**
     * New FlipInterpolator with a threshold of 0.5.
     */
    public FlipInterpolator() {
        this(0.5);
    }

    /**
     * @param threshold input value where the interpolator will flip between the two alternatives.  Defaults to 0.5.
     */
    public FlipInterpolator(double threshold) {
        setThreshold(threshold);
    }

    public double getThreshold() {
        return threshold;
    }

    /**
     * @param threshold input value where the interpolator will flip between the two alternatives.  Defaults to 0.5.
     */
    public void setThreshold(double threshold) {
        Check.normalNumber(threshold, "threshold");
        this.threshold = threshold;
    }

    @Override public double interpolate(double t) {
        return t < threshold ? 0 : 1;
    }
}
