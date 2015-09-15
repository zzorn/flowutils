package org.flowutils.time;

import org.flowutils.Check;
import org.flowutils.MathUtils;
import org.flowutils.MathUtils;
import org.flowutils.TimeUtils;

/**
 * Base class for common functionality of Time implementations.
 */
public abstract class TimeBase implements Time {

    private static final double SECONDS_TO_MILLISECONDS = 1000.0;

    // Main state
    private double secondsSinceStart = 0;
    private long stepCount = 0;

    // Temporary calculated values
    private double lastStepTimeStamp;
    private double lastStepDurationSeconds;

    /**
     * Creates a new TimeBase that starts at zero with zero steps.
     */
    protected TimeBase() {
        this(0, 0);
    }

    /**
     * @param time used to get the seconds since start and step count to initialize this time to.
     */
    protected TimeBase(Time time) {
        this(time.getSecondsSinceStart(), time.getStepCount());
    }

    /**
     * @param secondsSinceStart what to initialize elapsed time to.
     * @param stepCount what to initialize elapsed steps to.
     */
    protected TimeBase(double secondsSinceStart, long stepCount) {
        reset(secondsSinceStart, stepCount);
    }

    @Override public final void reset() {
        reset(0, 0);
    }

    @Override public final void reset(double secondsSinceStart, long stepCount) {
        Check.positiveOrZero(secondsSinceStart, "secondsSinceStart");
        Check.positiveOrZero(stepCount, "stepCount");

        this.secondsSinceStart = secondsSinceStart;
        this.stepCount = stepCount;

        lastStepTimeStamp = getCurrentTimeSeconds();
        lastStepDurationSeconds = 0;
    }

    @Override public final void nextStep() {
        double time = getCurrentTimeSeconds();
        stepCount++;
        lastStepDurationSeconds = Math.max(0, time - lastStepTimeStamp);
        secondsSinceStart += lastStepDurationSeconds;
        lastStepTimeStamp = time;
    }

    @Override public final long getMillisecondsSinceLastStep() {
        return (long) (SECONDS_TO_MILLISECONDS * getSecondsSinceLastStep());
    }

    @Override public final double getSecondsSinceLastStep() {
        return getCurrentTimeSeconds() - lastStepTimeStamp;
    }

    @Override public final double getLastStepDurationSeconds() {
        return lastStepDurationSeconds;
    }

    @Override public final long getLastStepDurationMilliseconds() {
        return (long) (SECONDS_TO_MILLISECONDS * lastStepDurationSeconds);
    }

    @Override public final double getStepsPerSecond() {
        if (lastStepDurationSeconds <= 0) return 0;
        else return 1.0 / lastStepDurationSeconds;
    }

    @Override public final long getMillisecondsSinceStart() {
        return (long) (SECONDS_TO_MILLISECONDS * secondsSinceStart);
    }

    @Override public final double getSecondsSinceStart() {
        return secondsSinceStart;
    }

    @Override public final long getStepCount() {
        return stepCount;
    }

    /**
     * @return current time as seconds since some arbitrary epoch that does not change.
     */
    protected abstract double getCurrentTimeSeconds();
}
