package org.flowutils.random;

import org.flowutils.rawimage.RawImage;
import org.flowutils.rawimage.RawImagePanel;
import org.flowutils.ui.SimpleFrame;

import java.util.Random;

/**
 *
 */
public class RandomTester {

    public static void main(String[] args) {

        int xSize = 256;
        int ySize = 256;

        RawImage rawImage = new RawImage(xSize, ySize);
        RawImagePanel rawImagePanel = new RawImagePanel(rawImage);
        new SimpleFrame("Random Visualization", rawImagePanel);

        RandomSequence randomSequence = new XorShift(42);
        /*
        final Random r = new Random();
        RandomSequence randomSequence = new BaseRandomSequence(0, new RandomHash() {
            @Override public long hash(long input) {
                return input;
            }
        }) {

            @Override protected void setHashedSeed(long seed) {
                r.setSeed(seed);
            }

            @Override public long nextLong() {
                return r.nextLong();
            }
        };
        */

        /*
        for (int y = 0; y < ySize; y++) {
            for (int x = 0; x < xSize; x++) {

                randomSequence.setSeed(y * xSize + x);

                double c = randomSequence.nextInt(256) / 255.0;
                //double c = randomSequence.nextDouble();
                rawImage.setPixel(x, y, c, c, c);
            }
        }
        */


        for (int i = 0; i < 300000; i++) {
            randomSequence.setSeed(i);

            int x = randomSequence.nextInt(xSize);
            int y = randomSequence.nextInt(ySize);

            double value = rawImage.getRed(x, y);
            value += 0.1;

            rawImage.setPixel(x, y, value, value, value);
        }

        rawImage.flush();
        rawImagePanel.reRender();
    }

}
