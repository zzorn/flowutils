package org.flowutils.drawcontext.marker;

import org.flowutils.drawcontext.DrawContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * A few simple default marker shapes.
 */
public final class DefaultMarkers {

    public static final Marker CIRCLE = new MarkerBase() {
        @Override public void draw(DrawContext drawContext,
                                   float x,
                                   float y,
                                   float radius,
                                   int fillColorCode,
                                   int outlineColorCode,
                                   float edgeThickness) {
            drawCircle(drawContext, fillColorCode, outlineColorCode, x, y, radius, edgeThickness);
        }
    };

    public static final Marker SQUARE = new MarkerBase() {
        @Override public void draw(DrawContext drawContext,
                                   float x,
                                   float y,
                                   float radius,
                                   int fillColorCode,
                                   int outlineColorCode,
                                   float edgeThickness) {
            drawSquare(drawContext, fillColorCode, outlineColorCode, x, y, radius, edgeThickness);
        }
    };

    public static final Marker DIAMOND = new MarkerBase() {
        @Override public void draw(DrawContext drawContext,
                                   float x,
                                   float y,
                                   float radius,
                                   int fillColorCode,
                                   int outlineColorCode,
                                   float edgeThickness) {
            drawDiamond(drawContext, fillColorCode, outlineColorCode, x, y, radius, edgeThickness);
        }
    };

    public static final Marker TRIANGLE_UP = new MarkerBase() {
        @Override public void draw(DrawContext drawContext,
                                   float x,
                                   float y,
                                   float radius,
                                   int fillColorCode,
                                   int outlineColorCode,
                                   float edgeThickness) {
            drawTriangle(drawContext, fillColorCode, outlineColorCode, x, y, radius, edgeThickness, true);
        }
    };

    public static final Marker TRIANGLE_DOWN = new MarkerBase() {
        @Override public void draw(DrawContext drawContext,
                                   float x,
                                   float y,
                                   float radius,
                                   int fillColorCode,
                                   int outlineColorCode,
                                   float edgeThickness) {
            drawTriangle(drawContext, fillColorCode, outlineColorCode, x, y, radius, edgeThickness, false);
        }
    };

    /**
     * An unmodifiable list with the markers available in this utility class.
     */
    public static final List<Marker> MARKERS = Collections.unmodifiableList(new ArrayList<Marker>(Arrays.asList(
            CIRCLE, SQUARE, DIAMOND, TRIANGLE_UP, TRIANGLE_DOWN
    )));


    /**
     * Draws a circle at the specified location
     * @param drawContext context to draw on
     * @param fillColorCode color code of the color to use for the fill
     * @param outlineColorCode color code of the color to use for the outline
     * @param xCenter approximate center
     * @param yCenter approximate center
     * @param radius approximate radius
     * @param lineWidth width of outline in pixels.
     */
    public static void drawCircle(DrawContext drawContext,
                                  int fillColorCode,
                                  int outlineColorCode,
                                  float xCenter,
                                  float yCenter,
                                  float radius,
                                  float lineWidth) {
        drawContext.outlineOval(drawContext.getColorFromColorCode(fillColorCode),
                                xCenter, yCenter,
                                2 * radius, 2 * radius,
                                drawContext.getColorFromColorCode(outlineColorCode),
                                lineWidth);
    }

    /**
     * Draws a triangle at the specified location
     * @param drawContext context to draw on
     * @param fillColorCode color code of the color to use for the fill
     * @param outlineColorCode color code of the color to use for the outline
     * @param xCenter approximate center
     * @param yCenter approximate center
     * @param radius approximate radius
     * @param lineWidth width of outline in pixels.
     * @param up true if an up-pointing triangle should be drawn, false if down triangle.
     */
    public static void drawTriangle(DrawContext drawContext,
                                  int fillColorCode,
                                  int outlineColorCode,
                                  float xCenter,
                                  float yCenter,
                                  float radius,
                                  float lineWidth,
                                  boolean up) {

        int dir = up ? -1 : 1;
        drawContext.outlineTriangle(drawContext.getColorFromColorCode(fillColorCode),
                                    xCenter - radius,
                                    yCenter,
                                    xCenter + radius,
                                    yCenter,
                                    xCenter,
                                    yCenter + radius * dir,
                                    drawContext.getColorFromColorCode(outlineColorCode),
                                    lineWidth);
    }

    /**
     * Draws a diamond shape at the specified location
     * @param drawContext context to draw on
     * @param fillColorCode color code of the color to use for the fill
     * @param outlineColorCode color code of the color to use for the outline
     * @param xCenter approximate center
     * @param yCenter approximate center
     * @param radius approximate radius
     * @param lineWidth width of outline in pixels.
     */
    public static void drawDiamond(DrawContext drawContext,
                                   int fillColorCode,
                                   int outlineColorCode,
                                   float xCenter,
                                   float yCenter,
                                   float radius,
                                   float lineWidth) {
        final Object fillColor = drawContext.getColorFromColorCode(fillColorCode);
        final Object outlineColor = drawContext.getColorFromColorCode(outlineColorCode);
        drawContext.fillTriangle(fillColor, xCenter-radius, yCenter, xCenter+radius, yCenter, xCenter, yCenter+radius);
        drawContext.fillTriangle(fillColor, xCenter-radius, yCenter, xCenter+radius, yCenter, xCenter, yCenter-radius);
        drawContext.drawTriangle(outlineColor, xCenter-radius, yCenter, xCenter+radius, yCenter, xCenter, yCenter+radius, lineWidth);
        drawContext.drawTriangle(outlineColor,
                                 xCenter - radius,
                                 yCenter,
                                 xCenter + radius,
                                 yCenter,
                                 xCenter,
                                 yCenter - radius,
                                 lineWidth);
    }

    /**
     * Draws a square at the specified location
     * @param drawContext context to draw on
     * @param fillColorCode color code of the color to use for the fill
     * @param outlineColorCode color code of the color to use for the outline
     * @param xCenter approximate center
     * @param yCenter approximate center
     * @param radius approximate radius
     * @param lineWidth width of outline in pixels.
     */
    public static void drawSquare(DrawContext drawContext,
                                  int fillColorCode,
                                  int outlineColorCode,
                                  float xCenter,
                                  float yCenter,
                                  float radius,
                                  float lineWidth) {
        final Object fillColor = drawContext.getColorFromColorCode(fillColorCode);
        final Object outlineColor = drawContext.getColorFromColorCode(outlineColorCode);
        drawContext.outlineRectangle(fillColor,
                                     xCenter - radius,
                                     yCenter - radius,
                                     2 * radius,
                                     2 * radius,
                                     outlineColor,
                                     lineWidth);
    }

    private DefaultMarkers() {
    }
}
