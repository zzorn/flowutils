package org.flowutils;

import org.flowutils.raster.field.MultiFieldToRawImageRenderer;
import org.flowutils.raster.field.multi.CompositeMultiField;
import org.flowutils.raster.field.single.NoiseField;
import org.flowutils.rawimage.RawImagePanel;

import javax.swing.*;
import java.awt.*;

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

        showTestFrame(view);
    }

    private static void showTestFrame(JComponent view) {
        JFrame testFrame = new JFrame("Rendering Example");
        testFrame.setPreferredSize(new Dimension(800, 600));
        testFrame.setContentPane(view);
        testFrame.pack();
        testFrame.setVisible(true);
        testFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

}
