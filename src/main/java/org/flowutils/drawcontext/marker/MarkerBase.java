package org.flowutils.drawcontext.marker;

import org.flowutils.drawcontext.DrawContext;

/**
 * Common functionality for Markers.  Just implements a few overridden methods with fewer arguments.
 */
public abstract class MarkerBase implements Marker {

    private static final float RELATIVE_EDGE_WIDTH = 0.2f;
    private static final float OUTLINE_BRIGHTNESS = 0.25f;

    @Override public final void draw(DrawContext drawContext, float x, float y, float radius, int fillColorCode) {
        int white = drawContext.getColorCode(1, 1, 1);
        int outlineColor = drawContext.mixColorCodes(OUTLINE_BRIGHTNESS, fillColorCode, white);
        draw(drawContext, x, y, radius, fillColorCode, outlineColor);
    }

    @Override public final void draw(DrawContext drawContext,
                               float x,
                               float y,
                               float radius,
                               int fillColorCode,
                               int outlineColorCode) {

        float edgeThickness = Math.min(Math.max(RELATIVE_EDGE_WIDTH * radius, 1), radius);
        draw(drawContext, x, y, radius, fillColorCode, outlineColorCode, edgeThickness);
    }

}
