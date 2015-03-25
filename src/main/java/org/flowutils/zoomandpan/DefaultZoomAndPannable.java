package org.flowutils.zoomandpan;

import org.flowutils.Check;
import org.flowutils.MathUtils;
import org.flowutils.rectangle.MutableRectangle;
import org.flowutils.rectangle.Rectangle;
import org.flowutils.rectangle.RectangleListener;

/**
 * Implementation of ZoomAndPannable that keeps track of a visible area that can be zoomed and panned.
 */
public class DefaultZoomAndPannable extends ZoomAndPannableBase {

    private final MutableRectangle visibleWorldArea = new MutableRectangle(1, 1);
    private Rectangle totalWorldArea = null;

    private boolean enforceZoomRange = false;

    private double minVisibleSizeX = 0.001;
    private double minVisibleSizeY = 0.001;
    private double maxVisibleSizeX = 1000;
    private double maxVisibleSizeY = 1000;

    private double viewSizeX = 1;
    private double viewSizeY = 1;

    /**
     * Creates a new DefaultZoomAndPannable with a visible area of 0,0 to 1,1.
     */
    public DefaultZoomAndPannable() {
    }

    /**
     * Creates a new DefaultZoomAndPannable with the specified size of the initially visible area.
     */
    public DefaultZoomAndPannable(double visibleWidth, double visibleHeight) {
        visibleWorldArea.setSize(visibleWidth, visibleHeight);
        viewSizeX = visibleWidth;
        viewSizeY = visibleHeight;
    }

    /**
     * Creates a new DefaultZoomAndPannable with the specified size and location of the initially visible area.
     */
    public DefaultZoomAndPannable(double visibleX, double visibleY, double visibleWidth, double visibleHeight) {
        visibleWorldArea.set(visibleX, visibleY, visibleX + visibleWidth, visibleY + visibleHeight);
        viewSizeX = visibleWidth;
        viewSizeY = visibleHeight;
    }

    /**
     * @param visibleWorldArea world area to initially be visible, or null to default to 0,0 to 1,1 visible area.
     */
    public DefaultZoomAndPannable(MutableRectangle visibleWorldArea) {
        this(visibleWorldArea, null);
    }

    /**
     * @param visibleWorldArea world area to initially be visible, or null to default to 0,0 to 1,1 visible area.
     * @param totalWorldArea world area to restrict the panning to, or null if no restriction should be placed on panning.
     */
    public DefaultZoomAndPannable(MutableRectangle visibleWorldArea,
                                  Rectangle totalWorldArea) {
        if (visibleWorldArea != null) {
            this.visibleWorldArea.set(visibleWorldArea);
            viewSizeX = visibleWorldArea.getSizeX();
            viewSizeY = visibleWorldArea.getSizeY();
        }
        setTotalWorldArea(totalWorldArea);
    }

    /**
     * @param visibleWorldArea world area to initially be visible, or null to default to 0,0 to 1,1 visible area.
     * @param totalWorldArea world area to restrict the panning to, or null if no restriction should be placed on panning.
     * @param minVisibleSizeX minimum and maximum sizes of the view, restricting zooming larger or smaller than this.
     * @param minVisibleSizeY minimum and maximum sizes of the view, restricting zooming larger or smaller than this.
     * @param maxVisibleSizeX minimum and maximum sizes of the view, restricting zooming larger or smaller than this.
     * @param maxVisibleSizeY minimum and maximum sizes of the view, restricting zooming larger or smaller than this.
     */
    public DefaultZoomAndPannable(MutableRectangle visibleWorldArea,
                                  Rectangle totalWorldArea,
                                  double minVisibleSizeX,
                                  double minVisibleSizeY,
                                  double maxVisibleSizeX,
                                  double maxVisibleSizeY) {
        if (visibleWorldArea != null) {
            this.visibleWorldArea.set(visibleWorldArea);
            viewSizeX = visibleWorldArea.getSizeX();
            viewSizeY = visibleWorldArea.getSizeY();
        }
        setTotalWorldArea(totalWorldArea);
        setVisibleSizeRange(minVisibleSizeX, minVisibleSizeY, maxVisibleSizeX, maxVisibleSizeY);
    }

    /**
     * @param listener listener that is notified when the visible area is zoomed or panned.
     *                 The listener is also passed a reference to this ZoomAndPannable instance.
     */
    public final void addVisibleAreaListener(RectangleListener<ZoomAndPannable> listener) {
        visibleWorldArea.addListener(listener, this);
    }

    /**
     * @param listener listener that is notified when the visible area is zoomed or panned.
     * @param listenerData additional data object to pass to the listener when it is invoked.
     */
    public final <T> void addVisibleAreaListener(RectangleListener<T> listener, T listenerData) {
        visibleWorldArea.addListener(listener, listenerData);
    }

    /**
     * @param listener listener to remove.
     */
    public final void removeVisibleAreaListener(RectangleListener listener) {
        visibleWorldArea.removeListener(listener);
    }

    @Override public final void zoom(double zoomChangeX, double zoomChangeY, double viewCenterX, double viewCenterY) {
        visibleWorldArea.scale(zoomChangeX, zoomChangeY, viewCenterX, viewCenterY);

        if (enforceZoomRange) {
            if (visibleWorldArea.getSizeX() < minVisibleSizeX) visibleWorldArea.setWidth(minVisibleSizeX);
            if (visibleWorldArea.getSizeY() < minVisibleSizeY) visibleWorldArea.setHeight(minVisibleSizeY);
            if (visibleWorldArea.getSizeX() > maxVisibleSizeX) visibleWorldArea.setWidth(maxVisibleSizeX);
            if (visibleWorldArea.getSizeY() > maxVisibleSizeY) visibleWorldArea.setHeight(maxVisibleSizeY);
        }

        ensureWorldVisible();
    }

    @Override public final void pan(double deltaX, double deltaY) {
        final double panDeltaX = deltaX * visibleWorldArea.getSizeX();
        final double panDeltaY = deltaY * visibleWorldArea.getSizeY();

        visibleWorldArea.move(panDeltaX, panDeltaY);

        ensureWorldVisible();
    }

    @Override public final void setZoom(double zoomX, double zoomY) {
        Check.positive(zoomX, "zoomX");
        Check.positive(zoomY, "zoomY");
        visibleWorldArea.setSize(viewSizeX / zoomX,
                                 viewSizeY / zoomY,
                                 true);

        ensureWorldVisible();
    }

    @Override public final void setPan(double x, double y, double relativeViewCenterX, double relativeViewCenterY) {
        visibleWorldArea.setPosition(x, y, relativeViewCenterX, relativeViewCenterY);

        ensureWorldVisible();
    }

    private void ensureWorldVisible() {
        // Ensure view stays within totalWorldArea, if it is available
        if (totalWorldArea != null && !totalWorldArea.isEmpty()) {
            if (!visibleWorldArea.intersects(totalWorldArea)) {
                // Move visibleWorldArea until it touches totalWorldArea
                if (visibleWorldArea.getMaxX() < totalWorldArea.getMinX()) {
                    visibleWorldArea.move(totalWorldArea.getMinX() - visibleWorldArea.getMaxX(), 0);
                }
                if (visibleWorldArea.getMaxY() < totalWorldArea.getMinY()) {
                    visibleWorldArea.move(0, totalWorldArea.getMinY() - visibleWorldArea.getMaxY());
                }
                if (visibleWorldArea.getMinX() > totalWorldArea.getMaxX()) {
                    visibleWorldArea.move(totalWorldArea.getMaxX() - visibleWorldArea.getMinX(), 0);
                }
                if (visibleWorldArea.getMinY() > totalWorldArea.getMaxY()) {
                    visibleWorldArea.move(0, totalWorldArea.getMaxY() - visibleWorldArea.getMinY());
                }
            }
        }
    }

    /**
     * Sets the visible view to show the total world area, if it has been specified.
     */
    public final void showAll() {
        if (totalWorldArea != null && !totalWorldArea.isEmpty()) {
            visibleWorldArea.set(totalWorldArea);
        }
    }

    /**
     * @return the reference width of the view when it is not zoomed in or out.
     */
    public final double getViewSizeX() {
        return viewSizeX;
    }

    /**
     * @param viewSizeX the reference width of the view when it is not zoomed in or out.
     *                  The zoom level is not maintained when this is used.
     */
    public final void setViewSizeX(double viewSizeX) {
        Check.positiveOrZero(viewSizeX, "viewSizeX");
        this.viewSizeX = viewSizeX;
    }

    /**
     * @return the reference height of the view when it is not zoomed in or out.
     */
    public final double getViewSizeY() {
        return viewSizeY;
    }

    /**
     * @param viewSizeY the reference height of the view when it is not zoomed in or out.
     *                  The zoom level is not maintained when this is used.
     */
    public final void setViewSizeY(double viewSizeY) {
        Check.positiveOrZero(viewSizeY, "viewSizeY");
        this.viewSizeY = viewSizeY;
    }

    /**
     * Sets the unzoomed view size.
     * The zoom level is not maintained when this is used.
     */
    public final void setViewSize(double viewSizeX, double viewSizeY) {
        setViewSizeX(viewSizeX);
        setViewSizeY(viewSizeY);
    }

    /**
     * Sets the unzoomed view size, and maintains the current zoom level.
     * This can be used when a view is resized, and the zoom level should stay the same.
     */
    public final void updateViewSize(double viewSizeX, double viewSizeY) {
        double zoomX = getZoomX();
        double zoomY = getZoomY();

        setViewSizeX(viewSizeX);
        setViewSizeY(viewSizeY);

        setZoom(zoomX, zoomY);
    }

    /**
     * @return average of current x and y zoom.
     */
    public final double getZoom() {
        return MathUtils.average(getZoomX(), getZoomY());
    }

    /**
     * @return current zoom scale along x, compared to the un-zoomed size.
     *         If the unzoomed size is 0, returns 1.
     */
    public final double getZoomX() {
        if (viewSizeX <= 0) return 1; // zero sized view.
        else {
            return visibleWorldArea.getSizeX() / viewSizeX;
        }
    }

    /**
     * @return current zoom scale along y, compared to the un-zoomed size.
     *         If the unzoomed size is 0, returns 1.
     */
    public final double getZoomY() {
        if (viewSizeY <= 0) return 1; // zero sized view.
        else {
            return visibleWorldArea.getSizeY() / viewSizeY;
        }
    }



    /**
     * @return the currently visible world area, as it has been panned and zoomed.
     */
    public final Rectangle getVisibleWorldArea() {
        return visibleWorldArea;
    }

    /**
     * @return total world area to restrict the panning to, or null if panning should not be restricted.
     */
    public final Rectangle getTotalWorldArea() {
        return totalWorldArea;
    }

    /**
     * @param totalWorldArea total world area to restrict the panning to, or null if panning should not be restricted.
     */
    public final void setTotalWorldArea(Rectangle totalWorldArea) {
        this.totalWorldArea = totalWorldArea;
    }



    /**
     * @return true if the zoom range should be clamped to the min and max view sizes.  Defaults to false.
     */
    public final boolean isEnforceZoomRange() {
        return enforceZoomRange;
    }

    /**
     * @param enforceZoomRange true if the zoom range should be clamped to the min and max view sizes. Defaults to false.
     */
    public final void setEnforceZoomRange(boolean enforceZoomRange) {
        this.enforceZoomRange = enforceZoomRange;
    }



    /**
     * @return minimum size for the view, along x axis, if the enforceZoomRange option is on (default to off).
     */
    public final double getMinVisibleSizeX() {
        return minVisibleSizeX;
    }

    public final void setMinVisibleSizeX(double minVisibleSizeX) {
        Check.positive(minVisibleSizeX, "minVisibleSizeX");
        this.minVisibleSizeX = minVisibleSizeX;
    }

    public final double getMinVisibleSizeY() {
        return minVisibleSizeY;
    }

    public final void setMinVisibleSizeY(double minVisibleSizeY) {
        Check.positive(minVisibleSizeY, "minVisibleSizeY");
        this.minVisibleSizeY = minVisibleSizeY;
    }

    public final double getMaxVisibleSizeX() {
        return maxVisibleSizeX;
    }

    public final void setMaxVisibleSizeX(double maxVisibleSizeX) {
        Check.positive(maxVisibleSizeX, "maxVisibleSizeX");
        this.maxVisibleSizeX = maxVisibleSizeX;
    }

    public final double getMaxVisibleSizeY() {
        return maxVisibleSizeY;
    }

    public final void setMaxVisibleSizeY(double maxVisibleSizeY) {
        Check.positive(maxVisibleSizeY, "maxVisibleSizeY");
        this.maxVisibleSizeY = maxVisibleSizeY;
    }

    /**
     * Set the maximum view size allowed (zooming larger is not possible).
     */
    public final void setMaxVisibleSize(double maxViewSize) {
        setMaxVisibleSizeX(maxViewSize);
        setMaxVisibleSizeY(maxViewSize);
    }

    /**
     * Set the minimum view size allowed (zooming smaller is not possible).
     */
    public final void setMinVisibleSize(double minViewSize) {
        setMinVisibleSizeX(minViewSize);
        setMinVisibleSizeY(minViewSize);
    }

    /**
     * Set the minimum and maximum view sizes allowed (zooming larger or smaller is not possible).
     * Also sets enforceZoomRange on so that these ranges are enforced.
     */
    public final void setVisibleSizeRange(double minViewSize, double maxViewSize) {
        setVisibleSizeRange(minViewSize, minViewSize, maxViewSize, maxViewSize);
    }

    /**
     * Set the minimum and maximum view sizes allowed (zooming larger or smaller is not possible).
     * Also sets enforceZoomRange on so that these ranges are enforced.
     */
    public final void setVisibleSizeRange(double minViewSizeX,
                                          double minViewSizeY,
                                          double maxViewSizeX,
                                          double maxViewSizeY) {
        setMinVisibleSizeX(minViewSizeX);
        setMinVisibleSizeY(minViewSizeY);
        setMaxVisibleSizeX(maxViewSizeX);
        setMaxVisibleSizeY(maxViewSizeY);
        setEnforceZoomRange(true);
    }

}
