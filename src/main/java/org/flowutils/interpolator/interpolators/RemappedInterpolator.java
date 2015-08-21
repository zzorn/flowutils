package org.flowutils.interpolator.interpolators;

import org.flowutils.interpolator.InterpolationRemap;
import org.flowutils.interpolator.Interpolator;
import org.flowutils.interpolator.InterpolatorBase;

import static org.flowutils.Check.notNull;

/**
 * Uses only the start or end of an interpolator, or reverses the start and end of the interpolator.
 */
public final class RemappedInterpolator extends InterpolatorBase {

    private Interpolator interpolator;
    private InterpolationRemap remap;

    /**
     * @param interpolator base interpolator to use
     * @param remap remapping to apply to the base interpolator (which part(s) of the base interpolator to use and how to combine them).
     */
    public RemappedInterpolator(Interpolator interpolator, InterpolationRemap remap) {
        setInterpolator(interpolator);
        setRemap(remap);
    }

    public Interpolator getInterpolator() {
        return interpolator;
    }

    /**
     * @param interpolator base interpolator to use
     */
    public void setInterpolator(Interpolator interpolator) {
        notNull(interpolator, "interpolator");
        this.interpolator = interpolator;
    }

    public InterpolationRemap getRemap() {
        return remap;
    }

    /**
     * @param remap remapping to apply to the base interpolator (which part(s) of the base interpolator to use and how to combine them).
     */
    public void setRemap(InterpolationRemap remap) {
        notNull(remap, "remap");
        this.remap = remap;
    }

    @Override public double interpolate(double t) {
        return remap.interpolate(t, interpolator);
    }
}
