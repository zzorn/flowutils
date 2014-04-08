package org.flowutils.raster.raster;

import org.flowutils.Symbol;

import java.util.Collection;
import java.util.Map;

/**
 * A MultiRaster implementation where channels can be added or removed on the fly.
 * The channel rasters are not interleaved by default (unless interleaved rasters are added).
 *
 * Adding or removing channels is not thread safe.
 */
public final class MutableMultiRaster extends MultiRasterBase {

    /**
     * @param sizeX x size of the raster.  Should not be zero.
     * @param sizeY y size of the raster.  Should not be zero.
     * @param initialChannelIds ids of channels to add initially.
     */
    public MutableMultiRaster(int sizeX,
                              int sizeY,
                              Symbol... initialChannelIds) {
        super(sizeX, sizeY);

        for (Symbol initialChannelId : initialChannelIds) {
            addChannel(initialChannelId);
        }
    }

    /**
     * @param sizeX x size of the raster.  Should not be zero.
     * @param sizeY y size of the raster.  Should not be zero.
     * @param initialChannelIds collection with ids of channels to add initially,
     *                          or null if no channels should be added in the constructor.
     */
    public MutableMultiRaster(int sizeX,
                              int sizeY,
                              Collection<Symbol> initialChannelIds) {
        super(sizeX, sizeY);

        for (Symbol initialChannelId : initialChannelIds) {
            addChannel(initialChannelId);
        }
    }

    /**
     * @param sizeX x size of the raster.  Should not be zero.
     * @param sizeY y size of the raster.  Should not be zero.
     * @param initialChannels map from channel id to channel raster with the channels to add initially,
     *                        or null if no channels should be added in the constructor.
     */
    public MutableMultiRaster(int sizeX,
                              int sizeY,
                              Map<Symbol, Raster> initialChannels) {
        super(sizeX, sizeY, initialChannels);
    }

    /**
     * Adds a new channel with the specified id to this raster, if it does not already have such a channel.
     */
    void addChannel(Symbol channelId) {
        if (!hasChannel(channelId)) {
            doAddChannel(channelId, new RasterImpl(getSizeX(), getSizeY()));
        }
    }

    /**
     * Adds a new channel with the specified id and channel raster to this raster, replacing any existing channel with the same id.
     */
    void addChannel(Symbol channelId, Raster raster) {
        doAddChannel(channelId, raster);
    }

    /**
     * Removes the specified channel from this raster, if the channel exists.
     */
    void removeChannel(Symbol channelId) {
        doRemoveChannel(channelId);
    }

}
