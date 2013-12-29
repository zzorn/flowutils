package org.flowutils.raster;

import org.flowutils.Check;
import org.flowutils.Symbol;
import org.flowutils.rectangle.ImmutableRectangle;
import org.flowutils.rectangle.Rectangle;

import java.util.*;
import java.util.Map.Entry;

/**
 * Common functionality for rasters.
 */
public abstract class RasterBase implements Raster {

    private Rectangle area;
    private final Set<Symbol> channels = new LinkedHashSet<Symbol>();

    private transient Set<Symbol> readOnlyChannels;

    /**
     * Defaults to an area from 0,0 to 1,1 and no available channels.
     */
    protected RasterBase() {
        this(null);
    }

    /**
     * Defaults to an area from 0,0 to 1,1.
     *
     * @param channels channels available in this raster.  If null, no channels will be available.
     */
    protected RasterBase(final Collection<Symbol> channels) {
        this(new ImmutableRectangle(0,0,1,1), channels);
    }

    /**
     * @param area area that this raster covers
     * @param channels channels available in this raster.  If null, no channels will be available.
     */
    protected RasterBase(final Rectangle area, final Collection<Symbol> channels) {
        Check.notNull(area, "area");
        this.area = area;

        if (channels != null) {
            this.channels.addAll(channels);
        }
    }

    @Override
    public final Rectangle getArea() {
        return area;
    }

    @Override
    public final Collection<Symbol> getChannels() {
        if (readOnlyChannels == null) {
            readOnlyChannels = Collections.unmodifiableSet(channels);
        }

        return readOnlyChannels;
    }

    @Override
    public final boolean hasChannel(final Symbol channelId) {
        return channels.contains(channelId);
    }

    /**
     * Set the area that this raster covers.
     */
    protected void setArea(final Rectangle area) {
        Check.notNull(area, "area");
        this.area = area;
    }

    /**
     * Add a channel to the available channels.
     */
    protected final void addChannel(Symbol channelId) {
        channels.add(channelId);
    }

    /**
     * Remove a channel from the available channels.
     */
    protected final void removeChannel(Symbol channelId) {
        channels.remove(channelId);
    }

    /**
     * Specify what channels are available.
     */
    protected final void setChannels(Collection<Symbol> channelIds) {
        channels.clear();
        channels.addAll(channelIds);
    }

    // Default implementation, can be overridden
    @Override
    public void getValues(final double x, final double y, final Map<Symbol, Float> dataOut) {
        for (Symbol channel : channels) {
            final float value = getValue(x, y, channel);
            dataOut.put(channel, value);
        }
    }

    // Default implementation, can be overridden
    @Override
    public void setValues(final double x, final double y, final Map<Symbol, Float> data) {
        for (Entry<Symbol, Float> entry : data.entrySet()) {
            final Symbol channel = entry.getKey();
            final float value = entry.getValue();

            // Only set values to the channels that we have
            if (channels.contains(channel)) {
                setValue(x, y, channel, value);
            }
        }
    }
}
