package org.flowutils;


import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test for MathUtils.
 */
public class MathUtilsTest {

    @org.junit.Before
    public void setUp() throws Exception {
    }

    @Test
    public void testWrap0to1() throws Exception {
        shouldEqual(0, MathUtils.wrap0To1(0));
        shouldEqual(0, MathUtils.wrap0To1(1));
        shouldEqual(0, MathUtils.wrap0To1(2));
        shouldEqual(0, MathUtils.wrap0To1(-1));
        shouldEqual(0.1, MathUtils.wrap0To1(0.1));
        shouldEqual(0.9, MathUtils.wrap0To1(-0.1));
        shouldEqual(0.1, MathUtils.wrap0To1(1.1));
        shouldEqual(0.1, MathUtils.wrap0To1(-123.9));
    }

    @Test
    public void testWrap() throws Exception {
        shouldEqual(0,   MathUtils.wrap(0, 0, 1));
        shouldEqual(0.3, MathUtils.wrap(0.3, 0, 1));
        shouldEqual(0,   MathUtils.wrap(1, 0, 1));

        shouldEqual(35,       MathUtils.wrap(35, 20, 100));
        shouldEqual(100-5,    MathUtils.wrap(15, 20, 100));
        shouldEqual(100-20-4, MathUtils.wrap(-4, 20, 100));
        shouldEqual(20+15,    MathUtils.wrap(115, 20, 100));

        shouldEqual(0.1, MathUtils.wrap(0.1, 0, 1));
        shouldEqual(0.1, MathUtils.wrap(1.1, 0, 1));
        shouldEqual(0.1, MathUtils.wrap(1242.1, 0, 1));

        shouldEqual(0.4, MathUtils.wrap(-0.6, 0, 1));
        shouldEqual(0.4, MathUtils.wrap(-1.6, 0, 1));
        shouldEqual(0.4, MathUtils.wrap(-213.6, 0, 1));

        shouldEqual(-1.5+0.3, MathUtils.wrap(-1.5 + 0.3, -1.5, 1.5));
        shouldEqual(-1.5+0.3, MathUtils.wrap(-1.5 - 3 * 2 + 0.3, -1.5, 1.5));
        shouldEqual(-1.5+0.3, MathUtils.wrap(-1.5 + 3 * 3 + 0.3, -1.5, 1.5));

    }

    @Test
    public void testRound() throws Exception {
        assertEquals(5, MathUtils.round(5, 1));
        assertEquals(10, MathUtils.round(11, 1));
        assertEquals(20, MathUtils.round(15, 1));
        assertEquals(20, MathUtils.round(19, 1));
        assertEquals(20, MathUtils.round(21, 1));
        assertEquals(15, MathUtils.round(15, 2));
        assertEquals(80, MathUtils.round(78, 1));
        assertEquals(123000, MathUtils.round(123456, 3));
        assertEquals(-123000, MathUtils.round(-123456, 3));
        assertEquals(988000, MathUtils.round(987654, 3));
        assertEquals(-988000, MathUtils.round(-987654, 3));
        assertEquals(1000000, MathUtils.round(999999, 3));
        assertEquals(-1000000, MathUtils.round(-999999, 3));
        assertEquals(-20, MathUtils.round(-15, 1));
        assertEquals(-20, MathUtils.round(-19, 1));
        assertEquals(-10, MathUtils.round(-11, 1));

    }

    private void shouldEqual(final double result, final double actual) {
        assertEquals(result, actual, 0.0001);
    }
}
