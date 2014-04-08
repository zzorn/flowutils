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

    /**
     * @return average of sizeX and sizeY
     */
    double getSizeAverage();

    /**
     * @return area of the rectangle (sizeX * sizeY)
     */
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

    /**
     * @param t a value from 0 to 1, where 0 corresponds to min x and 1 to max x.
     * @return the parameter mapped to the rectangle, minX when t == 0, and maxX when t == 1.
     *         if t is outside 0..1, the returned value will be outside the rectangle.
     *         Returns zero if the rectangle is empty and has no location.
     */
    double getMappedX(double t);

    /**
     * @param t a value from 0 to 1, where 0 corresponds to min y and 1 to max y.
     * @return the parameter mapped to the rectangle, minY when t == 0, and maxY when t == 1.
     *         if t is outside 0..1, the returned value will be outside the rectangle.
     *         Returns zero if the rectangle is empty and has no location.
     */
    double getMappedY(double t);

    /**
     * @param x an x coordinate value.
     * @return 0 if x equals minX, 1 if x equals maxX, and a linearily interpolated
     *         value in between and beyond.
     *         Returns zero if the rectangle is empty and has no location.
     */
    double getRelativeX(double x);

    /**
     * @param y an y coordinate value.
     * @return 0 if y equals minY, 1 if y equals maxY, and a linearily interpolated
     *         value in between and beyond.
     *         Returns zero if the rectangle is empty and has no location.
     */
    double getRelativeY(double y);
}
