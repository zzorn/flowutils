package org.flowutils.rectangle;

import java.util.HashMap;
import java.util.Map;

import static org.flowutils.Check.notNull;

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

    public void setX(double x1, double x2) {
        set(x1, minY, x2, maxY);
    }

    public void setY(double y1, double y2) {
        set(minX, y1, maxX, y2);
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

    public void setWidth(double width) {
        set(minX, minX + width, minY, maxY);
    }

    public void setHeight(double height) {
        set(minX, maxX, minY, minY + height);
    }

    public void set(Rectangle bounds) {
        set(bounds.getMinX(), bounds.getMinY(), bounds.getMaxX(), bounds.getMaxY());
        empty = bounds.isEmpty();
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
