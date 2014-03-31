package org.flowutils;

import org.slf4j.Logger;

/**
 * Utility class that can be used as parent class for classes that need to log things.
 */
public abstract class LoggingBase {

    /**
     * Logger class, initialized to the name of this class.
     */
    protected final Logger log = LogUtils.getLogger(this);


    /**
     * Logs a trace message.
     * @param indent Indentation level of the message, 0 = none, 1 = one level, etc.
     * @param message describes what the class is doing.
     */
    protected final void logTrace(int indent, final String message) {
        if (log.isTraceEnabled()) {
            log.trace(prepareLogMessage(indent, message));
        }
    }

    /**
     * Logs a debug message.
     * @param indent Indentation level of the message, 0 = none, 1 = one level, etc.
     * @param message describes what the class is doing.
     */
    protected final void logDebug(int indent, final String message) {
        if (log.isDebugEnabled()) {
            log.debug(prepareLogMessage(indent, message));
        }
    }

    /**
     * Logs an informational message.
     * @param indent Indentation level of the message, 0 = none, 1 = one level, etc.
     * @param message describes what the class is doing.
     */
    protected final void logInfo(int indent, final String message) {
        if (log.isInfoEnabled()) {
            log.info(prepareLogMessage(indent, message));
        }
    }

    /**
     * Logs a warning message.
     * @param indent Indentation level of the message, 0 = none, 1 = one level, etc.
     * @param message describes the warning condition.
     */
    protected final void logWarning(int indent, final String message) {
        if (log.isWarnEnabled()) {
            log.warn(prepareLogMessage(indent, message));
        }
    }

    /**
     * Logs an error message.
     * @param indent Indentation level of the message, 0 = none, 1 = one level, etc.
     * @param message describes the error condition.
     */
    protected final void logError(int indent, final String message) {
        if (log.isErrorEnabled()) {
            log.error(prepareLogMessage(indent, message));
        }
    }

    private String prepareLogMessage(int indent, String message) {
        StringBuilder sb = new StringBuilder();

        // Add indent
        for (int i = 0; i < indent; i++) {
            sb.append("    ");
        }

        // Add prefix, if any
        final String logPrefix = getLogPrefix();
        sb.append(logPrefix);

        if (!logPrefix.isEmpty()) {
            sb.append(" ");
        }

        // Add message
        sb.append(message);

        return sb.toString();
    }

    /**
     * @return A string that will be prefixed to logging messages.
     */
    protected String getLogPrefix() {
        return "";
    }

}
