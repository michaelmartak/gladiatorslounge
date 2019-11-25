/**
 * 
 */
package org.oaktownrpg.jgladiator.framework;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * Main interaction point for services that are discovered by the Hub.
 * 
 * @author michaelmartak
 *
 */
public interface Services {

    /**
     * Convenience method to iterate through all service providers
     * 
     * @param consumer
     */
    void visitServiceProviders(Consumer<GladiatorServiceProvider> consumer);

    /**
     * Convenience method to iterate through all services by providers
     * 
     * @param consumer
     */
    void visitServices(BiConsumer<GladiatorServiceProvider, GladiatorService> consumer);

}
