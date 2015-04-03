package org.flowutils.zoomandpan;

import org.flowutils.Check;

import java.util.ArrayList;
import java.util.List;

import static org.flowutils.Check.notNull;

/**
 * Some common functionality for ZoomAndPannables.
 */
public abstract class ZoomAndPannableBase implements ZoomAndPannable {

    private double defaultZoomStep = 2;
    private final List<ZoomAndPannableListener> listeners = new ArrayList<>();

    @Override public final void zoom(double zoomChange) {
        zoom(zoomChange, 0.5, 0.5);
    }

    @Override public final void zoom(double zoomChangeX, double zoomChangeY) {
        zoom(zoomChangeX, zoomChangeY, 0.5, 0.5);
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

    @Override public final void addListener(ZoomAndPannableListener listener) {
        notNull(listener, "listener");
        Check.notContained(listener, listeners, "listeners");

        listeners.add(listener);
    }

    @Override public final void removeListener(ZoomAndPannableListener listener) {
        listeners.remove(listener);
    }

    /**
     * Notifies listeners about a zoom event.
     */
    protected final void notifyZoomed(double zoomX, double zoomY) {
        for (ZoomAndPannableListener listener : listeners) {
            listener.onZoom(this, zoomX, zoomY);
        }
    }

    /**
     * Notifies listeners about a pan event.
     */
    protected final void notifyPanned(double relativeDeltaX, double relativeDeltaY) {
        for (ZoomAndPannableListener listener : listeners) {
            listener.onPan(this, relativeDeltaX, relativeDeltaY);
        }
    }
}
