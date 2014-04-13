package org.flowutils.raster.field.multi;

import org.flowutils.Symbol;
import org.flowutils.raster.field.single.RasterField;
import org.flowutils.raster.raster.multi.MultiRaster;

import static org.flowutils.MathUtils.fastFloor;
import static org.flowutils.MathUtils.mix;

/**
 * A MultiField that samples a MultiRaster.
 * Has a wrapping that defines how values outside the rectangle area are retrieved.
 */
public final class MultiRasterField extends MultiFieldBase {

    private MultiRaster raster;
    private boolean wrapX;
    private boolean wrapY;
    private boolean extendBorder;
    private float defaultValue;

    /**
     * Wraps edges in x and y directions.
     */
    MultiRasterField(MultiRaster raster) {
        this(raster, true, true, false, 0);
    }

    /**
     * Uses the specified defaultValue for any place outside the raster.
     */
    MultiRasterField(MultiRaster raster, float defaultValue) {
        this(raster, false, false, false, defaultValue);
    }

    /**
     * Wraps the specified edges.  Non-wrapped edges have their border values extended outwards.
     *
     * @param wrapX if true, the values are wrapped in the x direction.
     * @param wrapY if true, the values are wrapped in the y direction.
     */
    MultiRasterField(MultiRaster raster, boolean wrapX, boolean wrapY) {
        this(raster, wrapX, wrapY, true, 0);
    }

    /**
     * Wraps the specified edges.  Non-wrapped edges have the specified default value.
     *
     * @param wrapX if true, the values are wrapped in the x direction.
     * @param wrapY if true, the values are wrapped in the y direction.
     * @param defaultValue the default value to use outside the raster for directions that are not wrapped.
     */
    MultiRasterField(MultiRaster raster, boolean wrapX, boolean wrapY, float defaultValue) {
        this(raster, wrapX, wrapY, false, defaultValue);
    }

    /**
     * @param wrapX if true, the values are wrapped in the x direction.
     * @param wrapY if true, the values are wrapped in the y direction.
     * @param extendBorder if true, values are clamped to the border value for non-wrapped directions.
     * @param defaultValue the default value to use outside the raster for directions that are not wrapped if the border value is not extended.
     */
    MultiRasterField(MultiRaster raster, boolean wrapX, boolean wrapY, boolean extendBorder, float defaultValue) {
        this.wrapX = wrapX;
        this.wrapY = wrapY;
        this.extendBorder = extendBorder;
        this.defaultValue = defaultValue;

        setRaster(raster);
    }

    public MultiRaster getRaster() {
        return raster;
    }

    public void setRaster(MultiRaster raster) {
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

    @Override public float getValue(double x, double y, Symbol channelId, double sampleSize) {
        return RasterField.sampleRasterAt(raster.getChannel(channelId), x, y, wrapX, wrapY, extendBorder, defaultValue);
    }

}
