package org.flowutils;

/**
 * Utility functions related to math.
 */
public final class Maths {

    /**
     * Tau is 2 Pi, see http://www.tauday.com
     */
    public static final double Tau = Math.PI * 2;

    /**
     * Floating point version of Tau.
     */
    public static final float TauFloat = (float) Tau;

    /**
     * Interpolate between the start and end values (and beyond).
     * @param t 0 corresponds to start, 1 to end.
     * @return interpolated value
     */
    public static float mix(float t, float start, float end) {
        return start + t * (end - start);
    }

    /**
     * Interpolate between the start and end values (and beyond).
     * @param t 0 corresponds to start, 1 to end.
     * @return interpolated value
     */
    public static double mix(double t, double start, double end) {
        return start + t * (end - start);
    }

    /**
     * Interpolate between the start and end values.
     * Clamps output to be within the range formed by start and end.
     *
     * @param t 0 corresponds to start, 1 to end.
     * @return interpolated value, clamped to range formed by start and end.
     */
    public static float mixAndClamp(float t, float start, float end) {
        return clampToRange(start + t * (end - start), start, end);
    }

    /**
     * Interpolate between the start and end values.
     * Clamps output to be within the range formed by start and end.
     *
     * @param t 0 corresponds to start, 1 to end.
     * @return interpolated value, clamped to range formed by start and end.
     */
    public static double mixAndClamp(double t, double start, double end) {
        return clampToRange(start + t * (end - start), start, end);
    }

    /**
     * @return relative position of t between start and end.
     *         if t == start, returns 0, if t == end, returns 1, etc.
     *         In case start equals end, returns 0.5.
     */
    public static float relPos(float t, float start, float end) {
        if (end == start) return 0.5f;
        else return (t - start) / (end - start);
    }

    /**
     * @return relative position of t between start and end.
     *         if t == start, returns 0, if t == end, returns 1, etc.
     *         In case start equals end, returns 0.5.
     */
    public static double relPos(double t, double start, double end) {
        if (end == start) return 0.5;
        else return (t - start) / (end - start);
    }

    /**
     * Maps a value within a source range to the corresponding position in a target range.
     */
    public static float map(float t, float sourceStart, float sourceEnd, float targetStart, float targetEnd) {
        float r = relPos(t, sourceStart, sourceEnd);
        return mix(r, targetStart, targetEnd);
    }

    /**
     * Maps a value within a source range to the corresponding position in a target range.
     */
    public static double map(double t, double sourceStart, double sourceEnd, double targetStart, double targetEnd) {
        double r = relPos(t, sourceStart, sourceEnd);
        return mix(r, targetStart, targetEnd);
    }

    /**
     * Maps a value within a source range to the corresponding position in a target range.
     * Clamps the result to the target range.
     */
    public static float mapAndClamp(float t, float sourceStart, float sourceEnd, float targetStart, float targetEnd) {
        float r = relPos(t, sourceStart, sourceEnd);
        return clampToRange(mix(r, targetStart, targetEnd), targetStart, targetEnd);
    }

    /**
     * Maps a value within a source range to the corresponding position in a target range.
     * Clamps the result to the target range.
     */
    public static double mapAndClamp(double t, double sourceStart, double sourceEnd, double targetStart, double targetEnd) {
        double r = relPos(t, sourceStart, sourceEnd);
        return clampToRange(mix(r, targetStart, targetEnd), targetStart, targetEnd);
    }


    /**
     * Converts an angle in radians to degrees.
     */
    public static float toDegrees(float angleRad) {
        return (float)(360.0 * angleRad / Tau);
    }

    /**
     * Converts an angle in radians to degrees.
     */
    public static double toDegrees(double angleRad) {
        return (360.0 * angleRad / Tau);
    }

    /**
     * Converts an angle in radians to degrees.
     */
    public static float toRadians(float angleDeg) {
        return (float) ((angleDeg / 360.0) * Tau);
    }

    /**
     * Converts an angle in radians to degrees.
     */
    public static double toRadians(double angleDeg) {
        return (angleDeg / 360.0) * Tau;
    }

    /**
     * Clamps a value to be larger than the specified minimum value and smaller than the specified maximum value.
     */
    public static int clamp(int value, int min, int max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    /**
     * Clamps a value to be larger than the specified minimum value and smaller than the specified maximum value.
     */
    public static float clamp(float value, float min, float max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    /**
     * Clamps a value to be larger than the specified minimum value and smaller than the specified maximum value.
     */
    public static double clamp(double value, double min, double max) {
        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    /**
     * Clamps a value to the range formed by a and b.
     * The order that the range is specified does not matter,
     * the smallest of a and b is used as minimum, and the largest as maximum.
     */
    public static int clampToRange(int value, int a, int b) {
        int min = a;
        int max = b;
        if (a > b) {
            min = b;
            max = a;
        }

        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    /**
     * Clamps a value to the range formed by a and b.
     * The order that the range is specified does not matter,
     * the smallest of a and b is used as minimum, and the largest as maximum.
     */
    public static float clampToRange(float value, float a, float b) {
        float min = a;
        float max = b;
        if (a > b) {
            min = b;
            max = a;
        }

        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    /**
     * Clamps a value to the range formed by a and b.
     * The order that the range is specified does not matter,
     * the smallest of a and b is used as minimum, and the largest as maximum.
     */
    public static double clampToRange(double value, double a, double b) {
        double min = a;
        double max = b;
        if (a > b) {
            min = b;
            max = a;
        }

        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    /**
     * @return the fractional part of a value.
     */
    public static float roll0To1(float value) {
        return (float)(value - fastFloor(value));
    }

    /**
     * @return the fractional part of a value.
     */
    public static double roll0To1(double value) {
        return value - fastFloor(value);
    }

    /**
     * @return the range clamped to the 0..1 range (inclusive).
     */
    public static float clamp0To1(float value) {
        if (value < 0) return 0;
        if (value > 1) return 1;
        return value;
    }

    /**
     * @return the range clamped to the 0..1 range (inclusive).
     */
    public static double clamp0To1(double value) {
        if (value < 0) return 0;
        if (value > 1) return 1;
        return value;
    }

    /**
     * @return the range clamped to the -1..1 range (inclusive).
     */
    public static float clampMinus1To1(float value) {
        if (value < -1) return -1;
        if (value > 1) return 1;
        return value;
    }

    /**
     * @return the range clamped to the -1..1 range (inclusive).
     */
    public static double clampMinus1To1(double value) {
        if (value < -1) return -1;
        if (value > 1) return 1;
        return value;
    }

    /**
     * @return distance between two points in a 2D coordinate system.
     */
    public static float distance(float x1, float y1, float x2, float y2) {
        float x = x2 - x1;
        float y = y2 - y1;
        return (float) Math.sqrt(x * x + y * y);
    }

    /**
     * @return distance between two points in a 2D coordinate system.
     */
    public static double distance(double  x1, double y1, double x2, double y2) {
        double x = x2 - x1;
        double y = y2 - y1;
        return Math.sqrt(x * x + y * y);
    }

    /**
     * @return squared distance between two points in a 2D coordinate system.
     */
    public static float distanceSquared(float x1, float y1, float x2, float y2) {
        float x = x2 - x1;
        float y = y2 - y1;
        return x*x + y*y;
    }

    /**
     * @return squared distance between two points in a 2D coordinate system.
     */
    public static double distanceSquared(double x1, double y1, double x2, double y2) {
        double x = x2 - x1;
        double y = y2 - y1;
        return x*x + y*y;
    }

    /**
     * Fast floor function (should be faster than Math.floor()).
     */
    public static int fastFloor(final float value) {
        return value < 0.0f ? (int)(value - 1) : (int) value;
    }

    /**
     * Fast floor function (should be faster than Math.floor()).
     */
    public static int fastFloor(final double value) {
        return value < 0.0 ? (int)(value - 1) : (int) value;
    }

    private Maths() {
    }
}
