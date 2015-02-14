package org.flowutils.raster.field.single;

import org.flowutils.SimplexGradientNoise;

/**
 *
 */
public final class NoiseField extends FieldBase {

    public NoiseField() {
    }

    public NoiseField(final double scaleX, final double scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
    }

    public NoiseField(final double scaleX, final double scaleY, final double offsetX, final double offsetY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    private double scaleX  = 1;
    private double scaleY  = 1;
    private double offsetX = 0;
    private double offsetY = 0;

    public double getScaleX() {
        return scaleX;
    }

    public void setScaleX(final double scaleX) {
        this.scaleX = scaleX;
    }

    public double getScaleY() {
        return scaleY;
    }

    public void setScaleY(final double scaleY) {
        this.scaleY = scaleY;
    }

    public double getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(final double offsetX) {
        this.offsetX = offsetX;
    }

    public double getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(final double offsetY) {
        this.offsetY = offsetY;
    }

    @Override
    public float getValue(final double x, final double y, final double sampleSize) {
        return (float) SimplexGradientNoise.sdnoise2(x * scaleX + offsetX,
                                                     y * scaleY + offsetY);
    }
}
