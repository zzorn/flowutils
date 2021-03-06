package org.flowutils.rectangle;

import java.util.HashMap;
import java.util.Map;

/**
 * Rectangle implementation that can be changed.
 * Provides support for listening to changes.
 */
public final class MutableRectangle extends RectangleBase {

    private Map<RectangleListener, Object> listeners = null;

    public MutableRectangle() {
    }

    public MutableRectangle(Rectangle rectangle) {
        super(rectangle);
    }

    public MutableRectangle(double width, double height) {
        super(width, height);
    }

    public MutableRectangle(double minX, double minY, double maxX, double maxY) {
        super(minX, minY, maxX, maxY);
    }

    public static MutableRectangle fromWidthHeight(double x, double y, double width, double height) {
        return new MutableRectangle(x, y, x+width, y+height);
    }


    public void setX(double x1, double x2) {
        set(x1, minY, x2, maxY);
    }

    public void setY(double y1, double y2) {
        set(minX, y1, maxX, y2);
    }

    public void setX(double x1) {
        set(x1, minY, maxX, maxY);
    }

    public void setY(double y1) {
        set(minX, y1, maxX, maxY);
    }

    public void setMinX(double x1) {
        set(x1, minY, maxX, maxY);
    }

    public void setMaxX(double x2) {
        set(minX, minY, x2, maxY);
    }

    public void setMinY(double y1) {
        set(minX, y1, maxX, maxY);
    }

    public void setMaxY(double y2) {
        set(minX, minY, maxX, y2);
    }

    /**
     * Moves the minX edge of the rectangle by the specified amount.
     */
    public void changeMinX(double x1Delta) {
        set(minX+x1Delta, minY, maxX, maxY);
    }

    /**
     * Moves the minY edge of the rectangle by the specified amount.
     */
    public void changeMinY(double y1Delta) {
        set(minX, minY+y1Delta, maxX, maxY);
    }

    /**
     * Moves the maxX edge of the rectangle by the specified amount.
     */
    public void changeMaxX(double x2Delta) {
        set(minX, minY, maxX+x2Delta, maxY);
    }

    /**
     * Moves the maxY edge of the rectangle by the specified amount.
     */
    public void changeMaxY(double y2Delta) {
        set(minX, minY, maxX, maxY+y2Delta);
    }

    /**
     * Moves the rectangle by the specified amount along the x and y axis.
     */
    public void move(double deltaX, double deltaY) {
        set(minX + deltaX, minY + deltaY, maxX + deltaX, maxY + deltaY);
    }

    /**
     * Resizes the rectangle with the specified amount, the minX and minY of the rectangle will not move.
     * @param deltaXSize units to add to maxX
     * @param deltaYSize units to add to maxY
     */
    public void resize(double deltaXSize, double deltaYSize) {
        resize(deltaXSize, deltaYSize, false);
    }

    /**
     * Resizes the rectangle with the specified amount.
     * @param deltaXSize units to add to the sizeX
     * @param deltaYSize units to add to the sizeY
     * @param center if true the center of the rectangle will not move, if false the minX and minY of of the rectangle will not move.
     */
    public void resize(double deltaXSize, double deltaYSize, boolean center) {
        double w = getWidth() + deltaXSize;
        double h = getHeight() + deltaYSize;
        setSize(w, h, center);
    }

    /**
     * Scales the rectangle with the specified factor, the minX and minY of the rectangle will not move.
     * @param xScale units to multiply sizeX with
     * @param yScale units to multiply sizeY with
     */
    public void scale(double xScale, double yScale) {
        scale(xScale, yScale, false);
    }

    /**
     * Scales the rectangle with the specified factor.
     * @param xScale units to multiply sizeX with
     * @param yScale units to multiply sizeY with
     * @param center if true the center of the rectangle will not move, if false the minX and minY of of the rectangle will not move.
     */
    public void scale(double xScale, double yScale, boolean center) {
        if (center) {
            scale(xScale, yScale, 0.5, 0.5);
        }
        else {
            scale(xScale, yScale, 0, 0);
        }
    }

    /**
     * Scales the rectangle with the specified factor, using the specified relative position as the unmoving center of the scaling.
     * @param xScale units to multiply sizeX with
     * @param yScale units to multiply sizeY with
     * @param relativeCenterX relative position within the rectangle of the center of the scaling (0..1, can be outside the rectangle as well).
     * @param relativeCenterY relative position within the rectangle of the center of the scaling (0..1, can be outside the rectangle as well).
     */
    public void scale(double xScale, double yScale, double relativeCenterX, double relativeCenterY) {
        double w = getWidth() * xScale;
        double h = getHeight() * yScale;
        setSize(w, h, relativeCenterX, relativeCenterY);
    }

    public void setWidth(double width) {
        set(minX, minX + width, minY, maxY);
    }

    public void setHeight(double height) {
        set(minX, maxX, minY, minY + height);
    }


    /**
     * Sets the size of the rectangle to the specified size.  The minX and minY of of the rectangle will not move.
     * @param xSize new width of the rectangle
     * @param ySize new height of the rectangle
     */
    public void setSize(double xSize, double ySize) {
        setSize(xSize, ySize, false);
    }

    /**
     * Sets the size of the rectangle to the specified size.
     * @param xSize new width of the rectangle
     * @param ySize new height of the rectangle
     * @param center if true the center of the rectangle will not move, if false the minX and minY of of the rectangle will not move.
     */
    public void setSize(double xSize, double ySize, boolean center) {
        if (center) {
            setSize(xSize, ySize, 0.5, 0.5);
        }
        else {
            setSize(xSize, ySize, 0, 0);
        }
    }

    /**
     * Sets the size of the rectangle to the specified size.
     * @param xSize new width of the rectangle
     * @param ySize new height of the rectangle
     * @param relativeCenterX relative position within the rectangle of the center of the scaling (0..1, can be outside the rectangle as well).
     * @param relativeCenterY relative position within the rectangle of the center of the scaling (0..1, can be outside the rectangle as well).
     */
    public void setSize(double xSize, double ySize, double relativeCenterX, double relativeCenterY) {
        double cx = getMappedX(relativeCenterX);
        double cy = getMappedY(relativeCenterY);
        set(cx - 0.5* xSize, cy - 0.5* ySize,
            cx + 0.5* xSize, cy + 0.5* ySize);
    }

    /**
     * Sets the center position of the rectangle to the specified coordiantes.
     */
    public void setPosition(double x, double y) {
        setPosition(x, y, 0.5, 0.5);
    }

    /**
     * Sets the position of the rectangle.
     * @param x world position.
     * @param y world position.
     * @param relativeX relative position (0..1) inside the rectangle that should be at the specified world position.
     * @param relativeY relative position (0..1) inside the rectangle that should be at the specified world position.
     */
    public void setPosition(double x, double y, double relativeX, double relativeY) {
        double dx = x - getMappedX(relativeX);
        double dy = y - getMappedX(relativeY);
        move(dx, dy);
    }


    public void set(Rectangle rectangle) {
        set(rectangle.getMinX(), rectangle.getMinY(), rectangle.getMaxX(), rectangle.getMaxY());
        empty = rectangle.isEmpty();
    }

    public void set(double x1, double y1, double x2, double y2) {
        if (x1 != minX || x2 != maxX || y1 != minY || y2 != maxY) {
            init(x1, y1, x2, y2);

            // Notify listeners
            if (listeners != null) {
                for (Map.Entry<RectangleListener, Object> entry : listeners.entrySet()) {
                    RectangleListener listener = entry.getKey();
                    listener.onChanged(this, entry.getValue());
                }
            }
        }

        empty = false;
    }

    @Override
    public void addListener(RectangleListener listener, Object listenerData) {
        if (listeners == null) {
            listeners = new HashMap<RectangleListener, Object>();
        }

        listeners.put(listener, listenerData);
    }

    @Override
    public void removeListener(RectangleListener listener) {
        if (listeners != null) {
            listeners.remove(listener);
        }
    }

    /**
     * Modifies this Rectangle to include the specified bounds.
     */
    public void include(Rectangle bounds) {
        if (empty) set(bounds);
        else if (!contains(bounds)) {
            set(Math.min(minX, bounds.getMinX()),
                Math.min(minY, bounds.getMinY()),
                Math.max(maxX, bounds.getMaxX()),
                Math.max(maxY, bounds.getMaxY()));
        }
    }

    /**
     * Sets area to zero and location to origo.
     */
    public void clear() {
        set(0,0,0,0);
        empty = true;
    }

    /**
     * Set this Rectangle to the intersection of itself and the other Rectangle.
     * If there was no overlap, clears the Rectangle.
     * @return true if an intersection was found.
     */
    public boolean setToIntersection(Rectangle other) {
        if (empty) {
            return false;
        } else {
            double newMinX = Math.max(minX, other.getMinX());
            double newMinY = Math.max(minY, other.getMinY());
            double newMaxX = Math.min(maxX, other.getMaxX());
            double newMaxY = Math.min(maxY, other.getMaxY());

            if (newMaxX < newMinX || newMaxY < newMinY) {
                clear();
                return false;
            }
            else {
                set(newMinX, newMinY, newMaxX, newMaxY);
                return true;
            }
        }
    }

}
