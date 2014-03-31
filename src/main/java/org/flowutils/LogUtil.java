package org.flowutils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logging related utilities.
 */
public final class LogUtil {

    public static Logger getLogger() {
        final String simpleNameOfCallingClass = StringUtils.textAfter('.', ThreadUtils.getCallingClassName());
        return LoggerFactory.getLogger(simpleNameOfCallingClass);
    }


    private LogUtil() {
    }
}
