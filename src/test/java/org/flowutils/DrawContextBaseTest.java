package org.flowutils;

import junit.framework.Assert;
import org.flowutils.drawcontext.DrawContext;
import org.flowutils.drawcontext.DrawContextBase;
import org.flowutils.rectangle.ImmutableRectangle;
import org.flowutils.rectangle.Rectangle;
import org.flowutils.rectangle.intrectangle.IntRectangle;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class DrawContextBaseTest {

    protected static final float EPSILON = 0.001f;

    @Test
    public void testCreateSubContext() throws Exception {
        TestDrawContext drawContext = new TestDrawContext(20, 10);


        assertEquals(20, drawContext.subContext(0, 0, 20, 10).getWidth(), EPSILON);
        assertEquals(15, drawContext.subContext(5, 5, 15, 5).getWidth(), EPSILON);
        assertEquals(5, drawContext.subContext(0, 0, 5, 10).getWidth(), EPSILON);

        assertEquals(20, drawContext.subContext(ImmutableRectangle.fromWidthHeight(0, 0, 20, 10)).getWidth(), EPSILON);
        assertEquals(15, drawContext.subContext(ImmutableRectangle.fromWidthHeight(5, 5, 15, 5)).getWidth(), EPSILON);
        assertEquals(5, drawContext.subContext(ImmutableRectangle.fromWidthHeight(0, 0, 5, 10)).getWidth(), EPSILON);
    }

    private static class TestDrawContext extends DrawContextBase<Integer, Object, Object> {

        private TestDrawContext() {
        }

        private TestDrawContext(float width, float height) {
            super(width, height);
        }

        private TestDrawContext(float startX, float startY, float width, float height) {
            super(startX, startY, width, height);
        }

        private TestDrawContext(Rectangle rectangle) {
            super(rectangle);
        }

        private TestDrawContext(IntRectangle rectangle) {
            super(rectangle);
        }

        @Override protected <T extends DrawContext<Integer, Object, Object>> T doCreateSubContext(float startX,
                                                                                                  float startY,
                                                                                                  float width,
                                                                                                  float height) {
            return (T) new TestDrawContext(startX, startY, width, height);
        }

        @Override protected void doDrawLine(Integer integer, float x1, float y1, float x2, float y2, float lineWidth) {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override protected void doDrawRectangle(Integer integer,
                                                 float x1,
                                                 float y1,
                                                 float width,
                                                 float height,
                                                 float lineWidth) {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override protected void doFillRectangle(Integer fillColor, float x1, float y1, float width, float height) {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override protected void doDrawOval(Integer integer,
                                            float centerX,
                                            float centerY,
                                            float width,
                                            float height,
                                            float lineWidth) {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override protected void doFillOval(Integer fillColor,
                                            float centerX,
                                            float centerY,
                                            float width,
                                            float height) {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override protected void doDrawTriangle(Integer integer,
                                                float x1,
                                                float y1,
                                                float x2,
                                                float y2,
                                                float x3,
                                                float y3,
                                                float lineWidth) {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override protected void doFillTriangle(Integer fillColor,
                                                float x1,
                                                float y1,
                                                float x2,
                                                float y2,
                                                float x3,
                                                float y3) {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override protected void doDrawText(Integer integer,
                                            float x,
                                            float y,
                                            String text,
                                            Object o,
                                            float alignX,
                                            float alignY) {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override protected void doDrawImage(Object o, float x, float y) {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override protected void doDrawImage(Object o, float x, float y, float width, float height) {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override public Integer getColor(float red, float green, float blue, float alpha) {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override public Integer getColorFromHSL(float hue, float saturation, float luminance, float alpha) {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override public Integer getColorFromColorCode(int colorCode) {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override public float getRed(Integer integer) {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override public float getGreen(Integer integer) {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override public float getBlue(Integer integer) {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override public float getAlpha(Integer integer) {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override public float getHue(Integer integer) {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override public float getSaturation(Integer integer) {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override public float getLuminance(Integer integer) {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override public Object getDefaultFont() {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override public Object getFont(String fontName) {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override public Object getFont(String fontName, float fontSize) {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override public float getFontHeight(Object o) {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override public float getTextWidth(Object o, String text) {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override public float getFontHeightBaselineToBottom(Object o) {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override public float getFontHeightBaselineToTop(Object o) {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override public boolean getAntialias() {
            throw new UnsupportedOperationException("Not implemented");
        }

        @Override public boolean setAntialias(boolean antialias) {
            throw new UnsupportedOperationException("Not implemented");
        }
    }
}


