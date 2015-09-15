package org.flowutils.time;

import org.flowutils.Check;

/**
 * A Time implementation where the current gametime is manually incremented.
 * Useful for testing or time classes that should not be bound to wall clock.
 */
public final class ManualTime extends TimeBase {

    private double currentTimeSeconds = 0;

    /**
     * Creates a new ManualTime starting at time zero and step zero.
     */
    public ManualTime() {
    }

    /**
     * Creates a new ManualTime.
     *
     * @param secondsSinceStart what to initialize elapsed time to.
     * @param stepCount what to initialize elapsed steps to.
     */
    public ManualTime(double secondsSinceStart, long stepCount) {
        super(secondsSinceStart, stepCount);
    }

    /**
     * Advances the time by the specified number of seconds.
     */
    public void advanceTime(double secondsToAdd) {
        Check.positiveOrZero(secondsToAdd, "secondsToAdd");

        currentTimeSeconds += secondsToAdd;
    }

    /**
     * Advances the time by the specified number of milliseconds.
     */
    public void advanceTimeWithMilliseconds(long millisecondsToAdd) {
        Check.positiveOrZero(millisecondsToAdd, "millisecondsToAdd");

        currentTimeSeconds += millisecondsToAdd / 1000.0;
    }

    @Override protected double getCurrentTimeSeconds() {
        return currentTimeSeconds;
    }
}
