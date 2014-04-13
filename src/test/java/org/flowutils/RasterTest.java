package org.flowutils;

import org.flowutils.raster.field.RenderListener;
import org.flowutils.raster.field.multi.CompositeMultiField;
import org.flowutils.raster.field.multi.MultiField;
import org.flowutils.raster.field.multi.MultiFieldBase;
import org.flowutils.raster.field.multi.MultiRasterField;
import org.flowutils.raster.field.single.Field;
import org.flowutils.raster.field.single.FieldBase;
import org.flowutils.raster.raster.multi.CompositeMultiRaster;
import org.flowutils.raster.raster.multi.InterleavedMultiRaster;
import org.flowutils.raster.raster.multi.MultiRaster;
import org.flowutils.raster.raster.single.Raster;
import org.flowutils.raster.raster.single.RasterImpl;
import org.flowutils.rectangle.ImmutableRectangle;
import org.flowutils.rectangle.intrectangle.ImmutableIntRectangle;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class RasterTest {

    @Test
    public void testSampleMultiField() throws Exception {
        checkField(new TestField());

        final CompositeMultiField compositeTestField = new CompositeMultiField();
        compositeTestField.addChannel(TestField.RISING, new FieldBase() {
            @Override public float getValue(double x, double y, double sampleSize) {
                return (float) (x + y * 100);
            }
        });
        compositeTestField.addChannel(TestField.ONE, new FieldBase() {
            @Override public float getValue(double x, double y, double sampleSize) {
                return 1f;
            }
        });

        checkField(compositeTestField);
    }

    private void checkField(MultiField testField) {
        assertTrue(testField.hasChannel(Symbol.get("rising")));
        assertTrue(testField.hasChannel(Symbol.get("one")));
        assertFalse(testField.hasChannel(Symbol.get("two")));

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
    public void testRenderToMultiRaster() throws Exception {
        checkRenderToMultiRaster(new InterleavedMultiRaster(4, 4, TestField.ONE, TestField.RISING));
        checkRenderToMultiRaster(new CompositeMultiRaster(4, 4, TestField.ONE, TestField.RISING));
    }

    private void checkRenderToMultiRaster(MultiRaster raster) {
        assertTrue(raster.hasChannel(Symbol.get("rising")));
        assertTrue(raster.hasChannel(Symbol.get("one")));
        assertFalse(raster.hasChannel(Symbol.get("two")));

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

    @Test
    public void testRenderToRaster() throws Exception {
        checkRenderToRaster(new RasterImpl(4, 4));
        checkRenderToRaster(new InterleavedMultiRaster(4,
                                                       4,
                                                       TestField.ONE,
                                                       TestField.RISING).getChannel(TestField.RISING));
        checkRenderToRaster(new CompositeMultiRaster(4,
                                                     4,
                                                     TestField.ONE,
                                                     TestField.RISING).getChannel(TestField.RISING));
    }

    private void checkRenderToRaster(Raster raster) {
        TestField testField = new TestField();

        testField.getChannel(TestField.RISING).renderToRaster(raster, new ImmutableRectangle(0, 0, 3, 3));

        assertEquals(0f, raster.getValue(0, 0), 0.001f);
        assertEquals(1f, raster.getValue(1, 0), 0.001f);
        assertEquals(3f, raster.getValue(3, 0), 0.001f);
        assertEquals(103f, raster.getValue(3, 1), 0.001f);
        assertEquals(303f, raster.getValue(3, 3), 0.001f);
    }

    @Test
    public void testRenderListener() throws Exception {
        TestRenderListener testRenderListener = new TestRenderListener();

        TestField testField = new TestField();

        testField.renderToRaster(new InterleavedMultiRaster(4, 4, TestField.RISING, TestField.ONE),
                                 new ImmutableRectangle(0, 0, 3, 3),
                                 null,
                                 testRenderListener);

        assertTrue(testRenderListener.getNumCalls() > 0);
        assertEquals(1.0, testRenderListener.getLastProgress(), 0.001);
    }

    @Test
    public void testCancelRendering() throws Exception {
        TestRenderListener testRenderListener = new TestRenderListener();
        testRenderListener.setShouldCancel(true);

        TestField testField = new TestField();

        final InterleavedMultiRaster raster = new InterleavedMultiRaster(4, 4, TestField.RISING);
        testField.renderToRaster(raster, new ImmutableRectangle(0, 0, 3, 3), null, testRenderListener);

        assertEquals(1, testRenderListener.getNumCalls());
        assertTrue(testRenderListener.getLastProgress() < 1);

        assertEquals(1f, raster.getValue(1, 0, TestField.RISING), 0.001f);
        assertEquals(0f, raster.getValue(1, 1, TestField.RISING), 0.001f);
    }

    @Test
    public void testRasterChannel() throws Exception {
        checkRasterChannel(new InterleavedMultiRaster(4, 4, TestField.ONE, TestField.RISING));
        checkRasterChannel(new CompositeMultiRaster(4, 4, TestField.ONE, TestField.RISING));
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

    @Test
    public void testRenderToRasterSubArea() throws Exception {
        checkRenderSubArea(new InterleavedMultiRaster(4, 4, TestField.ONE, TestField.RISING));
        checkRenderSubArea(new CompositeMultiRaster(4, 4, TestField.ONE, TestField.RISING));
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

    @Test
    public void testRenderScaled() throws Exception {
        checkRenderScaled(new InterleavedMultiRaster(4, 4, TestField.ONE, TestField.RISING));
        checkRenderScaled(new CompositeMultiRaster(4, 4, TestField.ONE, TestField.RISING));
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

    @Test
    public void testRasterField() throws Exception {
        TestField testField = new TestField();

        final MultiRaster raster = new InterleavedMultiRaster(4, 4, TestField.ONE, TestField.RISING);

        testField.renderToRaster(raster, new ImmutableRectangle(0,0, 3,3));

        MultiRasterField multiRasterField = new MultiRasterField(raster, true, false);
        assertEquals(0f, multiRasterField.getValue(0, 0, TestField.RISING), 0.001f);
        assertEquals(101f, multiRasterField.getValue(1, 1, TestField.RISING), 0.001f);
        assertEquals(303f, multiRasterField.getValue(3, 3, TestField.RISING), 0.001f);
        assertEquals(0f, multiRasterField.getValue(4, 0, TestField.RISING), 0.001f);
        assertEquals(0.5f, multiRasterField.getValue(4.5, 0, TestField.RISING), 0.001f);
        assertEquals(1f, multiRasterField.getValue(5, 0, TestField.RISING), 0.001f);
        assertEquals(303f, multiRasterField.getValue(-1, 3, TestField.RISING), 0.001f);
        assertEquals(302f, multiRasterField.getValue(-2, 3, TestField.RISING), 0.001f);
        assertEquals(0f, multiRasterField.getValue(0, -1, TestField.RISING), 0.001f);
        assertEquals(301f, multiRasterField.getValue(5, 100, TestField.RISING), 0.001f);
        assertEquals(1.5f, multiRasterField.getValue(3.5, 0, TestField.RISING), 0.001f);

        MultiRasterField multiRasterField2 = new MultiRasterField(raster, false, true, 42);
        assertEquals(0f, multiRasterField2.getValue(0, 0, TestField.RISING), 0.001f);
        assertEquals(42f, multiRasterField2.getValue(-1, 0, TestField.RISING), 0.001f);
        assertEquals(0f, multiRasterField2.getValue(0, 4, TestField.RISING), 0.001f);
        assertEquals(100f, multiRasterField2.getValue(0, 5, TestField.RISING), 0.001f);
        assertEquals(42f, multiRasterField2.getValue(5, 10, TestField.RISING), 0.001f);
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

    private static class TestRenderListener implements RenderListener {

        private int numCalls = 0;
        private double lastProgress = -1;
        private boolean shouldCancel = false;

        @Override public boolean onRenderProgress(double progress) {
            numCalls++;
            lastProgress = progress;

            return !shouldCancel;
        }

        private int getNumCalls() {
            return numCalls;
        }

        private void setNumCalls(int numCalls) {
            this.numCalls = numCalls;
        }

        private double getLastProgress() {
            return lastProgress;
        }

        private void setLastProgress(double lastProgress) {
            this.lastProgress = lastProgress;
        }

        private boolean isShouldCancel() {
            return shouldCancel;
        }

        private void setShouldCancel(boolean shouldCancel) {
            this.shouldCancel = shouldCancel;
        }
    }

}
