package org.flowutils.gradient;

import java.util.Map;
import java.util.TreeMap;

import static org.flowutils.MathUtils.map;

/**
 * Simple value gradient.
 */
public final class Gradient {

    public static final double DEFAULT_VALUE = 0.0;

    private final TreeMap<Double, Double> values = new TreeMap<Double, Double>();

    /**
     * Creates a new gradient
     *
     * @param positionValuePairs zero or more comma separated position and value pairs.
     *                           E.g.: position1, value1, position2, value2, etc.
     */
    public Gradient(double ... positionValuePairs) {
        if ((positionValuePairs.length % 2) != 0) throw new IllegalArgumentException("Odd number of position and value pairs (parameters should be comma separated positions and values for the preceding position");

        for (int i = 0; i < positionValuePairs.length; i += 2) {
            addValue(positionValuePairs[i], positionValuePairs[i + 1]);
        }
    }


    /**
     * Adds a value to the gradient at a specific position
     */
    public void addValue(double position, double value) {
        values.put(position, value);
    }

    /**
     * Removes the entry closest to the specified position.
     */
    public void removeValue(double position) {
        final Map.Entry<Double, Double> floor = values.floorEntry(position);
        final Map.Entry<Double, Double> ceiling = values.ceilingEntry(position);

        // Remove closest existing entry.
        if (floor != null || ceiling != null) {
            if (floor == null) values.remove(ceiling.getKey());
            else if (ceiling == null) values.remove(floor.getKey());
            else {
                if (position - floor.getKey() < ceiling.getKey() - position) values.remove(floor.getKey());
                else values.remove(floor.getKey());
            }
        }
    }

    /**
     * Removes all values from the gradient.
     */
    public void clear() {
        values.clear();
    }

    /**
     * @return the underlying treemap.
     * Modifications to it are allowed, although not normally necessary.
     */
    public TreeMap<Double, Double> getValues() {
        return values;
    }

    /**
     * @return the value for the specified position in the gradient.
     * Interpolated if between values, but clamped to end values if outside the gradient range.
     * Zero if the gradient is empty.
     */
    public double getValue(double position) {
        final Map.Entry<Double, Double> floor = values.floorEntry(position);
        final Map.Entry<Double, Double> ceiling = values.ceilingEntry(position);

        if (floor == null && ceiling == null) return DEFAULT_VALUE;
        else if (floor == null) return ceiling.getValue();
        else if (ceiling == null) return floor.getValue();
        else {
            if (position == floor.getKey()) {
                return floor.getValue();
            }
            else if (position == ceiling.getKey()) {
                return ceiling.getValue();
            }
            else {
                return map(position,
                           floor.getKey(), ceiling.getKey(),
                           floor.getValue(), ceiling.getValue());
            }
        }

    }

}
