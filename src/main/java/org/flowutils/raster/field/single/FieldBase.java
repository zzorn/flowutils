package org.flowutils.raster.field.single;

import org.flowutils.Check;
import org.flowutils.raster.field.RenderListener;
import org.flowutils.raster.raster.single.Raster;
import org.flowutils.rectangle.ImmutableRectangle;
import org.flowutils.rectangle.Rectangle;
import org.flowutils.rectangle.intrectangle.IntRectangle;

import static org.flowutils.Check.notNull;

/**
 * Common functionality for fields.
 */
public abstract class FieldBase implements Field {

    private static final ImmutableRectangle DEFAULT_RENDER_AREA = new ImmutableRectangle(0,0, 1,1);

    private static final int PROGRESS_REPORTS_PER_RENDERING = 20;

    @Override public float getValue(double x, double y) {
        return getValue(x, y, 0);
    }

    @Override public final void renderToRaster(Raster raster) {
        renderToRaster(raster, DEFAULT_RENDER_AREA);
    }

    @Override public final void renderToRaster(Raster raster, Rectangle sourceArea) {
        renderToRaster(raster, sourceArea, null);
    }

    @Override public final void renderToRaster(Raster targetRaster, Rectangle sourceArea, IntRectangle targetArea) {
        renderToRaster(targetRaster, sourceArea, targetArea, null);
    }

    @Override
    public void renderToRaster(Raster targetRaster,
                               Rectangle sourceArea,
                               IntRectangle targetArea,
                               RenderListener renderListener) {
        notNull(targetRaster, "targetRaster");

        // Default to 0,0 - 1,1 source area if none given
        if (sourceArea == null) sourceArea = DEFAULT_RENDER_AREA;
        if (sourceArea.isEmpty()) throw new IllegalArgumentException("Source area can not be empty");

        // Use whole target raster area as the target area if none was specified
        final IntRectangle targetRasterExtent = targetRaster.getExtent();
        if (targetArea == null) {
            targetArea = targetRasterExtent;
        }
        if (!targetRasterExtent.contains(targetArea)) throw new IllegalArgumentException("The target area ("+targetArea+") is outside the extent of the target raster ("+
                                                                                         targetRasterExtent +")");
        final int targetSizeX = targetArea.getSizeX();
        final int targetSizeY = targetArea.getSizeY();

        // Calculate source start and steps
        final double sourceStartX = sourceArea.getMinX();
        final double sourceStartY = sourceArea.getMinY();
        final double sourceStepX = (1.0 / (targetSizeX - 1)) * sourceArea.getSizeX();
        final double sourceStepY = (1.0 / (targetSizeY - 1)) * sourceArea.getSizeY();
        final double sampleSize = ((sourceStepX + sourceStepY) * 0.5) * sourceArea.getSizeAverage();

        // Calculate the skips to adjust to the specified target area
        final int targetXStep = targetRaster.getDataXStep();
        final int targetYSkip = targetRaster.getDataYSkip() +
                                (targetRasterExtent.getSizeX() - targetSizeX) * targetXStep;
        final int targetOffset = targetRaster.getDataOffset() +
                                 targetArea.getMinX() * targetXStep +
                                 targetArea.getMinY() * (targetSizeX * targetXStep + targetYSkip);

        renderToArray(targetRaster.getData(),
                      targetSizeX,
                      targetSizeY,
                      targetOffset,
                      targetXStep,
                      targetYSkip,
                      sourceStartX,
                      sourceStartY,
                      sourceStepX,
                      sourceStepY,
                      sampleSize,
                      renderListener);
    }

    @Override public void renderToArray(float[] target,
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
                                        RenderListener listener) {
        Check.notNull(target, "target");
        Check.positiveOrZero(targetSizeX, "targetSizeX");
        Check.positiveOrZero(targetSizeY, "targetSizeY");
        Check.positiveOrZero(targetOffset, "targetOffset");
        Check.notZero(targetXStep, "targetXStep");
        Check.positiveOrZero(sourceSampleSize, "sourceSampleSize");

        // Initialize progress reporting counter
        final int listenerStep = Math.max(targetSizeY / PROGRESS_REPORTS_PER_RENDERING, 1);
        int listenerCountdown = listenerStep;

        boolean continueRendering = true;

        // Loop the target raster cells over the target area and sample the field to get a value for each.
        int i = targetOffset;
        double sourceY = sourceStartY;
        for (int y = 0; y < targetSizeY && continueRendering; y++) {

            // Reset source x position for each row
            double sourceX = sourceStartX;

            for (int x = 0; x < targetSizeX; x++) {
                // Sample value from field and assign it to the correct place in the raster data array
                target[i] = getValue(sourceX, sourceY, sourceSampleSize);

                // Step to next source and target location along x axis
                i += targetXStep;
                sourceX += sourceStepX;
            }

            // Step to next source and target location along y axis
            i += targetYSkip;
            sourceY += sourceStepY;

            // Report progress at some intervals, and also on the last line
            if (listener != null && (--listenerCountdown <= 0 || y >= targetSizeY-1)) {
                listenerCountdown = listenerStep;

                double progress = ((double)y) / targetSizeY;
                continueRendering = listener.onRenderProgress(progress);
            }
        }
    }

}
