package org.flowutils.raster;

import org.flowutils.Check;
import org.flowutils.Symbol;
import org.flowutils.rectangle.Rectangle;

import static org.flowutils.Maths.*;
import static org.flowutils.raster.EdgeType.*;

/**
 * Two dimensional float array with utility functions.
 */
public final class FloatRaster extends SingleChannelGridRaster {

    private static final String X_AXIS_NAME = "x";
    private static final String Y_AXIS_NAME = "y";

    private final int width;
    private final int height;
    private final float defaultValue;

    private EdgeType leftEdge   = WRAP_WRITABLE;
    private EdgeType rightEdge  = WRAP_WRITABLE;
    private EdgeType topEdge    = WRAP_WRITABLE;
    private EdgeType bottomEdge = WRAP_WRITABLE;

    private final float[] data ;

    /**
     * Creates a new float raster.
     * Accesses outside the edges of the raster will throw an exception.
     *
     * @param size used as both width and height of the raster.
     */
    public FloatRaster(int size) {
        this(size, size, NOT_ALLOWED);
    }

    /**
     * Creates a new float raster.
     * Accesses outside the edges of the raster will throw an exception.
     *
     * @param width width of the raster
     * @param height height of the raster
     */
    public FloatRaster(int width, int height) {
        this(width, height, NOT_ALLOWED);
    }

    /**
     * @param width width of the raster
     * @param height height of the raster
     * @param edgeType enum that specifies how to handle accesses outside the edges of the raster.
     */
    public FloatRaster(int width, int height, EdgeType edgeType) {
        this(width,  height,  edgeType, 0);
    }

    /**
     * @param width width of the raster
     * @param height height of the raster
     * @param edgeType enum that specifies how to handle accesses outside the edges of the raster.
     * @param defaultValue value that should be returned if the raster is accessed outside of its bounds, and the edgeType is EdgeType.CONSTANT.
     */
    public FloatRaster(int width, int height, EdgeType edgeType, float defaultValue) {
        this(width,  height,  edgeType, edgeType,  edgeType, edgeType, defaultValue);
    }

    /**
     * @param width width of the raster
     * @param height height of the raster
     * @param xEdgeType enum that specifies how to handle accesses outside the horizontal edges of the raster.
     * @param yEdgeType enum that specifies how to handle accesses outside the vertical edges of the raster.
     * @param defaultValue value that should be returned if the raster is accessed outside of its bounds, and the edgeType is EdgeType.CONSTANT.
     */
    public FloatRaster(int width, int height, EdgeType xEdgeType, EdgeType yEdgeType, float defaultValue) {
        this(width,  height,  xEdgeType, xEdgeType,  yEdgeType, yEdgeType, defaultValue);
    }

    /**
     * @param width width of the raster
     * @param height height of the raster
     * @param leftEdge specifies how to handle accesses or writes of data outside the left edge.
     * @param rightEdge specifies how to handle accesses or writes of data outside the right edge.
     * @param topEdge specifies how to handle accesses or writes of data outside the top edge.
     * @param bottomEdge specifies how to handle accesses or writes of data outside the bottom edge.
     * @param defaultValue value that should be returned if the raster is accessed outside of its bounds, and the edgeType is EdgeType.CONSTANT.
     */
    public FloatRaster(int width,
                       int height,
                       EdgeType leftEdge,
                       EdgeType rightEdge,
                       EdgeType topEdge,
                       EdgeType bottomEdge,
                       float defaultValue) {
        this(width, height, leftEdge, rightEdge, topEdge, bottomEdge, defaultValue, Symbol.get("value"));
    }

    /**
     * @param width width of the raster
     * @param height height of the raster
     * @param leftEdge specifies how to handle accesses or writes of data outside the left edge.
     * @param rightEdge specifies how to handle accesses or writes of data outside the right edge.
     * @param topEdge specifies how to handle accesses or writes of data outside the top edge.
     * @param bottomEdge specifies how to handle accesses or writes of data outside the bottom edge.
     * @param defaultValue value that should be returned if the raster is accessed outside of its bounds, and the edgeType is EdgeType.CONSTANT.
     * @param channelName name of the single channel provided by this raster.
     */
    public FloatRaster(int width,
                       int height,
                       EdgeType leftEdge,
                       EdgeType rightEdge,
                       EdgeType topEdge,
                       EdgeType bottomEdge,
                       float defaultValue,
                       Symbol channelName) {
        super(channelName);

        this.width = width;
        this.height = height;
        this.leftEdge = leftEdge;
        this.rightEdge = rightEdge;
        this.topEdge = topEdge;
        this.bottomEdge = bottomEdge;
        this.defaultValue = defaultValue;

        data = new float[width * height];
    }

    /**
     * @return behaviour used when trying to read or write data outside the left edge of the raster.
     */
    public EdgeType getLeftEdge() {
        return leftEdge;
    }

    /**
     * Specify the behaviour when trying to read or write data outside the left edge of the raster.
     */
    public void setLeftEdge(EdgeType leftEdge) {
        this.leftEdge = leftEdge;
    }

    /**
     * @return behaviour used when trying to read or write data outside the right edge of the raster.
     */
    public EdgeType getRightEdge() {
        return rightEdge;
    }

    /**
     * Specify the behaviour when trying to read or write data outside the right edge of the raster.
     */
    public void setRightEdge(EdgeType rightEdge) {
        this.rightEdge = rightEdge;
    }

    /**
     * @return behaviour used when trying to read or write data outside the top edge of the raster.
     */
    public EdgeType getTopEdge() {
        return topEdge;
    }

    /**
     * Specify the behaviour when trying to read or write data outside the top edge of the raster.
     */
    public void setTopEdge(EdgeType topEdge) {
        this.topEdge = topEdge;
    }

    /**
     * @return behaviour used when trying to read or write data outside the bottom edge of the raster.
     */
    public EdgeType getBottomEdge() {
        return bottomEdge;
    }

    /**
     * Specify the behaviour when trying to read or write data outside the bottom edge of the raster.
     */
    public void setBottomEdge(EdgeType bottomEdge) {
        this.bottomEdge = bottomEdge;
    }

    /**
     * Specify the behaviour when trying to read or write data outside the edges of the raster.
     *
     * @param edgeType EdgeType to use for all edges of the raster.
     */
    public void setEdgeTypes(EdgeType edgeType) {
        leftEdge   = edgeType;
        rightEdge  = edgeType;
        topEdge    = edgeType;
        bottomEdge = edgeType;
    }

    /**
     * Specify the behaviour when trying to read or write data outside the edges of the raster.
     *
     * @param xEdgeType EdgeType to use for the left and right edges.
     * @param yEdgeType EdgeType to use for the top and bottom edges.
     */
    public void setEdgeTypes(EdgeType xEdgeType, EdgeType yEdgeType) {
        leftEdge   = xEdgeType;
        rightEdge  = xEdgeType;
        topEdge    = yEdgeType;
        bottomEdge = yEdgeType;
    }

    /**
     * Specify the behaviour when trying to read or write data outside the edges of the raster.
     *
     * @param leftEdgeType EdgeType to use when trying to read or write beyond the left edge.
     * @param rightEdgeType EdgeType to use when trying to read or write beyond the right edge.
     * @param topEdgeType EdgeType to use when trying to read or write beyond the top edge.
     * @param bottomEdgeType EdgeType to use when trying to read or write beyond the bottom edge.
     */
    public void setEdgeTypes(EdgeType leftEdgeType, EdgeType rightEdgeType, EdgeType topEdgeType, EdgeType bottomEdgeType) {
        leftEdge   = leftEdgeType;
        rightEdge  = rightEdgeType;
        topEdge    = topEdgeType;
        bottomEdge = bottomEdgeType;
    }

    /**
     * @return value of raster at the specified coordinates (possibly wrapped, if the raster wraps).
     */
    public float get(int x, int y) {
        // Handle wrapping, clamping, or default values if the specified coordinates are outside the raster edges:

        if (x < 0) {
            if (leftEdge.isRedirecting()) x = leftEdge.wrap(x, width, X_AXIS_NAME);
            else return leftEdge.getValue(defaultValue, x, y, width, height);
        }
        else if (x >= width) {
            if (rightEdge.isRedirecting()) x = rightEdge.wrap(x, width, X_AXIS_NAME);
            else return rightEdge.getValue(defaultValue, x, y, width, height);
        }

        if (y < 0) {
            if (topEdge.isRedirecting()) y = topEdge.wrap(y, height, Y_AXIS_NAME);
            else return topEdge.getValue(defaultValue, x, y, width, height);
        }
        else if (y >= height) {
            if (bottomEdge.isRedirecting()) y = bottomEdge.wrap(y, height, Y_AXIS_NAME);
            else return bottomEdge.getValue(defaultValue, x, y, width, height);
        }

        return data[x + y * width];
    }

    /**
     * @return interpolated value of raster at the specified floating point coordinates (possibly wrapped, if the raster wraps).
     */
    public float getInterpolated(float x, float y) {
        int x0 = fastFloor(x);
        int y0 = fastFloor(y);
        int x1 = x0 + 1;
        int y1 = y0 + 1;
        float cx = x - x0;
        float cy = y - y0;
        final float yr0 = mix(cx, get(x0, y0), get(x1, y0));
        final float yr1 = mix(cx, get(x0, y1), get(x1, y1));
        return mix(cy, yr0, yr1);
    }

    /**
     * Set the value at the specified location on the raster to the specified value.
     * If the coordinate is outside the raster bounds, the location may be wrapped, clamped, or an error may be thrown,
     * depending on the edge types specified for the raster.
     *
     * @param x x coordinate of the location.  zero is left edge.
     * @param y y coordinate of the location.  zero is upper edge.
     * @param v value to set to the specified location.
     */
    public void set(int x, int y, float v) {
        // Handle wrapping, clamping, ignoring, or error throwing if the specified coordinates are outside the raster edges:

        if (x < 0) {
            if (!leftEdge.checkWriteable(x, y, width, height, v)) return;
            x = leftEdge.wrap(x, width, X_AXIS_NAME);
        }
        else if (x >= width) {
            if (!rightEdge.checkWriteable(x, y, width, height, v)) return;
            x = rightEdge.wrap(x, width, X_AXIS_NAME);
        }

        if (y < 0) {
            if (!topEdge.checkWriteable(x, y, width, height, v)) return;
            y = topEdge.wrap(y, height, Y_AXIS_NAME);
        }
        else if (y >= height) {
            if (!bottomEdge.checkWriteable(x, y, width, height, v)) return;
            y = bottomEdge.wrap(y, height, Y_AXIS_NAME);
        }

        data[x + y * width] = v;
    }

    /**
     * Fills the specified area of the raster with the specified value.
     * @param x left coordinate to start from (inclusive)
     * @param y top coordinate to start from (inclusive)
     * @param width width of the rectangular area to fill in number of cells.  If zero or negative, nothing is done.
     * @param height height of the rectangular area to fill in number of cells.  If zero or negative, nothing is done.
     * @param value value to set to all cells inside the rectangle.
     */
    public void fillRect(int x, int y, int width, int height, float value) {
        for (int yp = y; yp < y + height; yp++) {
            for (int xp = x; xp < x + width; xp++) {
                set(xp, yp, value);
            }
        }
    }

    /**
     * @return width of the raster in cells.
     */
    public int getWidth() {
        return width;
    }

    /**
     * @return height of the raster in cells.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Replaces the contents of this raster with the other raster.
     * The rasters must have the same size, if not, an exception is thrown.
     *
     * @param source raster to copy content from.
     */
    public void copyFrom(FloatRaster source) {
        checkSizeMatches(source);

        System.arraycopy(source.data, 0, data, 0, data.length);
    }

    /**
     * Multiplies all the values of this raster with the specified scale.
     */
    public void multiply(float scale) {
        for (int i = 0; i < data.length; i++) {
            data[i] *= scale;
        }
    }

    /**
     * Adds the specified value to all cells of this raster.
     */
    public void add(float offset) {
        for (int i = 0; i < data.length; i++) {
            data[i] += offset;
        }
    }

    /**
     * Multiplies all the values of this raster with the specified scale, and adds the offset.
     */
    public void multiplyAdd(float scale, float offset) {
        for (int i = 0; i < data.length; i++) {
            data[i] = data[i] * scale + offset;
        }
    }

    /**
     * Adds the source raster to this raster.
     * The rasters must be of the same size, or an exception is thrown.
     *
     * @param source raster to add to this raster.
     */
    public void add(FloatRaster source) {
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
    public void add(FloatRaster source, float sourceScale, float offset) {
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
    public void add(FloatRaster source, float originalScale, float sourceScale, float offset) {
        checkSizeMatches(source);

        for (int i = 0; i < data.length; i++) {
            data[i] = data[i] * originalScale + source.data[i] * sourceScale + offset;
        }
    }

    /**
     * Multiplies this raster with the specified source raster.
     *
     * The rasters must be of the same size, or an exception is thrown.
     *
     * @param source raster to multiply with this raster.
     */
    public void multiply(FloatRaster source) {
        checkSizeMatches(source);

        for (int i = 0; i < data.length; i++) {
            data[i] *= source.data[i];
        }
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
    public void multiply(FloatRaster source, float originalScale, float sourceScale, float offset) {
        checkSizeMatches(source);

        for (int i = 0; i < data.length; i++) {
            data[i] = (data[i] * originalScale) * (source.data[i] * sourceScale) + offset;
        }
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
    public void multiply(FloatRaster source, float originalScale, float sourceScale, float originalOffset, float sourceOffset, float offset) {
        checkSizeMatches(source);

        for (int i = 0; i < data.length; i++) {
            data[i] = (data[i] * originalScale + originalOffset) * (source.data[i] * sourceScale + sourceOffset) + offset;
        }
    }




    @Override
    public float getGridValue(final int gridX, final int gridY) {
        return get(gridX, gridY);
    }

    @Override
    public void setGridValue(final int gridX, final int gridY, final float value) {
        set(gridX, gridY, value);
    }

    @Override
    public int getGridSizeX() {
        return width;
    }

    @Override
    public int getGridSizeY() {
        return height;
    }

    @Override
    public void setArea(final Rectangle area) {
        super.setArea(area);
    }

    @Override
    public void setChannelId(final Symbol channelId) {
        super.setChannelId(channelId);
    }

    /* These are unfinished methods for supporting simple fluid dynamics simulation.

    public void diffuse(FloatRaster source, float diffusion, float deltaTime, float cellSizeMeter, int b) {
        checkSizeMatches(source);

        for (int k = 0; k < 20; k++) {
            diffuseStep(source, diffusion, deltaTime, cellSizeMeter);

            setBoundaries(b);
        }
    }

    public void setBoundaries(int b) {
        for (int x = 1; x < width -1; x++) {
            data[x]           = (b==2 ? -1 : 1) * data[x + 1* width];
            data[x + (height -1)* width] = (b==2 ? -1 : 1) * data[x + (height -2)* width];
        }
        for (int y = 1; y < height - 1; y++) {
            data[y* width]       = (b==1 ? -1 : 1) * data[1   + y * width];
            data[width -1 + y* width] = (b==1 ? -1 : 1) * data[width -2 + y * width];
        }

        set(0,   0,   (get(1,   0  ) + get(0,   1  )) / 2f);
        set(0,   height -1, (get(1,   height -1) + get(0,   height -2)) / 2f);
        set(width -1, 0,   (get(width -2,   0) + get(width -1, 1  )) / 2f);
        set(width -1, height -1, (get(1,   height -1) + get(0,   height -2)) / 2f);
    }

    public void diffuseStep(FloatRaster source, float diffusion, float deltaTime, float cellSizeMeter) {
        checkSizeMatches(source);

        float cellsPerMeter = 1f / cellSizeMeter;
        float a = deltaTime * diffusion * cellsPerMeter * cellsPerMeter;

        final float[] sourceData = source.data;

        for (int y = 1; y < height -1; y++) {
            for (int x = 1; x < width -1; x++) {
                data[x+y* width] = (sourceData[x + y* width] +
                               a * (sourceData[x+1 + y* width] +
                                    sourceData[x-1 + y* width] +
                                    sourceData[x + (y+1)* width] +
                                    sourceData[x + (y-1)* width]))
                              / (1 + 4 * a);
            }
        }
    }

    public void advect(int b, FloatRaster source, FloatRaster xVel, FloatRaster yVel, float deltaTime, float cellSizeMeter) {
        checkSizeMatches(source);
        checkSizeMatches(xVel);
        checkSizeMatches(yVel);

        float cellsPerMeter = 1f / cellSizeMeter;
        float dt0 = deltaTime * cellsPerMeter;

        for (int y = 1; y < height - 1; y++) {
            for (int x = 1; x < width - 1; x++) {
                float xSource = x - dt0 * xVel.get(x, y);
                float ySource = y - dt0 * yVel.get(x, y);

                clamp(xSource, 0.5f, width - 0.5f);
                clamp(ySource, 0.5f, height - 0.5f);

                data[x + y * width] = source.getInterpolated(xSource, ySource);
            }
        }

        setBoundaries(b);
    }

    public void densitySimulationStep(FloatRaster previousDensity, FloatRaster xVelocity, FloatRaster yVelocity, float diffusion, float deltaTimeSeconds, float cellSizeMeter) {
        add(previousDensity, deltaTimeSeconds, 0);
        previousDensity.diffuse(this, diffusion, deltaTimeSeconds, cellSizeMeter, 0);
        advect(0, previousDensity, xVelocity, yVelocity, deltaTimeSeconds, cellSizeMeter);
    }

    public static void velocityStep(FloatRaster xVel, FloatRaster yVel, FloatRaster prevXVel, FloatRaster prevYVel, FloatRaster xForce, FloatRaster yForce, float viscosity, float deltaTimeSeconds, float cellSizeMeter) {
        xVel.add(xForce, deltaTimeSeconds, 0);
        yVel.add(yForce, deltaTimeSeconds, 0);

        prevXVel.diffuse(xVel, viscosity, deltaTimeSeconds, cellSizeMeter, 1);
        prevYVel.diffuse(yVel, viscosity, deltaTimeSeconds, cellSizeMeter, 2);



        // TODO
    }

    public static void project(Raster xVel, Raster yVel, Raster p, Raster div, float cellSizeMeter) {

        // TODO


    }

*/


    /**
     * Make sure this raster has the same size as the source raster, if not, throw an IllegalArgumentException.
     */
    private void checkSizeMatches(FloatRaster source) {
        Check.equal(source.width, "source width", width, "target width");
        Check.equal(source.height, "source height", height, "target height");
    }

}

