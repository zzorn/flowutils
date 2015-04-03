package org.flowutils;

/**
 * Time related utilities.
 */
public final class TimeUtils {

    /**
     * Sleeps the current thread for approximately the specified number of milliseconds.
     *
     * @param milliseconds milliseconds to sleep.  If zero or negative, returns immediately.
     */
    public static void delay(long milliseconds) {
        if (milliseconds > 0) {
            try {
                Thread.sleep(milliseconds);
            } catch (InterruptedException e) {
                // Ignore
            }
        }
    }

    /**
     * Sleeps the current thread for approximately the specified number of seconds.
     *
     * @param seconds seconds to sleep.  If zero or negative, returns immediately.
     */
    public static void delaySeconds(double seconds) {
        if (seconds > 0) {
            try {
                Thread.sleep((long) (1000 * seconds));
            } catch (InterruptedException e) {
                // Ignore
            }
        }
    }


    private TimeUtils() {
    }
}
