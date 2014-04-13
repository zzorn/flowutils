package org.flowutils.raster.field.multi;

import org.flowutils.Check;
import org.flowutils.Symbol;
import org.flowutils.raster.field.RenderListener;
import org.flowutils.raster.field.single.Field;
import org.flowutils.raster.field.single.FieldDelegate;
import org.flowutils.raster.raster.multi.MultiRaster;
import org.flowutils.raster.raster.single.Raster;
import org.flowutils.rectangle.ImmutableRectangle;
import org.flowutils.rectangle.Rectangle;
import org.flowutils.rectangle.intrectangle.IntRectangle;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.flowutils.Check.notNull;

/**
 * Base class for fields with multiple channels.
 *
 * Handles most of the raster rendering, and provides functionality that allows subclasses to specify the available channels
 * if they do not want to implement the channel querying operations themselves.
 *
 * The inner loop of the raster rendering can be overridden if a subclass has a more efficient way to calculate the values than the getValue methods.
 */
public abstract class MultiFieldBase implements MultiField {

    private static final ImmutableRectangle DEFAULT_RENDER_AREA = new ImmutableRectangle(0,0, 1,1);

    private static final int PROGRESS_REPORTS_PER_RENDERING = 20;

    private final ConcurrentMap<Symbol, Field> fieldDelegates = new ConcurrentHashMap<Symbol, Field>(3);
    private final Collection<Symbol> availableChannels;

    protected MultiFieldBase() {
        this.availableChannels = null;
    }

    protected MultiFieldBase(Symbol ... availableChannels) {
        this(Arrays.asList(availableChannels));
    }

    protected MultiFieldBase(Collection<Symbol> availableChannels) {
        this.availableChannels = availableChannels;
    }

    @Override public Collection<Symbol> getChannelIds() {
        return availableChannels;
    }

    @Override public boolean hasChannel(Symbol channelId) {
        return availableChannels.contains(channelId);
    }

    @Override public Field getChannel(Symbol channelId) {
        if (hasChannel(channelId)) {
            Field field = fieldDelegates.get(channelId);

            // Create a delegate if we do not have one
            if (field == null) {
                field = new FieldDelegate(this, channelId);

                // Add the created field to the cache,
                // except if another thread already added the field, in that case use the previously added field.
                Field meanwhileAddedField = fieldDelegates.putIfAbsent(channelId, field);
                if (meanwhileAddedField != null) field = meanwhileAddedField;
            }

            return field;
        } else {
            return null;
        }
    }

    @Override public float getValue(double x, double y, Symbol channelId) {
        return getValue(x, y, channelId, 0);
    }


    @Override public final void renderToRaster(MultiRaster raster) {
        renderToRaster(raster, null);
    }

    @Override public final void renderToRaster(MultiRaster raster, Rectangle sourceArea) {
        renderToRaster(raster, sourceArea, null);
    }

    @Override public final void renderToRaster(MultiRaster raster, Rectangle sourceArea, IntRectangle targetArea) {
        renderToRaster(raster, sourceArea, targetArea, null);
    }

    @Override public final void renderToRaster(MultiRaster targetRaster,
                                               Rectangle sourceArea,
                                               IntRectangle targetArea,
                                               RenderListener renderListener) {
        notNull(targetRaster, "targetRaster");

        // Shortcut out if we have nothing to render
        if (targetRaster.getSizeX() == 0 ||
            targetRaster.getSizeY() == 0 ||
            targetRaster.getChannelIds().isEmpty()) return;

        // Default to 0,0 - 1,1 source area if none given
        if (sourceArea == null) sourceArea = DEFAULT_RENDER_AREA;
        if (sourceArea.isEmpty()) throw new IllegalArgumentException("Source area can not be empty");

        // Use whole target raster area as the target area if none was specified
        final IntRectangle targetRasterExtent = targetRaster.getExtent();
        if (targetArea == null) {
            targetArea = targetRasterExtent;
        }
        if (!targetRasterExtent.contains(targetArea)) throw new IllegalArgumentException("The target area ("+targetArea+") is outside the extent of the target raster ("+ targetRasterExtent +")");
        final int targetSizeX = targetArea.getSizeX();
        final int targetSizeY = targetArea.getSizeY();

        // Calculate source start and steps
        final double sourceStartX = sourceArea.getMinX();
        final double sourceStartY = sourceArea.getMinY();
        final double sourceStepX = (1.0 / (targetSizeX - 1)) * sourceArea.getSizeX();
        final double sourceStepY = (1.0 / (targetSizeY - 1)) * sourceArea.getSizeY();
        final double sampleSize = ((sourceStepX + sourceStepY) * 0.5) * sourceArea.getSizeAverage();

        // Determine channels to render
        final Collection<Symbol> channelIdsCollection = targetRaster.getChannelIds();
        final int channelCount = channelIdsCollection.size();
        Symbol[] channelIds = channelIdsCollection.toArray(new Symbol[channelCount]);

        // Calculate the skips to adjust to the specified target areas
        float [][] targetDatas = new float[channelCount][];
        final int[] targetOffsets = new int[channelCount];
        final int[] targetXSteps = new int[channelCount];
        final int[] targetYSkips = new int[channelCount];
        for (int channel = 0; channel < channelCount; channel++) {
            final Raster channelRaster = targetRaster.getChannel(channelIds[channel]);

            targetDatas[channel] = channelRaster.getData();
            targetXSteps[channel] = channelRaster.getDataXStep();
            targetYSkips[channel] = channelRaster.getDataYSkip() +
                                    (targetRasterExtent.getSizeX() - targetSizeX) * targetXSteps[channel];
            targetOffsets[channel] = channelRaster.getDataOffset() +
                                     targetArea.getMinX() * targetXSteps[channel] +
                                     targetArea.getMinY() * (targetXSteps[channel] * targetSizeX + targetYSkips[channel]);
        }

        renderToArrays(channelIds,
                       targetDatas,
                       targetSizeX,
                       targetSizeY,
                       targetOffsets,
                       targetXSteps,
                       targetYSkips,
                       sourceStartX,
                       sourceStartY,
                       sourceStepX,
                       sourceStepY,
                       sampleSize,
                       renderListener);
    }


    @Override public final void renderToArrays(Symbol[] targetChannelIds,
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
                                               RenderListener renderListener) {
        Check.notNull(targetDatas, "targetDatas");
        Check.notNull(targetChannelIds, "targetChannelIds");
        Check.positiveOrZero(targetSizeX, "targetSizeX");
        Check.positiveOrZero(targetSizeY, "targetSizeY");
        Check.positiveOrZero(sourceSampleSize, "sourceSampleSize");
        final int channelCount = targetChannelIds.length;
        Check.equals(targetDatas.length, "targetDatas.length", channelCount, "targetChannelIds.length");
        Check.equals(targetOffsets.length, "targetOffsets.length", channelCount, "targetChannelIds.length");
        Check.equals(targetXSteps.length, "targetXSteps.length", channelCount, "targetChannelIds.length");
        Check.equals(targetYSkips.length, "targetYSkips.length", channelCount, "targetChannelIds.length");

        // Shortcut out if we have nothing to render
        if (targetSizeX == 0 || targetSizeY == 0 || channelCount == 0) return;

        // Initialize progress reporting counter
        final int listenerStep = targetSizeY / PROGRESS_REPORTS_PER_RENDERING + 1;
        int listenerCountdown = listenerStep;

        // Loop the target raster cells over the target area and sample the field to get a value for each.
        int[] indexes = new int[channelCount];
        System.arraycopy(targetOffsets, 0, indexes, 0, channelCount);

        // Do actual iteration over the area.  This is split out into a separate function to make it easier to override.
        doRenderToArrays(channelCount, targetChannelIds,
                         targetDatas,
                         targetSizeX,
                         targetSizeY,
                         targetXSteps,
                         targetYSkips,
                         sourceStartX,
                         sourceStartY,
                         sourceStepX,
                         sourceStepY,
                         sourceSampleSize,
                         renderListener,
                         listenerStep,
                         listenerCountdown,
                         indexes);
    }

    /**
     * The core part of the render to array function, with all inputs checked for validity by the caller.
     * This can be overridden if there is a more efficient way to calculate the channel values than to call the
     * getValue functions for each target position.
     *
     * @param channelCount number of channels.
     * @param targetChannelIds channel ids for each channel
     * @param targetDatas data arrays to render each channel to
     * @param targetSizeX x size of target image to render
     * @param targetSizeY y size of target image to render
     * @param targetXSteps steps to advance in the target rasters for each step along the x direction
     * @param targetYSkips steps to skip in the target rasters between each row.
     * @param sourceStartX x start on the source fields.
     * @param sourceStartY y start on the source fields.
     * @param sourceStepX x step on the source fields.
     * @param sourceStepY y step on the source fields.
     * @param sourceSampleSize sampling size to use on the source fields.
     * @param renderListener listener to be notified of progress every time the listener countdown reaches zero, or null if no listener provided.
     * @param listenerStep what value the listener countdown should be assigned once it has reached zero and the listener notified.
     * @param listenerCountdown initial value for the listener countdown.
     * @param indexes array with target raster indexes, initialized to their initial position.  Can be changed.
     */
    protected void doRenderToArrays(int channelCount,
                                    Symbol[] targetChannelIds,
                                    float[][] targetDatas,
                                    int targetSizeX,
                                    int targetSizeY,
                                    int[] targetXSteps,
                                    int[] targetYSkips,
                                    double sourceStartX,
                                    double sourceStartY,
                                    double sourceStepX,
                                    double sourceStepY,
                                    double sourceSampleSize,
                                    RenderListener renderListener,
                                    int listenerStep,
                                    int listenerCountdown,
                                    int[] indexes) {

        // Y loop
        double sourceY = sourceStartY;
        for (int y = 0; y < targetSizeY; y++) {

            // X loop
            double sourceX = sourceStartX;
            for (int x = 0; x < targetSizeX; x++) {

                // Loop over the channels
                for (int channel = 0; channel < channelCount; channel++) {
                    // Sample value from the channel and assign it to the correct place in the correct raster data array
                    targetDatas[channel][indexes[channel]] = getValue(sourceX,
                                                                      sourceY,
                                                                      targetChannelIds[channel],
                                                                      sourceSampleSize);
                }

                // Step to next source and target location along x axis
                sourceX += sourceStepX;
                for (int channel = 0; channel < channelCount; channel++) {
                    indexes[channel] += targetXSteps[channel];
                }
            }

            // Step to next source and target location along y axis
            sourceY += sourceStepY;
            for (int channel = 0; channel < channelCount; channel++) {
                indexes[channel] += targetYSkips[channel];
            }

            // Report progress at some intervals, and also on the last line
            if (renderListener != null && (listenerCountdown-- <= 0 || y >= targetSizeY-1)) {
                listenerCountdown = listenerStep;

                double progress = ((double)y) / targetSizeY;
                renderListener.onRenderProgress(progress);
            }
        }
    }

}
