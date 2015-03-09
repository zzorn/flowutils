package org.flowutils.drawcontext;

import org.flowutils.Check;
import org.flowutils.MathUtils;

/**
 * Common functionality for DrawContexts.
 */
public abstract class DrawContextBase<COLOR, FONT, IMAGE> implements DrawContext<COLOR, FONT, IMAGE> {

    protected static final float SMALL_GAP_FACTOR = 0.5f;
    protected static final float LARGE_GAP_FACTOR = 2f;

    protected static final float DEFAULT_GAP_PIXELS = 10f;
    private static final float DEFAULT_LINE_WIDTH = 1f;

    protected static final float DEFAULT_TEXT_ALIGN_Y = 0f;
    protected static final float DEFAULT_TEXT_ALIGN_X = 0f;

    protected static final float DEFAULT_PIXELS_PER_INCH = 96;
    protected static final int OUTLINE_DISTANCE = 1;
    protected static final int RED_SHIFT = 16;
    protected static final int ALPHA_SHIFT = 24;
    protected static final int GREEN_SHIFT = 8;
    protected static final int BLUE_SHIFT = 0;
    protected static final int COLOR_COMPONENT_MASK = 0xFF;

    private float gap = DEFAULT_GAP_PIXELS;

    private float startX;
    private float startY;
    private float width;
    private float height;

    protected DrawContextBase() {
    }

    protected DrawContextBase(float width, float height) {
        this(0,0,width, height);
    }

    protected DrawContextBase(float startX, float startY, float width, float height) {
        setContextSize(startX, startY, width, height);
    }

    protected final void setContextSize(float startX, float startY, float width, float height) {
        setStartX(startX);
        setStartY(startY);
        setWidth(width);
        setHeight(height);
    }

    protected final void setStartX(float startX) {
        Check.normalNumber(startX, "startX");
        this.startX = startX;
    }

    protected final void setStartY(float startY) {
        Check.normalNumber(startY, "startY");
        this.startY = startY;
    }

    protected void setWidth(float width) {
        Check.positive(width, "width");
        this.width = width;
    }

    protected void setHeight(float height) {
        Check.positive(height, "height");
        this.height = height;
    }

    @Override public float getSmallGap() {
        return getGap() * SMALL_GAP_FACTOR;
    }

    @Override public float getLargeGap() {
        return getGap() * LARGE_GAP_FACTOR;
    }

    @Override public float getGap() {
        return gap;
    }

    public void setGap(float gap) {
        Check.positiveOrZero(gap, "gap");
        this.gap = gap;
    }

    protected final float getStartX() {
        return startX;
    }

    protected final float getStartY() {
        return startY;
    }

    protected final float getEndX() {
        return getStartX() + getWidth();
    }

    protected final float getEndY() {
        return getStartY() + getHeight();
    }

    @Override public float getWidth() {
        return width;
    }

    @Override public float getHeight() {
        return height;
    }

    @Override public float getCenterX() {
        return 0.5f * getWidth();
    }

    @Override public float getCenterY() {
        return 0.5f * getHeight();
    }

    @Override public float getRelX(float relativeX) {
        return MathUtils.mix(relativeX, 0, getWidth());
    }

    @Override public float getRelY(float relativeY) {
        return MathUtils.mix(relativeY, 0, getHeight());
    }

    @Override public void clear(COLOR color) {
        fillRectangle(color, getStartX(), getStartY(), getWidth(), getHeight());
    }

    @Override public float getPixelsPerInchX() {
        return getPixelsPerInch();
    }

    @Override public float getPixelsPerInchY() {
        return getPixelsPerInch();
    }

    /**
     * Either override this, or override getPixelsPerInchX and Y.  Or leave it as it is to return a default hardcoded value.
     * @return pixels per inch on the current display.
     */
    protected float getPixelsPerInch() {
        return DEFAULT_PIXELS_PER_INCH;
    }

    @Override public COLOR getColor(float luminance) {
        return getColor(luminance, luminance, luminance, 1f);
    }

    @Override public COLOR getColor(float red, float green, float blue) {
        return getColor(red, green, blue, 1f);
    }

    @Override public COLOR getColorFromHSL(float hue, float saturation, float luminance) {
        return getColor(hue, saturation, luminance, 1f);
    }

    @Override public final int getColorCode(float red, float green, float blue) {
        return getColorCode(red, green, blue, 1f);
    }

    @Override public final int getColorCode(float red, float green, float blue, float alpha) {
        int r = MathUtils.clampToRange((int) (red*255), 0, 255);
        int g = MathUtils.clampToRange((int) (green*255), 0, 255);
        int b = MathUtils.clampToRange((int) (blue*255), 0, 255);
        int a = MathUtils.clampToRange((int) (alpha*255), 0, 255);
        return ((a & COLOR_COMPONENT_MASK) << ALPHA_SHIFT) |
               ((r & COLOR_COMPONENT_MASK) << RED_SHIFT) |
               ((g & COLOR_COMPONENT_MASK) << GREEN_SHIFT)  |
               ((b & COLOR_COMPONENT_MASK) << BLUE_SHIFT);
    }

    @Override public final float getRedFromCode(int colorCode) {
        return ((colorCode >> RED_SHIFT) & COLOR_COMPONENT_MASK) / 255f;
    }

    @Override public final float getGreenFromCode(int colorCode) {
        return ((colorCode >> GREEN_SHIFT) & COLOR_COMPONENT_MASK) / 255f;
    }

    @Override public final float getBlueFromCode(int colorCode) {
        return ((colorCode >> BLUE_SHIFT) & COLOR_COMPONENT_MASK) / 255f;
    }

    @Override public final float getAlphaFromCode(int colorCode) {
        return ((colorCode >> ALPHA_SHIFT) & COLOR_COMPONENT_MASK) / 255f;
    }

    // TODO: Implement the various HSL color methods.

    @Override public COLOR mixColors(float mixAmount, COLOR colorA, COLOR colorB) {
        float ar = getRed(colorA);
        float ag = getGreen(colorA);
        float ab = getBlue(colorA);
        float aa = getAlpha(colorA);
        float br = getRed(colorB);
        float bg = getGreen(colorB);
        float bb = getBlue(colorB);
        float ba = getAlpha(colorB);
        float r = MathUtils.clamp0To1(MathUtils.mix(mixAmount, ar, br));
        float g = MathUtils.clamp0To1(MathUtils.mix(mixAmount, ag, bg));
        float b = MathUtils.clamp0To1(MathUtils.mix(mixAmount, ab, bb));
        float a = MathUtils.clamp0To1(MathUtils.mix(mixAmount, aa, ba));
        return getColor(r,g,b,a);
    }

    @Override public final int mixColorCodes(float mixAmount, int colorA, int colorB) {
        float ar = getRedFromCode(colorA);
        float ag = getGreenFromCode(colorA);
        float ab = getBlueFromCode(colorA);
        float aa = getAlphaFromCode(colorA);
        float br = getRedFromCode(colorB);
        float bg = getGreenFromCode(colorB);
        float bb = getBlueFromCode(colorB);
        float ba = getAlphaFromCode(colorB);
        float r = MathUtils.clamp0To1(MathUtils.mix(mixAmount, ar, br));
        float g = MathUtils.clamp0To1(MathUtils.mix(mixAmount, ag, bg));
        float b = MathUtils.clamp0To1(MathUtils.mix(mixAmount, ab, bb));
        float a = MathUtils.clamp0To1(MathUtils.mix(mixAmount, aa, ba));
        return getColorCode(r, g, b, a);
    }

    @Override public COLOR getBlack() {
        return getColor(0f);
    }

    @Override public COLOR getWhite() {
        return getColor(1f);
    }

    @Override public final void drawPixel(COLOR color, float x, float y) {
        doDrawPixel(color, x + startX, y + startY);
    }

    @Override public final void drawLine(COLOR color, float x1, float y1, float x2, float y2) {
        drawLine(color, x1, y1, x2, y2, DEFAULT_LINE_WIDTH);
    }

    @Override public final void drawLine(COLOR color, float x1, float y1, float x2, float y2, float width) {
        doDrawLine(color,
                   x1+startX, y1+startY,
                   x2+startX, y2+startY,
                   width);
    }

    @Override public final void drawRectangle(COLOR color, float x, float y, float width, float height, float lineWidth) {
        doDrawRectangle(color, x+startX, y+startY, width, height, lineWidth);
    }

    @Override public final void fillRectangle(COLOR fillColor, float x, float y, float width, float height) {
        doFillRectangle(fillColor, x+startX, y+startY, width, height);
    }

    @Override public final void drawOval(COLOR color,
                                   float centerX,
                                   float centerY,
                                   float width,
                                   float height,
                                   float lineWidth) {
        doDrawOval(color, centerX+startX, centerY+startY, width, height, lineWidth);
    }

    @Override public final void fillOval(COLOR fillColor, float centerX, float centerY, float width, float height) {
        doFillOval(fillColor, centerX+startX, centerY+startY, width, height);
    }

    @Override public final void drawTriangle(COLOR color,
                                       float x1,
                                       float y1,
                                       float x2,
                                       float y2,
                                       float x3,
                                       float y3,
                                       float lineWidth) {
        doDrawTriangle(color,
                       x1+startX, y1+startY,
                       x2+startX, y2+startY,
                       x3+startX, y3+startY,
                       lineWidth);
    }

    @Override public final void fillTriangle(COLOR fillColor, float x1, float y1, float x2, float y2, float x3, float y3) {
        doFillTriangle(fillColor,
                       x1 + startX, y1 + startY,
                       x2 + startX, y2 + startY,
                       x3 + startX, y3 + startY);
    }

    @Override public final void outlineRectangle(COLOR fillColor,
                                        float x1,
                                        float y1,
                                        float width,
                                        float height,
                                        COLOR outlineColor,
                                        float outlineWidth) {
        fillRectangle(fillColor, x1, y1, width, height);
        drawRectangle(outlineColor, x1, y1, width, height, outlineWidth);
    }

    @Override public final void outlineOval(COLOR fillColor,
                                   float centerX,
                                   float centerY,
                                   float width,
                                   float height,
                                   COLOR outlineColor,
                                   float outlineWidth) {
        fillOval(fillColor, centerX, centerY, width, height);
        drawOval(outlineColor, centerX, centerY, width, height, outlineWidth);
    }

    @Override public final void outlineTriangle(COLOR fillColor,
                                       float x1,
                                       float y1,
                                       float x2,
                                       float y2,
                                       float x3,
                                       float y3,
                                       COLOR outlineColor,
                                       float outlineWidth) {
        fillTriangle(fillColor, x1, y1, x2, y2, x3, y3);
        drawTriangle(outlineColor, x1, y1, x2, y2, x3, y3, outlineWidth);
    }

    @Override public final void drawText(COLOR color, float x, float y, String text) {
        drawText(color, x, y, text, getDefaultFont());
    }

    @Override public final void drawText(COLOR color, float x, float y, String text, FONT font) {
        drawText(color, x, y, text, font, DEFAULT_TEXT_ALIGN_X, DEFAULT_TEXT_ALIGN_Y);
    }

    @Override public final void drawText(COLOR color,
                                   float x,
                                   float y,
                                   String text,
                                   FONT font,
                                   float alignX,
                                   float alignY,
                                   COLOR outlineColor) {

        // Draw outline...
        drawText(outlineColor, x- OUTLINE_DISTANCE, y- OUTLINE_DISTANCE, text, font, alignX, alignY);
        drawText(outlineColor, x                  , y- OUTLINE_DISTANCE, text, font, alignX, alignY);
        drawText(outlineColor, x+ OUTLINE_DISTANCE, y- OUTLINE_DISTANCE, text, font, alignX, alignY);
        drawText(outlineColor, x- OUTLINE_DISTANCE, y,                   text, font, alignX, alignY);
        drawText(outlineColor, x+ OUTLINE_DISTANCE, y,                   text, font, alignX, alignY);
        drawText(outlineColor, x- OUTLINE_DISTANCE, y+ OUTLINE_DISTANCE, text, font, alignX, alignY);
        drawText(outlineColor, x                  , y+ OUTLINE_DISTANCE, text, font, alignX, alignY);
        drawText(outlineColor, x+ OUTLINE_DISTANCE, y+ OUTLINE_DISTANCE, text, font, alignX, alignY);

        // Draw text
        drawText(color, x, y, text, font, alignX, alignY);
    }


    @Override public final void drawText(COLOR color, float x, float y, String text, FONT font, float alignX, float alignY) {
        doDrawText(color, x+startX, y+startY, text, font, alignX, alignY);
    }

    @Override public final void drawImage(IMAGE image, float x, float y) {
        doDrawImage(image, x + startX, y + startY);
    }

    @Override public final void drawImage(IMAGE image, float x, float y, float width, float height) {
        doDrawImage(image, x+startX, y+startY, width, height);
    }

    @Override public final <T extends DrawContext<COLOR, IMAGE, FONT>> T subContext(float x, float y, float width, float height) {
        Check.positiveOrZero(x, "x");
        Check.positiveOrZero(y, "y");
        Check.lessOrEqual(width, "width", (this.width - (x + this.startX)), "max width");
        Check.lessOrEqual(height, "height", (this.height-(y + this.startY)), "max height");

        return doCreateSubContext(x+startX, y+startY, width, height);
    }

    protected abstract <T extends DrawContext<COLOR, IMAGE, FONT>> T doCreateSubContext(float startX, float startY, float width, float height);

    protected void doDrawPixel(COLOR color, float x, float y) {
        doDrawLine(color, x, y, x, y, DEFAULT_LINE_WIDTH);
    }

    protected abstract void doDrawLine(COLOR color, float x1, float y1, float x2, float y2, float lineWidth);
    protected abstract void doDrawRectangle(COLOR color, float x1, float y1, float width, float height, float lineWidth);
    protected abstract void doFillRectangle(COLOR fillColor, float x1, float y1, float width, float height);
    protected abstract void doDrawOval(COLOR color,
                                       float centerX,
                                       float centerY,
                                       float width,
                                       float height,
                                       float lineWidth);
    protected abstract void doFillOval(COLOR fillColor, float centerX, float centerY, float width, float height);
    protected abstract void doDrawTriangle(COLOR color,
                                           float x1,
                                           float y1,
                                           float x2,
                                           float y2,
                                           float x3,
                                           float y3,
                                           float lineWidth);
    protected abstract void doFillTriangle(COLOR fillColor, float x1, float y1, float x2, float y2, float x3, float y3);
    protected abstract void doDrawText(COLOR color, float x, float y, String text, FONT font, float alignX, float alignY);
    protected abstract void doDrawImage(IMAGE image, float x, float y);
    protected abstract void doDrawImage(IMAGE image, float x, float y, float width, float height);

}
