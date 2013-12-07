package org.flowutils.time;

import org.flowutils.Check;

/**
 * A Time implementation where the current gametime is manually incremented.
 * Useful for testing.
 */
public final class ManualTime extends TimeBase {

    private long currentTimeMs = 0;

    /**
     * Creates a new ManualTime starting at time zero and step zero.
     */
    public ManualTime() {
    }

    /**
     * Creates a new ManualTime.
     *
     * @param millisecondsSinceStart what to initialize elapsed time to.
     * @param stepCount what to initialize elapsed steps to.
     */
    public ManualTime(long millisecondsSinceStart, long stepCount) {
        super(millisecondsSinceStart, stepCount);
    }

    /**
     * Creates a new ManualTime.
     *
     * @param millisecondsSinceStart what to initialize elapsed time to.
     * @param stepCount what to initialize elapsed steps to.
     * @param smoothingFactor what to initialize the step smoothing value to.
     */
    public ManualTime(long millisecondsSinceStart, long stepCount, double smoothingFactor) {
        super(millisecondsSinceStart, stepCount, smoothingFactor);
    }

    /**
     * Advances the time by the specified number of milliseconds.
     * @param millisecondsToAdd
     */
    public void advanceTime(long millisecondsToAdd) {
        Check.positiveOrZero(millisecondsToAdd, "millisecondsToAdd");

        currentTimeMs += millisecondsToAdd;
    }

    @Override protected long getCurrentTimeMs() {
        return currentTimeMs;
    }
}
