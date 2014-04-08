package org.flowutils.raster.field;

import org.flowutils.Check;
import org.flowutils.raster.raster.Raster;
import org.flowutils.rectangle.ImmutableRectangle;
import org.flowutils.rectangle.Rectangle;

import static org.flowutils.Check.notNull;

/**
 * Common functionality for fields.
 */
public abstract class FieldBase implements Field {

    private static final ImmutableRectangle DEFAULT_RENDER_AREA = new ImmutableRectangle(0,0, 1,1);

    @Override public final float getValue(double x, double y) {
        return getValue(x, y, 0);
    }

    @Override public final void renderToRaster(Raster raster) {
        renderToRaster(raster, DEFAULT_RENDER_AREA);
    }

    @Override public final void renderToRaster(Raster raster, Rectangle area) {
        notNull(raster, "raster");
        notNull(area, "area");
        if (area.isEmpty()) throw new IllegalArgumentException("Area can not be empty");

        final int sizeX = raster.getSizeX();
        final int sizeY = raster.getSizeY();

        final double sourceStartX = area.getMinX();
        final double sourceStartY = area.getMinY();
        final double sourceStepX = (1.0 / (sizeX - 1)) * area.getSizeX();
        final double sourceStepY = (1.0 / (sizeY - 1)) * area.getSizeY();
        final double sampleSize = ((sourceStepX + sourceStepY) * 0.5) * area.getSizeAverage();

        renderToArray(raster.getData(),
                      sizeX,
                      sizeY,
                      raster.getDataOffset(),
                      raster.getDataXStep(),
                      raster.getDataYSkip(),
                      sourceStartX,
                      sourceStartY,
                      sourceStepX,
                      sourceStepY,
                      sampleSize);
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
                                        double sourceSampleSize) {
        Check.notNull(target, "target");
        Check.positiveOrZero(targetSizeX, "targetSizeX");
        Check.positiveOrZero(targetSizeY, "targetSizeY");
        Check.positiveOrZero(targetOffset, "targetOffset");
        Check.notZero(targetXStep, "targetXStep");
        Check.positiveOrZero(sourceSampleSize, "sourceSampleSize");


        int i = targetOffset;
        double sourceY = sourceStartY;
        for (int y = 0; y < targetSizeY; y++) {
            double sourceX = sourceStartX;
            for (int x = 0; x < targetSizeX; x++) {
                target[i] = getValue(sourceX, sourceY, sourceSampleSize);

                i += targetXStep;
                sourceX += sourceStepX;
            }

            i += targetYSkip;
            sourceY += sourceStepY;
        }
    }

}
