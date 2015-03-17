package org.flowutils.rectangle;

import static org.flowutils.Check.notNull;

/**
 * A Rectangle that can not be moved or re-sized.
 */
// TODO: Refactor rectangles to use x,y,width,height for constructor parameters and in general?
public final class ImmutableRectangle extends RectangleBase {

    public ImmutableRectangle() {
    }

    public ImmutableRectangle(Rectangle rectangle) {
        super(rectangle);
    }

    public ImmutableRectangle(double width, double height) {
        super(width, height);
    }

    public ImmutableRectangle(double minX, double minY, double maxX, double maxY) {
        super(minX, minY, maxX, maxY);
    }

    public static ImmutableRectangle fromWidthHeight(double x, double y, double width, double height) {
        return new ImmutableRectangle(x, y, x+width, y+height);
    }

    @Override
    public void addListener(RectangleListener listener, Object listenerData) {
        // No listeners needed for immutable Rectangles.
    }

    @Override
    public void removeListener(RectangleListener listener) {
        // No listeners needed for immutable Rectangles.
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RectangleBase that = (RectangleBase) o;

        if (Double.compare(that.maxX, maxX) != 0) return false;
        if (Double.compare(that.maxY, maxY) != 0) return false;
        if (Double.compare(that.minX, minX) != 0) return false;
        if (Double.compare(that.minY, minY) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = minX != +0.0d ? Double.doubleToLongBits(minX) : 0L;
        result = (int) (temp ^ (temp >>> 32));
        temp = minY != +0.0d ? Double.doubleToLongBits(minY) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = maxX != +0.0d ? Double.doubleToLongBits(maxX) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = maxY != +0.0d ? Double.doubleToLongBits(maxY) : 0L;
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }



}
