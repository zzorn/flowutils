package org.flowutils.raster.field.single;

import org.flowutils.raster.field.RenderListener;
import org.flowutils.raster.raster.single.Raster;
import org.flowutils.rectangle.Rectangle;
import org.flowutils.rectangle.intrectangle.IntRectangle;

/**
 * A two dimensional field with a single floating point value for each position.
 *
 * Can be rendered to a raster or float array.
 */
public interface Field {

    /**
     * @return the value at the specified position, with a sample size of zero (= maximum detail available)
     */
    float sampleValue(double x, double y);

    /**
     * @param sampleSize size of the area to sample around the specified coordinate.  0 = use maximum available detail.
     * @return the value at the specified position, with the specified sample size.
     */
    float sampleValue(double x, double y, double sampleSize);

    /**
     * Writes the area 0,0 to 1,1 from this field to the specified raster.
     */
    void renderToRaster(Raster raster);

    /**
     * Writes the specified area from this field to the specified raster
     */
    void renderToRaster(Raster raster, Rectangle area);

    /**
     * Writes the specified area from this field to the specified raster
     *
     * @param targetRaster MultiRaster to render to
     * @param sourceArea source area from the field to render
     * @param targetArea target area to render to on the raster
     */
    void renderToRaster(Raster targetRaster, Rectangle sourceArea, IntRectangle targetArea);

    /**
     * Writes the specified area from this field to the specified raster
     *
     * @param targetRaster MultiRaster to render to
     * @param sourceArea source area from the field to render
     * @param targetArea target area to render to on the raster
     * @param renderListener listener that is notified of rendering progress, or null if no listener specified.
     */
    void renderToRaster(Raster targetRaster, Rectangle sourceArea, IntRectangle targetArea, RenderListener renderListener);


    /**
     * Renders a part of this field to the target array
     *
     * @param target array to render to
     * @param targetSizeX size of the target area to render to
     * @param targetSizeY size of the target area to render to
     * @param targetOffset offset to the start of the area to render to in the array
     * @param targetXStep step to apply to get from one element to the next in a row (1 == no extra steps between each element)
     * @param targetYSkip elements to skip between each target row
     * @param sourceStartX x position to start sampling the field from
     * @param sourceStartY y position to start sampling the field from
     * @param sourceStepX x step to apply to the source after each element sample of the field
     * @param sourceStepY y step to apply to the source after each row sampled from the field
     * @param sourceSampleSize sample size to use when sampling the field
     * @param renderListener listener that is notified of rendering progress, or null if no listener specified.
     */
    void renderToArray(float[] target,
                       int targetSizeX,
                       int targetSizeY,
                       int targetOffset,
                       int targetXStep,
                       int targetYSkip,
                       double sourceStartX,
                       double sourceStartY,
                       double sourceStepX,
                       double sourceStepY,
                       double sourceSampleSize,
                       RenderListener renderListener);
}
