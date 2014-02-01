package org.flowutils.raster;

import org.flowutils.Maths;
import org.flowutils.Symbol;
import org.flowutils.rectangle.Rectangle;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Provides implementations of Raster functions using the GridRaster.
 */
public abstract class GridRasterBase extends RasterBase implements GridRaster {

    protected GridRasterBase() {
    }

    protected GridRasterBase(final Collection<Symbol> channels) {
        super(channels);
    }

    protected GridRasterBase(final Rectangle area, final Collection<Symbol> channels) {
        super(area, channels);
    }

    @Override
    public float getValue(final double x, final double y, final Symbol channel) {
        return getGridValue(mapAreaToGridX(x), mapAreaToGridY(y), channel);
    }

    @Override
    public void setValue(final double x, final double y, final Symbol channel, final float value) {
        setGridValue(mapAreaToGridX(x), mapAreaToGridY(y), channel, value);
    }



    // Default implementation, can be overridden
    @Override
    public void getGridValues(final int gridX, final int gridY, final Map<Symbol, Float> dataOut) {
        for (Symbol channel : getChannels()) {
            final float value = getValue(gridX, gridY, channel);
            dataOut.put(channel, value);
        }
    }

    // Default implementation, can be overridden
    @Override
    public void setGridValues(final int gridX, final int gridY, final Map<Symbol, Float> data) {
        for (Entry<Symbol, Float> entry : data.entrySet()) {
            final Symbol channel = entry.getKey();
            final float value = entry.getValue();

            // Only set values to the channels that we have
            if (hasChannel(channel)) {
                setValue(gridX, gridY, channel, value);
            }
        }
    }

    // Default implementation, can be overridden
    @Override
    public void getValues(final double x, final double y, final Map<Symbol, Float> dataOut) {
        getGridValues(mapAreaToGridX(x), mapAreaToGridY(y), dataOut);
    }

    // Default implementation, can be overridden
    @Override
    public void setValues(final double x, final double y, final Map<Symbol, Float> data) {
        setGridValues(mapAreaToGridX(x), mapAreaToGridY(y), data);
    }

    /**
     * Map an area coordinate to a grid coordinate.
     * @param x area coordinate.
     * @return grid coordinate.
     */
    public final int mapAreaToGridX(double x) {
        final Rectangle area = getArea();
        return Maths.fastFloor(Maths.map(x, area.getMinX(), area.getMaxX(), 0, getGridSizeX()));
    }

    /**
     * Map an area coordinate to a grid coordinate.
     * @param y area coordinate.
     * @return grid coordinate.
     */
    public final int mapAreaToGridY(double y) {
        final Rectangle area = getArea();
        return Maths.fastFloor(Maths.map(y, area.getMinY(), area.getMaxY(), 0, getGridSizeY()));
    }

}
