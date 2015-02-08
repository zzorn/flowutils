package org.flowutils.service;

import java.util.Collection;

/**
 * Used to get services.
 */
public interface ServiceProvider extends Service {

    /**
     * @return the service of the specified type, or null if not available.
     */
    <T extends Service> T getService(Class<T> serviceType);

    /**
     * @return read only list with all services available.
     */
    Collection<Service> getServices();

    /**
     * Adds a service to this application.
     * If there is already a service of the same type, an exception is thrown.
     * @param service service to add.  If this application has already been initialized, the newly added service will also be initialized.
     * @return the added service, for chaining etc.
     */
    <T extends Service> T addService(T service);

    /**
     * Removes the specified service type.
     * @return removed service, or null if none found
     */
    <T extends Service> T removeService(Class<T> serviceType);
}
