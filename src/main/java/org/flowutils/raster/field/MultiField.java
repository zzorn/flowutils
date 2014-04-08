package org.flowutils.raster.field;

import org.flowutils.Symbol;
import org.flowutils.raster.raster.MultiRaster;
import org.flowutils.raster.raster.Raster;
import org.flowutils.rectangle.Rectangle;

import java.util.Collection;
import java.util.Map;

/**
 * A two dimensional continuous data field, with one or more channels.
 */
// TODO: Remove raster classes to a separate raster library
public interface MultiField {

    /**
     * @param x x coordinate to get data for
     * @param y y coordinate to get data for
     * @param channelId id of channel to get data for
     * @return the data value at the specified location and channel.
     */
    float sampleValue(double x, double y, Symbol channelId);

    /**
     * @param x x coordinate to get data for
     * @param y y coordinate to get data for
     * @param sampleSize size of the area to sample.  If zero, will use the most exact available value.
     * @param channelId id of channel to get data for
     * @return the data value at the specified location and channel.
     */
    float sampleValue(double x, double y, double sampleSize, Symbol channelId);

    /**
     * Retrieve the values of all the channels at the specified location.
     *
     * @param x x coordinate to get data for
     * @param y y coordinate to get data for
     * @param dataOut map to write channel values to.
     */
    void sampleValues(double x, double y, Map<Symbol, Float> dataOut);

    /**
     * Retrieve the values of all the channels at the specified location.
     *
     * @param x x coordinate to get data for
     * @param y y coordinate to get data for
     * @param sampleSize size of the area to sample.  If zero, will use the most exact available value.
     * @param dataOut map to write channel values to.
     */
    void sampleValues(double x, double y, double sampleSize, Map<Symbol, Float> dataOut);

    /**
     * @return ids of the available channels.
     */
    Collection<Symbol> getChannelIds();

    /**
     * @return true if the specified channel is available.
     */
    boolean hasChannel(Symbol channelId);

    /**
     * @return field for the specified channel only.
     */
    Field getChannel(Symbol channelId);


//    void buildSource(SourceBuilder sourceBuilder);

    /**
     * @return compile a renderer that will render this MultiField to any provided interleaved raster with the specified channels in the specified order.
     */
    Renderer createRenderer(Collection<Symbol> channelIds);

    /**
     * Creates a renderer, then uses it to render the area 0,0 to 1,1 from this field to the specified raster.
     */
    void renderToRaster(MultiRaster raster);

    /**
     * Creates a renderer, then uses it to render the specified area from this field to the specified raster
     */
    void renderToRaster(MultiRaster raster, Rectangle area);

}
