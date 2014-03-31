package org.flowutils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logging related utilities.
 */
public final class LogUtils {

    /**
     * @return logger named after the calling class.
     *         Note that this does not handle descendant classes, the logger will be named after the class that it is called in.
     *         Use getLogger(this) instead if you will subclass the class that you create a logger in.
     */
    public static Logger getLogger() {
        return LoggerFactory.getLogger(ThreadUtils.getCallingClassName());
    }

    /**
     * @return logger named after the class of the specified object.
     */
    public static Logger getLogger(Object objectToLogFor) {
        return LoggerFactory.getLogger(objectToLogFor.getClass());
    }

    /**
     * @return logger named after the specified class.
     */
    public static Logger getLoggerForClass(Class classToLog) {
        return LoggerFactory.getLogger(classToLog);
    }


    private LogUtils() {
    }
}
