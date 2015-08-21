package org.flowutils.interpolator;

/**
 * IN_OUT = normal interpolator range,                                                      __/¨¨
 * IN = use first half of normal interpolator range and scale it to full range,             ____/
 * OUT = use second half of normal interpolator range and scale it to full range,           /¨¨¨¨
 * OUT_IN = first use second half of interpolator and then first half of the interpolator.  ,---'
 */
public enum InterpolationRemap {

    /**
     * Use the first half of a normal interpolator range and scale it to full range.  ____/
     */
    IN,

    /**
     * Use the second half of a normal interpolator range and scale it to full range.   /¨¨¨¨¨
     */
    OUT,

    /**
     * Normal interpolator range. __/¨¨
     */
    IN_OUT,

    /**
     * First use the second half of an interpolator and then the first half of the interpolator.  ,---'
     */
    OUT_IN,
    ;

    public double interpolate(double t, Interpolator interpolator) {
        switch (this) {
            case IN: return interpolator.interpolate(t * 0.5) * 2.0;
            case OUT: return interpolator.interpolate(t * 0.5 + 0.5) * 2.0 - 1.0;
            case IN_OUT: return interpolator.interpolate(t);
            case OUT_IN: return t < 0.5 ?
                                interpolator.interpolate(t * 0.5 + 0.5) * 2.0 - 1.0 :
                                interpolator.interpolate(t * 0.5) * 2.0;
            default:
                throw new IllegalStateException("Unknown interpolate direction " + this);
        }
    }
}
