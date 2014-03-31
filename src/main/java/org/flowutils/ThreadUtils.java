package org.flowutils;

/**
 * Thread related utilities.
 */
public final class ThreadUtils {

    private static final int TRACE_BACK_OFFSET = 2;

    /**
     * @return name of the method calling the method that called this method.
     */
    public static String getCallingMethodName() {
        return getCallingMethodName(2);
    }

    /**
     * @return name of the class of the method calling the method that called this method.
     */
    public static String getCallingClassName() {
        return getCallingClassName(2);
    }

    /**
     * @param stepsBack how many steps back to look.  1 = method calling the method that called this method.
     * @return name of the method calling the method that called this method if stepsBack is 1,
     *         if 2, the method that called that method, and so on.
     *         Throws exception if stepsBack is larger than the stacktrace.
     */
    public static String getCallingMethodName(int stepsBack) {
        return getCallingStackTraceElement(stepsBack + 1).getMethodName();
    }

    /**
     * @param stepsBack how many steps back to look.  1 = class of method calling the method that called this method.
     * @return the class of the method calling the method that called this method if stepsBack is 1,
     *         if 2, the class of the method that called that method, and so on.
     *         Throws exception if stepsBack is larger than the stacktrace.
     */
    public static String getCallingClassName(int stepsBack) {
        return getCallingStackTraceElement(stepsBack + 1).getClassName();
    }

    /**
     * @param stepsBack how many steps back to look.  1 = stack trace element of method calling the method that called this method.
     * @return the stack trace element of the method calling the method that called this method if stepsBack is 1,
     *         if 2, the method that called that method, and so on.
     *         Throws exception if stepsBack is larger than the stacktrace.
     */
    public static StackTraceElement getCallingStackTraceElement(int stepsBack) {
        Check.positiveOrZero(stepsBack, "stepsBack");

        // Go back to StackTraceElement with method calling this method
        stepsBack += TRACE_BACK_OFFSET;

        // Get trace element
        final StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        if (stepsBack < stackTrace.length) {
            return stackTrace[stepsBack];
        } else {
            throw new IllegalArgumentException("Too many steps back in the call hierarchy requested " +
                                               "("+(stepsBack - TRACE_BACK_OFFSET)+"), " +
                                               "the stack trace length is only "+
                                               (stackTrace.length - TRACE_BACK_OFFSET)+" elements");
        }
    }

    private ThreadUtils() {
    }
}
