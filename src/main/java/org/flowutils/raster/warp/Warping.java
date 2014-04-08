package org.flowutils.raster.warp;

/**
 * Warps x and y coordinates in some way.
 */
public interface Warping {

    /**
     * @param originalX original x coordinate
     * @param originalY original y coordinate
     * @return warped x coordinate.
     */
    double warpX(double originalX, double originalY);

    /**
     * @param originalX original x coordinate
     * @param originalY original y coordinate
     * @return warped y coordinate.
     */
    double warpY(double originalX, double originalY);

    /**
     * @return new sample size
     */
    double warpSampleSize(double originalX, double originalY, double originalSampleSize);

}
