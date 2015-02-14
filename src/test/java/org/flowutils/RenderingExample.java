package org.flowutils;

import org.flowutils.raster.field.MultiFieldToRawImageRenderer;
import org.flowutils.raster.field.multi.CompositeMultiField;
import org.flowutils.raster.field.single.NoiseField;
import org.flowutils.rawimage.RawImagePanel;

/**
 *
 */
public class RenderingExample {

    public static void main(String[] args) {

        CompositeMultiField field = new CompositeMultiField();
        field.addChannel(Symbol.get("height"), new NoiseField(3, 3, 0, 0));
        field.addChannel(Symbol.get("moisture"), new NoiseField(10, 10, 32, 13));

        RawImagePanel view = new RawImagePanel(
                new MultiFieldToRawImageRenderer(field,
                                                 Symbol.get("height"),
                                                 Symbol.get("height"),
                                                 Symbol.get("moisture")));

        SimpleFrame simpleFrame = new SimpleFrame("Rendering Example", view);

    }

}
