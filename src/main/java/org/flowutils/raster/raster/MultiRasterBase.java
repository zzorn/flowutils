package org.flowutils.raster.raster;

import org.flowutils.Check;
import org.flowutils.Symbol;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.flowutils.Check.notNull;
import static org.flowutils.Check.positive;

/**
 * Common functionality for MultiRasters.
 */
public abstract class MultiRasterBase implements MultiRaster {

    private final int sizeX;
    private final int sizeY;
    private final Map<Symbol, Raster> channels = new LinkedHashMap<Symbol, Raster>();

    /**
     * @param sizeX x size of the raster.  Should not be zero.
     * @param sizeY y size of the raster.  Should not be zero.
     */
    protected MultiRasterBase(int sizeX, int sizeY) {
        this(sizeX, sizeY, null);
    }

    /**
     * @param sizeX x size of the raster.  Should not be zero.
     * @param sizeY y size of the raster.  Should not be zero.
     * @param channels map from channel id to channel raster with the channels to add initially, or null if no channels added in the constructor.
     */
    protected MultiRasterBase(int sizeX, int sizeY, Map<Symbol, Raster> channels) {
        positive(sizeX, "sizeX");
        positive(sizeY, "sizeY");

        this.sizeX = sizeX;
        this.sizeY = sizeY;

        // Add initial channels
        if (channels != null) {
            for (Map.Entry<Symbol, Raster> entry : channels.entrySet()) {
                final Symbol channelId = entry.getKey();
                final Raster channelRaster = entry.getValue();
                doAddChannel(channelId, channelRaster);
            }
        }
    }



    @Override public final int getSizeX() {
        return sizeX;
    }

    @Override public final int getSizeY() {
        return sizeY;
    }

    @Override public final Collection<Symbol> getChannelIds() {
        return Collections.unmodifiableCollection(channels.keySet());
    }

    @Override public final boolean hasChannel(Symbol channelId) {
        return channels.containsKey(channelId);
    }

    @Override public final Raster getChannel(Symbol channelId) {
        final Raster raster = channels.get(channelId);
        if (raster != null) {
            return raster;
        } else {
            throw new IllegalArgumentException("No channel with id '" + channelId + "' found.");
        }
    }

    @Override public final Map<Symbol, Raster> getChannels() {
        return Collections.unmodifiableMap(channels);
    }

    @Override public final float getValue(int gridX, int gridY, Symbol channel) {
        return getChannel(channel).getValue(gridX, gridY);
    }

    @Override public final void getValues(int gridX, int gridY, Map<Symbol, Float> dataOut) {
        for (Map.Entry<Symbol, Raster> entry : channels.entrySet()) {
            final Symbol channelId = entry.getKey();
            final Raster channelRaster = entry.getValue();

            dataOut.put(channelId, channelRaster.getValue(gridX, gridY));
        }
    }

    @Override public final void setValue(int gridX, int gridY, Symbol channel, float value) {
        getChannel(channel).setValue(gridX, gridY, value);
    }

    @Override public final void setValues(int gridX, int gridY, Map<Symbol, Float> data) {
        for (Map.Entry<Symbol, Float> entry : data.entrySet()) {
            final Symbol channelId = entry.getKey();

            final Raster channelRaster = channels.get(channelId);
            if (channelRaster != null) {
                final float value = entry.getValue();
                channelRaster.setValue(gridX, gridY, value);
            }
        }
    }


    /**
     * Keeps track of a new channel, or replaces a previous channel with the same id.
     *
     * Not thread safe, should not be called at the same time as channels may be enumerated or changed from other threads.
     *
     * @param channelId id of the channel.
     * @param channelRaster raster associated with the channel.
     */
    protected final void doAddChannel(Symbol channelId, Raster channelRaster) {
        notNull(channelId, "channelId");
        notNull(channelRaster, "channelRaster");
        Check.equal(channelRaster.getSizeX(), "x size of added channel", sizeX, "raster x size");
        Check.equal(channelRaster.getSizeY(), "y size of added channel", sizeY, "raster y size");

        channels.put(channelId, channelRaster);
    }

    /**
     * Removes an existing channel, if found.
     *
     * Not thread safe, should not be called at the same time as channels may be enumerated or changed from other threads.
     *
     * @param channelId id of the channel to remove.
     */
    protected final void doRemoveChannel(Symbol channelId) {
        notNull(channelId, "channelId");

        channels.remove(channelId);
    }

}
