package org.flowutils;

import org.flowutils.regression.RegressionFunction;
import org.flowutils.regression.RegressionUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

/**
 *
 */
public class RegressionUtilsTest {
    @Test
    public void testRegression() throws Exception {
        assertRegression(3, false, 1f, 2f);
        assertRegression(2+3, false, 2+1f, 2+2f);
        assertRegression(4, true, 1f, 2f);
        assertRegression(8, true, 1f, 2f, 4f);
        assertRegression(2.5f, false, 1f, null, 2f);
        assertRegression(2f, false, 1f, 4f, 1f);
        assertRegression(1f, true, 4f, 2f);
        assertRegression(3f, true, 3f, 3f, 3f);
        assertRegression(3f, false, 3f, 3f, 3f);

        // Test with start index and count
        assertRegressionWithOffsetAndSize(3f, 1, 2, false, 5f, 3f, 3f);
        assertRegressionWithOffsetAndSize(3f, 1, 2, false, 5f, 3f, 3f, 2f);
        assertRegressionWithOffsetAndSize(3f, 0, 2, false, 3f, 3f, 55f, 2f);
        assertRegressionWithOffsetAndSize(3f, 0, 3, false, 3f, 3f, 3f, 44f);
        assertRegressionWithOffsetAndSize(3f, 1, 3, false, 44f, 3f, 3f, 3f, 3f);
        assertRegressionWithOffsetAndSize(3f, 1, 3, false, 44f, 3f, null, 3f, 3f);


        // Test with too little data
        assertRegressionIsNull(false);
        assertRegressionIsNull(false, 1f);
        assertRegressionIsNull(false, 1f, null);
        assertRegressionIsNull(false, null, 1f);
        assertRegressionIsNull(false, null, 1f, null, null);
        assertRegressionIsNull(false, null, null, null);
        assertRegressionIsNull(false, null, null);

        assertRegressionThrowsErrorWithSizeAndOffset(1, 2, false, 1f, 1f);
        assertRegressionThrowsErrorWithSizeAndOffset(0, 3, false, 1f, 1f);
        assertRegressionThrowsErrorWithSizeAndOffset(3, 3, false, 1f, 1f, null, null, null);
        assertRegressionThrowsErrorWithSizeAndOffset(0, 2, false, 1f);
    }

    private void assertRegression(float expectedNextValue, final boolean exponential, Float ... data) {
        final RegressionFunction regressionFunction = RegressionUtils.calculateRegression(exponential, data);
        Assert.assertEquals(expectedNextValue, regressionFunction.calculateValue(data.length), 0.0001);
    }

    private void assertRegressionWithOffsetAndSize(float expectedNextValue,
                                                   int startIndex,
                                                   int count,
                                                   final boolean exponential,
                                                   Float... data) {
        final RegressionFunction regressionFunction = RegressionUtils.calculateRegression(exponential, startIndex, count, Arrays.asList(data));
        Assert.assertEquals(expectedNextValue, regressionFunction.calculateValue(data.length), 0.0001);
    }

    private void assertRegressionIsNull(final boolean exponential, Float ... data) {
        Assert.assertNull(RegressionUtils.calculateRegression(exponential, data));
    }

    private void assertRegressionThrowsErrorWithSizeAndOffset(int startIndex, int count, final boolean exponential, Float ... data) {
        try {
            RegressionUtils.calculateRegression(exponential, startIndex, count, Arrays.asList(data));
            Assert.fail("Should have thrown an IllegalArgumentException");
        }
        catch (IllegalArgumentException e) {
            // ok
        }
    }
}
