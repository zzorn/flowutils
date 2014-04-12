package org.flowutils.raster.raster.single;


import org.flowutils.rectangle.intrectangle.IntRectangle;

/**
 * Represents the data of a single channel in a raster.
 * The grid cells in the raster go from 0,0 (inclusive) to sizeX,sizeY (exclusive)
 */
public interface Raster {

    /**
     * @return x size of the channel in grid cells.
     */
    int getSizeX();

    /**
     * @return y size of the channel in grid cells.
     */
    int getSizeY();

    /**
     * @return rectangle starting at 0,0 and extending to sizeX-1, sizeY-1 (inclusive).
     */
    IntRectangle getExtent();

    /**
     * @return read & write array with the values in the channel, laid out row by row, using the
     * dataOffset, dataXSkip and dataYSkip specified in this Raster.
     */
    float[] getData();

    /**
     * @return offset in the array to where the data from this raster begins.
     */
    int getDataOffset();

    /**
     * @return total elements to step when moving from one value on a row to the next in the data array.  Will be at least one.
     */
    int getDataXStep();

    /**
     * @return elements to skip between each row of this raster in the data.
     */
    int getDataYSkip();

    /**
     * @return the value in the specified grid cell.  Throws exception if the coordinate is outside the raster.
     */
    float getValue(int x, int y);

    /**
     * Sets the specified value to the specified grid cell.  Throws exception if the coordinate is outside the raster.
     */
    void setValue(int x, int y, float value);

    /**
     * @return the interpolated value at the specified coordinate.  Throws exception if the coordinate is outside the raster.
     */
    float sampleValue(double x, double y);

    /**
     * @return the interpolated value at the specified coordinate.  Throws exception if the coordinate is outside the raster.
     */
    float sampleValue(double x, double y, double sampleSize);

    /**
     * @return true if the data array contains data other than the values associated with this raster,
     *         false if the data array only contains data for this raster, dataOffset is zero, dataXStep is one, and dataYSkip is zero.
     */
    boolean isDataInterleaved();

    /**
     * @return number of elements to step over to get to the next row.
     */
    int getDataRowStep();
}
