package org.flowutils.raster.field;

import org.flowutils.Symbol;
import org.flowutils.raster.raster.InterleavedMultiRaster;
import org.flowutils.raster.raster.MultiRaster;
import org.flowutils.rectangle.Rectangle;

import java.util.Collection;
import java.util.Map;

/**
 * A renderer that renders something to a MultiRaster.
 */
public interface Renderer {

    /**
     * @param targetRaster MultiRaster to render to
     * @param sourceArea source area to render
     * @param renderListener listener that is notified of rendering progress, or null if no listener specified.
     */
    void renderToRaster(MultiRaster targetRaster, Rectangle sourceArea, RenderListener renderListener);

}
