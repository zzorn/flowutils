package org.flowutils.image;

/**
 * Something that renders a RawImage
 */
public interface RawImageRenderer {

    /**
     * @param target the target to render to.
     */
    void renderImage(RawImage target);

}
