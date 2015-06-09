package org.flowutils.regression;

import org.flowutils.Check;

/**
 * A regression function calculated for some data.
 * May be linear or exponential.
 */
public final class RegressionFunction {
    private final float a;
    private final float b;
    private final boolean exponential;

    /**
     * Creates a regression function of the form a + b * x if exponential is false,
     * or exp(a + b * x) if exponential is true.
     */
    public RegressionFunction(float a, float b, boolean exponential) {
        Check.normalNumber(a, "a");
        Check.normalNumber(b, "b");

        this.a = a;
        this.b = b;
        this.exponential = exponential;
    }

    public float getA() {
        return a;
    }

    public float getB() {
        return b;
    }

    /**
     * @return true if exponential regression function, false if linear regression function.
     */
    public boolean isExponential() {
        return exponential;
    }

    /**
     * @return the value of the regression function at the specified x coordinate.
     */
    public float calculateValue(float x) {
        if (!exponential) {
            return a + b * x;
        } else {
            return (float) Math.exp(a + b * x);
        }
    }

    /**
     * @param y0 start y.  Note that if the regression function is exponential, this can not be zero (as exp(log(0)) is not defined).
     * @param dx number of x steps to extrapolate from the start y value.
     * @return the value extrapolated assuming a start at y0 and going dx steps forward (or back if negative).
     */
    public float extrapolateValue(float y0, float dx) {
        if (!exponential) {
            return y0 + b * dx;
        } else {
            return (float) Math.exp(Math.log(y0) + b * dx);
        }
    }
}
