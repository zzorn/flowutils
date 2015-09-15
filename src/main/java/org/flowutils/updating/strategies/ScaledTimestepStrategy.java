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

    public ScaledTimestepStrategy() {
        this(1.0);
    }

    public ScaledTimestepStrategy(double timeScale) {
        setTimeScale(timeScale);
    }

    public double getTimeScale() {
        return timeScale;
    }

    public void setTimeScale(double timeScale) {
        Check.positive(timeScale, "timeScale");
        this.timeScale = timeScale;
    }

    @Override protected void doUpdate(Updating simulation, ManualTime localTime, Time externalTime) {

        // Cap the elapsed time
        final double elapsedTime = externalTime.getLastStepDurationSeconds() * timeScale;

        // Update local time
        localTime.advanceTime(elapsedTime);
        localTime.nextStep();

        // Update simulation
        simulation.update(localTime);
    }
}
