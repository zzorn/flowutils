package org.flowutils.collections.dataseries;

/**
 * A data series that returns the same value for all locations.
 */
public final class ConstantDataSeries<T extends Number, V> extends DataSeriesBase<T, V> {

    private V constantValue;


    /**
     * @param seriesAxis axis that the positions of the values in this DataSeries are distributed along.
     * @param constantValue value to return for all positions.
     */
    protected ConstantDataSeries(Axis<T> seriesAxis, V constantValue) {
        super(seriesAxis);
        setConstantValue(constantValue);
    }

    /**
     * @param seriesAxis axis that the positions of the values in this DataSeries are distributed along.
     * @param constantValue value to return for all positions.
     * @param start start of the data range
     * @param end end of the data range
     */
    protected ConstantDataSeries(Axis<T> seriesAxis, V constantValue, T start, T end) {
        super(seriesAxis);
        setRange(start, end);
        setConstantValue(constantValue);
    }

    public V getConstantValue() {
        return constantValue;
    }

    public void setConstantValue(V constantValue) {
        this.constantValue = constantValue;
    }

    @Override public V getValue(T position) {
        return constantValue;
    }

}
