package org.flowutils.rectangle.intrectangle;

/**
 * Immutable integer rectangle.
 */
public final class ImmutableIntRectangle extends IntRectangleBase {

    public ImmutableIntRectangle() {
    }

    public ImmutableIntRectangle(IntRectangle rectangle) {
        super(rectangle);
    }

    public ImmutableIntRectangle(int width, int height) {
        super(width, height);
    }

    public ImmutableIntRectangle(int minX, int minY, int maxX, int maxY) {
        super(minX, minY, maxX, maxY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IntRectangleBase that = (IntRectangleBase) o;

        if (empty != that.empty) return false;
        if (maxX != that.maxX) return false;
        if (maxY != that.maxY) return false;
        if (minX != that.minX) return false;
        if (minY != that.minY) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = minX;
        result = 31 * result + minY;
        result = 31 * result + maxX;
        result = 31 * result + maxY;
        result = 31 * result + (empty ? 1 : 0);
        return result;
    }


}
