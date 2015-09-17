package org.flowutils.time;

import org.flowutils.Check;

/**
 * Time class that uses real time passed as gametime.
 */
public final class RealTime extends TimeBase {

    private double speedupFactor;
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
        this(0, 0, speedupFactor);
    }

    /**
     * Creates a new RealTime.
     *
     * @param secondsSinceStart what to initialize elapsed time to.
     * @param stepCount what to initialize elapsed steps to.
     */
    public RealTime(double secondsSinceStart, long stepCount) {
        this(secondsSinceStart, stepCount, 1.0);
    }

    /**
     * Creates a new RealTime.
     *
     * @param time time to initialize secondsSinceStart and stepCount from
     */
    public RealTime(Time time) {
        this(time, 1.0);
    }

    /**
     * Creates a new RealTime.
     *
     * @param time time to initialize secondsSinceStart and stepCount from
     * @param speedupFactor factor for converting real time to gametime.
     *                      1 -> 1:1, 2 -> gametime twice as fast as real time, 0.5 -> gametime half as fast as real time.
     */
    public RealTime(Time time, double speedupFactor) {
        this(time.getSecondsSinceStart(), time.getStepCount(), speedupFactor);
    }

    /**
     * Creates a new RealTime.
     *
     * @param secondsSinceStart what to initialize elapsed time to.
     * @param stepCount what to initialize elapsed steps to.
     * @param speedupFactor factor for converting real time to gametime.
     *                      1 -> 1:1, 2 -> gametime twice as fast as real time, 0.5 -> gametime half as fast as real time.
     */
    public RealTime(double secondsSinceStart, long stepCount, double speedupFactor) {
        super(secondsSinceStart, stepCount);

        this.startTime = System.currentTimeMillis();

        setSpeedupFactor(speedupFactor);
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

    /**
     * @param speedupFactor speedupFactor applied to time.
     *  1: 1 real second == 1 game second,
     *  2: 1 real second == 2 game seconds,
     *  0.5: 1 real second = 0.5 game seconds,
     *  etc.
     */
    public void setSpeedupFactor(double speedupFactor) {
        Check.positive(speedupFactor, "speedupFactor");
        this.speedupFactor = speedupFactor;
    }

    protected double getCurrentTimeSeconds() {
        // Count time from constructor invocation
        final long elapsedTimeMs = System.currentTimeMillis() - startTime;

        return elapsedTimeMs * speedupFactor / 1000.0;
    }

}
