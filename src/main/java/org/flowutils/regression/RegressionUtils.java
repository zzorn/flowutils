package org.flowutils.regression;

import org.flowutils.Check;
import org.flowutils.collections.ringbuffer.RingBuffer;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Utility functions for doing regression analysis of a set of datapoints.
 */
public final class RegressionUtils {

    /**
     * @param data data to regression fit to a line.
     *             The data is assumed to have x coordinates starting with 0 and increasing by 1 for each value.
     *             null entries are ignored (the x is still increased for them).
     * @return the regression function for the given data, or null if the data has less than two non-null values.
     */
    public static RegressionFunction calculateRegression(Float ... data) {
        return calculateRegression(false, data);
    }

    /**
     * @param exponential if true, the data will be fitted to an exponential instead of a line.
     * @param data data to regression fit to a line or exponential.
     *             The data is assumed to have x coordinates starting with 0 and increasing by 1 for each value.
     *             null entries are ignored (the x is still increased for them).
     * @return the regression function for the given data, or null if the data has less than two non-null values.
     */
    public static RegressionFunction calculateRegression(boolean exponential, Float ... data) {
        return calculateRegression(exponential, 0, data.length, Arrays.asList(data));
    }

    /**
     * @param exponential if true, the data will be fitted to an exponential instead of a line.
     * @param data data to regression fit to a line or exponential.
     *             The data is assumed to have x coordinates starting with 0 and increasing by 1 for each value.
     *             null entries are ignored (the x is still increased for them).
     * @return the regression function for the given data, or null if the data has less than two non-null values.
     */
    public static RegressionFunction calculateRegression(boolean exponential, List<Float> data) {
        return calculateRegression(exponential, 0, data.size(), data);
    }

    /**
     * @param exponential if true, the data will be fitted to an exponential instead of a line.
     * @param startIndex index in the data series to start from.
     * @param count number of entries to use from the data series.
     * @param data data to regression fit to a line or exponential.
     *             The data is assumed to have x coordinates starting with 0 and increasing by 1 for each value.
     *             null entries are ignored (the x is still increased for them).
     * @return the regression function for the given data, or null if the data has less than two non-null values.
     */
    public static RegressionFunction calculateRegression(boolean exponential, int startIndex, int count, List<Float> data) {
        return calculateRegression(0, 1, exponential, startIndex, count, data);
    }

    /**
     * @param x0 x value of the first data entry.
     * @param xStep x step between data entries.
     * @param exponential if true, the data will be fitted to an exponential instead of a line.
     * @param data data to regression fit to a line or exponential.  null entries are ignored (the x is still increased for them)
     * @return the regression function for the given data, or null if the data has less than two non-null values.
     */
    public static RegressionFunction calculateRegression(float x0,
                                                         float xStep,
                                                         boolean exponential,
                                                         Float ... data ) {
        return calculateRegression(x0, xStep, exponential, 0, data.length, Arrays.asList(data));
    }

    /**
     * @param x0 x value of the first data entry.
     * @param xStep x step between data entries.
     * @param exponential if true, the data will be fitted to an exponential instead of a line.
     * @param startIndex index in the data series to start from.
     * @param count number of entries to use from the data series.
     * @param data data to regression fit to a line or exponential.  null entries are ignored (the x is still increased for them)
     * @return the regression function for the given data, or null if the data has less than two non-null values.
     */
    public static RegressionFunction calculateRegression(float x0,
                                                         float xStep,
                                                         boolean exponential,
                                                         int startIndex,
                                                         int count,
                                                         List<Float> data) {
        Check.notZero(xStep, "xStep", 0);
        Check.positiveOrZero(startIndex, "startIndex");


        // Calculate x and y averages
        float sumY = 0;
        float sumX = 0;
        int elementCount = 0;
        float x = x0;
        for (int i = 0; i < count; i++) {
            Float y = data.get(startIndex + i);
            if (y != null) {
                if (exponential) y = getLogarithmOfY(y);
                sumX += x;
                sumY += y;
                elementCount++;
            }

            x += xStep;
        }

        if (elementCount <= 1) return null;

        float averageX = sumX / elementCount;
        float averageY = sumY / elementCount;

        //if (elementCount == 1) return new Line(averageY, 0); // With only one element, assume sideways line.

        // Calculate regression
        x = x0;
        float bUpper = 0;
        float bLower = 0;
        for (int i = 0; i < count; i++) {
            Float y = data.get(startIndex + i);
            if (y != null) {
                if (exponential) y = getLogarithmOfY(y);
                bUpper += (x - averageX) * (y - averageY);
                bLower += (x - averageX) * (x - averageX);
            }

            x += xStep;
        }

        if (bLower == 0) throw new IllegalStateException("Unexpectedly the divisor was zero");

        float b = bUpper / bLower;
        float a = averageY - b * averageX;

        return new RegressionFunction(a, b, exponential);
    }

    /**
     * @param x0 x value of the first data entry.
     * @param xStep x step between data entries.
     * @param exponential if true, the data will be fitted to an exponential instead of a line.
     * @param startIndex index in the data series to start from.
     * @param count number of entries to use from the data series.
     * @param data data to regression fit to a line or exponential.  null entries are ignored (the x is still increased for them)
     * @return the regression function for the given data, or null if the data has less than two non-null values.
     */
    public static RegressionFunction calculateRegression(float x0,
                                                         float xStep,
                                                         boolean exponential,
                                                         int startIndex,
                                                         int count,
                                                         RingBuffer<Float> data) {
        Check.notZero(xStep, "xStep", 0);
        Check.positiveOrZero(startIndex, "startIndex");


        // Calculate x and y averages
        float sumY = 0;
        float sumX = 0;
        int elementCount = 0;
        float x = x0;
        Iterator<Float> iterator = getIteratorAtStartIndex(startIndex, data);
        for (int i = 0; i < count; i++) {
            Float y = iterator.next();
            if (y != null) {
                if (exponential) y = getLogarithmOfY(y);
                sumX += x;
                sumY += y;
                elementCount++;
            }

            x += xStep;
        }

        if (elementCount <= 1) return null;

        float averageX = sumX / elementCount;
        float averageY = sumY / elementCount;

        //if (elementCount == 1) return new Line(averageY, 0); // With only one element, assume sideways line.

        // Calculate regression
        x = x0;
        float bUpper = 0;
        float bLower = 0;
        iterator = getIteratorAtStartIndex(startIndex, data);
        for (int i = 0; i < count; i++) {
            Float y = iterator.next();
            if (y != null) {
                if (exponential) y = getLogarithmOfY(y);
                bUpper += (x - averageX) * (y - averageY);
                bLower += (x - averageX) * (x - averageX);
            }

            x += xStep;
        }

        if (bLower == 0) throw new IllegalStateException("Unexpectedly the divisor was zero");

        float b = bUpper / bLower;
        float a = averageY - b * averageX;

        return new RegressionFunction(a, b, exponential);
    }

    private static Float getLogarithmOfY(Float y) {
        if (y == 0) throw new IllegalStateException("Can not do exponential regression for values close to or exactly zero");
        return (Float) (float) Math.log(y);
    }

    private static Iterator<Float> getIteratorAtStartIndex(int startIndex, Iterable<Float> data) {
        // Get to correct place in data series
        final Iterator<Float> iterator = data.iterator();
        int index = 0;
        while (index < startIndex) {
            iterator.next();
            index++;
        }
        return iterator;
    }

    private RegressionUtils() {
    }

}
