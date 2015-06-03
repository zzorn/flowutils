package org.flowutils.rawimage;


import org.flowutils.rectangle.MutableRectangle;
import org.flowutils.rectangle.Rectangle;

import javax.swing.*;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * A Swing Panel that shows a backing FastImage.
 * The image is either provided directly, or created with a renderer which recreates it whenever the panel is resized.
 */
// TODO: Should this contain the source area, or should that be a separate panel with zoom etc?  Maybe separate panel
public class RawImagePanel extends JPanel {

    private RawImageRenderer renderer;
    private RawImage  rawImage   = null;
    private final Rectangle sourceArea = new MutableRectangle(0, 0, 1, 1);

    private boolean imagePainted = false;

    /**
     * Creates an empty RawImagePanel.  Use setRawImage or setRenderer to set content.
     */
    public RawImagePanel() {
        // Listen to resizes, and re-render the image when a resize happened
        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {

                if (RawImagePanel.this.renderer != null) {
                    final int width = getWidth();
                    final int height = getHeight();

                        // Create image if we have any size to work with.
                        if (width > 0 && height > 0) rawImage = new RawImage(width, height);
                        else rawImage = null;
                    }

                    reRender();
                }
            });

        setOpaque(false);
    }

    /**
     * Creates a new RawImagePanel that shows the specified RawImage.
     */
    public RawImagePanel(RawImage rawImage) {
        this();

        this.rawImage = rawImage;
    }

    /**
     * Creates a new RawImagePanel that uses the specified renderer to redraw a RawImage whenever the panel size changes.
     *
     * @param renderer something that can render the contents of the image when needed.
     */
    public RawImagePanel(RawImageRenderer renderer) {
        this();

        setRenderer(renderer);
    }

    /**
     * @return the renderer used to render this image.
     */
    public RawImageRenderer getRenderer() {
        return renderer;
    }



    /**
     * @param renderer the renderer to use for rendering this image.
     */
    public void setRenderer(RawImageRenderer renderer) {
        this.renderer = renderer;

        setOpaque(renderer != null);

        reRender();
    }

    /**
     * Set the raw image to render.  Disables any specified renderer.
     */
    public void setRawImage(RawImage rawImage) {
        this.rawImage = rawImage;
        renderer = null;
        reRender();
    }

    /**
     * @return the area to render to this image by the renderer.
     * Can be modified.
     */
    public Rectangle getSourceArea() {
        return sourceArea;
    }

    /**
     * Triggers a re-render of the image and a repaint of this panel.
     */
    public void reRender() {
        imagePainted = false;
        repaint();
    }

    public void paintComponent(Graphics g) {
        if (rawImage != null) {

            // Re-render the image if needed
            if (!imagePainted && renderer != null) {
                renderer.renderImage(rawImage, rawImage.getExtent(), sourceArea, null);
                imagePainted = true;

                // Flush the image to be sure we have the latest version
                rawImage.getImage().flush();
            }

            // Draw the renderer image to the panel
            rawImage.renderToGraphics(g);
        }
    }

}
