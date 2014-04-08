package org.flowutils.raster.field;

/**
 * Listens to render progress, and can be used to stop a rendering.
 */
public interface RenderListener {

    /**
     * @param progress goes from 0 to 1.
     * @return true if rendering should continue, false if rendering should be stopped.
     */
    boolean onRenderProgress(double progress);

}
