package org.flowutils;

import org.flowutils.time.ManualTime;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test Time class.
 */
public class TimeTest {

    private static final double EPSILON = 0.001;

    @Test
    public void testTime() throws Exception {
        ManualTime time = new ManualTime();

        assertEquals(0, time.getLastStepDurationMilliseconds());
        assertEquals(0, time.getLastStepDurationSeconds(), EPSILON);
        assertEquals(0, time.getStepCount());
        assertEquals(0, time.getStepsPerSecond(), EPSILON);
        assertEquals(0, time.getMillisecondsSinceStart());
        assertEquals(0, time.getSecondsSinceStart(), EPSILON);
        assertEquals(0, time.getSecondsSinceLastStep(), EPSILON);

        time.advanceTime(0.1);

        assertEquals(0,   time.getLastStepDurationMilliseconds());
        assertEquals(0,   time.getLastStepDurationSeconds(), EPSILON);
        assertEquals(0,   time.getStepCount());
        assertEquals(0,   time.getStepsPerSecond(), EPSILON);
        assertEquals(0,   time.getMillisecondsSinceStart());
        assertEquals(0.1, time.getSecondsSinceLastStep(), EPSILON);

        time.nextStep();

        assertEquals(100, time.getLastStepDurationMilliseconds());
        assertEquals(0.1, time.getLastStepDurationSeconds(), EPSILON);
        assertEquals(1,   time.getStepCount());
        assertEquals(10,  time.getStepsPerSecond(), EPSILON);
        assertEquals(100, time.getMillisecondsSinceStart());
        assertEquals(0.0, time.getSecondsSinceLastStep(), EPSILON);


        time.advanceTime(0.0);
        time.nextStep();

        assertEquals(0,   time.getLastStepDurationMilliseconds());
        assertEquals(0,   time.getLastStepDurationSeconds(), EPSILON);
        assertEquals(2,   time.getStepCount());
        assertEquals(0,   time.getStepsPerSecond(), EPSILON);
        assertEquals(100, time.getMillisecondsSinceStart());
        assertEquals(0.1, time.getSecondsSinceStart(), EPSILON);
        assertEquals(0.0, time.getSecondsSinceLastStep(), EPSILON);

        time.advanceTime(1.0);
        time.nextStep();
        time.advanceTime(0.3);

        assertEquals(1000,time.getLastStepDurationMilliseconds());
        assertEquals(1,   time.getLastStepDurationSeconds(), EPSILON);
        assertEquals(3,   time.getStepCount());
        assertEquals(1,   time.getStepsPerSecond(), EPSILON);
        assertEquals(1100, time.getMillisecondsSinceStart());
        assertEquals(1.1, time.getSecondsSinceStart(), EPSILON);
        assertEquals(0.3, time.getSecondsSinceLastStep(), EPSILON);

        time.reset();

        assertEquals(0, time.getLastStepDurationMilliseconds());
        assertEquals(0, time.getLastStepDurationSeconds(), EPSILON);
        assertEquals(0, time.getStepCount());
        assertEquals(0, time.getStepsPerSecond(), EPSILON);
        assertEquals(0, time.getMillisecondsSinceStart());
        assertEquals(0, time.getSecondsSinceStart(), EPSILON);
        assertEquals(0, time.getSecondsSinceLastStep(), EPSILON);

        time.reset(0.5, 11);

        assertEquals(0, time.getLastStepDurationMilliseconds());
        assertEquals(0, time.getLastStepDurationSeconds(), EPSILON);
        assertEquals(11, time.getStepCount());
        assertEquals(0, time.getStepsPerSecond(), EPSILON);
        assertEquals(500, time.getMillisecondsSinceStart());
        assertEquals(0.5, time.getSecondsSinceStart(), EPSILON);
        assertEquals(0, time.getSecondsSinceLastStep(), EPSILON);
    }
}
