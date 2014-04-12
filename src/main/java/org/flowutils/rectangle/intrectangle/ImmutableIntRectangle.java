package org.flowutils.rectangle.intrectangle;

/**
 * Immutable integer rectangle.
 */
public final class ImmutableIntRectangle extends IntRectangleBase {

    public ImmutableIntRectangle(int width, int height) {
        init(0, 0, width-1, height-1);
    }

    public ImmutableIntRectangle(int x1, int y1, int x2, int y2) {
        init(x1, y1, x2, y2);
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
