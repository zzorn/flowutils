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
    private final Class<? extends Service> serviceType;
    private final String name;
    private ServiceProvider serviceProvider;

    /**
     */
    protected ServiceBase() {
        this(null);
    }

    /**
     * @param name name of the service, used for debug messages etc.
     */
    protected ServiceBase(String name) {
        this(name, null);
    }

    /**
     * @param name name of the service, used for debug messages etc.
     * @param serviceType type of the service, used when accessing services from an application.
     */
    protected ServiceBase(String name, Class<? extends Service> serviceType) {
        this(name, serviceType, true);
    }

    /**
     * @param name name of the service, used for debug messages etc.
     * @param serviceType type of the service, used when accessing services from an application.
     *                    By default the type of the service class.
     * @param shutDownWhenJVMClosing if true, will call shutdown in a separate concurrent thread when the Java Virtual Machine is closed,
     *                               if shutdown has not already been called.
     */
    protected ServiceBase(String name,
                          Class<? extends Service> serviceType,
                          boolean shutDownWhenJVMClosing) {
        if (serviceType == null) serviceType = getClass();
        if (name == null) name = getClass().getSimpleName();

        this.serviceType = serviceType;
        this.name = name;
        setAutomaticallyShutDownWhenJVMClosing(shutDownWhenJVMClosing);
    }

    @Override public final boolean isAutomaticallyShutDownWhenJVMClosing() {
        return shutDownWhenJVMClosing;
    }

    @Override public final void setAutomaticallyShutDownWhenJVMClosing(boolean shutDownWhenJVMClosing) {
        if (isInitialized()) throw new IllegalStateException("Must be called before calling init");

        this.shutDownWhenJVMClosing = shutDownWhenJVMClosing;
    }

    @Override public final String getName() {
        return name;
    }

    @Override public final Class<? extends Service> getType() {
        return serviceType;
    }

    @Override public final void init() {
        init(null);
    }

    @Override public final void init(ServiceProvider serviceProvider) {
        // Check that init has not been called before
        if (initialized) {
            throw new IllegalStateException("The Service " + getName() + " has already been initialized, can not initialize again!");
        }

        logInfo(0, "Initializing");

        setServiceProvider(serviceProvider);

        // Do any service specific initialization
        doInit(serviceProvider);

        initialized = true;

        // If desired, registers a thread that will run shutdown when the application exits.
        if (shutDownWhenJVMClosing) {
            logTrace(2, "Registering Shutdown Hook");
            registerShutdownHook();
            logTrace(3, "Shutdown Hook Registered");
        }

        logTrace(1, "Initialized");
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
        shutdownThread.setName("JVM_ShutdownHook_for_" + getName() );

        // Register the thread to be called before the JVM closes
        Runtime.getRuntime().addShutdownHook(shutdownThread);
    }

    @Override public final void shutdown() {
        if (shutdown) {
            throw new IllegalStateException("The Service " + getName() + " has already been shutdown, can not shutdown again!");
        }

        // Only do the shutdown if we were initialized earlier
        if (initialized) {
            logInfo(0, "Shutting Down");

            doShutdown();

            logTrace(1, "Shut Down");
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
     * @param serviceProvider can be queried for other services.  Not all services have necessarily been initialized yet.
     */
    protected abstract void doInit(ServiceProvider serviceProvider);

    /**
     * Do the shutdown.
     */
    protected abstract void doShutdown();

    /**
     *
     * @return ServiceProvider that can be queried for other services, or null if none provided (or service has not yet been initialized).
     */
    protected final ServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    private void setServiceProvider(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

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
            message = "Can not " + action + ", the " + getName() +
                      " service has not yet been initialized!  Call init first.";
        }
        else if (shutdown) {
            if (action == null) action = "invoke " + ThreadUtils.getCallingMethodName();
            message = "Can not " + action + ", the " + getName() +
                      " service has already been shut down!";
        }

        if (message != null) {
            logError(0, ": " + message);
            throw new IllegalStateException(message);
        }
    }

    @Override protected final String getLogPrefix() {
        return getName();
    }

}
