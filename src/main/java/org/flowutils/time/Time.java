package org.flowutils.time;

/**
 * Keeps track of gametime passed and timesteps handled.
 */
public interface Time {

    /**
     * Call this every frame or update to advance the timestep.
     */
    void nextStep();

    /**
     * @return number of seconds since the last step was recorded, in gametime.
     * Returns seconds since reset if this is the first tick.
     */
    double getSecondsSinceLastStep();

    /**
     * @return number of seconds that the most recent full step lasted, in gametime.
     * Returns zero if this is the first tick.
     */
    double getLastStepDurationSeconds();

    /**
     * @return exponentially averaged number of seconds that the most recent steps lasted, in gametime.
     * Returns zero if this is the first tick.
     */
    double getSmoothedStepDurationSeconds();

    /**
     * @return number of milliseconds that the most recent full step lasted, in gametime.
     * Returns zero if this is the first tick.
     */
    long getLastStepDurationMs();

    /**
     * @return number of steps per second, based on the last value recorded, in gametime.
     * Returns zero if this is the first tick.
     */
    double getLastStepsPerSecond();

    /**
     * @return exponentially averaged number of steps per second, in gametime.
     * Returns zero if this is the first tick.
     */
    double getSmoothedStepsPerSecond();

    /**
     * @return number of milliseconds since the Time was created, or since the last call to reset, in gametime.
     */
    long getMillisecondsSinceStart();

    /**
     * @return number of seconds since the Time was created, or since the last call to reset, in gametime.
     */
    double getSecondsSinceStart();

    /**
     * @return number of steps since Time was created, or since the last call to reset.
     */
    long getStepCount();

    /**
     * Restarts the time and stepcount from zero.
     */
    void reset();

    /**
     * Reset to a specific state.
     * @param millisecondsSinceStart number of milliseconds since start in gametime.
     * @param stepCount number of steps since start.
     */
    void reset(long millisecondsSinceStart, long stepCount);

    /**
     * @return weight of previous step durations when calculating the smoothed tick duration.
     * 0 = do not smoothe, 0.5 = average this step duration and the earlier average.
     * Must be zero or larger and less than one.
     */
    double getSmoothingFactor();

    /**
     * @param smoothingFactor weight of previous step durations when calculating the smoothed tick duration.
     * 0 = do not smoothe, 0.5 = average this step duration and the earlier average.
     * Must be zero or larger and less than one.
     */
    void setSmoothingFactor(double smoothingFactor);

}
