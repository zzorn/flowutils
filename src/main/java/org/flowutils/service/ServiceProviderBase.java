package org.flowutils.service;

import java.util.*;

import static org.flowutils.Check.notNull;

/**
 * A service that contains other services.  Can be used as an application base class.
 */
public class ServiceProviderBase extends ServiceBase implements ServiceProvider {

    private final LinkedHashMap<Class<? extends Service>, Service> services = new LinkedHashMap<>();

    /**
     * Will automatically call shutdown for all services that have not yet been shut down when the JVM exits.
     */
    public ServiceProviderBase() {
        this(null);
    }

    /**
     * Will automatically call shutdown for all services that have not yet been shut down when the JVM exits.
     *
     * @param name name of the application, used for debug messages etc.
     */
    public ServiceProviderBase(String name) {
        this(name, true);
    }

    /**
     * @param name name of the application, used for debug messages etc.
     * @param shutDownWhenJVMClosing if true, will call shutdown in a separate concurrent thread when the Java Virtual Machine is closed,
     *                               if shutdown has not already been called.
     *                               The default is true.
     */
    public ServiceProviderBase(String name, boolean shutDownWhenJVMClosing) {
        super(name, null, shutDownWhenJVMClosing);
        setLoggingPrefix(getName());
    }

    @Override public final <T extends Service> T addService(T service) {
        notNull(service, "service");
        final Class<? extends Service> type = service.getClass();

        // Ensure not already added
        if (services.containsKey(type)) throw new IllegalArgumentException("A service of the type " + type + " has already been added!");

        // Add it
        logInfo("Adding service " + service.getName());
        services.put(type, service);

        // Do not automatically shut it down on JVM exit, as this service provider will be automatically shut down on jvm exit and will shut down all added services.
        service.setAutomaticallyShutDownWhenJVMClosing(false);

        // Start service if we are already started
        if (isActive()) {
            increaseLoggingIndent();
            service.init(this);
            decreaseLoggingIndent();
        }

        // Allow extending classes to hook into service addition and removal
        onServiceAdded(service);

        return service;
    }

    /**
     * @return the service of the specified type, or null if none found.
     */
    @Override public final <T extends Service> T getService(Class<T> serviceType) {
        return (T) services.get(serviceType);
    }

    @Override public final Collection<Service> getServices() {
        return Collections.unmodifiableCollection(services.values());
    }

    @Override public final <T extends Service> T removeService(Class<T> serviceType) {
        final T service = (T) services.get(serviceType);
        if (service != null) {
            logInfo("Removing service " + serviceType);
            if (service.isActive()) {
                increaseLoggingIndent();
                service.shutdown();
                decreaseLoggingIndent();
            }

            // Allow extending classes to hook into service addition and removal
            onServiceRemoved(service);
        }
        else {
            logInfo("Can not remove service " + serviceType + ", not found");
        }

        return service;
    }


    @Override protected void doInit(ServiceProvider serviceProvider) {
        increaseLoggingIndent();

        registerServices();

        // Initialize added services
        for (Service service : services.values()) {
            service.init(this);
        }

        decreaseLoggingIndent();
    }

    @Override protected void doShutdown() {
        increaseLoggingIndent();

        // Get services, store them in a temporary list in reverse order
        List<Service> reverseOrderList = new ArrayList<>(services.size());
        for (Service service : services.values()) {
            reverseOrderList.add(0, service);
        }

        // Shutdown services in reverse order
        for (Service service : reverseOrderList) {
            service.shutdown();
        }

        decreaseLoggingIndent();
    }

    /**
     * Called before services are initialized.  Can be used to register services using calls to addService().
     */
    protected void registerServices() {}

    /**
     * Called when a service has been added to this ServiceProvider.
     * @param service the added service
     */
    protected <T extends Service> void onServiceAdded(T service) {
    }

    /**
     * Called when a service has been removed from this ServiceProvider.
     * @param service the removed service
     */
    protected <T extends Service> void onServiceRemoved(T service) {
    }

}
