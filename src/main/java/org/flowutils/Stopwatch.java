package org.flowutils;

import java.lang.management.ManagementFactory;

/**
 * Simple utility class for performance timing etc.
 * Measures CPU time of the current thread.
 */
public final class Stopwatch {

    private static final double NANOSECONDS_TO_SECONDS = (1.0 / 1000000000.0);

    private String description;
    private final int lapsToDiscard;
    private int lapsToDiscardLeft = 0;
    private int lapsRecorded = 0;
    private long totalTime_ns = 0;
    private long lapStartTime = 0;
    private long timeElapsedWhenPaused = 0;
    private boolean paused = false;

    /**
     * Creates a new stopwatch and starts it.
     */
    public Stopwatch() {
        this(null, 0);
    }

    /**
     * Creates a new stopwatch and starts it.
     *
     * @param description human readable description for what we are measuring.
     */
    public Stopwatch(String description) {
        this(description, 0);
    }

    /**
     * Creates a new stopwatch and starts it.
     *
     * @param numberOfInitialLapsToDiscard this number of initial measured laps will be discarded.
     *                                     Can be used to ignore the initial rounds of some testing while the JVM warms up.
     * @param description human readable description for what we are measuring.
     */
    public Stopwatch(String description, int numberOfInitialLapsToDiscard) {
        this.description = description;
        this.lapsToDiscard = numberOfInitialLapsToDiscard;
        start(numberOfInitialLapsToDiscard);
    }

    /**
     * Starts the stopwatch from zero, clearing any previous results and laps.
     * Will discard the same number of initial laps as specified in the constructor.
     */
    public void start() {
        start(lapsToDiscard);
    }

    /**
     * Starts the stopwatch, clearing any previous results.
     *
     * @param numberOfInitialLapsToDiscard this number of initial measured laps will be discarded.
     *                                     Can be used to ignore the initial rounds of some testing while the JVM warms up.
     */
    public void start(int numberOfInitialLapsToDiscard) {
        lapsToDiscardLeft = numberOfInitialLapsToDiscard;
        lapsRecorded = 0;
        totalTime_ns = 0;
        startLap(getCurrentTime_ns());
    }

    /**
     * Registers a lap and updates the average lap time
     * (or discards this lap time if we still have some laps left to discard before the JVM has warmed up enough).
     */
    public void lap() {
        long now = getCurrentTime_ns();

        if (lapsToDiscardLeft > 0) {
            // Discard lap
            lapsToDiscardLeft--;
        }
        else {
            // Measure lap
            totalTime_ns += getElapsedLapTime_ns(now);
            lapsRecorded ++;
        }

        // Start a new lap
        startLap(now);
    }

    private void startLap(long now) {
        if (paused) {
            timeElapsedWhenPaused = 0;
        }
        else {
            lapStartTime = now;
        }
    }

    /**
     * Temporarily stop measuring elapsed time, resume with resume().
     * Ignored if already paused.
     */
    public void pause() {
        if (!paused) {
            timeElapsedWhenPaused = getElapsedLapTime_ns(getCurrentTime_ns());
            paused = true;
        }
    }

    /**
     * Resume if we are paused, otherwise do nothing.
     */
    public void resume() {
        if (paused) {
            setElapsedTime(timeElapsedWhenPaused);
            paused = false;
        }
    }

    /**
     * @return time elapsed so far on this lap, as a human readable string including a time unit.
     */
    public String getElapsedLapTime() {
        return convertSecondsToReadableString(getElapsedLapTimeSeconds());
    }

    /**
     * @return time elapsed so far on this lap, in seconds.
     */
    public double getElapsedLapTimeSeconds() {
        return getElapsedLapTime_ns(getCurrentTime_ns()) * NANOSECONDS_TO_SECONDS;
    }

    private long getElapsedLapTime_ns(long now) {
        if (paused) {
            return timeElapsedWhenPaused;
        }
        else {
            return now - lapStartTime;
        }
    }

    private void setElapsedTime(long elapsedTime) {
        lapStartTime = getCurrentTime_ns() - elapsedTime;
    }

    private long getCurrentTime_ns() {
        return ManagementFactory.getThreadMXBean().getCurrentThreadCpuTime();
    }

    /**
     * @return average lap time in seconds for the laps that were not discarded.
     */
    public double getAverageLapTimeSeconds() {
        if (lapsRecorded <= 0) throw new IllegalStateException("No laps recorded");

        return ((double) (totalTime_ns / lapsRecorded)) * NANOSECONDS_TO_SECONDS;
    }

    /**
     * Returns the measured average lap time as a human readable string.
     */
    public String getResult() {
        if (description != null) {
            return description + " took on average " + getAverageLapTimeAsString() + ".";
        }
        else {
            return "Average time " + getAverageLapTimeAsString() + ".";
        }
    }

    /**
     * Prints the measured average lap time to system out.
     */
    public void printResult() {
        System.out.println(getResult());
    }

    /**
     * @return human readable description of what we are measuring.
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description human readable description of what we are measuring.
     */
    public void setDescription(String description) {
        this.description = description;
    }

    private String getAverageLapTimeAsString() {
        return convertSecondsToReadableString(getAverageLapTimeSeconds());
    }

    private String convertSecondsToReadableString(double seconds) {
        if (seconds > 60*60) return "" + (seconds / (60*60)) + " hours";
        if (seconds > 60) return "" + (seconds / 60) + " minutes";
        else if (seconds >= 1) return "" + seconds + " seconds";
        else if (seconds >= 0.001) return "" + (seconds * 1000) + " milliseconds";
        else if (seconds >= 0.0000001) return "" + (seconds * 1000000) + " microseconds";
        else return "" + (seconds * 1000000000) + " nanoseconds";
    }


}
