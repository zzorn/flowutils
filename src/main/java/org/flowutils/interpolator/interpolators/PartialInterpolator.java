package org.flowutils.interpolator.interpolators;


import org.flowutils.interpolator.InterpolationRemap;
import org.flowutils.interpolator.Interpolator;
import org.flowutils.interpolator.InterpolatorBase;

import static org.flowutils.Check.notNull;

/**
 * Takes a specific part of the tweening curve of the underlying interpolator.
 */
public final class PartialInterpolator extends InterpolatorBase {

    private Interpolator baseInterpolator;
    private InterpolationRemap remap;

    /**
     * Uses the IN direction of the underlying interpolator.
     * @param baseInterpolator the underlying interpolator to defer to.
     */
    public PartialInterpolator(Interpolator baseInterpolator) {
        this(baseInterpolator, InterpolationRemap.IN);
    }

    /**
     * @param baseInterpolator the underlying interpolator to defer to.
     * @param remap the part of the underlying interpolator to use.
     */
    public PartialInterpolator(Interpolator baseInterpolator, InterpolationRemap remap) {
        setBaseInterpolator(baseInterpolator);
        setRemap(remap);
    }

    public Interpolator getBaseInterpolator() {
        return baseInterpolator;
    }

    public void setBaseInterpolator(Interpolator baseInterpolator) {
        notNull(baseInterpolator, "baseInterpolator");
        this.baseInterpolator = baseInterpolator;
    }

    public InterpolationRemap getRemap() {
        return remap;
    }

    public void setRemap(InterpolationRemap remap) {
        notNull(remap, "remap");
        this.remap = remap;
    }

    @Override public double interpolate(double t) {
        return baseInterpolator.interpolate(t, remap);
    }
}
