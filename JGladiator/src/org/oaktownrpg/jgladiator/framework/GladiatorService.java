/**
 * 
 */
package org.oaktownrpg.jgladiator.framework;

import java.util.function.Consumer;

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
	 * Returns the localized name of the service
	 * 
	 * @return a localized string, never null
	 */
	String getLocalizedName();

	/**
	 * Called to initialize the service
	 * 
	 * @param onFailure callback to invoke if something went wrong with the service
	 *                  during initialization
	 * @param onReady   callback to invoke when the service is fully initialized
	 */
	void initialize(Consumer<ServiceFailure> onFailure, Runnable onReady);

}
