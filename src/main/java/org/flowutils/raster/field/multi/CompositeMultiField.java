package org.flowutils.raster.field.multi;

import org.flowutils.Check;
import org.flowutils.Symbol;
import org.flowutils.raster.field.single.Field;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * A MultiField implementation that uses a Field for each channel.
 */
public final class CompositeMultiField extends MultiFieldBase {

    private final ConcurrentMap<Symbol, Field> fields = new ConcurrentHashMap<Symbol, Field>();

    @Override public final float getValue(double x, double y, Symbol channelId) {
        return getValue(x, y, channelId, 0);
    }

    @Override public final float getValue(double x, double y, Symbol channelId, double sampleSize) {
        return getChannel(channelId).getValue(x, y, sampleSize);
    }

    @Override public final boolean hasChannel(Symbol channelId) {
        return getChannelIds().contains(channelId);
    }

    @Override public final Collection<Symbol> getChannelIds() {
        return fields.keySet();
    }

    @Override public final Field getChannel(Symbol channelId) {
        return fields.get(channelId);
    }

    /**
     * Register a field to use for a channel.  Replaces any previous field used for that channel.
     */
    public final void setChannel(Symbol channelId, Field field) {
        Check.notNull(channelId, "channelId");
        Check.notNull(field, "field");

        fields.put(channelId, field);
    }

    /**
     * Removes the field for the specified channel, if it has been set with setChannel.
     */
    public final void removeChannel(Symbol channelId) {
        fields.remove(channelId);
    }

}
