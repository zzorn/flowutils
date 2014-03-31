package org.flowutils.service;

import org.flowutils.LoggingBase;
import org.flowutils.ThreadUtils;

/**
 * Base class that provides general Service lifecycle related functionality.
 */
public abstract class ServiceBase extends LoggingBase implements Service {

    private boolean initialized = false;
    private boolean shutdown = false;

    private boolean shutDownWhenJVMClosing = true;

    protected ServiceBase() {
    }

    /**
     * @param shutDownWhenJVMClosing if true, will call shutdown in a separate concurrent thread when the Java Virtual Machine is closed,
     *                               if shutdown has not already been called.
     *                               The default is true.
     */
    protected ServiceBase(boolean shutDownWhenJVMClosing) {
        setShutDownWhenJVMClosing(shutDownWhenJVMClosing);
    }

    /**
     * @return true if the service will call shutdown in a separate concurrent thread when the Java Virtual Machine is closed,
     *         if shutdown has not already been called.
     *         False if no automatic shutdown is done.
     */
    public boolean isShutDownWhenJVMClosing() {
        return shutDownWhenJVMClosing;
    }

    /**
     * @param shutDownWhenJVMClosing if true, will call shutdown in a separate concurrent thread when the Java Virtual Machine is closed,
     *                               if shutdown has not already been called.
     *                               This method should be called before init(), as init handles registering of the shutdown hook.
     *                               The default is true.
     */
    public void setShutDownWhenJVMClosing(boolean shutDownWhenJVMClosing) {
        if (isInitialized()) throw new IllegalStateException("Must be called before calling init");

        this.shutDownWhenJVMClosing = shutDownWhenJVMClosing;
    }

    @Override public String getServiceName() {
        return getClass().getSimpleName();
    }

    @Override public final void init() {
        // Check that init has not been called before
        if (initialized) {
            throw new IllegalStateException("The Service " + getServiceName() + " has already been initialized, can not initialize again!");
        }

        logInfo(0, "Initializing");

        // Do any service specific initialization
        doInit();

        initialized = true;

        // If desired, registers a thread that will run shutdown when the application exits.
        if (shutDownWhenJVMClosing) {
            logTrace(2, "Registering Shutdown Hook");
            registerShutdownHook();
            logTrace(3, "Shutdown Hook Registered");
        }

        logInfo(1, "Initialized");
    }

    private void registerShutdownHook() {
        // Create thread that calls shutdown on this service when executed
        final Thread shutdownThread = new Thread() {
            @Override public void run() {
                if (!shutdown) {
                    shutdown();
                }
            }
        };

        // Set a clear name for the thread, to ease debugging and clarify logging
        shutdownThread.setName("JVM_ShutdownHook_for_" + getServiceName() );

        // Register the thread to be called before the JVM closes
        Runtime.getRuntime().addShutdownHook(shutdownThread);
    }

    @Override public final void shutdown() {
        if (shutdown) {
            throw new IllegalStateException("The Service " + getServiceName() + " has already been shutdown, can not shutdown again!");
        }

        // Only do the shutdown if we were initialized earlier
        if (initialized) {
            logInfo(0, "Shutting Down");

            doShutdown();

            logInfo(1, "Shut Down");
        }

        shutdown = true;
    }

    @Override public final boolean isActive() {
        return initialized && !shutdown;
    }

    @Override public final boolean isInitialized() {
        return initialized;
    }

    @Override public final boolean isShutdown() {
        return shutdown;
    }

    /**
     * Do the initialization.
     */
    protected abstract void doInit();

    /**
     * Do the shutdown.
     */
    protected abstract void doShutdown();

    /**
     * Utility method that ensures that the service is active, and throws an exception if that is not the case.
     */
    protected final void ensureActive() {
        ensureActive(null);
    }

    /**
     * Utility method that ensures that the service is active, and throws an exception if that is not the case.
     * @param action description of the action we were about to take.  Included in error message if the service is not active.
     */
    protected final void ensureActive(String action) {
        String message = null;

        if (!initialized) {
            if (action == null) action = "invoke " + ThreadUtils.getCallingMethodName();
            message = "Can not " + action + ", the " + getServiceName() +
                      " service has not yet been initialized!  Call init first.";
        }
        else if (shutdown) {
            if (action == null) action = "invoke " + ThreadUtils.getCallingMethodName();
            message = "Can not " + action + ", the " + getServiceName() +
                      " service has already been shut down!";
        }

        if (message != null) {
            logError(0, ": " + message);
            throw new IllegalStateException(message);
        }
    }

    @Override protected final String getLogPrefix() {
        return getServiceName();
    }
}
