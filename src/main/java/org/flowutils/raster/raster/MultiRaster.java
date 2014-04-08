package org.flowutils.raster.raster;

import org.flowutils.Symbol;
import org.flowutils.raster.field.MultiField;
import org.flowutils.rectangle.Rectangle;

import java.util.Collection;
import java.util.Map;

/**
 * A two dimensional data raster with float values, composed of one or more channels.
 */
// TODO: Make this interleaved by default?  Or create separate InterleavedRaster maybe
public interface MultiRaster {

    /**
     * @return width of the raster in grid cells.
     */
    int getSizeX();

    /**
     * @return height of the raster in grid cells.
     */
    int getSizeY();

    /**
     * @return the identifiers of the channels available in this raster.
     */
    Collection<Symbol> getChannelIds();

    /**
     * @return true if this raster has a channel with the specified id.
     */
    boolean hasChannel(Symbol channelId);

    /**
     * @param channelId the id of the channel to get.
     * @return the specified channel.
     * @throws IllegalArgumentException if the channel is not available.
     */
    Raster getChannel(Symbol channelId);

    /**
     * @return a map from channel ids to the channels available in this raster.
     */
    Map<Symbol, Raster> getChannels();


    /**
     * Get the value of the specified channel at the specified raster grid location.
     *
     * @param gridX grid x coordinate to get data for.  Starts at zero and goes to gridSizeX.
     * @param gridY grid y coordinate to get data for.  Starts at zero and goes to gridSizeY.
     * @param channel channel to get data for
     * @return the data value at the specified location and channel.
     */
    float getValue(int gridX, int gridY, Symbol channel);

    /**
     * Retrieve the values of all the channels at the specified grid location.
     *
     * @param gridX grid x coordinate to get data for.  Starts at zero and goes to gridSizeX.
     * @param gridY grid y coordinate to get data for.  Starts at zero and goes to gridSizeY.
     * @param dataOut map to write cannel values to.
     */
    void getValues(int gridX, int gridY, Map<Symbol, Float> dataOut);

    /**
     * Set the raster grid location to the specified value
     *
     * @param gridX grid x coordinate to get data for.  Starts at zero and goes to gridSizeX.
     * @param gridY grid y coordinate to get data for.  Starts at zero and goes to gridSizeY.
     * @param channel channel to set data for
     * @param value value to set
     */
    void setValue(int gridX, int gridY, Symbol channel, float value);

    /**
     * Set the specified channels of the closest raster location to
     * the specified value (alternatively, change adjacent raster values
     * so that the values at the specified location are the desired ones).
     *
     * @param gridX grid x coordinate to get data for.  Starts at zero and goes to gridSizeX.
     * @param gridY grid y coordinate to get data for.  Starts at zero and goes to gridSizeY.
     * @param data channel id to channel value map.
     */
    void setValues(int gridX, int gridY, Map<Symbol, Float> data);



}
