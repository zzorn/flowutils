package org.flowutils.raster.raster.multi;

import org.flowutils.Symbol;
import org.flowutils.raster.raster.single.Raster;
import org.flowutils.raster.raster.single.RasterImpl;

import java.util.Arrays;
import java.util.Collection;

import static org.flowutils.Check.notNull;

/**
 * A MultiRaster that interleaves the data of its channels in a single large data array.
 * This speeds up access in tight loops if the channels at a specific location are usually accessed together.
 *
 * Channels can not be added or removed after the InterleavedMultiRaster is created.
 */
public final class InterleavedMultiRaster extends MultiRasterBase {

    private final float data[];

    /**
     * @param sizeX x size of the raster (must be positive)
     * @param sizeY y size of the raster (must be positive)
     * @param channels the ids of the channels to have in the raster, in the order they should be interleaved at each pixel.
     */
    public InterleavedMultiRaster(int sizeX, int sizeY, Symbol ... channels) {
        this(sizeX, sizeY, Arrays.asList(channels));
    }

    /**
     * @param sizeX x size of the raster (must be positive)
     * @param sizeY y size of the raster (must be positive)
     * @param channels a collection with the ids of the channels to have in the raster,
     *                 in the order they should be interleaved at each pixel.
     */
    public InterleavedMultiRaster(int sizeX, int sizeY, Collection<Symbol> channels) {
        super(sizeX, sizeY);
        notNull(channels, "channels");

        // Create data array to hold the interleaved channel rasters in
        int rasterSize = sizeX * sizeY;
        data = new float[rasterSize * channels.size()];

        // Create the channel rasters
        int xStep = channels.size();
        int offset = 0;
        for (Symbol channelId : channels) {
            Raster channelRaster = new RasterImpl(sizeX, sizeY, data, offset++, xStep, 0);
            doAddChannel(channelId, channelRaster);
        }
    }

    /**
     * @return interleaved data array containing the channel elements for each pixel in sequence.
     *         E.g. if we have three channels, R, G, and B in that order, the data array will contain pixel 0,0 of channel R,
     *         followed by pixel 0,0 of channel G, followed by pixel 0,0 of channel B, followed by pixel 1,0 of channel R,
     *         pixel 1,0 of channel G, etc.
     */
    public float[] getData() {
        return data;
    }

}
