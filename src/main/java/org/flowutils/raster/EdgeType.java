package org.flowutils.raster;

/**
 * Describes the type of an edge a raster has, when trying to read or write outside it.
 */
@Deprecated
public enum EdgeType {

    /**
     * Will return 0 for reads outside the edge, ignores writes outside the edge.
     */
    ZERO(false, false),

    /**
     * Will return -1 for reads outside the edge, ignores writes outside the edge.
     */
    MINUS_ONE(false, false),

    /**
     * Will return 1 for reads outside the edge, ignores writes outside the edge.
     */
    ONE(false, false),

    /**
     * Will return the specified default value for reads outside the edge, ignores writes outside the edge.
     */
    CONSTANT(false, false),

    /**
     * Will return the value at the edge closest to the position for reads, ignores writes outside the edge.
     */
    CLAMP(false, true),

    /**
     * Will return the value at the edge closest to the position for reads, writes go to the closest pixel on the edge.
     */
    CLAMP_WRITABLE(true, true),

    /**
     * Will wrap around to the other side of the raster for reads, ignores writes outside the edge.
     */
    WRAP(false, true),

    /**
     * Will wrap around to the other side of the raster for reads, wraps writes as well.
     */
    WRAP_WRITABLE(true, true),

    /**
     * Will throw an error for any attempted writes or reads outside the raster bounds.
     */
    NOT_ALLOWED(false, false),;

    private final boolean writeable;
    private final boolean redirecting;

    private EdgeType(boolean writeable, boolean redirecting) {
        this.writeable = writeable;
        this.redirecting = redirecting;
    }

    public boolean isWriteable() {
        return writeable;
    }

    /**
     * @return true if the edge can be written to, false if not.
     * @throws IllegalArgumentException if it is forbidden to try to write to the edge.
     */
    public boolean checkWriteable(int x, int y, int w, int h, float value) {
        if (this == NOT_ALLOWED) throw new IllegalArgumentException("Can not set value "+value+" at coordinate ("+x+", "+y+"), because it is outside the raster bounds (0,0 - "+w+", "+h+"), and writing outside the raster bounds for this edge is not allowed.");
        return writeable;
    }

    public boolean isRedirecting() {
        return redirecting;
    }

    /**
     * Return the value that should be used outside the edge, if this is a non-redirecting edge type.
     * @throws IllegalArgumentException if accesses outside the edge is not allowed.
     * @throws IllegalStateException if this is a redirecting edge type.
     * @param defaultValue default value for values outside the edges, used if this is CONSTANT edge type.
     * @param x x coordinate, used in error message if access is not allowed.
     * @param y y coordinate, used in error message if access is not allowed.
     * @param w raster width, used in error message if access is not allowed.
     * @param h raster height, used in error message if access is not allowed.
     * @return the value to use outside the edge.
     */
    public float getValue(float defaultValue, int x, int y, int w, int h) {
        switch (this) {
            case ZERO:        return 0;
            case MINUS_ONE:   return -1;
            case ONE:         return 1;
            case CONSTANT:    return defaultValue;
            case NOT_ALLOWED: throw new IllegalArgumentException("The coordinate ("+x+", "+y+") is outside of the raster, which has width "+w+" and height "+h+".");
            default: throw new IllegalStateException("The edge type "+this+" does not support getting a value");
        }
    }

    /**
     * Wraps the coordinate.  Only works if this is a redirecting type of edge.
     *
     * @param coord coordinate to wrap
     * @param size maximum value for coordinate (minimum value is 0)
     * @return wrapped coordinate
     */
    public int wrap(int coord, int size, String coordinateName) {
        switch (this) {
            case CLAMP: // Fall through
            case CLAMP_WRITABLE:
                if (coord < 0) return 0;
                else if (coord >= size) return size - 1;
                else return coord;

            case WRAP: // Fall through
            case WRAP_WRITABLE:
                coord = coord % size;
                if (coord < 0) coord += size;
                return coord;

            default:
                throw new IllegalStateException(coordinateName+" coordinate "+coord+" out of range (raster size in "+coordinateName+" direction is "+size+"), edge type " + this + " does not support wrapping the coordinate.");
        }
    }


}
