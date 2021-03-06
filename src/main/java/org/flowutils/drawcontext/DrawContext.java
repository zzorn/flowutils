package org.flowutils.drawcontext;

import org.flowutils.rectangle.MutableRectangle;
import org.flowutils.rectangle.Rectangle;
import org.flowutils.rectangle.intrectangle.IntRectangle;

/**
 * Implementation neutral drawing context.
 *
 * By default one unit is one pixel.
 * The coordinates in the DrawContext extend from 0,0 (inclusive) to width,height (exclusive)
 *
 * @param <COLOR> the color class used by this DrawContext.
 * @param <FONT>  the font class used by this DrawContext.
 * @param <IMAGE> the image class used by this DrawContext.
 */
// TODO: In the future relative drawing operations might be added, that use coordinates between 0 and 1 instead of 0 and width/height
// TODO: Consider splitting color and font related methods off into ColorContext and FontContext interfaces and base classes.  Makes them usable in other places as well.
// TODO: Create replacement mutable Color class with float values.
public interface DrawContext<COLOR, FONT, IMAGE> {

    /**
     * @return width of this DrawContext
     */
    float getWidth();

    /**
     * @return height of this DrawContext
     */
    float getHeight();

    /**
     * @return a (possibly new) rectangle covering the visible size of the DrawContext, from 0,0 to width,height (exclusive).
     *         May or may not change when the DrawContext size changes.
     */
    Rectangle getSize();

    /**
     * @param sizeOut the rectangle to write the size to.  Should not be null.
     * @return the provided rectangle set to cover the visible size of the DrawContext, from 0,0 to width,height (exclusive).
     */
    MutableRectangle getSize(MutableRectangle sizeOut);

    /**
     * @return x coordinate in the center of this draw context.
     */
    float getCenterX();

    /**
     * @return y coordinate in the center of this draw context.
     */
    float getCenterY();

    /**
     * @return an x position from 0 to width, depending on the value of the parameter, which is in the range 0..1
     */
    float getRelX(float relativeX);

    /**
     * @return an y position from 0 to height, depending on the value of the parameter, which is in the range 0..1
     */
    float getRelY(float relativeY);

    /**
     * @return approximate number of pixels per inch along the x axis on the display this DrawContext is shown on
     * (or a default if we are operating on a headless environment).
     */
    float getPixelsPerInchX();

    /**
     * @return approximate number of pixels per inch along the y axis on the display this DrawContext is shown on
     * (or a default if we are operating on a headless environment).
     */
    float getPixelsPerInchY();

    /**
     * @return a default gap size for UI layouts and such.
     */
    float getGap();

    /**
     * @return a small gap size for UI layouts and such.
     */
    float getSmallGap();

    /**
     * @return a large gap size for UI layouts and such.
     */
    float getLargeGap();

    /**
     * @param luminance 0f = black, 1f = white.
     * @return a greyscale color with the specified luminance.
     */
    COLOR getColor(float luminance);

    /**
     * @param red red component of the color, in the range 0..1.
     * @param green green component of the color, in the range 0..1.
     * @param blue blue component of the color, in the range 0..1.
     * @return the specified color.
     */
    COLOR getColor(float red, float green, float blue);

    /**
     * @param red red component of the color, in the range 0..1.
     * @param green green component of the color, in the range 0..1.
     * @param blue blue component of the color, in the range 0..1.
     * @param alpha alpha component of the color, in the range 0..1. 0 = transparent, 1 = solid.
     * @return the specified color.
     */
    COLOR getColor(float red, float green, float blue, float alpha);

    /**
     * @param hue hue to use, from 0 to 1.
     * @param saturation the saturation to use, 0 = greyscale, 1 = color.
     * @param luminance the luminance to use, 0 = dark, 1 = bright.
     * @return the specified color.
     */
    COLOR getColorFromHSL(float hue, float saturation, float luminance);

    /**
     * @param hue hue to use, from 0 to 1.
     * @param saturation the saturation to use, 0 = greyscale, 1 = color.
     * @param luminance the luminance to use, 0 = dark, 1 = bright.
     * @param alpha alpha component of the color, in the range 0..1. 0 = transparent, 1 = solid.
     * @return the specified color.
     */
    COLOR getColorFromHSL(float hue, float saturation, float luminance, float alpha);

    /**
     * @param colorCode a color code in the same format as Java Swing color codes.
     * @return the color specified by the provided color code
     */
    COLOR getColorFromColorCode(int colorCode);

    /**
     * @return the Java Swing type color code with the specified color components and full alpha.
     */
    int getColorCode(float red, float green, float blue);

    /**
     * @return the Java Swing type color code with the specified color components.
     */
    int getColorCode(float red, float green, float blue, float alpha);

    float getRedFromCode(int colorCode);
    float getGreenFromCode(int colorCode);
    float getBlueFromCode(int colorCode);
    float getAlphaFromCode(int colorCode);

    /**
     * @return red component of the color, in the range 0..1.
     */
    float getRed(COLOR color);

    /**
     * @return green component of the color, in the range 0..1.
     */
    float getGreen(COLOR color);

    /**
     * @return blue component of the color, in the range 0..1.
     */
    float getBlue(COLOR color);

    /**
     * @return alpha component of the color, in the range 0..1.  0 = transparent, 1 = solid.
     */
    float getAlpha(COLOR color);

    /**
     * @return the hue of the color, in the range 0..1.  Returns 0 if the color has no distinct hue (e.g. greyscale)
     */
    float getHue(COLOR color);

    /**
     * @return the saturation of the color, in the range 0..1. 0 = greyscale, 1 = color.
     */
    float getSaturation(COLOR color);

    /**
     * @return the luminance of the color, in the range 0..1. 0 = dark, 1 = bright.
     */
    float getLuminance(COLOR color);

    /**
     * @param mixAmount range from 0..1.  0 = return color a, 1 = color b.
     * @param a color to use for mixAmount 0
     * @param b color to use for mixAmount 1
     * @return the mix of the two provided colors
     */
    COLOR mixColors(float mixAmount, COLOR a, COLOR b);

    int mixColorCodes(float mixAmount, int colorA, int colorB);

    /**
     * @return the color solid black.
     */
    COLOR getBlack();

    /**
     * @return the color solid white.
     */
    COLOR getWhite();

    /**
     * @return the default font provided by this DrawContext.  Can be used in calls to drawText.
     */
    FONT getDefaultFont();

    /**
     * @return the font with the specified name, if found.
     */
    FONT getFont(String fontName);

    /**
     * @return the font with the specified name and size, if found.
     */
    // TODO: Maybe add function that gets font with any variants applied (bold, italic, etc)
    FONT getFont(String fontName, float fontSize);

    /**
     * @return maximum height of the specified font.
     */
    float getFontHeight(FONT font);

    /**
     * @return width of the specified text using the specified font.
     */
    float getTextWidth(FONT font, String text);

    /**
     * @return distance from the baseline in the font to the absolute bottom of the font.
     */
    float getFontHeightBaselineToBottom(FONT font);

    /**
     * @return distance from the baseline in the font to the absolute top of the font.
     */
    float getFontHeightBaselineToTop(FONT font);

    /**
     * @return if true, hints that edges of drawn shapes should be smoothed with antialiasing.
     */
    boolean getAntialias();

    /**
     * @param antialias if true, hints that edges of drawn shapes should be smoothed with antialiasing.
     * @return the previous state of antialiasing.
     */
    boolean setAntialias(boolean antialias);


    /**
     * Clears the whole draw context to the specified color
     * @param color color to fill the drawing with.
     */
    void clear(COLOR color);

    /**
     * Draws a single pixel on the DrawContext.
     */
    void drawPixel(COLOR color, float x, float y);

    /**
     * Draws a line from x1,y1 to x2,y2
     */
    void drawLine(COLOR color, float x1, float y1, float x2, float y2);

    /**
     * Draws a line from x1,y1 to x2,y2 with the specified width
     */
    void drawLine(COLOR color, float x1, float y1, float x2, float y2, float width);

    /**
     * Draws a hollow rectangle of the specified size and color with the upper left corner at x,y.
     * @param lineWidth width of the line used to draw the rectangle.
     */
    void drawRectangle(COLOR color, float x, float y, float width, float height, float lineWidth);

    /**
     * Draws a filled rectangle of the specified size and color with the upper left corner at x,y.
     */
    void fillRectangle(COLOR fillColor, float x, float y, float width, float height);

    /**
     * Draws an outlined filled rectangle of the specified size and color with the upper left corner at x,y.
     * @param fillColor color to fill with
     * @param outlineColor color to use for the outline
     * @param outlineWidth width of the outline.
     */
    void outlineRectangle(COLOR fillColor,
                       float x,
                       float y,
                       float width,
                       float height,
                       COLOR outlineColor,
                       float outlineWidth);

    /**
     * Draws a hollow rectangle of the specified color.
     * @param lineWidth width of the line used to draw the rectangle.
     */
    void drawRectangle(COLOR color, Rectangle rectangle, float lineWidth);

    /**
     * Draws a filled rectangle of the specified color.
     */
    void fillRectangle(COLOR fillColor, Rectangle rectangle);

    /**
     * Draws an outlined filled rectangle of the specified color.
     * @param fillColor color to fill with
     * @param outlineColor color to use for the outline
     * @param outlineWidth width of the outline.
     */
    void outlineRectangle(COLOR fillColor,
                          Rectangle rectangle,
                          COLOR outlineColor,
                          float outlineWidth);

    /**
     * Draws a hollow oval with the specified center and size.
     */
    void drawOval(COLOR color, float centerX, float centerY, float width, float height, float lineWidth);

    /**
     * Draws a filled oval with the specified center and size.
     */
    void fillOval(COLOR fillColor, float centerX, float centerY, float width, float height);

    /**
     * Draws an outlined filled oval with the specified center and size.
     * @param fillColor color to fill with
     * @param outlineColor color to use for the outline
     * @param outlineWidth width of the outline.
     */
    void outlineOval(COLOR fillColor,
                  float centerX,
                  float centerY,
                  float width,
                  float height,
                  COLOR outlineColor,
                  float outlineWidth);

    /**
     * Draws a hollow triangle with the corners at x1,y1, x2,y2, and x3,y3.
     */
    void drawTriangle(COLOR color, float x1, float y1, float x2, float y2, float x3, float y3, float lineWidth);

    /**
     * Draws a filled triangle with the corners at x1,y1, x2,y2, and x3,y3.
     */
    void fillTriangle(COLOR fillColor, float x1, float y1, float x2, float y2, float x3, float y3);

    /**
     * Draws an outlined filled triangle with the corners at x1,y1, x2,y2, and x3,y3.
     * @param fillColor color to fill with
     * @param outlineColor color to use for the outline
     * @param outlineWidth width of the outline.
     */
    void outlineTriangle(COLOR fillColor,
                      float x1,
                      float y1,
                      float x2,
                      float y2,
                      float x3,
                      float y3,
                      COLOR outlineColor,
                      float outlineWidth);


    /**
     * Draws a text at the specified location using the default font.
     * @param color color to use for the text
     * @param x x location, aligned so that the text is to the right of this position.
     * @param y y location, aligned so that the text baseline is above this position.
     * @param text text to draw
     */
    void drawText(COLOR color, float x, float y, String text);

    /**
     * Draws a text at the specified location.
     * @param color color to use for the text
     * @param x x location, aligned so that the text is to the right of this position.
     * @param y y location, aligned so that the text baseline is above this position.
     * @param text text to draw
     * @param font font to use
     */
    void drawText(COLOR color, float x, float y, String text, FONT font);

    /**
     * Draws a text at the specified location.
     * @param color color to use for the text
     * @param x x location
     * @param y y location
     * @param text text to draw
     * @param font font to use
     * @param alignX the alignment of the text relative the draw location.  0 = left align, 0.5 = center align, 1 = right aling.
     * @param alignY the alignment of the text relative the draw location.  0 = top align, 0.5 = center align, 1 = bottom aling.
     */
    void drawText(COLOR color, float x, float y, String text, FONT font, float alignX, float alignY);

    /**
     * Draws a text at the specified location.
     * @param color color to use for the text
     * @param x x location
     * @param y y location
     * @param text text to draw
     * @param font font to use
     * @param alignX the alignment of the text relative the draw location.  0 = left align, 0.5 = center align, 1 = right aling.
     * @param alignY the alignment of the text relative the draw location.  0 = top align, 0.5 = center align, 1 = bottom aling.
     * @param outlineColor color to outline the text with
     */
    void drawText(COLOR color, float x, float y, String text, FONT font, float alignX, float alignY, COLOR outlineColor);

    /**
     * Draw the specified image at the specified location.
     * @param image image to draw
     * @param x left upper corner of the image.
     * @param y left upper corner of the image.
     */
    void drawImage(IMAGE image, float x, float y);

    /**
     * Draw the specified image at the specified location with a specific size.
     * @param image image to draw
     * @param x left upper corner of the image.
     * @param y left upper corner of the image.
     * @param width width to scale the image to
     * @param height height to scale the image to
     */
    void drawImage(IMAGE image, float x, float y, float width, float height);

    /**
     * Creates a new DrawContext that is a sub-area of this DrawContext.
     * @param x some x coordinte within the current DrawContext
     * @param y some y coordinte within the current DrawContext
     * @param width some width that fits within the current DrawContext
     * @param height some height that fits within the current DrawContext
     * @return a new DrawContext that covers a subset of this DrawContext.  It is not allowed to extend outside the current draw context.
     */
    <T extends DrawContext<COLOR, IMAGE, FONT>> T subContext(float x, float y, float width, float height);

    /**
     * Creates a new DrawContext that is a sub-area of this DrawContext.
     * @param rectangle the area within this DrawContext that the subContext should cover.
     * @return a new DrawContext that covers a subset of this DrawContext.  It is not allowed to extend outside the current draw context.
     */
    <T extends DrawContext<COLOR, IMAGE, FONT>> T subContext(Rectangle rectangle);

    /**
     * Creates a new DrawContext that is a sub-area of this DrawContext.
     * @param rectangle the area within this DrawContext that the subContext should cover.
     * @return a new DrawContext that covers a subset of this DrawContext.  It is not allowed to extend outside the current draw context.
     */
    <T extends DrawContext<COLOR, IMAGE, FONT>> T subContext(IntRectangle rectangle);

}
