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
     * @return number of seconds that the most recent full step lasted, in gametime.
     * Returns zero if this is the first tick.
     */
    double getLastStepDurationSeconds();

    /**
     * @return number of milliseconds that the most recent full step lasted, in gametime.
     * Returns zero if this is the first tick.
     */
    long getLastStepDurationMilliseconds();

    /**
     * @return number of seconds since the last step was recorded, in gametime.
     * Returns seconds since reset if this is the first tick.
     */
    double getSecondsSinceLastStep();

    /**
     * @return number of milliseconds seconds since the last step was recorded, in gametime.
     * Returns milliseconds since reset if this is the first tick.
     */
    long getMillisecondsSinceLastStep();

    /**
     * @return number of steps per second, based on the last value recorded, in gametime.
     * Returns zero if this is the first tick.
     */
    double getStepsPerSecond();

    /**
     * @return number of seconds since the Time was created, or since the last call to reset, in gametime.
     */
    double getSecondsSinceStart();

    /**
     * @return number of milliseconds since the Time was created, or since the last call to reset, in gametime.
     */
    long getMillisecondsSinceStart();

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
     * @param secondsSinceStart number of seconds since start in gametime.
     * @param stepCount number of steps since start.
     */
    void reset(double secondsSinceStart, long stepCount);
}
