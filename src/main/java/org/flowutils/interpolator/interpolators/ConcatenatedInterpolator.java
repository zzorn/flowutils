package org.flowutils.interpolator.interpolators;

import org.flowutils.Check;
import org.flowutils.interpolator.InterpolationRemap;
import org.flowutils.interpolator.Interpolator;
import org.flowutils.interpolator.InterpolatorBase;

import static org.flowutils.Check.notNull;

/**
 * Uses one interpolator for the start of the slope and another for the end of the slope.
 */
public final class ConcatenatedInterpolator extends InterpolatorBase {

    private Interpolator startInterpolator;
    private Interpolator endInterpolator;
    private InterpolationRemap startRemap;
    private InterpolationRemap endRemap;
    private double transitionPoint;

    /**
     * @param startInterpolator interpolator to use below 0.5 t.
     * @param endInterpolator interpolator to use at and above 0.5 t.
     */
    public ConcatenatedInterpolator(Interpolator startInterpolator, Interpolator endInterpolator) {
        this(startInterpolator, endInterpolator, 0.5);
    }

    /**
     * @param startInterpolator interpolator to use below the transition point.
     * @param endInterpolator interpolator to use at and above the transition point.
     * @param transitionPoint point along the 0..1 interval where we should switch from startTweener to endTweener.
     */
    public ConcatenatedInterpolator(Interpolator startInterpolator,
                                    Interpolator endInterpolator,
                                    double transitionPoint) {
        this(startInterpolator, endInterpolator, transitionPoint, InterpolationRemap.IN, InterpolationRemap.OUT);
    }

    /**
     * @param startInterpolator interpolator to use below the transition point.
     * @param endInterpolator interpolator to use at and above the transition point.
     * @param transitionPoint point along the 0..1 interval where we should switch from startTweener to endTweener.
     * @param startRemap tweening type to use from the first interpolator.  IN by default.
     * @param endRemap tweening type to use from the second interpolator.  OUT by default.
     */
    public ConcatenatedInterpolator(Interpolator startInterpolator,
                                    Interpolator endInterpolator,
                                    double transitionPoint,
                                    InterpolationRemap startRemap,
                                    InterpolationRemap endRemap) {
        setStartInterpolator(startInterpolator);
        setEndInterpolator(endInterpolator);
        setTransitionPoint(transitionPoint);
        setStartRemap(startRemap);
        setEndRemap(endRemap);
    }

    public Interpolator getStartInterpolator() {
        return startInterpolator;
    }

    public void setStartInterpolator(Interpolator startInterpolator) {
        notNull(startInterpolator, "startInterpolator");
        this.startInterpolator = startInterpolator;
    }

    public Interpolator getEndInterpolator() {
        return endInterpolator;
    }

    public void setEndInterpolator(Interpolator endInterpolator) {
        notNull(endInterpolator, "endInterpolator");
        this.endInterpolator = endInterpolator;
    }

    public InterpolationRemap getStartRemap() {
        return startRemap;
    }

    public void setStartRemap(InterpolationRemap startRemap) {
        notNull(startRemap, "startRemap");
        this.startRemap = startRemap;
    }

    public InterpolationRemap getEndRemap() {
        return endRemap;
    }

    public void setEndRemap(InterpolationRemap endRemap) {
        notNull(endRemap, "endRemap");
        this.endRemap = endRemap;
    }

    public double getTransitionPoint() {
        return transitionPoint;
    }

    public void setTransitionPoint(double transitionPoint) {
        Check.normalNumber(transitionPoint, "transitionPoint");
        this.transitionPoint = transitionPoint;
    }

    @Override public double interpolate(double t) {
        if (t < transitionPoint) {
            return startInterpolator.interpolate(t, 0, transitionPoint, 0, 1, false, startRemap);
        }
        else {
            return endInterpolator.interpolate(t, transitionPoint, 1, 0, 1, false, endRemap);
        }
    }
}
