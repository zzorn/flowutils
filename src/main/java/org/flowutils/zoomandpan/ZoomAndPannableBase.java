package org.flowutils.zoomandpan;

import org.flowutils.Check;

/**
 * Some common functionality for ZoomAndPannables.
 */
public abstract class ZoomAndPannableBase implements ZoomAndPannable {

    private double defaultZoomStep = 2;

    @Override public final void zoom(double zoomChange) {
        zoom(zoomChange, 0.5, 0.5);
    }

    @Override public final void zoom(double zoomChange, double viewCenterX, double viewCenterY) {
        zoom(zoomChange, zoomChange, viewCenterX, viewCenterY);
    }

    @Override public final void resetZoom() {
        setZoom(1.0);
    }

    @Override public final void setZoom(double zoom) {
        setZoom(zoom, zoom);
    }

    @Override public final void setPan(double centerX, double centerY) {
        setPan(centerX, centerY, 0.5, 0.5);
    }

    @Override public final double getDefaultZoomStep() {
        return defaultZoomStep;
    }

    /**
     * @param defaultZoomStep Default zoom step to use, e.g. for mouse wheel increments.
     */
    public final void setDefaultZoomStep(double defaultZoomStep) {
        Check.greater(defaultZoomStep, "defaultZoomStep", 1, "one");
        this.defaultZoomStep = defaultZoomStep;
    }
}
