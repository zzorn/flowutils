package org.flowutils.rectangle;

import org.flowutils.Check;

import static org.flowutils.Check.notNull;
import static org.flowutils.MathUtils.*;

/**
 * Common functionality for Rectangles.
 */
public abstract class RectangleBase implements Rectangle {

    protected double minX;
    protected double minY;
    protected double maxX;
    protected double maxY;

    protected boolean empty = true;

    protected RectangleBase() {
        empty = true;
    }

    public RectangleBase(Rectangle rectangle) {
        notNull(rectangle, "rectangle");

        if (rectangle.isEmpty()) {
            empty = true;
        }
        else {
            init(rectangle.getMinX(), rectangle.getMinY(), rectangle.getMaxX(), rectangle.getMaxY());
        }
    }

    protected RectangleBase(double width, double height) {
        this(0, 0, width, height);
    }

    protected RectangleBase(double minX, double minY, double maxX, double maxY) {
        init(minX, minY, maxX, maxY);
    }

    @Override public final double getX() {
        return minX;
    }

    @Override public final double getY() {
        return minY;
    }

    @Override public final double getWidth() {
        return empty ? 0 : maxX - minX;
    }

    @Override public final double getHeight() {
        return empty ? 0 : maxY - minY;
    }

    @Override
    public final double getMinX() {
        return minX;
    }

    @Override
    public final double getMinY() {
        return minY;
    }

    @Override
    public final double getMaxX() {
        return maxX;
    }

    @Override
    public final double getMaxY() {
        return maxY;
    }

    public final boolean isEmpty() {
        return empty;
    }

    @Override
    public final double getCenterX() {
        return (minX + maxX) * 0.5;
    }

    @Override
    public final double getCenterY() {
        return (minY + maxY) * 0.5;
    }

    @Override
    public final double getSizeX() {
        return empty ? 0 : maxX - minX;
    }

    @Override
    public final double getSizeY() {
        return empty ? 0 : maxY - minY;
    }

    @Override public double getSizeAverage() {
        return ((maxX - minX) + (maxY - minY)) * 0.5;
    }

    @Override
    public final double getArea() {
        return (maxX - minX) * (maxY - minY);
    }

    @Override public boolean contains(double x, double y) {
        if (empty) return false;
        else return x >= minX &&
                    y >= minY &&
                    x <= maxX &&
                    y <= maxY;
    }

    @Override
    public final boolean contains(Rectangle rectangle) {
        if (empty || rectangle.isEmpty()) return false;
        else return rectangle.getMinX() >= minX &&
                    rectangle.getMinY() >= minY &&
                    rectangle.getMaxX() <= maxX &&
                    rectangle.getMaxY() <= maxY;
    }

    @Override
    public final boolean intersects(Rectangle rectangle) {
        if (empty || rectangle.isEmpty()) return false;
        else return rectangle.getMinX() <= maxX &&
                    rectangle.getMaxX() >= minX &&
                    rectangle.getMinY() <= maxY &&
                    rectangle.getMaxY() >= minY;
    }

    @Override
    public final double getCircumference() {
        return getSizeX() * 2 + getSizeY() * 2;
    }

    @Override
    public final double getSquaredCenterDistanceTo(double x, double y) {
        double dx = getCenterX() - x;
        double dy = getCenterY() - y;
        return dx * dx + dy * dy;
    }

    @Override
    public final double getCenterDistanceTo(double x, double y) {
        return Math.sqrt(getSquaredCenterDistanceTo(x, y));
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

    protected final void init(double x1, double y1, double x2, double y2) {
        Check.normalNumber(x1, "x1");
        Check.normalNumber(x2, "x2");
        Check.normalNumber(y1, "y1");
        Check.normalNumber(y2, "y2");

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
        return "Rectangle{" +
               "minX=" + minX +
               ", minY=" + minY +
               ", maxX=" + maxX +
               ", maxY=" + maxY +
               ", empty=" + empty +
               '}';
    }
}
