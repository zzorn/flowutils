package org.flowutils.time;

import org.flowutils.Check;

/**
 * Time class that uses real time passed as gametime.
 */
public final class RealTime extends TimeBase {

    private final double speedupFactor;
    private final long startTime;

    /**
     * Creates a new RealTime starting at zero gametime and zero timesteps.
     */
    public RealTime() {
        this(0,0);
    }

    /**
     * Creates a new RealTime.
     *
     * @param speedupFactor factor for converting real time to gametime.
     *                      1 -> 1:1, 2 -> gametime twice as fast as real time, 0.5 -> gametime half as fast as real time.
     */
    public RealTime(double speedupFactor) {
        this(0, 0, DEFAULT_SMOOTHING_FACTOR, speedupFactor);
    }

    /**
     * Creates a new RealTime.
     *
     * @param millisecondsSinceStart what to initialize elapsed time to.
     * @param stepCount what to initialize elapsed steps to.
     */
    public RealTime(long millisecondsSinceStart, long stepCount) {
        this(millisecondsSinceStart, stepCount, DEFAULT_SMOOTHING_FACTOR);
    }

    /**
     * Creates a new RealTime.
     *
     * @param millisecondsSinceStart what to initialize elapsed time to.
     * @param stepCount what to initialize elapsed steps to.
     * @param smoothingFactor what to initialize the step smoothing value to.
     */
    public RealTime(long millisecondsSinceStart, long stepCount, double smoothingFactor) {
        this(millisecondsSinceStart, stepCount, smoothingFactor, 1.0);
    }

    /**
     * Creates a new RealTime.
     *
     * @param millisecondsSinceStart what to initialize elapsed time to.
     * @param stepCount what to initialize elapsed steps to.
     * @param smoothingFactor what to initialize the step smoothing value to.
     * @param speedupFactor factor for converting real time to gametime.
     *                      1 -> 1:1, 2 -> gametime twice as fast as real time, 0.5 -> gametime half as fast as real time.
     */
    public RealTime(long millisecondsSinceStart, long stepCount, double smoothingFactor, double speedupFactor) {
        super(millisecondsSinceStart, stepCount, smoothingFactor);

        Check.positive(speedupFactor, "speedupFactor");

        this.speedupFactor = speedupFactor;
        this.startTime = System.currentTimeMillis();
    }

    /**
     * @return speedupFactor applied to time.
     *  1: 1 real second == 1 game second,
     *  2: 1 real second == 2 game seconds,
     *  0.5: 1 real second = 0.5 game seconds,
     *  etc.
     */
    public double getSpeedupFactor() {
        return speedupFactor;
    }

    protected long getCurrentTimeMs() {
        // Count time from constructor invocation,
        // so that the speedup factor doesn't accidentally end up increasing time past maxlong if it is large.
        final long elapsedTime = System.currentTimeMillis() - startTime;

        return (long) (elapsedTime * speedupFactor);
    }

}
