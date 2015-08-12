package org.flowutils;

import static java.lang.Math.*;

/**
 * Utility functions related to math.
 */
public final class MathUtils {

    /**
     * Tau is 2 Pi, see http://www.tauday.com
     */
    public static final double Tau = PI * 2;

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
     * Smoothly interpolate between the start and end values, using cosine interpolation.
     * @param t 0 corresponds to start, 1 to end.
     *          Values smaller than zero return the start value, and values greater than one return the end value.
     * @return smoothly interpolated value
     */
    public static float mixSmooth(float t, float start, float end) {
        if (t <= 0) return start;
        else if (t >= 1) return end;
        else {
            float projectedT = (float) (0.5 - cos(t * PI) * 0.5);
            return start + projectedT * (end - start);
        }
    }

    /**
     * Smoothly interpolate between the start and end values, using cosine interpolation.
     * @param t 0 corresponds to start, 1 to end.
     *          Values smaller than zero return the start value, and values greater than one return the end value.
     * @return smoothly interpolated value
     */
    public static double mixSmooth(double t, double start, double end) {
        if (t <= 0) return start;
        else if (t >= 1) return end;
        else {
            double projectedT = (0.5 - cos(t * PI) * 0.5);
            return start + projectedT * (end - start);
        }
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
     * @return relative position of t between start and end.
     *         if t == start, returns 0, if t == end, returns 1, etc.
     *         In case start equals end, returns 0.5.
     */
    public static double relPos(int t, int start, int end) {
        if (end == start) return 0.5;
        else return (double) (t - start) / (end - start);
    }

    /**
     * @return relative position of t between start and end.
     *         if t == start, returns 0, if t == end, returns 1, etc.
     *         In case start equals end, returns 0.5.
     */
    public static double relPos(long t, long start, long end) {
        if (end == start) return 0.5;
        else return (double) (t - start) / (end - start);
    }

    /**
     * @return relative position of t between start and end, clamped to the range 0..1 (inclusive).
     *         if t == start, returns 0, if t == end, returns 1, etc.
     *         In case start equals end, returns 0.5.
     */
    public static float relPosAndClamp(float t, float start, float end) {
        if (end == start) return 0.5f;
        else return clamp0To1((t - start) / (end - start));
    }

    /**
     * @return relative position of t between start and end, clamped to the range 0..1 (inclusive).
     *         if t == start, returns 0, if t == end, returns 1, etc.
     *         In case start equals end, returns 0.5.
     */
    public static double relPosAndClamp(double t, double start, double end) {
        if (end == start) return 0.5;
        else return clamp0To1((t - start) / (end - start));
    }

    /**
     * @return relative position of t between start and end, clamped to the range 0..1 (inclusive).
     *         if t == start, returns 0, if t == end, returns 1, etc.
     *         In case start equals end, returns 0.5.
     */
    public static double relPosAndClamp(int t, int start, int end) {
        if (end == start) return 0.5;
        else return clamp0To1((double) (t - start) / (end - start));
    }

    /**
     * @return relative position of t between start and end, clamped to the range 0..1 (inclusive).
     *         if t == start, returns 0, if t == end, returns 1, etc.
     *         In case start equals end, returns 0.5.
     */
    public static double relPosAndClamp(long t, long start, long end) {
        if (end == start) return 0.5;
        else return clamp0To1((double) (t - start) / (end - start));
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
     * Smoothly maps a value within a source range to the corresponding position in a target range.
     * Uses cosine interpolation.  If t is smaller than 0, targetStart is returned.  If t is larger than one, targetEnd is returned.
     */
    public static float mapSmooth(float t, float sourceStart, float sourceEnd, float targetStart, float targetEnd) {
        float r = relPos(t, sourceStart, sourceEnd);
        return mixSmooth(r, targetStart, targetEnd);
    }

    /**
     * Smoothly maps a value within a source range to the corresponding position in a target range.
     * Uses cosine interpolation.  If t is smaller than 0, targetStart is returned.  If t is larger than one, targetEnd is returned.
     */
    public static double mapSmooth(double t, double sourceStart, double sourceEnd, double targetStart, double targetEnd) {
        double r = relPos(t, sourceStart, sourceEnd);
        return mixSmooth(r, targetStart, targetEnd);
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
     * @return the average of the parameters.
     */
    public static float average(float v1, float v2) {
        return 0.5f * (v1 + v2);
    }

    /**
     * @return the average of the parameters.
     */
    public static double average(double v1, double v2) {
        return 0.5 * (v1 + v2);
    }

    /**
     * @return the average of the parameters.
     */
    public static float average(float v1, float v2, float v3) {
        return (v1 + v2 + v3) / 3.0f;
    }

    /**
     * @return the average of the parameters.
     */
    public static double average(double v1, double v2, double v3) {
        return (v1 + v2 + v3) / 3.0;
    }

    /**
     * @return the average of the parameters.
     */
    public static float average(float v1, float v2, float v3, float v4, float ... additionalValues) {
        float sum = v1 + v2 + v3 + v4;
        for (float additionalValue : additionalValues) {
            sum += additionalValue;
        }
        return sum / (additionalValues.length + 4);
    }

    /**
     * @return the average of the parameters.
     */
    public static double average(double v1, double v2, double v3, double v4, double ... additionalValues) {
        double sum = v1 + v2 + v3 + v4;
        for (double additionalValue : additionalValues) {
            sum += additionalValue;
        }
        return sum / (additionalValues.length + 4);
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
     * Wraps a value to the range zero (inclusive) to one (exclusive).
     * @return the fractional part of a value.
     */
    public static float wrap0To1(float value) {
        if (value >= 0 && value < 1) return value;
        else {
            value = value % 1f;
            if (value < 0) value += 1f;
            return value;
        }
    }

    /**
     * Wraps a value to the range zero (inclusive) to one (exclusive).
     * @return the fractional part of a value.
     */
    public static double wrap0To1(double value) {
        if (value >= 0 && value < 1) return value;
        else {
            value = value % 1.0;
            if (value < 0) value += 1.0;
            return value;
        }
    }

    /**
     * Wraps a value to be larger or equal to zero and smaller than the specified maximum value.
     * @throws IllegalArgumentException if maximum is smaller than minimum.
     */
    public static int wrap(int value, int max) {
        if (max < 0) throw new IllegalArgumentException("The specified maximum value ("+max+") was smaller than the minimum value (0) when trying to wrap the value ("+value+")");

        if (value >= 0 && value < max) return value;
        else {
            if (max <= 0) return 0;

            value = value % max;
            if (value < 0) value += max;
            return value;
        }
    }

    /**
     * Wraps a value to be larger or equal to zero and smaller than the specified maximum value.
     * @throws IllegalArgumentException if maximum is smaller than minimum.
     */
    public static float wrap(float value, float max) {
        if (max < 0) throw new IllegalArgumentException("The specified maximum value ("+max+") was smaller than the minimum value (0) when trying to wrap the value ("+value+")");

        if (value >= 0 && value < max) return value;
        else {
            if (max <= 0) return 0;

            value = value % max;
            if (value < 0) value += max;
            return value;
        }
    }

    /**
     * Wraps a value to be larger or equal to zero and smaller than the specified maximum value.
     * @throws IllegalArgumentException if maximum is smaller than minimum.
     */
    public static double wrap(double value, double max) {
        if (max < 0) throw new IllegalArgumentException("The specified maximum value ("+max+") was smaller than the minimum value (0) when trying to wrap the value ("+value+")");

        if (value >= 0 && value < max) return value;
        else {
            if (max <= 0) return 0;

            value = value % max;
            if (value < 0) value += max;
            return value;
        }
    }

    /**
     * Wraps a value to be larger or equal than the specified minimum value and smaller than the specified maximum value.
     * @throws IllegalArgumentException if maximum is smaller than minimum.
     */
    public static int wrap(int value, int min, int max) {
        if (max < min) throw new IllegalArgumentException("The specified maximum value ("+max+") was smaller than the minimum value ("+min+") when trying to wrap the value ("+value+")");

        if (value >= min && value < max) return value;
        else {
            int t = value - min;
            int s = max - min;

            if (s <= 0) return min;

            t = t % s;
            if (t < 0) t += s;
            return min + t;
        }
    }

    /**
     * Wraps a value to be larger or equal to the specified minimum value and smaller than the specified maximum value.
     * @throws IllegalArgumentException if maximum is smaller than minimum.
     */
    public static float wrap(float value, float min, float max) {
        if (max < min) throw new IllegalArgumentException("The specified maximum value ("+max+") was smaller than the minimum value ("+min+") when trying to wrap the value ("+value+")");

        if (value >= min && value < max) return value;
        else {
            float t = value - min;
            float s = max - min;

            if (s <= 0) return min;

            t = t % s;
            if (t < 0) t += s;
            return min + t;
        }
    }

    /**
     * Wraps a value to be larger or equal to the specified minimum value and smaller than the specified maximum value.
     * @throws IllegalArgumentException if maximum is smaller than minimum.
     */
    public static double wrap(double value, double min, double max) {
        if (max < min) throw new IllegalArgumentException("The specified maximum value ("+max+") was smaller than the minimum value ("+min+") when trying to wrap the value ("+value+")");

        if (value >= min && value < max) return value;
        else {
            double t = value - min;
            double s = max - min;

            if (s <= 0) return min;

            t = t % s;
            if (t < 0) t += s;
            return min + t;
        }
    }

    /**
     * Clamps a value to be larger than the specified minimum value and smaller than the specified maximum value.
     * @throws IllegalArgumentException if maximum is smaller than minimum.
     */
    public static int clamp(int value, int min, int max) {
        if (max < min) throw new IllegalArgumentException("The specified maximum value ("+max+") was smaller than the minimum value ("+min+") when trying to clamp the value ("+value+")");

        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    /**
     * Clamps a value to be larger than the specified minimum value and smaller than the specified maximum value.
     * @throws IllegalArgumentException if maximum is smaller than minimum.
     */
    public static float clamp(float value, float min, float max) {
        if (max < min) throw new IllegalArgumentException("The specified maximum value ("+max+") was smaller than the minimum value ("+min+") when trying to clamp the value ("+value+")");

        if (value < min) return min;
        if (value > max) return max;
        return value;
    }

    /**
     * Clamps a value to be larger than the specified minimum value and smaller than the specified maximum value.
     * @throws IllegalArgumentException if maximum is smaller than minimum.
     */
    public static double clamp(double value, double min, double max) {
        if (max < min) throw new IllegalArgumentException("The specified maximum value ("+max+") was smaller than the minimum value ("+min+") when trying to clamp the value ("+value+")");

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
        return (float) sqrt(x * x + y * y);
    }

    /**
     * @return distance between two points in a 2D coordinate system.
     */
    public static double distance(double  x1, double y1, double x2, double y2) {
        double x = x2 - x1;
        double y = y2 - y1;
        return sqrt(x * x + y * y);
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

    private MathUtils() {
    }

    /**
     * Rounds to closest integer.
     */
    public static int round(float value) {
        value += 0.5f;

        // Use the fast floor formula
        return value < 0.0f ? (int)(value - 1) : (int) value;
    }

    /**
     * Rounds to closest integer.
     */
    public static int round(double value) {
        value += 0.5;

        // Use the fast floor formula
        return value < 0.0f ? (int)(value - 1) : (int) value;
    }

    /**
     * @return a modulus b, with always a positive result (if the result would be negative, b is added to it).
     */
    public static int modPositive(int a, int b) {
        int result = a % b;
        if (result >= 0) {
            return result;
        }
        else {
            return result + b;
        }
    }

    /**
     * @return a modulus b, with always a positive result (if the result would be negative, b is added to it).
     */
    public static long modPositive(long a, long b) {
        long result = a % b;
        if (result >= 0) {
            return result;
        }
        else {
            return result + b;
        }
    }

    /**
     * @return a modulus b, with always a positive result (if the result would be negative, b is added to it).
     */
    public static float modPositive(float a, float b) {
        float result = a % b;
        if (result >= 0) {
            return result;
        }
        else {
            return result + b;
        }
    }

    /**
     * @return a modulus b, with always a positive result (if the result would be negative, b is added to it).
     */
    public static double modPositive(double a, double b) {
        double result = a % b;
        if (result >= 0) {
            return result;
        }
        else {
            return result + b;
        }
    }

    /**
     * A tunable normalized sigmoid function that return values in the -1..1 range for inputs in the -1..1 range.
     *
     * See http://www.slideshare.net/dinodini1/simplest-ai-trick-gdc2013-dino-v2-1 for reference.
     *
     * @param x input, clamped to the -1 to 1 range.
     * @param sharpness the sharpness of the curve, in the range -1..1.
     *                  When 0, the result is equal to the input, when it goes towards 1 the S shape gets sharper (more output directed towards -1 or +1, less towards 0).
     *                  If negative, it will produce an inverse S curve that gets shallower towards -1 (more output directed towards 0, less towards -1 or 1).
     * @return a value between -1 and 1, depending on the position of x between -1 and 1.
     */
    public static double sigmoid(double x, double sharpness) {
        final double k;

        // Clamp x
        if (x < -1.0) x = -1.0;
        else if (x > 1.0) x = 1.0;

        // Calculate k factor from a more intuitive sharpness parameter, and also handle edge cases where we would get divisions by zero.
        if (sharpness <= -1) {
            if (x <= -1) return -1;
            else if (x >= 1) return 1;
            else return 0;
        }
        else if (sharpness >= 1) {
            if (x < 0) return -1;
            else if (x > 0) return 1;
            else return 0;
        }
        else if (sharpness <= -0.5) {
            k = 2.0 + 2.0 * sharpness;
        }
        else if (sharpness < 0) {
            k = -1.0 / (2.0 * sharpness);
        }
        else if (sharpness >= 0.5) {
            k = -3.0 + 2.0 * sharpness;
        }
        else if (sharpness > 0) {
            k = -1.0 / sharpness;
        }
        else return x;  // Sharpness was zero

        // Tunable normalized sigmoid function:
        final double absX = x < 0 ? -x : x;
        final double sign = (x >= 0 ? 1 : -1);
        return sign * absX * k / (1.0 + k - absX);

    }

}
