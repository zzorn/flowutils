package org.flowutils.interpolator;

/**
 * Something that interpolates between two values in some way (fills in values in between)
 */
// IDEA: Add bounce and overshooting interpolators.
public interface Interpolator {

    /**
     * Interpolates between 0 and 1, using this interpolation function and an interpolation position t.
     * Same as interpolate(t, 0, 1, false).
     *
     * @param t relative value indicating the tweening position, goes from 0 to 1.
     *          May go beyond 0 or 1 as well, but the result may vary depending on the interpolator used.
     * @return a value between 0 and 1 (may exceed that range as well), at point t.
     */
    double interpolate(double t);

    /**
     * Interpolates between 0 and 1, using this interpolation function and an interpolation position t.
     * Same as interpolate(t, 0, 1, false).
     *
     * @param t relative value indicating the tweening position, goes from 0 to 1.
     *          May go beyond 0 or 1 as well, but the result may vary depending on the interpolator used.
     * @param interpolationRemap determines what portion of the tweening curve to use.
     * @return a value between 0 and 1 (may exceed that range as well), at point t.
     */
    double interpolate(double t, InterpolationRemap interpolationRemap);

    /**
     * Interpolates between 0 and 1, using this interpolation function and an interpolation position t.
     * Same as interpolate(t, 0, 1, clampT).
     *
     * @param t relative value indicating the tweening position, goes from 0 to 1 (or beyond if clampT is false).
     * @param clampT if true, t is clamped to the 0..1 range, if false, t may be outside that range and the result may be outside the 0..1 range.
     * @return a value between 0 and 1 (may exceed that range as well), at point t.
     */
    double interpolate(double t, boolean clampT);

    /**
     * Interpolates between 0 and 1, using this interpolation function and an interpolation position t.
     * Same as interpolate(t, 0, 1, clampT).
     *
     * @param t relative value indicating the tweening position, goes from 0 to 1 (or beyond if clampT is false).
     * @param clampT if true, t is clamped to the 0..1 range, if false, t may be outside that range and the result may be outside the 0..1 range.
     * @param interpolationRemap determines what portion of the tweening curve to use.
     * @return a value between 0 and 1 (may exceed that range as well), at point t.
     */
    double interpolate(double t, boolean clampT, InterpolationRemap interpolationRemap);

    /**
     * Interpolates between a and b, using this interpolation function and an interpolation position t.
     * Same as interpolate(t, a, b, false).
     *
     * @param t relative value indicating mix between a and b, goes from 0 to 1.
     *          May go beyond 0 or 1 as well, but the result may vary depending on the interpolator used.
     * @param a value when t = 0.0
     * @param b value when t = 1.0
     * @return the value between a and b (may exceed that range as well), at point t.
     */
    double interpolate(double t, double a, double b);

    /**
     * Interpolates between a and b, using this interpolation function and an interpolation position t.
     *
     * @param t relative value indicating mix between a and b, goes from 0 to 1 (or beyond if clampT is false).
     * @param a value when t = 0.0
     * @param b value when t = 1.0
     * @param clampT if true, t is clamped to the 0..1 range, if false, t may be outside that range and the result may be outside the a..b range.
     * @return the value between a and b (may exceed that range as well), at point t.
     */
    double interpolate(double t, double a, double b, boolean clampT);

    /**
     * Interpolates between a and b, using this interpolation function and an interpolation position t.
     *
     * @param t relative value indicating mix between a and b, goes from 0 to 1 (or beyond if clampT is false).
     * @param a value when t = 0.0
     * @param b value when t = 1.0
     * @param clampT if true, t is clamped to the 0..1 range, if false, t may be outside that range and the result may be outside the a..b range.
     * @param interpolationRemap determines what portion of the tweening curve to use.
     * @return the value between a and b (may exceed that range as well), at point t.
     */
    double interpolate(double t, double a, double b, boolean clampT, InterpolationRemap interpolationRemap);

    /**
     * Interpolates between targetStart and targetEnd, using this interpolation function and an interpolation position sourcePos between sourceStart and sourceEnd.
     *
     * @param sourcePos relative value indicating mix between targetStart and targetEnd, goes from sourceStart to sourceEnd (or beyond).
     * @param sourceStart value for sourcePos corresponding to targetStart
     * @param sourceEnd value for sourcePos corresponding to targetEnd
     * @param targetStart result value when sourcePos = sourceStart
     * @param targetEnd result value when sourcePos = sourceEnd
     * @return the value between targetStart and targetEnd (may exceed that range as well), at point sourcePos.
     */
    double interpolate(double sourcePos, double sourceStart, double sourceEnd, double targetStart, double targetEnd);

    /**
     * Interpolates between targetStart and targetEnd, using this interpolation function and an interpolation position sourcePos between sourceStart and sourceEnd.
     *
     * @param sourcePos relative value indicating mix between targetStart and targetEnd, goes from sourceStart to sourceEnd (or beyond if clampSourcePos is false).
     * @param sourceStart value for sourcePos corresponding to targetStart
     * @param sourceEnd value for sourcePos corresponding to targetEnd
     * @param targetStart result value when sourcePos = sourceStart
     * @param targetEnd result value when sourcePos = sourceEnd
     * @param clampSourcePos if true, sourcePos is clamped to the sourceStart..sourceEnd range,
     *               if false, sourcePos may be outside that range and the result may be outside the targetStart..targetEnd range.
     * @return the value between targetStart and targetEnd (may exceed that range as well), at point sourcePos.
     */
    double interpolate(double sourcePos,
                       double sourceStart,
                       double sourceEnd,
                       double targetStart,
                       double targetEnd,
                       boolean clampSourcePos);

    /**
     * Interpolates between targetStart and targetEnd, using this interpolation function and an interpolation position sourcePos between sourceStart and sourceEnd.
     *
     * @param sourcePos relative value indicating mix between targetStart and targetEnd, goes from sourceStart to sourceEnd (or beyond if clampSourcePos is false).
     * @param sourceStart value for sourcePos corresponding to targetStart
     * @param sourceEnd value for sourcePos corresponding to targetEnd
     * @param targetStart result value when sourcePos = sourceStart
     * @param targetEnd result value when sourcePos = sourceEnd
     * @param clampSourcePos if true, sourcePos is clamped to the sourceStart..sourceEnd range,
     *               if false, sourcePos may be outside that range and the result may be outside the targetStart..targetEnd range.
     * @param interpolationRemap determines what portion of the tweening curve to use.
     * @return the value between targetStart and targetEnd (may exceed that range as well), at point sourcePos.
     */
    double interpolate(double sourcePos,
                       double sourceStart,
                       double sourceEnd,
                       double targetStart,
                       double targetEnd,
                       boolean clampSourcePos,
                       InterpolationRemap interpolationRemap);

}
