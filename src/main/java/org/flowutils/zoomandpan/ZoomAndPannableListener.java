package org.flowutils.zoomandpan;

/**
 * Listens to zoom and pan events.
 */
public interface ZoomAndPannableListener {

    /**
     * Called when the view is zoomed around its center
     * @param zoomAndPannable the ZoomAndPannable that was changed.
     * @param zoomX zoom change.  > 1 zoom in at center, < 1 zoom out at center.
     * @param zoomY zoom change.  > 1 zoom in at center, < 1 zoom out at center.
     */
    void onZoom(ZoomAndPannable zoomAndPannable, double zoomX, double zoomY);

    /**
     * Called when the view is panned
     * @param zoomAndPannable the ZoomAndPannable that was changed.
     * @param relativeDeltaX pan delta, relative to previous visible area size.
     * @param relativeDeltaY pan delta, relative to previous visible area size.
     */
    void onPan(ZoomAndPannable zoomAndPannable, double relativeDeltaX, double relativeDeltaY);

}
