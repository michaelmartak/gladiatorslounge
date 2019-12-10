/**
 * 
 */
package org.oaktownrpg.jgladiator.framework;

import java.util.function.Consumer;

import org.oaktownrpg.jgladiator.framework.ccg.Gatherer;

/**
 * A service that provides lookup-based information (e.g., search, images, or
 * suggestions). Authentication is forbidden, since performance is critical.
 * Caching and updating is an important aspect of the service.
 * 
 * @author michaelmartak
 *
 */
public interface LookupService extends ServiceType {

    /**
     * Returns whether this lookup service can be used to gather Ccg information and
     * used as a source of truth for a given Ccg.
     * 
     * @return
     */
    default boolean canGather() {
        return false;
    }

    /**
     * Gather data from the lookup service.
     */
    default void gather(Consumer<ServiceFailure> onFailure, Gatherer gatherer) {
    }

}
