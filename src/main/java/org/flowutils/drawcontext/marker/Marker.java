package org.flowutils.drawcontext.marker;

import org.flowutils.drawcontext.DrawContext;

/**
 * Some kind of symbol or sprite that can be drawn on a DrawContext.
 */
public interface Marker {

    /**
     * Draws the mark.  Uses a slightly lighter color for the outline.
     *
     * @param drawContext context to draw it in
     * @param x center position
     * @param y center position
     * @param radius approximate radius in pixels
     * @param fillColorCode color to use for the internal fill
     */
    public void draw(DrawContext drawContext,
                     float x,
                     float y,
                     float radius,
                     int fillColorCode);

    /**
     * Draws the mark.
     * @param drawContext context to draw it in
     * @param x center position
     * @param y center position
     * @param radius approximate radius in pixels
     * @param fillColorCode color to use for the internal fill
     * @param outlineColorCode color to use for the border
     */
    public void draw(DrawContext drawContext,
                     float x,
                     float y,
                     float radius,
                     int fillColorCode,
                     int outlineColorCode);

    /**
     * Draws the mark.
     * @param drawContext context to draw it in
     * @param x center position
     * @param y center position
     * @param radius approximate radius in pixels
     * @param fillColorCode color to use for the internal fill
     * @param outlineColorCode color to use for the border
     * @param edgeThickness thickness of edge lines in pixels.
     */
    public void draw(DrawContext drawContext,
                     float x,
                     float y,
                     float radius,
                     int fillColorCode,
                     int outlineColorCode,
                     float edgeThickness);

}
