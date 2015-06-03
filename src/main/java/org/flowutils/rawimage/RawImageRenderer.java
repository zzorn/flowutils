package org.flowutils.rawimage;

import org.flowutils.raster.field.RenderListener;
import org.flowutils.rectangle.Rectangle;
import org.flowutils.rectangle.intrectangle.IntRectangle;

/**
 * Something that renders a RawImage
 */
public interface RawImageRenderer {

    /**
     * @param target the target RawImage to render to.
     *               Remember to call flush on the target after the image has been rendered.
     * @param targetArea the area to render to on the target (e.g. if only a part needs to be refreshed).
     * @param sourceArea Source area to render to the target.
     * @param listener listener to notify about the rendering progress, or null if none.
     */
    void renderImage(RawImage target,
                     IntRectangle targetArea,
                     Rectangle sourceArea,
                     RenderListener listener);

}
