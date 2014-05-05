package org.flowutils;

import java.lang.management.ManagementFactory;

/**
 * Simple utility class for performance timing etc.
 * Measures CPU time of the current thread.
 */
public final class Stopwatch {

    private static final double NANOSECONDS_TO_SECONDS = (1.0 / 1000000000.0);
    private int lapsToDiscardLeft = 0;
    private int lapsRecorded = 0;
    private long totalTime_ns = 0;
    private long lapStartTime = 0;
    private long timeElapsedWhenPaused = 0;
    private boolean paused = false;

    public Stopwatch() {
        this(0);
    }

    public Stopwatch(int lapsToDiscardLeft) {
        start(lapsToDiscardLeft);
    }

    public void start() {
        start(0);
    }

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

    public double getAverageLapTimeSeconds() {
        if (lapsRecorded <= 0) throw new IllegalStateException("No laps recorded");

        return ((double) (totalTime_ns / lapsRecorded)) * NANOSECONDS_TO_SECONDS;
    }

    public void printResult(String description) {
        System.out.println(description + " took on average " + getAverageLapTimeAsString() + ".");
    }

    private String getAverageLapTimeAsString() {
        double time = getAverageLapTimeSeconds();
        return convertSecondsToReadableString(time);
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
