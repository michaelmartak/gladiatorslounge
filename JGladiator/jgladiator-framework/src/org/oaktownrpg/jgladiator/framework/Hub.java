/**
 * 
 */
package org.oaktownrpg.jgladiator.framework;

import org.oaktownrpg.jgladiator.framework.ccg.CardIdentityCatalog;
import org.oaktownrpg.jgladiator.framework.ccg.Ccg;
import org.oaktownrpg.jgladiator.framework.ccg.Gatherer;

/**
 * Central Hub that all services communicate with.
 * <p/>
 * In practice, the hub is the only Singleton. There can be multiple of any
 * kind(s) of service. It is assumed that communication with the hub will be
 * handled in an asynchronous, thread-safe fashion.
 * 
 * @author michaelmartak
 *
 */
public interface Hub {

    /**
     * Returns the localization service
     * 
     * @return localization service, never null
     */
    Localization localization();

    /**
     * Returns the discoverable services
     * 
     * @return discoverable services, never null
     */
    Services services();

    /**
     * Returns the executors for concurrency / threading
     * 
     * @return executors, never null
     */
    HubExecutors executors();

    /**
     * Returns the application's local storage service
     * 
     * @return storage, never null
     */
    Storage storage();

    /**
     * Returns the HTTP service
     * 
     * @return http service, never null
     */
    Http http();

    /**
     * Returns the gatherer that will persist card data in the local store
     * 
     * @return a card lookup gatherer object
     */
    Gatherer cardLookup();

    /**
     * Returns the catalog of known cards for the given CCG.
     * 
     * @return a card catalog, never null
     */
    CardIdentityCatalog cardIdentityCatalog(Ccg ccg);

}
