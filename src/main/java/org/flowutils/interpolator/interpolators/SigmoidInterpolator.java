package org.flowutils.interpolator.interpolators;

import org.flowutils.Check;
import org.flowutils.MathUtils;
import org.flowutils.interpolator.InterpolatorBase;

/**
 * A sigmoid (S curve) shaped function with a tunable sharpness.
 */
public final class SigmoidInterpolator extends InterpolatorBase {

    private double sharpness;

    /**
     * Creates a new SigmoidInterpolator with a sharpness of 0.5.
     */
    public SigmoidInterpolator() {
        this(0.5);
    }

    /**
     * @param sharpness sigmoid sharpness factor, from -1 to 1, linear at 0, approaching ,---' at -1 and __|¨¨ at 1.
     */
    public SigmoidInterpolator(double sharpness) {
        this.sharpness = sharpness;
    }

    /**
     * @return sigmoid sharpness factor, from -1 to 1, linear at 0, approaching ,---' at -1 and __|¨¨ at 1.
     */
    public double getSharpness() {
        return sharpness;
    }

    /**
     * @param sharpness sigmoid sharpness factor, from -1 to 1, linear at 0, approaching ,---' at -1 and __|¨¨ at 1.
     */
    public void setSharpness(double sharpness) {
        Check.inRangeMinusOneToOne(sharpness, "sharpness");
        this.sharpness = sharpness;
    }

    @Override public double interpolate(double t) {
        return MathUtils.sigmoidZeroToOne(t, sharpness);
    }
}
