package org.flowutils.raster;

import org.flowutils.Symbol;

import java.util.Map;

/**
 * A type of raster that has a known size, and possibility to access
 * the grid cells by cell coordinates as well.
 */
public interface GridRaster extends Raster {

    /**
     * @return width of the raster in grid cells.
     */
    int getGridSizeX();

    /**
     * @return height of the raster in grid cells.
     */
    int getGridSizeY();

    /**
     * Get the value of the specified channel at the specified raster grid location.
     *
     * @param gridX grid x coordinate to get data for.  Starts at zero and goes to gridSizeX.
     * @param gridY grid y coordinate to get data for.  Starts at zero and goes to gridSizeY.
     * @param channel channel to get data for
     * @return the data value at the specified location and channel.
     */
    float getGridValue(int gridX, int gridY, Symbol channel);

    /**
     * Retrieve the values of all the channels at the specified grid location.
     *
     * @param gridX grid x coordinate to get data for.  Starts at zero and goes to gridSizeX.
     * @param gridY grid y coordinate to get data for.  Starts at zero and goes to gridSizeY.
     * @param dataOut map to write cannel values to.
     */
    void getGridValues(int gridX, int gridY, Map<Symbol, Float> dataOut);

    /**
     * Set the raster grid location to the specified value
     *
     * @param gridX grid x coordinate to get data for.  Starts at zero and goes to gridSizeX.
     * @param gridY grid y coordinate to get data for.  Starts at zero and goes to gridSizeY.
     * @param channel channel to set data for
     * @param value value to set
     */
    void setGridValue(int gridX, int gridY, Symbol channel, float value);

    /**
     * Set the specified channels of the closest raster location to
     * the specified value (alternatively, change adjacent raster values
     * so that the values at the specified location are the desired ones).
     *
     * @param gridX grid x coordinate to get data for.  Starts at zero and goes to gridSizeX.
     * @param gridY grid y coordinate to get data for.  Starts at zero and goes to gridSizeY.
     * @param data channel id to channel value map.
     */
    void setGridValues(int gridX, int gridY, Map<Symbol, Float> data);

}
