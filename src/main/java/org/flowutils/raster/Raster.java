package org.flowutils.raster;

import org.flowutils.Symbol;
import org.flowutils.rectangle.Rectangle;

import java.util.Collection;
import java.util.Map;

/**
 * Interface for a raster with float values, composed of one or more channels.
 */
public interface Raster {

    /**
     * @return the identifiers of the channels available in this raster.
     */
    Collection<Symbol> getChannels();

    /**
     * @return true if this raster has a channel with the specified id.
     */
    boolean hasChannel(Symbol channelId);

    /**
     * @return logical area that this raster covers.
     */
    Rectangle getArea();

    /**
     * @param x x coordinate to get data for
     * @param y y coordinate to get data for
     * @param channel channel to get data for
     * @return the data value at the specified location and channel.
     */
    float getValue(double x, double y, Symbol channel);

    /**
     * Set the closest raster location to the specified value
     * (alternatively, change adjacent raster values so that the value
     * at the specified location is the desired one).
     * @param x approximate x coordinate to set value of
     * @param y approximate y coordinate to set value of
     * @param channel channel to set data for
     * @param value value to set
     */
    void setValue(double x, double y, Symbol channel, float value);

    /**
     * Retrieve the values of all the channels at the specified location.
     *
     * @param x x coordinate to get data for
     * @param y y coordinate to get data for
     * @param dataOut map to write cannel values to.
     */
    void getValues(double x, double y, Map<Symbol, Float> dataOut);

    /**
     * Set the specified channels of the closest raster location to
     * the specified value (alternatively, change adjacent raster values
     * so that the values at the specified location are the desired ones).
     * @param x approximate x coordinate to set value of
     * @param y approximate y coordinate to set value of
     * @param data channel id to channel value map.
     */
    void setValues(double x, double y, Map<Symbol, Float> data);

}
