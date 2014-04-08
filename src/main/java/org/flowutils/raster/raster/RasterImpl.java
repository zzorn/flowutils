package org.flowutils.raster.raster;

import org.flowutils.Check;

import static org.flowutils.MathUtils.fastFloor;
import static org.flowutils.MathUtils.mix;

/**
 * A channel with raster values.
 */
public final class RasterImpl implements Raster {

    private final int sizeX;
    private final int sizeY;
    private final int dataOffset;
    private final int dataXStep;
    private final int dataYSkip;
    private final int dataRowSize;
    private final float[] data;

    /**
     * Creates a new RasterChannel with the specified size.
     *
     * @param sizeX x size of the raster
     * @param sizeY y size of the raster
     */
    public RasterImpl(int sizeX, int sizeY) {
        this(sizeX, sizeY, new float[sizeX*sizeY]);
    }

    /**
     * Creates a new RasterChannel with the specified size and the specified backing array.
     *
     * @param sizeX x size of the raster
     * @param sizeY y size of the raster
     * @param data the data array to use.
     */
    public RasterImpl(int sizeX, int sizeY, float[] data) {
        this(sizeX, sizeY, data, 0, 1, 0);
    }

    /**
     * Creates a new RasterChannel with the specified size and the specified backing array and the specified
     * spacing in the backing array (useful for interleaved backing arrays).
     *
     * @param sizeX x size of the raster
     * @param sizeY y size of the raster
     * @param data the data array to use.
     * @param dataOffset offset to the start of the values for this raster in the data array.
     * @param dataXStep total elements to step when moving from one value on a row to the next in the data array.  Should not be zero.
     * @param dataYSkip extra elements to skip between each row in the data array.
     */
    public RasterImpl(int sizeX, int sizeY, float[] data, int dataOffset, int dataXStep, int dataYSkip) {
        Check.positive(sizeX, "sizeX");
        Check.positive(sizeY, "sizeY");
        Check.notNull(data, "data");
        Check.positiveOrZero(dataOffset, "dataOffset");
        Check.notZero(dataXStep, "dataXStep");

        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.dataOffset = dataOffset;
        this.dataXStep = dataXStep;
        this.dataYSkip = dataYSkip;
        this.data = data;

        dataRowSize = sizeX * dataXStep + dataYSkip;
    }

    @Override public final boolean isDataInterleaved() {
        return data.length != (sizeX * sizeY) || dataOffset != 0 || dataXStep != 1 || dataYSkip != 0;
    }

    @Override public int getSizeX() {
        return sizeX;
    }

    @Override public int getSizeY() {
        return sizeY;
    }

    @Override public float[] getData() {
        return data;
    }

    public int getDataOffset() {
        return dataOffset;
    }

    @Override public int getDataXStep() {
        return dataXStep;
    }

    public int getDataYSkip() {
        return dataYSkip;
    }

    @Override public float getValue(int x, int y) {
        if (x < 0 || x >= sizeX ||
            y < 0 || y >= sizeY) throw new IllegalArgumentException("The coordinate ("+x+","+y+") is outside the raster (which has a size of "+sizeX+","+sizeY+").");

        return data[getIndex(x, y)];
    }

    @Override public void setValue(int x, int y, float value) {
        if (x < 0 || x >= sizeX ||
            y < 0 || y >= sizeY) throw new IllegalArgumentException("The coordinate ("+x+","+y+") is outside the raster (which has a size of "+sizeX+","+sizeY+").");

        data[getIndex(x, y)] = value;
    }

    @Override public float getInterpolatedValue(float x, float y) {
        if (x < 0 || x > sizeX - 1 ||
            y < 0 || y > sizeY - 1) throw new IllegalArgumentException("The coordinate ("+x+","+y+") is outside the raster (which has a size of "+sizeX+","+sizeY+").");

        int x0 = fastFloor(x);
        int y0 = fastFloor(y);
        int x1 = x0 + 1;
        int y1 = y0 + 1;
        float cx = x - x0;
        float cy = y - y0;
        final float yr0 = mix(cx, data[getIndex(x0, y0)], data[getIndex(x1, y0)]);
        final float yr1 = mix(cx, data[getIndex(x0, y1)], data[getIndex(x1, y1)]);
        return mix(cy, yr0, yr1);
    }

    /**
     * @return index of the specified coordinate in the data array.  Does not check boundaries.
     */
    private int getIndex(int x, int y) {
        return dataOffset + y * dataRowSize + x * dataXStep;
    }

    /**
     * Replaces the contents of this raster with the other raster.
     * The rasters must have the same size, if not, an exception is thrown.
     *
     * @param source raster to copy content from.
     */
    public void copyFrom(Raster source) {
        checkSizeMatches(source);

        if (nonInterleavedOperationPossible(source)) {
            // Non-interleaved data, we can do a system copy
            System.arraycopy(source.getData(), 0, data, 0, data.length);
        }
        else {
            // Copy interleaved data
            final int sourceXStep = source.getDataXStep();
            final int sourceYSkip = source.getDataYSkip();
            final float[] sourceData = source.getData();

            int targetIndex = dataOffset;
            int sourceIndex = source.getDataOffset();

            for (int y = 0; y < sizeY; y++) {
                for (int x = 0; x < sizeX; x++) {
                    data[targetIndex] = sourceData[sourceIndex];

                    targetIndex += dataXStep;
                    sourceIndex += sourceXStep;
                }
                targetIndex += dataYSkip;
                sourceIndex += sourceYSkip;
            }
        }
    }

    /**
     * Multiplies all the values of this raster with the specified scale.
     */
    public void multiply(float scale) {
        multiplyAdd(scale, 0);
    }

    /**
     * Adds the specified value to all cells of this raster.
     */
    public void add(float offset) {
        multiplyAdd(1, offset);
    }

    /**
     * Multiplies all the values of this raster with the specified scale, and adds the offset.
     */
    public void multiplyAdd(float scale, float offset) {
        if (!isDataInterleaved()) {
            // Non-interleaved data, we can do a simple loop
            final int size = data.length;
            for (int i = 0; i < size; i++) {
                data[i] = data[i] * scale + offset;
            }
        }
        else {
            // Loop interleaved data
            int index = dataOffset;
            for (int y = 0; y < sizeY; y++) {
                for (int x = 0; x < sizeX; x++) {
                    data[index] = data[index] * scale + offset;

                    index += dataXStep;
                }
                index += dataYSkip;
            }
        }
    }

    /**
     * Adds the source raster to this raster.
     * The rasters must be of the same size, or an exception is thrown.
     *
     * @param source raster to add to this raster.
     */
    public void add(Raster source) {
        add(source, 1, 1, 0);
    }

    /**
     * Adds the source raster to this raster.
     * The rasters must be of the same size, or an exception is thrown.
     *
     * @param source raster to add to this raster.
     * @param sourceScale value to scale the source raster with.
     * @param offset value to add to the result at each cell.
     */
    public void add(Raster source, float sourceScale, float offset) {
        add(source, 1, sourceScale, offset);
    }

    /**
     * Adds the source raster to this raster.
     * The rasters must be of the same size, or an exception is thrown.
     *
     * @param source raster to add to this raster.
     * @param originalScale value to scale this raster with.
     * @param sourceScale value to scale the source raster with.
     * @param offset value to add to the result at each cell.
     */
    public void add(Raster source, float originalScale, float sourceScale, float offset) {
        checkSizeMatches(source);

        final float[] sourceData = source.getData();

        if (nonInterleavedOperationPossible(source)) {
            // Non-interleaved data, we can do a simple loop
            final int size = data.length;

            for (int i = 0; i < size; i++) {
                data[i] = data[i] * originalScale + sourceData[i] * sourceScale + offset;
            }

        }
        else {
            // Add interleaved rasters
            final int sourceXStep = source.getDataXStep();
            final int sourceYSkip = source.getDataYSkip();

            int targetIndex = dataOffset;
            int sourceIndex = source.getDataOffset();

            for (int y = 0; y < sizeY; y++) {
                for (int x = 0; x < sizeX; x++) {
                    data[targetIndex] = data[targetIndex] * originalScale + sourceData[sourceIndex] * sourceScale + offset;

                    targetIndex += dataXStep;
                    sourceIndex += sourceXStep;
                }
                targetIndex += dataYSkip;
                sourceIndex += sourceYSkip;
            }
        }
    }

    /**
     * Multiplies this raster with the specified source raster.
     *
     * The rasters must be of the same size, or an exception is thrown.
     *
     * @param source raster to multiply with this raster.
     */
    public void multiply(Raster source) {
        multiply(source, 1, 1, 0);
    }

    /**
     * Multiplies this raster with the specified source raster.
     *
     * The rasters must be of the same size, or an exception is thrown.
     *
     * @param source raster to multiply with this raster.
     * @param originalScale value to scale this raster with before multiplying.
     * @param sourceScale value to scale the source raster with before multiplying.
     * @param offset value to add to the result after multiplying
     */
    public void multiply(Raster source, float originalScale, float sourceScale, float offset) {
        multiply(source, originalScale, sourceScale, 0, 0, offset);
    }

    /**
     * Multiplies this raster with the specified source raster.
     *
     * Each cell value is calculated as:  cellValue = (cellValue * originalScale + originalOffset) + (sourceCellValue * sourceScale + sourceOffset) + offset.
     *
     * The rasters must be of the same size, or an exception is thrown.
     *
     * @param source raster to multiply with this raster.
     * @param originalScale value to scale this raster with before multiplying.
     * @param sourceScale value to scale the source raster with before multiplying.
     * @param originalOffset value to add to this raster after scaling and before multiplying.
     * @param sourceOffset value to add to the source raster after scaling and before multiplying.
     * @param offset value to add to the result after multiplying
     */
    public void multiply(Raster source, float originalScale, float sourceScale, float originalOffset, float sourceOffset, float offset) {
        checkSizeMatches(source);

        final float[] sourceData = source.getData();

        if (nonInterleavedOperationPossible(source)) {
            // Non-interleaved data, we can do a simple loop
            final int size = data.length;
            for (int i = 0; i < size; i++) {
                data[i] = (data[i] * originalScale + originalOffset) * (sourceData[i] * sourceScale + sourceOffset) + offset;
            }
        }
        else {
            // Multiply interleaved rasters
            final int sourceXStep = source.getDataXStep();
            final int sourceYSkip = source.getDataYSkip();

            int targetIndex = dataOffset;
            int sourceIndex = source.getDataOffset();

            for (int y = 0; y < sizeY; y++) {
                for (int x = 0; x < sizeX; x++) {
                    data[targetIndex] = (data[targetIndex] * originalScale + originalOffset) * (sourceData[sourceIndex] * sourceScale + sourceOffset) + offset;

                    targetIndex += dataXStep;
                    sourceIndex += sourceXStep;
                }
                targetIndex += dataYSkip;
                sourceIndex += sourceYSkip;
            }
        }

    }



    /**
     * Make sure this raster has the same size as the source raster, if not, throw an IllegalArgumentException.
     */
    private void checkSizeMatches(Raster source) {
        Check.equal(source.getSizeX(), "source sizeX", sizeX, "target sizeX");
        Check.equal(source.getSizeY(), "source sizeY", sizeY, "target sizeY");
    }

    private boolean nonInterleavedOperationPossible(Raster source) {
        return !isDataInterleaved() &&
               !source.isDataInterleaved();
    }

}
