package org.flowutils.updating.strategies;

import org.flowutils.time.Time;
import org.flowutils.updating.Updating;

/**
 * Simple UpdateStrategy that simply uses the provided time for updating.
 *
 * If the provided time is e.g. updated every frame, it leads to a variable timestep strategy where the
 * timestep is the duration of the last frame.
 *
 * It is not very suitable for most physics simulations, but will suffice well for non-time sensitive things.
 */
public final class VariableTimestepStrategy extends UpdateStrategyBase {

    @Override protected void doUpdate(Updating simulation, Time externalTime) {
        simulation.update(externalTime);
    }

}
