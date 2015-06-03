package org.flowutils.raster.field;

import org.flowutils.Symbol;
import org.flowutils.raster.field.multi.MultiField;
import org.flowutils.rawimage.RawImage;
import org.flowutils.rawimage.RawImageRenderer;
import org.flowutils.rectangle.Rectangle;
import org.flowutils.rectangle.intrectangle.IntRectangle;

/**
 *
 */
public class MultiFieldToRawImageRenderer implements RawImageRenderer {

    private MultiField multiField;
    private Symbol     redChannel;
    private Symbol     greenChannel;
    private Symbol     blueChannel;
    private Symbol     alphaChannel;

    public MultiFieldToRawImageRenderer(final MultiField multiField, final Symbol valueChannel) {
        this(multiField, valueChannel, valueChannel, valueChannel);
    }

    public MultiFieldToRawImageRenderer(final MultiField multiField,
                                        final Symbol redChannel,
                                        final Symbol greenChannel,
                                        final Symbol blueChannel) {
        this(multiField, redChannel, greenChannel, blueChannel, null);
    }

    public MultiFieldToRawImageRenderer(final MultiField multiField,
                                        final Symbol redChannel,
                                        final Symbol greenChannel,
                                        final Symbol blueChannel,
                                        final Symbol alphaChannel) {
        this.multiField = multiField;
        this.redChannel = redChannel;
        this.greenChannel = greenChannel;
        this.blueChannel = blueChannel;
        this.alphaChannel = alphaChannel;
    }

    public MultiField getMultiField() {
        return multiField;
    }

    public void setMultiField(final MultiField multiField) {
        this.multiField = multiField;
    }

    public Symbol getRedChannel() {
        return redChannel;
    }

    public void setRedChannel(final Symbol redChannel) {
        this.redChannel = redChannel;
    }

    public Symbol getGreenChannel() {
        return greenChannel;
    }

    public void setGreenChannel(final Symbol greenChannel) {
        this.greenChannel = greenChannel;
    }

    public Symbol getBlueChannel() {
        return blueChannel;
    }

    public void setBlueChannel(final Symbol blueChannel) {
        this.blueChannel = blueChannel;
    }

    public Symbol getAlphaChannel() {
        return alphaChannel;
    }

    public void setAlphaChannel(final Symbol alphaChannel) {
        this.alphaChannel = alphaChannel;
    }

    @Override
    public void renderImage(final RawImage target, final IntRectangle targetArea, final Rectangle sourceArea, final RenderListener listener) {
        if (multiField != null && !targetArea.isEmpty() && targetArea.getArea() > 0) {
            multiField.renderToRawImage(redChannel,
                                        greenChannel,
                                        blueChannel,
                                        alphaChannel,
                                        target,
                                        sourceArea,
                                        targetArea,
                                        listener);
        }
        else {
            target.clear();
        }
    }
}
