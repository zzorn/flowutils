package org.flowutils.rectangle;

/**
 * Listener that is notified about changes to a Rectangle.
 */
public interface RectangleListener {

    /**
     * Called when the specified Rectangle is changed.
     *
     * @param rectangle Rectangle that changed.
     * @param listenerData data object specified when the listener was added to the Rectangle.
     */
    void onChanged(Rectangle rectangle, Object listenerData);

}
