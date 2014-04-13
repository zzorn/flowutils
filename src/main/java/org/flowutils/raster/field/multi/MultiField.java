package org.flowutils.raster.field.multi;

import org.flowutils.Symbol;
import org.flowutils.raster.field.RenderListener;
import org.flowutils.raster.field.single.Field;
import org.flowutils.raster.raster.multi.MultiRaster;
import org.flowutils.rectangle.Rectangle;
import org.flowutils.rectangle.intrectangle.IntRectangle;

import java.util.Collection;

/**
 * A two dimensional continuous data field, with one or more channels.
 *
 * Can be rendered to a MultiRaster or float arrays.
 */
public interface MultiField {

    /**
     * @param x x coordinate to get data for
     * @param y y coordinate to get data for
     * @param channelId id of channel to get data for
     * @return the data value at the specified location and channel.
     */
    float getValue(double x, double y, Symbol channelId);

    /**
     * @param x x coordinate to get data for
     * @param y y coordinate to get data for
     * @param channelId id of channel to get data for
     * @param sampleSize size of the area to sample.  If zero, will use the most exact available value.
     * @return the data value at the specified location and channel.
     */
    float getValue(double x, double y, Symbol channelId, double sampleSize);

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

    /**
     * Render the area 0,0 to 1,1 from this field to the specified raster.
     */
    void renderToRaster(MultiRaster raster);

    /**
     * Render the specified area from this field to the specified raster
     */
    void renderToRaster(MultiRaster raster, Rectangle sourceArea);

    /**
     * Render the fieldArea from this field to the rasterArea on the specified raster
     */
    void renderToRaster(MultiRaster raster, Rectangle sourceArea, IntRectangle targetArea);

    /**
     * Writes the specified area from this field to the specified area on the raster
     *
     * @param targetRaster MultiRaster raster to render to
     * @param sourceArea source area from the field to render (null to render the area 0, 0 to 1, 1
     * @param targetArea area on the raster to render to (null to render to the whole raster)
     * @param renderListener listener that is notified of rendering progress, or null if no listener specified.
     */
    void renderToRaster(MultiRaster targetRaster, Rectangle sourceArea, IntRectangle targetArea, RenderListener renderListener);

    /**
     * Renders a part of this multifield to the target arrays
     *
     * @param targetChannelIds ids of channels to render to, in the same order as the other target arrays.
     * @param targetDatas target data arrays to render to
     * @param targetSizeX size of the target area to render to
     * @param targetSizeY size of the target area to render to
     * @param targetOffsets offsets to the start of the area to render to in the array for each target data array.
     * @param targetXSteps steps to apply to get from one element to the next in a row (1 == no extra steps between each element) for each target data array.
     * @param targetYSkips elements to skip between each target row for each target data array.
     * @param sourceStartX x position to start sampling the field from
     * @param sourceStartY y position to start sampling the field from
     * @param sourceStepX x step to apply to the source after each element sample of the field
     * @param sourceStepY y step to apply to the source after each row sampled from the field
     * @param sourceSampleSize sample size to use when sampling the field
     * @param renderListener listener that is notified of rendering progress, or null if no listener specified.
     */
    void renderToArrays(Symbol[] targetChannelIds,
                        float[][] targetDatas,
                        int targetSizeX,
                        int targetSizeY,
                        int[] targetOffsets,
                        int[] targetXSteps,
                        int[] targetYSkips,
                        double sourceStartX,
                        double sourceStartY,
                        double sourceStepX,
                        double sourceStepY,
                        double sourceSampleSize,
                        RenderListener renderListener);
}
