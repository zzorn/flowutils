package org.flowutils.updating;

import org.flowutils.time.Time;
import org.flowutils.updating.strategies.ChainedUpdateStrategy;
import org.flowutils.updating.strategies.UpdateStrategy;

/**
 * An Updating simulation, using a doUpdate method to run the simulation,
 * along with an UpdateStrategy that is used to modify the update time and frequency.
 * Used as a base class for Updating implementations that can have some strategy
 */
public abstract class UpdatingWithStrategy implements Updating {

    private UpdateStrategy updateStrategy;

    private final Updating thisUpdating = new Updating() {
        @Override public void update(Time time) {
            doUpdate(time);
        }
    };

    /**
     * Uses no special strategy, just updates itself directly with the provided time.
     */
    protected UpdatingWithStrategy() {
        this(null);
    }

    /**
     * @param updateStrategy strategy to use when updating, or null to not use any special strategy,
     *                       just update the simulation directly with the provided time.
     */
    protected UpdatingWithStrategy(UpdateStrategy updateStrategy) {
        setUpdateStrategy(updateStrategy);
    }

    /**
     * Create a UpdatingWithStrategy with two or more update strategies, applied in reverse order (last added strategies are executed first).
     */
    protected UpdatingWithStrategy(UpdateStrategy firstUpdateStrategy,
                                UpdateStrategy secondUpdateStrategy,
                                UpdateStrategy... additionalUpdateStrategies) {
        final ChainedUpdateStrategy chainedStrategy = new ChainedUpdateStrategy();
        chainedStrategy.addStrategy(firstUpdateStrategy);
        chainedStrategy.addStrategy(secondUpdateStrategy);
        chainedStrategy.addStrategies(additionalUpdateStrategies);
        setUpdateStrategy(chainedStrategy);
    }

    public final UpdateStrategy getUpdateStrategy() {
        return updateStrategy;
    }

    public final void setUpdateStrategy(UpdateStrategy updateStrategy) {
        this.updateStrategy = updateStrategy;
    }

    @Override public final void update(Time time) {
        if (updateStrategy != null) {
            updateStrategy.update(thisUpdating, time);
        }
        else {
            doUpdate(time);
        }
    }


    protected abstract void doUpdate(Time time);
}
