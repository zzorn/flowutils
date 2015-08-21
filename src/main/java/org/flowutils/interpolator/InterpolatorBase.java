package org.flowutils.interpolator;

/**
 * Common functionality for Tweeners.
 */
public abstract class InterpolatorBase implements Interpolator {

    @Override public final double interpolate(double t, InterpolationRemap interpolationRemap) {
        return interpolationRemap.interpolate(t, this);
    }

    @Override public final double interpolate(double t, boolean clampT) {
        if (clampT) {
            if (t < 0) t = 0;
            if (t > 1) t = 1;
        }

        return interpolate(t);
    }

    @Override public final double interpolate(double t, boolean clampT, InterpolationRemap interpolationRemap) {
        if (clampT) {
            if (t < 0) t = 0;
            if (t > 1) t = 1;
        }

        return interpolationRemap.interpolate(t, this);
    }

    @Override public final double interpolate(double t, double a, double b) {
        return a + interpolate(t) * (b - a);
    }

    @Override public final double interpolate(double t, double a, double b, boolean clampT) {
        if (clampT) {
            if (t < 0) t = 0;
            if (t > 1) t = 1;
        }

        return a + interpolate(t) * (b - a);
    }

    @Override public final double interpolate(double t,
                                              double a,
                                              double b,
                                              boolean clampT,
                                              InterpolationRemap interpolationRemap) {
        if (clampT) {
            if (t < 0) t = 0;
            if (t > 1) t = 1;
        }

        return a + interpolate(t, interpolationRemap) * (b - a);
    }

    @Override
    public final double interpolate(double sourcePos,
                                    double sourceStart,
                                    double sourceEnd,
                                    double targetStart,
                                    double targetEnd) {
        double relativePos = sourceEnd == sourceStart ? 0.5 : (sourcePos - sourceStart) / (sourceEnd - sourceStart);

        relativePos = interpolate(relativePos);

        return targetStart + relativePos * (targetEnd - targetStart);
    }

    @Override
    public final double interpolate(double sourcePos,
                                    double sourceStart,
                                    double sourceEnd,
                                    double targetStart,
                                    double targetEnd,
                                    boolean clampSourcePos) {
        double relativePos = sourceEnd == sourceStart ? 0.5 : (sourcePos - sourceStart) / (sourceEnd - sourceStart);

        relativePos = interpolate(relativePos, clampSourcePos);

        return targetStart + relativePos * (targetEnd - targetStart);
    }

    @Override
    public double interpolate(double sourcePos,
                              double sourceStart,
                              double sourceEnd,
                              double targetStart,
                              double targetEnd,
                              boolean clampSourcePos,
                              InterpolationRemap interpolationRemap) {
        double relativePos = sourceEnd == sourceStart ? 0.5 : (sourcePos - sourceStart) / (sourceEnd - sourceStart);

        relativePos = interpolate(relativePos, clampSourcePos, interpolationRemap);

        return targetStart + relativePos * (targetEnd - targetStart);
    }
}
