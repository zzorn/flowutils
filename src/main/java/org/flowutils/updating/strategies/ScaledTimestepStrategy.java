package org.flowutils.updating.strategies;

import org.flowutils.Check;
import org.flowutils.time.ManualTime;
import org.flowutils.time.Time;
import org.flowutils.updating.Updating;

/**
 * Simple UpdateStrategy that just scales the amount of time elapsed with a constant.
 */
public final class ScaledTimestepStrategy extends UpdateStrategyWithLocalTimeBase {

    private double timeScale;

    /**
     * New ScaledTimestepStrategy with a time scale of 1 (Same local time speed as external time speed).
     */
    public ScaledTimestepStrategy() {
        this(1.0);
    }

    /**
     * @param timeScale scale for passing time.
     *                  2 = double the amount of local time will pass for a given amount of external time,
     *                  0.5 = half the amount of local time will pass for a given amount of external time,
     *                  etc.
     */
    public ScaledTimestepStrategy(double timeScale) {
        setTimeScale(timeScale);
    }

    /**
     * @return scale for passing time.
     *                  2 = double the amount of local time will pass for a given amount of external time,
     *                  0.5 = half the amount of local time will pass for a given amount of external time,
     *                  etc.
     */
    public double getTimeScale() {
        return timeScale;
    }

    /**
     * @param timeScale scale for passing time.
     *                  2 = double the amount of local time will pass for a given amount of external time,
     *                  0.5 = half the amount of local time will pass for a given amount of external time,
     *                  etc.
     */
    public void setTimeScale(double timeScale) {
        Check.positive(timeScale, "timeScale");
        this.timeScale = timeScale;
    }

    @Override protected void doUpdate(Updating simulation, ManualTime localTime, Time externalTime) {

        // Cap the elapsed time
        final double elapsedTime = externalTime.getLastStepDurationSeconds() * timeScale;

        // Update local time
        localTime.advanceTimeSeconds(elapsedTime);
        localTime.nextStep();

        // Update simulation
        simulation.update(localTime);
    }
}
