package org.flowutils.updating.strategies;

import org.flowutils.Check;
import org.flowutils.MathUtils;
import org.flowutils.time.ManualTime;
import org.flowutils.time.Time;
import org.flowutils.updating.Updating;

/**
 * UpdateStrategy that smooths the elapsed time by averaging it over time.
 */
public final class SmoothedTimestepStrategy extends UpdateStrategyWithLocalTimeBase {

    private double previousSmoothedDuration;
    private double smoothingFactor;
    private boolean firstStep = true;

    public SmoothedTimestepStrategy() {
        this(0.5);
    }

    public SmoothedTimestepStrategy(double smoothingFactor) {
        setSmoothingFactor(smoothingFactor);
    }

    public double getSmoothingFactor() {
        return smoothingFactor;
    }

    public void setSmoothingFactor(double smoothingFactor) {
        Check.inRange(smoothingFactor, "smoothingFactor", 0, 1);
        this.smoothingFactor = smoothingFactor;
    }

    @Override protected void doUpdate(Updating simulation, ManualTime localTime, Time externalTime) {
        // Smooth
        final double externalElapsedTime = externalTime.getLastStepDurationSeconds();
        final double elapsedTime = firstStep ? externalElapsedTime : MathUtils.mix(smoothingFactor, externalElapsedTime, previousSmoothedDuration);
        previousSmoothedDuration = elapsedTime;
        firstStep = false;

        // Update local time
        localTime.advanceTime(elapsedTime);
        localTime.nextStep();

        // Update simulation
        simulation.update(localTime);
    }
}
