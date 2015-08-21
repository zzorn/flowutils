package org.flowutils.interpolator.interpolators;

import org.flowutils.interpolator.Interpolator;
import org.flowutils.interpolator.InterpolatorBase;

import static org.flowutils.Check.notNull;

/**
 * First applies the inner interpolator, then applies the outer interpolator on the result of the inner interpolator.
 */
public final class NestedInterpolator extends InterpolatorBase {

    private Interpolator inner;
    private Interpolator outer;

    /**
     * @param inner interpolator to apply first
     * @param outer interpolator to apply on the result of the inner interpolator
     */
    public NestedInterpolator(Interpolator inner, Interpolator outer) {
        setInner(inner);
        setOuter(outer);
    }

    public Interpolator getInner() {
        return inner;
    }

    /**
     * @param inner interpolator to apply first
     */
    public void setInner(Interpolator inner) {
        notNull(inner, "inner");
        this.inner = inner;
    }

    public Interpolator getOuter() {
        return outer;
    }

    /**
     * @param outer interpolator to apply on the result of the inner interpolator
     */
    public void setOuter(Interpolator outer) {
        notNull(outer, "outer");
        this.outer = outer;
    }

    @Override public double interpolate(double t) {
        return outer.interpolate(inner.interpolate(t));
    }
}
