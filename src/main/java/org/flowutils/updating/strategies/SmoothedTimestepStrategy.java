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

    /**
     * Creates a SmoothedTimestepStrategy with a smoothing factor of 0.5.
     */
    public SmoothedTimestepStrategy() {
        this(0.5);
    }

    /**
     * @param smoothingFactor how much to smooth the timeSteps.
     *                        0 = no smoothing, use external time steps directly,
     *                        0.5 = average external time step and previous smoothed time step,
     *                        approaching 1 = use almost completely previous smoothed time step.
     */
    public SmoothedTimestepStrategy(double smoothingFactor) {
        setSmoothingFactor(smoothingFactor);
    }

    /**
     * @return how much to smooth the timeSteps.
     *                        0 = no smoothing, use external time steps directly,
     *                        0.5 = average external time step and previous smoothed time step,
     *                        approaching 1 = use almost completely previous smoothed time step.
     */
    public double getSmoothingFactor() {
        return smoothingFactor;
    }

    /**
     * @param smoothingFactor how much to smooth the timeSteps.
     *                        0 = no smoothing, use external time steps directly,
     *                        0.5 = average external time step and previous smoothed time step,
     *                        approaching 1 = use almost completely previous smoothed time step.
     */
    public void setSmoothingFactor(double smoothingFactor) {
        Check.inRange(smoothingFactor, "smoothingFactor", 0, 1);
        this.smoothingFactor = smoothingFactor;
    }

    @Override protected void doUpdate(Updating simulation, ManualTime localTime, Time externalTime) {
        // Smooth
        final double externalElapsedTime = externalTime.getLastStepDurationSeconds();
        final double smoothedElapsedTime = firstStep ? externalElapsedTime : MathUtils.mix(smoothingFactor, externalElapsedTime, previousSmoothedDuration);
        previousSmoothedDuration = smoothedElapsedTime;
        firstStep = false;

        // Update local time
        localTime.advanceTimeSeconds(smoothedElapsedTime);
        localTime.nextStep();

        // Update simulation
        simulation.update(localTime);
    }
}
