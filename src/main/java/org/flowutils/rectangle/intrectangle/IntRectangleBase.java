package org.flowutils.rectangle.intrectangle;

import org.flowutils.rectangle.Rectangle;

import static org.flowutils.Check.notNull;
import static org.flowutils.MathUtils.map;

/**
 * Common functionality for integer based Rectangles.
 */
public abstract class IntRectangleBase implements IntRectangle {

    protected int minX;
    protected int minY;
    protected int maxX;
    protected int maxY;

    protected boolean empty = true;

    protected IntRectangleBase() {
        empty = true;
    }

    protected IntRectangleBase(IntRectangle rectangle) {
        notNull(rectangle, "rectangle");

        if (rectangle.isEmpty()) {
            empty = true;
        }
        else {
            init(rectangle.getMinX(), rectangle.getMinY(), rectangle.getMaxX(), rectangle.getMaxY());
        }
    }

    protected IntRectangleBase(int width, int height) {
        this(0, 0, width - 1, height - 1);
    }

    protected IntRectangleBase(int minX, int minY, int maxX, int maxY) {
        init(minX, minY, maxX, maxY);
    }

    @Override
    public final int getMinX() {
        return minX;
    }

    @Override
    public final int getMinY() {
        return minY;
    }

    @Override
    public final int getMaxX() {
        return maxX;
    }

    @Override
    public final int getMaxY() {
        return maxY;
    }

    public final boolean isEmpty() {
        return empty;
    }

    @Override
    public final int getSizeX() {
        return maxX - minX + 1;
    }

    @Override
    public final int getSizeY() {
        return maxY - minY + 1;
    }

    @Override
    public final int getArea() {
        return getSizeX() * getSizeY();
    }

    @Override public boolean contains(int x, int y) {
        if (empty) return false;
        else return x >= minX &&
                    y >= minY &&
                    x <= maxX &&
                    y <= maxY;
    }

    @Override
    public final boolean contains(IntRectangle rectangle) {
        if (empty || rectangle.isEmpty()) return false;
        else return rectangle.getMinX() >= minX &&
                    rectangle.getMinY() >= minY &&
                    rectangle.getMaxX() <= maxX &&
                    rectangle.getMaxY() <= maxY;
    }

    @Override
    public final boolean intersects(IntRectangle rectangle) {
        if (empty || rectangle.isEmpty()) return false;
        else return rectangle.getMinX() <= maxX &&
                    rectangle.getMaxX() >= minX &&
                    rectangle.getMinY() <= maxY &&
                    rectangle.getMaxY() >= minY;
    }

    @Override
    public double getMappedX(final double t) {
        if (empty) return 0;
        else return map(t, 0, 1, minX, maxX);
    }

    @Override
    public double getMappedY(final double t) {
        if (empty) return 0;
        else return map(t, 0, 1, minY, maxY);
    }

    @Override
    public double getRelativeX(final double x) {
        if (empty) return 0;
        else return map(x, minX, maxX, 0, 1);
    }

    @Override
    public double getRelativeY(final double y) {
        if (empty) return 0;
        else return map(y, minY, maxY, 0, 1);
    }

    protected final void init(int x1, int y1, int x2, int y2) {
        if (x1 <= x2) {
            minX = x1;
            maxX = x2;
        }
        else {
            minX = x2;
            maxX = x1;
        }

        if (y1 <= y2) {
            minY = y1;
            maxY = y2;
        }
        else {
            minY = y2;
            maxY = y1;
        }

        empty = false;
    }


    @Override public String toString() {
        return "IntRectangle{" +
               "minX=" + minX +
               ", minY=" + minY +
               ", maxX=" + maxX +
               ", maxY=" + maxY +
               ", empty=" + empty +
               '}';
    }

}
