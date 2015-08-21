package org.flowutils.interpolator.interpolators;

import org.flowutils.Check;
import org.flowutils.MathUtils;
import org.flowutils.interpolator.Interpolator;
import org.flowutils.interpolator.InterpolatorBase;

import static org.flowutils.Check.notNull;

/**
 * Interpolates in a specific number of discrete steps.
 * Uses an underlying interpolator to determine the function to sample (defaults to linear).
 */
public final class SteppingInterpolator extends InterpolatorBase {

    private Interpolator baseInterpolator;
    private int steps;

    /**
     * Uses linear interpolator with the 8 discrete steps.
     */
    public SteppingInterpolator() {
        this(8);
    }

    /**
     * Uses linear interpolator with the specified number of discrete steps.
     * @param steps number of discrete steps to use.
     */
    public SteppingInterpolator(int steps) {
        this(steps, LinearInterpolator.IN_OUT);
    }

    /**
     * @param steps number of discrete steps to use.
     * @param baseInterpolator base interpolator to use.
     */
    public SteppingInterpolator(int steps, Interpolator baseInterpolator) {
        setSteps(steps);
        setBaseInterpolator(baseInterpolator);
    }

    public Interpolator getBaseInterpolator() {
        return baseInterpolator;
    }

    /**
     * @param baseInterpolator base interpolator to use.
     */
    public void setBaseInterpolator(Interpolator baseInterpolator) {
        notNull(baseInterpolator, "baseInterpolator");
        this.baseInterpolator = baseInterpolator;
    }

    public int getSteps() {
        return steps;
    }

    /**
     * @param steps number of discrete steps to use.
     */
    public void setSteps(int steps) {
        Check.positive(steps, "steps");
        this.steps = steps;
    }

    @Override public double interpolate(double t) {
        final double stepSize = 1.0 / (steps + 1.0);
        final int step = MathUtils.fastFloor(t / stepSize);
        return baseInterpolator.interpolate((double)step / steps);
    }
}
