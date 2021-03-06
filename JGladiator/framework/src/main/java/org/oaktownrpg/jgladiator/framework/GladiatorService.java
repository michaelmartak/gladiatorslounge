/**
 * 
 */
package org.oaktownrpg.jgladiator.framework;

import java.util.function.Consumer;

import org.oaktownrpg.jgladiator.framework.ccg.Ccg;

/**
 * Base (tag) interface for any service in the Gladiator framework.
 * <p/>
 * Services are expected to be thread-safe, as outlined below.
 * 
 * @author mmartak
 *
 */
public interface GladiatorService {

    /**
     * Returns the service provider for this service
     * 
     * @return the service provider, never null
     */
    GladiatorServiceProvider getProvider();

    /**
     * Returns the identifying string uniquely identifying this service. This
     * service should be unique within the context of its service provider (multiple
     * service providers, with different names, can provide services with the same
     * names).
     * 
     * @return a unique identifying string, never null
     */
    String getIdentifier();

    /**
     * Returns the type of service
     * 
     * @return an enum type, never null
     */
    ServiceTypeEnum getType();

    /**
     * Returns the Ccg the service is specific to.
     * 
     * @return the Ccg, never null
     */
    default Ccg getCcg() {
        return Ccg.MTG;
    };

    /**
     * Returns the type-specific functionality of the service
     * 
     * @param <T>
     * @param serviceType the service type, never null
     * @return the service-specific functionality
     * @throws ClassCastException if this service does not provide the given type
     */
    <T extends ServiceType> T asType(Class<T> serviceType);

    /**
     * Called by the hub to initialize the service. Nothing time-intensive should
     * happen during initialization.
     * 
     * @param hub the hub calling the service
     */
    void initialize(Hub hub);

    /**
     * Called by the hub to start the service.
     * 
     * @param onFailure callback to invoke if something went wrong with the service
     *                  during initialization
     * @param onReady   callback to invoke when the service is fully initialized
     */
    void start(Consumer<ServiceFailure> onFailure, Runnable onReady);

}
