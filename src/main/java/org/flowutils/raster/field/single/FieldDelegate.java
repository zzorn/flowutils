package org.flowutils.raster.field.single;

import org.flowutils.Symbol;
import org.flowutils.raster.field.multi.MultiField;

import static org.flowutils.Check.notNull;

/**
 * Picks out values from a single field in a MultiField.
 */
public final class FieldDelegate extends FieldBase {

    private final MultiField sourceField;
    private final Symbol channel;

    public FieldDelegate(MultiField sourceField, Symbol channel) {
        notNull(sourceField, "sourceField");
        notNull(channel, "channel");

        this.sourceField = sourceField;
        this.channel = channel;
    }

    @Override public float getValue(double x, double y) {
        return sourceField.getValue(x, y, channel, 0);
    }

    @Override public float getValue(double x, double y, double sampleSize) {
        return sourceField.getValue(x, y, channel, sampleSize);
    }

}
