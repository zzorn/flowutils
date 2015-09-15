package org.flowutils.updating.strategies;

import org.flowutils.time.Time;
import org.flowutils.updating.Updating;

/**
 * Common functionality for UpdateStrategies.
 */
public abstract class UpdateStrategyBase implements UpdateStrategy {

    @Override public void update(Updating simulation, Time externalTime) {
        if (simulation != null) {
            doUpdate(simulation, externalTime);
        }
    }

    protected abstract void doUpdate(Updating simulation, Time externalTime);
}
