package org.flowutils.raster;

import org.flowutils.Check;

import static org.flowutils.Maths.*;
import static org.flowutils.raster.EdgeType.*;

/**
 * Two dimensional float array with utility functions.
 */
public final class FloatRaster  {
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
        this.width = width;
        this.height = height;
        this.leftEdge = leftEdge;
        this.rightEdge = rightEdge;
        this.topEdge = topEdge;
        this.bottomEdge = bottomEdge;
        this.defaultValue = defaultValue;

        data = new float[width * height];
    }

    public EdgeType getLeftEdge() {
        return leftEdge;
    }

    public void setLeftEdge(EdgeType leftEdge) {
        this.leftEdge = leftEdge;
    }

    public EdgeType getRightEdge() {
        return rightEdge;
    }

    public void setRightEdge(EdgeType rightEdge) {
        this.rightEdge = rightEdge;
    }

    public EdgeType getTopEdge() {
        return topEdge;
    }

    public void setTopEdge(EdgeType topEdge) {
        this.topEdge = topEdge;
    }

    public EdgeType getBottomEdge() {
        return bottomEdge;
    }

    public void setBottomEdge(EdgeType bottomEdge) {
        this.bottomEdge = bottomEdge;
    }

    public void setEdgeTypes(EdgeType edgeType) {
        leftEdge   = edgeType;
        rightEdge  = edgeType;
        topEdge    = edgeType;
        bottomEdge = edgeType;
    }

    public void setEdgeTypes(EdgeType xEdgeType, EdgeType yEdgeType) {
        leftEdge   = xEdgeType;
        rightEdge  = xEdgeType;
        topEdge    = yEdgeType;
        bottomEdge = yEdgeType;
    }

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
        // Handle wrapping, clamping, or default values if the specified coordinates are outside the raster edges

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

    public void set(int x, int y, float v) {
        // Handle wrapping, clamping, ignoring, or error throwing if the specified coordinates are outside the raster edges

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

    public void fillRect(int x, int y, int w, int h, float v) {
        for (int yp = y; yp < y+h; yp++) {
            for (int xp = x; xp < x+w; xp++) {
                set(xp, yp, v);
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void copyFrom(FloatRaster other) {
        System.arraycopy(other.data, 0, data, 0, data.length);
    }

    public void add(FloatRaster source, float scale, float offset) {
        checkSizeMatches(source);

        for (int i = 0; i < data.length; i++) {
            data[i] += source.data[i] * scale + offset;
        }
    }




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



    private void checkSizeMatches(FloatRaster source) {
        Check.equal(source.width, "source width", width, "target width");
        Check.equal(source.height, "source height", height, "target height");
    }


}
