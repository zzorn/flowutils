package org.flowutils.rectangle;

/**
 * Rectangular axis aligned bounding area.
 * Has a concept for empty rectangles, that do not contain any other Rectangles, and are not contained in any other Rectangles.
 * Has mutable and immutable implementations.  The mutable implementation provides support for listening to changes.
 */
public interface Rectangle {

    double getMinX();
    double getMaxX();
    double getMinY();
    double getMaxY();

    double getCenterX();
    double getCenterY();
    double getSizeX();
    double getSizeY();
    double getArea();

    /**
     * @return true if the Rectangle represents no area, that is, it can not collide with anything or contain anything.
     * Used for uninitialized / cleared mutable Rectangles.
     */
    boolean isEmpty();

    boolean contains(Rectangle rectangle);
    boolean intersects(Rectangle rectangle);

    // TODO: Add union, intersects, union of many, etc.?

    /**
     * Add a listener that is notified if the Rectangle changes.
     * If the Rectangle implementation is immutable, this can be just a stub that ignores the listener.
     * @param listener a listener that will be called when the Rectangle changes dimensions or location.
     * @param listenerData a data object that should be passed to the listener when called.
     */
    void addListener(RectangleListener listener, Object listenerData);

    /**
     * Remove a listener.
     * If the Rectangle implementation is immutable, this can be just a stub that ignores the listener.
     * @param listener the listener to remove.
     */
    void removeListener(RectangleListener listener);

    /**
     * @return the distance around the Rectangle, when walking along the edges (so width * 2 + height * 2);
     */
    double getCircumference();

    /**
     * @return squared distance from the center of this bound to the specified coordinate.
     */
    double getSquaredCenterDistanceTo(double x, double y);

    /**
     * @return distance from the center of this bound to the specified coordinate.
     */
    double getCenterDistanceTo(double x, double y);
}
