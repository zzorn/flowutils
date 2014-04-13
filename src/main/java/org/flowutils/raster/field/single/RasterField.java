package org.flowutils.raster.field.single;

import org.flowutils.raster.raster.single.Raster;

import static org.flowutils.MathUtils.fastFloor;
import static org.flowutils.MathUtils.mix;

/**
 * A field that samples a raster
 * Has a wrapping that defines how values outside the rectangle area are retrieved.
 */
// TODO: Pass the wrapping to the raster when sampling it?
// TODO: Should the raster be normalized to 0,0 - 1,1 size?
public final class RasterField extends FieldBase {

    private Raster raster;
    private boolean wrapX;
    private boolean wrapY;
    private boolean extendBorder;
    private float defaultValue;

    /**
     * Wraps edges in x and y directions.
     */
    RasterField(Raster raster) {
        this(raster, true, true, false, 0);
    }

    /**
     * Uses the specified defaultValue for any place outside the raster.
     */
    RasterField(Raster raster, float defaultValue) {
        this(raster, false, false, false, defaultValue);
    }

    /**
     * Wraps the specified edges.  Non-wrapped edges have their border values extended outwards.
     *
     * @param wrapX if true, the values are wrapped in the x direction.
     * @param wrapY if true, the values are wrapped in the y direction.
     */
    RasterField(Raster raster, boolean wrapX, boolean wrapY) {
        this(raster, wrapX, wrapY, true, 0);
    }

    /**
     * Wraps the specified edges.  Non-wrapped edges have the specified default value.
     *
     * @param wrapX if true, the values are wrapped in the x direction.
     * @param wrapY if true, the values are wrapped in the y direction.
     * @param defaultValue the default value to use outside the raster for directions that are not wrapped.
     */
    RasterField(Raster raster, boolean wrapX, boolean wrapY, float defaultValue) {
        this(raster, wrapX, wrapY, false, defaultValue);
    }

    /**
     * @param wrapX if true, the values are wrapped in the x direction.
     * @param wrapY if true, the values are wrapped in the y direction.
     * @param extendBorder if true, values are clamped to the border value for non-wrapped directions.
     * @param defaultValue the default value to use outside the raster for directions that are not wrapped if the border value is not extended.
     */
    RasterField(Raster raster, boolean wrapX, boolean wrapY, boolean extendBorder, float defaultValue) {
        this.raster = raster;
        this.wrapX = wrapX;
        this.wrapY = wrapY;
        this.extendBorder = extendBorder;
        this.defaultValue = defaultValue;
    }

    public Raster getRaster() {
        return raster;
    }

    public void setRaster(Raster raster) {
        this.raster = raster;
    }

    public boolean isWrapX() {
        return wrapX;
    }

    public void setWrapX(boolean wrapX) {
        this.wrapX = wrapX;
    }

    public boolean isWrapY() {
        return wrapY;
    }

    public void setWrapY(boolean wrapY) {
        this.wrapY = wrapY;
    }

    public boolean isExtendBorder() {
        return extendBorder;
    }

    public void setExtendBorder(boolean extendBorder) {
        this.extendBorder = extendBorder;
    }

    public float getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(float defaultValue) {
        this.defaultValue = defaultValue;
    }

    public float getValue(double x, double y, double sampleSize) {
        return sampleRasterAt(raster, x, y, wrapX, wrapY, extendBorder, defaultValue);
    }

    /**
     * Used by RasterField and MultiRaster field to get a value from a raster with wrapping.
     */
    public static float sampleRasterAt(final Raster raster,
                                       double x,
                                       double y,
                                       final boolean wrapX,
                                       final boolean wrapY,
                                       final boolean extendBorder,
                                       final float defaultValue) {

        if (raster == null) return defaultValue;
        if (!wrapX && !extendBorder && (x < 0 || x > raster.getSizeX() - 1)) return defaultValue;
        if (!wrapY && !extendBorder && (y < 0 || y > raster.getSizeY() - 1)) return defaultValue;

        int x0 = fastFloor(x);
        int y0 = fastFloor(y);
        int x1 = x0 + 1;
        int y1 = y0 + 1;
        float cx = (float) (x - x0);
        float cy = (float) (y - y0);
        final float yr0 = mix(cx, getDataAt(raster, x0, y0, wrapX, wrapY), getDataAt(raster, x1, y0, wrapX, wrapY));
        final float yr1 = mix(cx, getDataAt(raster, x0, y1, wrapX, wrapY), getDataAt(raster, x1, y1, wrapX, wrapY));
        return mix(cy, yr0, yr1);
    }

    /**
     * @return value at the specified coordinate.  Wraps if needed.
     */
    private static float getDataAt(Raster raster, int x, int y, final boolean wrapX, final boolean wrapY) {
        x = warpCoordinate(x, wrapX, raster.getSizeX());
        y = warpCoordinate(y, wrapY, raster.getSizeY());

        return raster.getValue(x, y);
    }

    private static int warpCoordinate(int value, final boolean wrap, final int size) {
        if (wrap) {
            value %= size;
            if (value < 0) value += size;
        }
        else if (value < 0) value = 0;
        else if (value >= size) value = size - 1;

        return value;
    }


}
