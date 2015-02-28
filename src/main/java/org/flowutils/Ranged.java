package org.flowutils;

import org.flowutils.random.RandomSequence;

import java.util.List;
import java.util.Random;

import static org.flowutils.Check.notNull;

/**
 * Wraps floating point number between zero and one.
 *
 * Contains utilities for mapping it to other ranges and doing math with it.
 *
 * This is a mutable class, so the value it holds may change.
 * This class is not thread safe (the same Ranged instance should not be accessed from different threads at the same time).
 */
public final class Ranged {

    private static final double EPSILON = Double.MIN_VALUE * 10;

    private double value = 0;

    /**
     * Creates a new Ranged object with a value of zero.
     */
    public Ranged() {
        value = 0;
    }

    /**
     * Creates a new Ranged object.
     * @param value a value in the 0..1 range.  Will be clamped to that range if it is outside it.
     *              Infinite or NaN are not allowed and will cause an exception.
     */
    public Ranged(double value) {
        set(value);
    }

    /**
     * Creates a new Ranged object.
     * @param value a value in the 0..1 range.  Will be clamped to that range if it is outside it.
     *              Infinite or NaN are not allowed and will cause an exception.
     */
    public Ranged(float value) {
        set(value);
    }

    /**
     * Creates a new Ranged object.
     * @param value a value in the 0..1 range.  Will be clamped to that range if it is outside it.
     */
    public Ranged(int value) {
        set(value);
    }

    /**
     * Creates a new Ranged based on a boolean value (true = 1, false = 0).
     */
    public Ranged(boolean value) {
        set(value);
    }

    /**
     * Creates a new Ranged object using an other Ranged object as initial value.
     */
    public Ranged(Ranged source) {
        set(source);
    }

    /**
     * Creates a new Ranged with a random value, using the specified random number generator.
     */
    public Ranged(RandomSequence random) {
        setRandom(random);
    }

    /**
     * Creates a new Ranged with a random value, using the specified random number generator.
     */
    public Ranged(Random random) {
        setRandom(random);
    }

    /**
     * @return the value of this Ranged object, which will be between zero and one, inclusive.
     */
    public double get() {
        return value;
    }

    /**
     * @param value a value in the 0..1 range.  Will be clamped to that range if it is outside it.
     *              Infinite or NaN are not allowed and will cause an exception.
     * @return this instance for chaining operations.
     */
    public Ranged set(double value) {
        Check.normalNumber(value, "value");

        this.value = MathUtils.clamp0To1(value);
        return this;
    }

    /**
     * @param value a value in the 0..1 range.  Will be clamped to that range if it is outside it.
     *              Infinite or NaN are not allowed and will cause an exception.
     * @return this instance for chaining operations.
     */
    public Ranged set(float value) {
        return set((double) value);
    }

    /**
     * @param value a value in the 0..1 range.  Will be clamped to that range if it is outside it.
     * @return this instance for chaining operations.
     */
    public Ranged set(int value) {
        return set((double) value);
    }

    /**
     * Set this Ranged value from a boolean.
     * @param value if false, the value will be 0, if true, the value will be 1.
     * @return this instance for chaining operations.
     */
    public Ranged set(boolean value) {
        if (value) this.value = 1.0;
        else this.value = 0.0;

        return this;
    }

    /**
     * @param source a ranged object to copy the value of.
     * @return this instance for chaining operations.
     */
    public Ranged set(Ranged source) {
        notNull(source, "source");
        return set(source.get());
    }

    /**
     * Sets the value of to the value, wrapping it to the 0..1 range.
     * @param value a value that will be wrapped to the 0..1 range.
     *              Infinite or NaN are not allowed and will cause an exception.
     * @return this instance for chaining operations.
     */
    public Ranged wrap(double value) {
        Check.normalNumber(value, "value");

        this.value = MathUtils.wrap0To1(value);
        return this;
    }

    /**
     * Sets the value of to the value, wrapping it to the range 0..max and then the relative position within that range is used as value.
     * @param value a value that will be wrapped to the 0..max range, and then the relative position within this range used as the value.
     *              Infinite or NaN are not allowed and will cause an exception.
     * @return this instance for chaining operations.
     */
    public Ranged wrap(double value, double max) {
        Check.normalNumber(value, "value");
        this.value = MathUtils.relPosAndClamp(MathUtils.wrap(value, max), 0, max);
        return this;
    }

    /**
     * Sets the value of to the value, wrapping it to the range specified by min and max and then the relative position within that range is used as value.
     * @param value a value that will be wrapped to the min..max range, and then the relative position within this range used as the value.
     *              Infinite or NaN are not allowed and will cause an exception.
     * @return this instance for chaining operations.
     */
    public Ranged wrap(double value, double min, double max) {
        Check.normalNumber(value, "value");
        this.value = MathUtils.relPosAndClamp(MathUtils.wrap(value, min, max), min, max);
        return this;
    }

    /**
     * @return true if the value is zero.
     */
    public boolean isZero() {
        return value <= EPSILON;
    }

    /**
     * @return true if the value is one.
     */
    public boolean isOne() {
        return value >= 1.0 - EPSILON;
    }

    /**
     * @return true if the value is equal to or larger than one half.
     */
    public boolean isHalfOrAbove() {
        return value >= 0.5;
    }

    /**
     * @return a value between zero and maxValue, depending on the current value.
     */
    public double map(double maxValue) {
        return value * maxValue;
    }

    /**
     * @return a value between zero and maxValue, depending on the current value.
     */
    public float map(float maxValue) {
        return (float) (value * maxValue);
    }

    /**
     * @return a value between zero and maxValue, depending on the current value.
     */
    public int map(int maxValue) {
        return (int) (value * maxValue);
    }

    /**
     * @return a value between minValue and maxValue, depending on the current value.
     */
    public double map(double minValue, double maxValue) {
        return minValue + value * (maxValue - minValue);
    }

    /**
     * @return a value between minValue and maxValue, depending on the current value.
     */
    public float map(float minValue, float maxValue) {
        return minValue + (float) (value * (maxValue - minValue));
    }

    /**
     * @return a value between minValue and maxValue, depending on the current value.
     */
    public int map(int minValue, int maxValue) {
        return minValue + (int) (value * (maxValue - minValue));
    }

    /**
     * @return the value mapped to the -1..1 (inclusive) range.
     */
    public double mapToMinusOneToOne() {
        return value * 2.0 - 1.0;
    }

    /**
     * Sets the value to the relative position of the position between start and end.
     * If position is same as start, the value will be 0, if the position is the same as end, the value will be 1.
     * @return this Ranged instance, for chaining operations.
     */
    public Ranged setRelativePos(double position, double start, double end) {
        value = MathUtils.relPosAndClamp(position, start, end);
        return this;
    }

    /**
     * @param random random number generator to use.
     * @return true with a probability of the current value (0 = always false, 1 = always true)
     */
    public boolean test(RandomSequence random) {
        return random.nextDouble() < value;
    }

    /**
     * @param random random number generator to use.
     * @return true with a probability of the current value (0 = always false, 1 = always true)
     */
    public boolean test(Random random) {
        return random.nextDouble() < value;
    }

    /**
     * Sets the value of this Ranged object to a random value between zero and one (exclusive), using the specified random number generator.
     * @return this Ranged instance, for chaining operations.
     */
    public Ranged setRandom(RandomSequence random) {
        value = random.nextDouble();
        return this;
    }

    /**
     * Sets the value of this Ranged object to a random value between zero and one (exclusive), using the specified random number generator.
     * @return this Ranged instance, for chaining operations.
     */
    public Ranged setRandom(Random random) {
        value = random.nextDouble();
        return this;
    }

    /**
     * @return the value as a float.
     */
    public float toFloat() {
        return (float) value;
    }

    /**
     * @return the value as an integer (either 0 or 1).
     */
    public int toInt() {
        if (value >= 0.5) return 1;
        else return 0;
    }

    /**
     * @return the value as a boolean.  Will be true if the value is half or above, false otherwise.
     */
    public boolean toBoolean() {
        return value >= 0.5;
    }

    /**
     * Adds the value of this Ranged object with the specified value, and stores the result in this Ranged object, clamped to 0..1 range.
     * @param value value to add to this.
     * @return this Ranged object, for chaining.
     */
    public Ranged add(double value) {
        Check.normalNumber(value, "value");
        this.value = MathUtils.clamp0To1(this.value + value);
        return this;
    }

    /**
     * Adds the value of this Ranged object with the other one, and stores the result in this Ranged object, clamped to the 0..1 range.
     * @param other value to add to this.
     * @return this Ranged object, for chaining.
     */
    public Ranged add(Ranged other) {
        notNull(other, "other");
        value = MathUtils.clamp0To1(value + other.get());
        return this;
    }

    /**
     * Subtracts the value of this Ranged object with the specified value, and stores the result in this Ranged object, clamped to 0..1 range.
     * @param value value to subtract from this.
     * @return this Ranged object, for chaining.
     */
    public Ranged sub(double value) {
        Check.normalNumber(value, "value");
        this.value = MathUtils.clamp0To1(this.value - value);
        return this;
    }

    /**
     * Subtracts the value of this Ranged object with the other one, and stores the result in this Ranged object, clamped to the 0..1 range.
     * @param other value to subtract from this.
     * @return this Ranged object, for chaining.
     */
    public Ranged sub(Ranged other) {
        notNull(other, "other");
        value = MathUtils.clamp0To1(value - other.get());
        return this;
    }

    /**
     * Multiplies the value of this Ranged object with the specified value, and stores the result in this Ranged object, clamped to 0..1 range.
     * @param value value to multiply this by.
     * @return this Ranged object, for chaining.
     */
    public Ranged mul(double value) {
        Check.normalNumber(value, "value");
        this.value = MathUtils.clamp0To1(this.value * value);
        return this;
    }

    /**
     * Multiplies the value of this Ranged object with the other one, and stores the result in this Ranged object.
     * @param other value to multiply this by.
     * @return this Ranged object, for chaining.
     */
    public Ranged mul(Ranged other) {
        notNull(other, "other");
        value *= other.get();
        return this;
    }

    /**
     * Divides the value of this Ranged object with the specified value, and stores the result in this Ranged object, clamped to 0..1 range.
     * @param value value to divide this by.  An exception is throw if this is zero.
     * @return this Ranged object, for chaining.
     */
    public Ranged div(double value) {
        Check.normalNumber(value, "value");
        Check.notZero(value, "value", EPSILON);
        this.value = MathUtils.clamp0To1(this.value / value);
        return this;
    }

    /**
     * Divides the value of this Ranged object with the other one, and stores the result in this Ranged object, clamped to 0..1 range..
     * @param other value to divide this by.
     * @return this Ranged object, for chaining.
     */
    public Ranged div(Ranged other) {
        notNull(other, "other");
        final double otherValue = other.get();
        Check.notZero(otherValue, "otherValue", EPSILON);
        this.value = MathUtils.clamp0To1(this.value / otherValue);
        return this;
    }

    /**
     * @return one element from the given list depending on the current value (0 == first element, 1 == last element).
     */
    public <T> T pick(List<T> elements) {
        notNull(elements, "elements");
        return elements.get(map(elements.size() - 1));
    }

    /**
     * @return one element from the given array depending on the current value (0 == first element, 1 == last element).
     */
    public <T> T pick(T ... elements) {
        notNull(elements, "elements");
        return elements[map(elements.length - 1)];
    }

    @Override public String toString() {
        return "" + value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Ranged ranged = (Ranged) o;

        if (Double.compare(ranged.value, value) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        long temp = Double.doubleToLongBits(value);
        return (int) (temp ^ (temp >>> 32));
    }
}
