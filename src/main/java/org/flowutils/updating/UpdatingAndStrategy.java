package org.flowutils.updating;

import org.flowutils.time.Time;
import org.flowutils.updating.strategies.UpdateStrategy;

/**
 * An Updating simulation, along with an UpdateStrategy that is used for it.
 */
public final class UpdatingAndStrategy extends UpdatingWithStrategy {

    private Updating simulation;

    /**
     * Uses no special strategy, just updates the simulation directly with the provided time.
     * @param simulation simulation to update
     */
    public UpdatingAndStrategy(Updating simulation) {
        this(simulation, null);
    }

    /**
     * @param simulation simulation to update
     * @param updateStrategy strategy to use when updating, or null to not use any special strategy,
     *                       just update the simulation directly with the provided time.
     */
    public UpdatingAndStrategy(Updating simulation, UpdateStrategy updateStrategy) {
        super(updateStrategy);
        setSimulation(simulation);
    }

    /**
     * Create a UpdatingAndStrategy with two or more update strategies, applied in reverse order (last added strategies are executed first).
     * @param simulation simulation to update
     */
    public UpdatingAndStrategy(Updating simulation,
                               UpdateStrategy firstUpdateStrategy,
                               UpdateStrategy secondUpdateStrategy,
                               UpdateStrategy... additionalUpdateStrategies) {
        super(firstUpdateStrategy, secondUpdateStrategy, additionalUpdateStrategies);

        setSimulation(simulation);
    }

    public final Updating getSimulation() {
        return simulation;
    }

    public final void setSimulation(Updating simulation) {
        this.simulation = simulation;
    }

    @Override protected void doUpdate(Time time) {
        if (simulation != null) {
            simulation.update(time);
        }
    }

}
