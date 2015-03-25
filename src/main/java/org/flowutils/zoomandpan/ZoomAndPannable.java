package org.flowutils.zoomandpan;

/**
 * Something that can be zoomed and panned.
 */
public interface ZoomAndPannable {

    /**
     * Zooms in or out at the center of the visible area.
     * @param zoomChange factor to multiply the zoom with, > 1 increases magnification, < 1 decreases magnification.
     */
    void zoom(double zoomChange);

    /**
     * @param zoomChange factor to multiply the zoom with, > 1 increases magnification, < 1 decreases magnification.
     * @param viewCenterX relative position on the view to keep centered when zooming, 0..1 range.
     * @param viewCenterY relative position on the view to keep centered when zooming, 0..1 range.
     */
    void zoom(double zoomChange, double viewCenterX, double viewCenterY);

    /**
     * @param zoomChangeX factor to multiply the x zoom with, > 1 increases magnification, < 1 decreases magnification.
     * @param zoomChangeY factor to multiply the y zoom with, > 1 increases magnification, < 1 decreases magnification.
     * @param viewCenterX relative position on the view to keep centered when zooming, 0..1 range.
     * @param viewCenterY relative position on the view to keep centered when zooming, 0..1 range.
     */
    void zoom(double zoomChangeX, double zoomChangeY, double viewCenterX, double viewCenterY);

    /**
     * Sets the zoom to the unzoomed (initial) view size.
     * Equivalent with calling setZoom(1).
     */
    void resetZoom();

    /**
     * Sets the zoom level.
     * @param zoom scale along both axis, relative to the unzoomed (initial) view size.  1 = initial size, 2 = zoomed in 2x, 0.5 = zoomed out 0.5x, etc.
     */
    void setZoom(double zoom);

    /**
     * Sets the zoom level.
     * @param zoomX scale along x axis, relative to the unzoomed (initial) view size.  1 = initial size, 2 = zoomed in 2x, 0.5 = zoomed out 0.5x, etc.
     * @param zoomY scale along y axis, relative to the unzoomed (initial) view size.  1 = initial size, 2 = zoomed in 2x, 0.5 = zoomed out 0.5x, etc.
     */
    void setZoom(double zoomX, double zoomY);

    /**
     * Pans some distance sideways.
     * @param deltaX 1 if the view was panned from the rightmost to the leftmost edge, -1 if panned the opposite way.
     *               Smaller if panned only a part of the visible area.
     * @param deltaY 1 if the view was panned from the bottommost to the topmost edge, -1 if panned the opposite way.
     *               Smaller if panned only a part of the visible area.
     */
    void pan(double deltaX, double deltaY);

    /**
     * Sets the view center position.
     * @param centerX the center world coordinate of the view.
     * @param centerY the center world coordinate of the view.
     */
    void setPan(double centerX, double centerY);

    /**
     * Sets the view position.
     * @param x world position
     * @param y world position
     * @param relativeViewCenterX relative position on the view to set the world position for.  0 = left edge, 1 = right edge.
     * @param relativeViewCenterY relative position on the view to set the world position for.  0 = leading edge, 1 = trailing edge.
     */
    void setPan(double x, double y, double relativeViewCenterX, double relativeViewCenterY);

    /**
     * @return Default zoom step to use, e.g. for mouse wheel increments.
     */
    double getDefaultZoomStep();

    /**
     * Sets the view to show the total work area, if applicable.
     * If not applicable, does nothing.
     */
    public void showAll();

}
