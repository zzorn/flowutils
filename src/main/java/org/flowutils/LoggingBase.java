package org.flowutils;

import org.slf4j.Logger;

import static org.flowutils.Check.notNull;

/**
 * Utility class that can be used as parent class for classes that need to log things.
 */
public abstract class LoggingBase {

    /**
     * Logger class, initialized to the name of this class.
     */
    protected final Logger log = LogUtils.getLogger(this);

    private int indent = 0;

    private String loggingPrefix;

    /**
     * Logs a trace message.
     * @param message describes what the class is doing.
     */
    protected final void logTrace(final String message) {
        logTrace(0, message);
    }

    /**
     * Logs a trace message.
     * @param indent Extra indentation level of the message in addition to existing indent, 0 = none, 1 = one level, etc.
     * @param message describes what the class is doing.
     */
    protected final void logTrace(int indent, final String message) {
        if (log.isTraceEnabled()) {
            log.trace(prepareLogMessage(indent, message));
        }
    }

    /**
     * Logs a debug message.
     * @param message describes what the class is doing.
     */
    protected final void logDebug(final String message) {
        logDebug(0, message);
    }

    /**
     * Logs a debug message.
     * @param indent Extra indentation level of the message in addition to existing indent, 0 = none, 1 = one level, etc.
     * @param message describes what the class is doing.
     */
    protected final void logDebug(int indent, final String message) {
        if (log.isDebugEnabled()) {
            log.debug(prepareLogMessage(indent, message));
        }
    }

    /**
     * Logs an informational message.
     * @param message describes what the class is doing.
     */
    protected final void logInfo(final String message) {
        logInfo(0, message);
    }

    /**
     * Logs an informational message.
     * @param indent Extra indentation level of the message in addition to existing indent, 0 = none, 1 = one level, etc.
     * @param message describes what the class is doing.
     */
    protected final void logInfo(int indent, final String message) {
        if (log.isInfoEnabled()) {
            log.info(prepareLogMessage(indent, message));
        }
    }

    /**
     * Logs a warning message.
     * @param message describes the warning condition.
     */
    protected final void logWarning(final String message) {
        logWarning(0, message);
    }

    /**
     * Logs a warning message.
     * @param indent Extra indentation level of the message in addition to existing indent, 0 = none, 1 = one level, etc.
     * @param message describes the warning condition.
     */
    protected final void logWarning(int indent, final String message) {
        if (log.isWarnEnabled()) {
            log.warn(prepareLogMessage(indent, message));
        }
    }

    /**
     * Logs an error message.
     * @param message describes the error condition.
     */
    protected final void logError(final String message) {
        logError(0, message);
    }

    /**
     * Logs an error message.
     * @param indent Extra indentation level of the message in addition to existing indent, 0 = none, 1 = one level, etc.
     * @param message describes the error condition.
     */
    protected final void logError(int indent, final String message) {
        if (log.isErrorEnabled()) {
            log.error(prepareLogMessage(indent, message));
        }
    }

    /**
     * Increase the indent for subsequent log messages.
     */
    protected final void increaseLoggingIndent() {
        setLoggingIndent(indent + 1);
    }

    /**
     * Decrease the indent for subsequent log messages.
     */
    protected final void decreaseLoggingIndent() {
        setLoggingIndent(indent - 1);
    }

    /**
     * Change the indent for subsequent log messages.
     */
    protected final void changeLoggingIndent(int change) {
        setLoggingIndent(indent + change);
    }

    /**
     * Set the indent for subsequent log messages.
     */
    protected final void setLoggingIndent(int indent) {
        this.indent = indent;
        if (this.indent < 0) this.indent = 0;
    }

    /**
     * @return the current logging indent.
     */
    protected final int getLoggingIndent() {
        return indent;
    }

    /**
     * @return A string that will be prefixed to logging messages.
     */
    protected String getLogPrefix() {
        return loggingPrefix;
    }

    /**
     * @param loggingPrefix A string that will be prefixed to logging messages.
     */
    protected final void setLoggingPrefix(String loggingPrefix) {
        this.loggingPrefix = loggingPrefix;
    }

    /**
     * @param loggingPrefixClass A class whose simple name will be prefixed to logging messages.
     */
    protected final void setLoggingPrefix(Class loggingPrefixClass) {
        notNull(loggingPrefixClass, "loggingPrefixClass");

        this.loggingPrefix = loggingPrefixClass.getSimpleName();
    }

    private String prepareLogMessage(int indent, String message) {
        StringBuilder sb = new StringBuilder();

        // Add indent
        for (int i = 0; i < this.indent + indent; i++) {
            sb.append("    ");
        }

        // Add prefix, if any
        final String logPrefix = getLogPrefix();
        if (logPrefix != null && !logPrefix.isEmpty()) {
            sb.append(logPrefix);
            sb.append(": ");
        }

        // Add message
        sb.append(message);

        return sb.toString();
    }
}
