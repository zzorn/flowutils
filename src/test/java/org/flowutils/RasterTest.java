package org.flowutils;

import org.flowutils.raster.field.multi.MultiFieldBase;
import org.flowutils.raster.field.single.Field;
import org.flowutils.raster.raster.multi.CompositeMultiRaster;
import org.flowutils.raster.raster.multi.InterleavedMultiRaster;
import org.flowutils.raster.raster.multi.MultiRaster;
import org.flowutils.raster.raster.single.Raster;
import org.flowutils.rectangle.ImmutableRectangle;
import org.flowutils.rectangle.intrectangle.ImmutableIntRectangle;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RasterTest {

    @Test
    public void testSampleMultiField() throws Exception {
        TestField testField = new TestField();
        assertEquals(0f, testField.getValue(0, 0, Symbol.get("rising")), 0.001f);
        assertEquals(1f, testField.getValue(1, 0, Symbol.get("rising")), 0.001f);
        assertEquals(2f, testField.getValue(2, 0, Symbol.get("rising")), 0.001f);
        assertEquals(102f, testField.getValue(2, 1, Symbol.get("rising")), 0.001f);

        assertEquals(1f, testField.getValue(0, 0, Symbol.get("one")), 0.001f);
        assertEquals(1f, testField.getValue(2, 1, Symbol.get("one")), 0.001f);
    }

    @Test
    public void testSampleFieldChannel() throws Exception {
        TestField testField = new TestField();

        final Field risingChannel = testField.getChannel(Symbol.get("rising"));

        assertEquals(0f, risingChannel.getValue(0, 0), 0.001f);
        assertEquals(1f, risingChannel.getValue(1, 0), 0.001f);
        assertEquals(2f, risingChannel.getValue(2, 0), 0.001f);
        assertEquals(102f, risingChannel.getValue(2, 1), 0.001f);
    }

    @Test
    public void testRenderToRaster() throws Exception {
        checkRenderToRaster(new InterleavedMultiRaster(4, 4, TestField.ONE, TestField.RISING));
        checkRenderToRaster(new CompositeMultiRaster(4, 4, TestField.ONE, TestField.RISING));
    }

    @Test
    public void testRasterChannel() throws Exception {
        checkRasterChannel(new InterleavedMultiRaster(4, 4, TestField.ONE, TestField.RISING));
        checkRasterChannel(new CompositeMultiRaster(4, 4, TestField.ONE, TestField.RISING));
    }

    @Test
    public void testRenderToRasterSubArea() throws Exception {
        checkRenderSubArea(new InterleavedMultiRaster(4, 4, TestField.ONE, TestField.RISING));
        checkRenderSubArea(new CompositeMultiRaster(4, 4, TestField.ONE, TestField.RISING));
    }

    @Test
    public void testRenderScaled() throws Exception {
        checkRenderScaled(new InterleavedMultiRaster(4, 4, TestField.ONE, TestField.RISING));
        checkRenderScaled(new CompositeMultiRaster(4, 4, TestField.ONE, TestField.RISING));
    }

    private void checkRenderToRaster(MultiRaster raster) {
        TestField testField = new TestField();

        testField.renderToRaster(raster, new ImmutableRectangle(0, 0, 3, 3));

        assertEquals(0f, raster.getValue(0, 0, TestField.RISING), 0.001f);
        assertEquals(1f, raster.getValue(1, 0, TestField.RISING), 0.001f);
        assertEquals(3f, raster.getValue(3, 0, TestField.RISING), 0.001f);
        assertEquals(103f, raster.getValue(3, 1, TestField.RISING), 0.001f);
        assertEquals(303f, raster.getValue(3, 3, TestField.RISING), 0.001f);

        assertEquals(1f, raster.getValue(0, 0, TestField.ONE), 0.001f);
        assertEquals(1f, raster.getValue(1, 0, TestField.ONE), 0.001f);
        assertEquals(1f, raster.getValue(2, 3, TestField.ONE), 0.001f);
    }

    private void checkRasterChannel(MultiRaster raster) {TestField testField = new TestField();

        testField.renderToRaster(raster, new ImmutableRectangle(0, 0, 3, 3));

        final Raster risingChannel = raster.getChannel(Symbol.get("rising"));

        assertEquals(0f, risingChannel.getValue(0, 0), 0.001f);
        assertEquals(1f, risingChannel.getValue(1, 0), 0.001f);
        assertEquals(3f, risingChannel.getValue(3, 0), 0.001f);
        assertEquals(103f, risingChannel.getValue(3, 1), 0.001f);
        assertEquals(303f, risingChannel.getValue(3, 3), 0.001f);
    }

    private void checkRenderSubArea(MultiRaster raster) {
        TestField testField = new TestField();

        testField.renderToRaster(raster, new ImmutableRectangle(1,1, 2, 2), new ImmutableIntRectangle(2, 2, 3, 3));

        assertEquals(0f, raster.getValue(1, 1, TestField.RISING), 0.001f);
        assertEquals(101f, raster.getValue(2, 2, TestField.RISING), 0.001f);
        assertEquals(102f, raster.getValue(3, 2, TestField.RISING), 0.001f);
        assertEquals(201f, raster.getValue(2, 3, TestField.RISING), 0.001f);
        assertEquals(202f, raster.getValue(3, 3, TestField.RISING), 0.001f);

        assertEquals(0f, raster.getValue(0, 0, TestField.ONE), 0.001f);
        assertEquals(0f, raster.getValue(2, 1, TestField.ONE), 0.001f);
        assertEquals(1f, raster.getValue(2, 3, TestField.ONE), 0.001f);
    }

    private void checkRenderScaled(MultiRaster raster) {
        TestField testField = new TestField();

        testField.renderToRaster(raster, new ImmutableRectangle(1,1, 2, 3), new ImmutableIntRectangle(1, 1, 3, 3));

        assertEquals(101f, raster.getValue(1, 1, TestField.RISING), 0.001f);
        assertEquals(201f, raster.getValue(1, 2, TestField.RISING), 0.001f);
        assertEquals(301f, raster.getValue(1, 3, TestField.RISING), 0.001f);
        assertEquals(101.5f, raster.getValue(2, 1, TestField.RISING), 0.001f);
        assertEquals(201.5f, raster.getValue(2, 2, TestField.RISING), 0.001f);
        assertEquals(301.5f, raster.getValue(2, 3, TestField.RISING), 0.001f);
        assertEquals(102f, raster.getValue(3, 1, TestField.RISING), 0.001f);
        assertEquals(202f, raster.getValue(3, 2, TestField.RISING), 0.001f);
        assertEquals(302f, raster.getValue(3, 3, TestField.RISING), 0.001f);
    }

    private static class TestField extends MultiFieldBase {
        private static final Symbol RISING = Symbol.get("rising");
        private static final Symbol ONE = Symbol.get("one");
        private static final Symbol DROPPING = Symbol.get("dropping");

        private TestField() {
            super(RISING, ONE, DROPPING);
        }

        @Override public float getValue(double x, double y, Symbol channelId, double sampleSize) {
            if (channelId.equals(RISING)) {
                return (float) (x + y * 100);
            }
            else if (channelId.equals(ONE)) {
                return 1;
            }
            else if (channelId.equals(DROPPING)) {
                return (float) (-x - y * 100);
            }
            else {
                throw new IllegalArgumentException("Unknown channel " + channelId);
            }
        }
    }
}
