package org.flowutils.rectangle.intrectangle;

/**
 * Integer based axis-aligned rectangle.
 */
public interface IntRectangle {

    int getMinX();
    int getMaxX();
    int getMinY();
    int getMaxY();

    int getSizeX();
    int getSizeY();

    /**
     * @return area of the rectangle (sizeX * sizeY)
     */
    int getArea();

    /**
     * @return true if the Rectangle represents no area, that is, it can not collide with anything or contain anything.
     * Used for uninitialized / cleared mutable Rectangles.
     */
    boolean isEmpty();

    /**
     * @return true if the coordinate is within the rectangle.
     */
    boolean contains(int x, int y);

    /**
     * @return true if the specified rectangle is contained inside this rectangle.
     */
    boolean contains(IntRectangle rectangle);

    /**
     * @return true if the specified rectangle overlaps this rectangle (including just an edge).
     */
    boolean intersects(IntRectangle rectangle);

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
