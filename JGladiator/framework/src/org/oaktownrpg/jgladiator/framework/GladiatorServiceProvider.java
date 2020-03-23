/**
 * 
 */
package org.oaktownrpg.jgladiator.framework;

import java.util.Collection;

/**
 * Provider (endpoint) of multiple services that are typically all connected
 * through a single source or hub.
 * 
 * @author mmartak
 *
 */
public interface GladiatorServiceProvider {

    /**
     * Returns the unique identifier for this service provider. It is the
     * responsibility of the service provider to provide an ID that is presentable
     * enough for debugging purposes and that will not clash with other, similar
     * service providers if viewed together in a list.
     * 
     * @return an identifier string, never null
     */
    String getIdentifier();

    /**
     * Initializes this service provider from the hub.
     * 
     * @param hub
     */
    void initialize(Hub hub);

    /**
     * Returns the class to use to load localized resources for all provided
     * services.
     * 
     * @return a java class; if null, assumes the default bundle
     */
    Class<?> localizedResources();

    /**
     * Returns a collection of services provided by this provider
     * 
     * @return a collection of services, never null, presumed to be immutable
     */
    Collection<GladiatorService> getServices();

}
