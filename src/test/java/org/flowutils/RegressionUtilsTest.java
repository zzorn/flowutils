package org.flowutils;

import org.flowutils.regression.RegressionFunction;
import org.flowutils.regression.RegressionUtils;
import org.junit.Assert;
import org.junit.Test;

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
    }

    private void assertRegression(float expectedNextValue, final boolean exponential, Float ... data) {
        final RegressionFunction regressionFunction = RegressionUtils.calculateRegression(exponential, data);
        Assert.assertEquals(expectedNextValue, regressionFunction.calculateValue(data.length), 0.0001);
    }
}
