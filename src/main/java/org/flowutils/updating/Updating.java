package org.flowutils.updating;

import org.flowutils.time.Time;

/**
 * Something that is updated over time.
 */
public interface Updating {

    /**
     * @param time current simulation time.  Also contains the duration of the last time step.
     */
    void update(Time time);

}
