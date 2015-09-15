package org.flowutils.updating.strategies;

import org.flowutils.Check;
import org.flowutils.time.ManualTime;
import org.flowutils.time.Time;
import org.flowutils.updating.Updating;

/**
 * An UpdateStrategy that caps the maximum duration of time steps.
 *
 * This helps limit physics and other artifacts in case the application experiences lag spikes or slowness
 * (e.g. caused by temporary system overload due to unrelated things, or things like disk io).
 */
public final class CappedTimestepStrategy extends UpdateStrategyWithLocalTimeBase {

    private double maximumStepDurationSeconds;

    /**
     * Creates a CappedStepDurationStrategy with the default maximum step duration of 100 milliseconds,
     * corresponding to a slowest allowed simulation framerate of 10fps.
     */
    public CappedTimestepStrategy() {
        this(0.1);
    }

    /**
     * @param maximumStepDurationSeconds maximum allowed update duration in seconds.
     *                                   If an update would take longer, the time is capped to this value.
     */
    public CappedTimestepStrategy(double maximumStepDurationSeconds) {
        setMaximumStepDuration(maximumStepDurationSeconds);
    }

    /**
     * @return maximum allowed update duration in seconds.  If an update would be slower, the time is capped to this value.
     */
    public double getMaximumStepDuration() {
        return maximumStepDurationSeconds;
    }

    /**
     * @param maximumStepDurationSeconds maximum allowed update duration in seconds.
     *                                   If an update would be slower, the time is capped to this value.
     */
    public void setMaximumStepDuration(double maximumStepDurationSeconds) {
        Check.positive(maximumStepDurationSeconds, "maximumStepDurationSeconds");
        this.maximumStepDurationSeconds = maximumStepDurationSeconds;
    }

    @Override protected void doUpdate(Updating simulation, ManualTime localTime, Time externalTime) {

        // Cap the elapsed time
        final double elapsedTime = Math.min(maximumStepDurationSeconds, externalTime.getLastStepDurationSeconds());

        // Update local time
        localTime.advanceTime(elapsedTime);
        localTime.nextStep();

        // Update simulation
        simulation.update(localTime);
    }
}
