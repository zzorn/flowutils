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

        assertEquals("Assuming smoothing factor is 0.5 by default", 0.5, time.getSmoothingFactor(), EPSILON);

        assertEquals(0, time.getLastStepDurationMs());
        assertEquals(0, time.getLastStepDurationSeconds(), EPSILON);
        assertEquals(0, time.getStepCount());
        assertEquals(0, time.getLastStepsPerSecond(), EPSILON);
        assertEquals(0, time.getMillisecondsSinceStart());
        assertEquals(0, time.getSecondsSinceStart(), EPSILON);
        assertEquals(0, time.getSecondsSinceLastStep(), EPSILON);
        assertEquals(0, time.getSmoothedStepDurationSeconds(), EPSILON);
        assertEquals(0, time.getSmoothedStepsPerSecond(), EPSILON);

        time.advanceTime(100);

        assertEquals(0,   time.getLastStepDurationMs());
        assertEquals(0,   time.getLastStepDurationSeconds(), EPSILON);
        assertEquals(0,   time.getStepCount());
        assertEquals(0,   time.getLastStepsPerSecond(), EPSILON);
        assertEquals(0,   time.getMillisecondsSinceStart());
        assertEquals(0.1, time.getSecondsSinceLastStep(), EPSILON);
        assertEquals(0,   time.getSmoothedStepDurationSeconds(), EPSILON);
        assertEquals(0,   time.getSmoothedStepsPerSecond(), EPSILON);

        time.nextStep();

        assertEquals(100, time.getLastStepDurationMs());
        assertEquals(0.1, time.getLastStepDurationSeconds(), EPSILON);
        assertEquals(1,   time.getStepCount());
        assertEquals(10,  time.getLastStepsPerSecond(), EPSILON);
        assertEquals(100, time.getMillisecondsSinceStart());
        assertEquals(0.0, time.getSecondsSinceLastStep(), EPSILON);
        assertEquals(0.05,time.getSmoothedStepDurationSeconds(), EPSILON);
        assertEquals(20,  time.getSmoothedStepsPerSecond(), EPSILON);


        time.advanceTime(0);
        time.nextStep();

        assertEquals(0,   time.getLastStepDurationMs());
        assertEquals(0,   time.getLastStepDurationSeconds(), EPSILON);
        assertEquals(2,   time.getStepCount());
        assertEquals(0,   time.getLastStepsPerSecond(), EPSILON);
        assertEquals(100, time.getMillisecondsSinceStart());
        assertEquals(0.1, time.getSecondsSinceStart(), EPSILON);
        assertEquals(0.0, time.getSecondsSinceLastStep(), EPSILON);
        assertEquals(0.025,time.getSmoothedStepDurationSeconds(), EPSILON);
        assertEquals(40,   time.getSmoothedStepsPerSecond(), EPSILON);

        time.advanceTime(1000);
        time.nextStep();
        time.advanceTime(300);

        assertEquals(1000,time.getLastStepDurationMs());
        assertEquals(1,   time.getLastStepDurationSeconds(), EPSILON);
        assertEquals(3,   time.getStepCount());
        assertEquals(1,   time.getLastStepsPerSecond(), EPSILON);
        assertEquals(1100, time.getMillisecondsSinceStart());
        assertEquals(1.1, time.getSecondsSinceStart(), EPSILON);
        assertEquals(0.3, time.getSecondsSinceLastStep(), EPSILON);
        assertEquals((1+0.025)/2,time.getSmoothedStepDurationSeconds(), EPSILON);

        time.reset();

        assertEquals(0, time.getLastStepDurationMs());
        assertEquals(0, time.getLastStepDurationSeconds(), EPSILON);
        assertEquals(0, time.getStepCount());
        assertEquals(0, time.getLastStepsPerSecond(), EPSILON);
        assertEquals(0, time.getMillisecondsSinceStart());
        assertEquals(0, time.getSecondsSinceStart(), EPSILON);
        assertEquals(0, time.getSecondsSinceLastStep(), EPSILON);
        assertEquals(0, time.getSmoothedStepDurationSeconds(), EPSILON);
        assertEquals(0, time.getSmoothedStepsPerSecond(), EPSILON);

        time.reset(500, 11);

        assertEquals(0, time.getLastStepDurationMs());
        assertEquals(0, time.getLastStepDurationSeconds(), EPSILON);
        assertEquals(11, time.getStepCount());
        assertEquals(0, time.getLastStepsPerSecond(), EPSILON);
        assertEquals(500, time.getMillisecondsSinceStart());
        assertEquals(0.5, time.getSecondsSinceStart(), EPSILON);
        assertEquals(0, time.getSecondsSinceLastStep(), EPSILON);
        assertEquals(0, time.getSmoothedStepDurationSeconds(), EPSILON);
        assertEquals(0, time.getSmoothedStepsPerSecond(), EPSILON);

    }
}
