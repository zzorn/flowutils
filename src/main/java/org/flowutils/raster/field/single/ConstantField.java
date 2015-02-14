package org.flowutils.raster.field.single;

/**
 *
 */
public final class ConstantField extends FieldBase {

    public ConstantField() {
        this(0);
    }

    public ConstantField(final float value) {
        this.value = value;
    }

    private float value;

    public float getValue() {
        return value;
    }

    public void setValue(final float value) {
        this.value = value;
    }

    @Override
    public float getValue(final double x, final double y, final double sampleSize) {
        return value;
    }
}
