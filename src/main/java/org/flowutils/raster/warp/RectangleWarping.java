package org.flowutils.raster.warp;

import org.flowutils.rectangle.ImmutableRectangle;
import org.flowutils.rectangle.Rectangle;

import static org.flowutils.Check.*;
import static org.flowutils.Check.notNull;

/**
 * Warps input coordinates 0,0 to 1,1 to the specified rectangles upper left and lower right corner.
 */
public final class RectangleWarping implements Warping {

    private static final ImmutableRectangle DEFAULT_RECTANGLE = new ImmutableRectangle(0,0, 1,1);

    private Rectangle rectangle;

    public RectangleWarping() {
        this(DEFAULT_RECTANGLE);
    }

    public RectangleWarping(Rectangle rectangle) {
        setRectangle(rectangle);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        notNull(rectangle, "rectangle");

        this.rectangle = rectangle;
    }

    @Override public double warpX(double originalX, double originalY) {
        return rectangle.getMappedX(originalX);
    }

    @Override public double warpY(double originalX, double originalY) {
        return rectangle.getMappedY(originalY);
    }

    @Override public double warpSampleSize(double originalX, double originalY, double originalSampleSize) {
        if (originalSampleSize <= 0) {
            return 0;
        }
        else {
            return originalSampleSize * rectangle.getSizeAverage();
        }
    }
}
