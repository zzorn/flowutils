package org.flowutils.raster;

import org.flowutils.Check;
import org.flowutils.Symbol;
import org.flowutils.rectangle.ImmutableRectangle;
import org.flowutils.rectangle.Rectangle;

import java.util.Collections;

/**
 * Base class for a GridRaster that has a single channel.
 */
public abstract class SingleChannelGridRaster extends GridRasterBase {

    private Symbol channelId;

    /**
     * Uses the default channel name as channel id, and a raster area of 0,0 to 1,1.
     */
    protected SingleChannelGridRaster() {
        this(Symbol.get("value"));
    }

    /**
     * Uses the specified channel name as channel id, and a raster area of 0,0 to 1,1.
     *
     * @param channelId the id of the channel.
     */
    protected SingleChannelGridRaster(final Symbol channelId) {
        this(new ImmutableRectangle(0,0,1,1), channelId);
    }

    /**
     * Uses the specified raster area and channel id.
     *
     * @param area the area the raster covers in the coordinate system.
     * @param channelId the id of the channel.
     */
    protected SingleChannelGridRaster(final Rectangle area, final Symbol channelId) {
        super(area, null);
        setChannelId(channelId);
    }

    /**
     * @return the id of the channel.
     */
    public final Symbol getChannelId() {
        return channelId;
    }

    /**
     * @param channelId the id of the channel provided by this raster.
     */
    protected void setChannelId(final Symbol channelId) {
        Check.notNull(channelId, "channelId");
        this.channelId = channelId;

        setChannels(Collections.singleton(channelId));
    }

    /**
     * Get the value at the specified raster grid location.
     *
     * @param gridX grid x coordinate to get data for.  Starts at zero and goes to gridSizeX.
     * @param gridY grid y coordinate to get data for.  Starts at zero and goes to gridSizeY.
     * @return the data value at the specified location and channel.
     */
    public abstract float getGridValue(int gridX, int gridY);

    /**
     * Set the raster grid location to the specified value
     *
     * @param gridX grid x coordinate to get data for.  Starts at zero and goes to gridSizeX.
     * @param gridY grid y coordinate to get data for.  Starts at zero and goes to gridSizeY.
     * @param value value to set
     */
    public abstract void setGridValue(int gridX, int gridY, float value);


    @Override
    public final float getGridValue(final int gridX, final int gridY, final Symbol channel) {
        if (!channelId.equals(channel)) throw new IllegalArgumentException("No channel named '"+channel+"' is available, the only channel available is '"+channelId+"'");

        return getGridValue(gridX, gridY);
    }

    @Override
    public final void setGridValue(final int gridX, final int gridY, final Symbol channel, final float value) {
        if (!channelId.equals(channel)) throw new IllegalArgumentException("No channel named '"+channel+"' is available, the only channel available is '"+channelId+"'");

        setGridValue(gridX, gridY, value);
    }
}
