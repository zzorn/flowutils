package org.flowutils.time;

import org.flowutils.Check;
import org.flowutils.MathUtils;
import org.flowutils.MathUtils;
import org.flowutils.TimeUtils;

/**
 * Base class for common functionality of Time implementations.
 */
public abstract class TimeBase implements Time {

    /**
     * How much smoothing to apply by default, that is, how much of the old smoothed step duration to mix into the new one.
     */
    protected static final double DEFAULT_SMOOTHING_FACTOR = 0.5;

    private static final double MILLISECONDS_TO_SECONDS = 1.0 / 1000.0;

    // Main state
    private long millisecondsSinceStart = 0;
    private long stepCount = 0;

    // Temporary calculated values
    private long lastStepTimeStamp;
    private long lastStepDurationMs;
    private double lastStepDurationSeconds;
    private double smoothedStepDurationSeconds = 0;
    private double smoothingFactor = DEFAULT_SMOOTHING_FACTOR;

    /**
     * Creates a new TimeBase that starts at zero with zero steps.
     */
    protected TimeBase() {
        this(0, 0);
    }

    /**
     * @param millisecondsSinceStart what to initialize elapsed time to.
     * @param stepCount what to initialize elapsed steps to.
     */
    protected TimeBase(long millisecondsSinceStart, long stepCount) {
        this(millisecondsSinceStart, stepCount, DEFAULT_SMOOTHING_FACTOR);
    }

    /**
     * @param millisecondsSinceStart what to initialize elapsed time to.
     * @param stepCount what to initialize elapsed steps to.
     * @param smoothingFactor what to initialize the step smoothing value to.
     */
    protected TimeBase(long millisecondsSinceStart, long stepCount, double smoothingFactor) {
        reset(millisecondsSinceStart, stepCount);
        setSmoothingFactor(smoothingFactor);
    }

    @Override public void reset() {
        reset(0, 0);
    }

    @Override public void reset(long millisecondsSinceStart, long stepCount) {
        Check.positiveOrZero(millisecondsSinceStart, "millisecondsSinceStart");
        Check.positiveOrZero(stepCount, "stepCount");

        this.millisecondsSinceStart = millisecondsSinceStart;
        this.stepCount = stepCount;

        lastStepTimeStamp = getCurrentTimeMs();
        lastStepDurationMs = 0;
        lastStepDurationSeconds = 0;
        smoothedStepDurationSeconds = 0;
    }

    @Override public void nextStep() {
        long time = getCurrentTimeMs();
        stepCount++;
        lastStepDurationMs = Math.max(0, time - lastStepTimeStamp);
        millisecondsSinceStart += lastStepDurationMs;
        lastStepDurationSeconds = lastStepDurationMs * MILLISECONDS_TO_SECONDS;
        lastStepTimeStamp = time;

        smoothedStepDurationSeconds = MathUtils.mix(smoothingFactor,
                                                    lastStepDurationSeconds,
                                                    smoothedStepDurationSeconds);
    }

    @Override public long getMillisecondsSinceLastStep() {
        return getCurrentTimeMs() - lastStepTimeStamp;
    }

    @Override public double getSecondsSinceLastStep() {
        return MILLISECONDS_TO_SECONDS * (getCurrentTimeMs() - lastStepTimeStamp);
    }

    @Override public float getSecondsSinceLastStepAsFloat() {
        return (float) getSecondsSinceLastStep();
    }

    @Override public double getLastStepDurationSeconds() {
        return lastStepDurationSeconds;
    }

    @Override public long getLastStepDurationMs() {
        return lastStepDurationMs;
    }

    @Override public double getLastStepsPerSecond() {
        if (lastStepDurationMs == 0) return 0;
        else return 1.0 / (lastStepDurationMs * MILLISECONDS_TO_SECONDS);
    }

    @Override public double getSmoothedStepsPerSecond() {
        if (smoothedStepDurationSeconds == 0) return 0;
        else return 1.0 / (smoothedStepDurationSeconds);
    }

    @Override public double getSmoothedStepDurationSeconds() {
        return smoothedStepDurationSeconds;
    }

    @Override public long getMillisecondsSinceStart() {
        return millisecondsSinceStart;
    }

    @Override public double getSecondsSinceStart() {
        return millisecondsSinceStart * MILLISECONDS_TO_SECONDS;
    }

    @Override public long getStepCount() {
        return stepCount;
    }

    @Override public double getSmoothingFactor() {
        return smoothingFactor;
    }

    @Override public void setSmoothingFactor(double smoothingFactor) {
        Check.positiveOrZero(smoothingFactor, "smoothingFactor");
        Check.less(smoothingFactor, "smoothingFactor", 1, "maximum smoothing");

        this.smoothingFactor = smoothingFactor;
    }

    @Override public void delayMilliseconds(long milliseconds) {
        TimeUtils.delay(milliseconds);
    }

    @Override public void delaySeconds(double seconds) {
        TimeUtils.delaySeconds(seconds);
    }

    protected abstract long getCurrentTimeMs();
}
