package org.flowutils;


import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Unit test for Maths.
 */
public class MathsTest {

    @org.junit.Before
    public void setUp() throws Exception {
    }

    @Test
    public void testWrap0to1() throws Exception {
        shouldEqual(0, Maths.wrap0To1(0));
        shouldEqual(0, Maths.wrap0To1(1));
        shouldEqual(0, Maths.wrap0To1(2));
        shouldEqual(0, Maths.wrap0To1(-1));
        shouldEqual(0.1, Maths.wrap0To1(0.1));
        shouldEqual(0.9, Maths.wrap0To1(-0.1));
        shouldEqual(0.1, Maths.wrap0To1(1.1));
        shouldEqual(0.1, Maths.wrap0To1(-123.9));
    }

    @Test
    public void testWrap() throws Exception {
        shouldEqual(0,   Maths.wrap(0, 0, 1));
        shouldEqual(0.3, Maths.wrap(0.3, 0, 1));
        shouldEqual(0,   Maths.wrap(1, 0, 1));

        shouldEqual(35,       Maths.wrap(35, 20, 100));
        shouldEqual(100-5,    Maths.wrap(15, 20, 100));
        shouldEqual(100-20-4, Maths.wrap(-4, 20, 100));
        shouldEqual(20+15,    Maths.wrap(115, 20, 100));

        shouldEqual(0.1, Maths.wrap(0.1, 0, 1));
        shouldEqual(0.1, Maths.wrap(1.1, 0, 1));
        shouldEqual(0.1, Maths.wrap(1242.1, 0, 1));

        shouldEqual(0.4, Maths.wrap(-0.6, 0, 1));
        shouldEqual(0.4, Maths.wrap(-1.6, 0, 1));
        shouldEqual(0.4, Maths.wrap(-213.6, 0, 1));

        shouldEqual(-1.5+0.3, Maths.wrap(-1.5+0.3, -1.5, 1.5));
        shouldEqual(-1.5+0.3, Maths.wrap(-1.5-3*2+0.3, -1.5, 1.5));
        shouldEqual(-1.5+0.3, Maths.wrap(-1.5+3*3+0.3, -1.5, 1.5));

    }

    private void shouldEqual(final double result, final double actual) {
        assertEquals(result, actual, 0.0001);
    }
}
