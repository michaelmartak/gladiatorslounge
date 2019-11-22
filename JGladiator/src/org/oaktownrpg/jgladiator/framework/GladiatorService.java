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
	 * Returns the localized name of the service
	 * 
	 * @return a localized string, never null
	 */
	String getLocalizedName();

	/**
	 * Returns the type of service
	 * 
	 * @return an enum type, never null
	 */
	ServiceType getType();

	/**
	 * Called to initialize the service. Initialization is any preparation that
	 * should be completed by the service before the service is officially started.
	 * For example, all resources the service needs to be allocated.
	 * 
	 * @param onFailure callback to invoke if something went wrong with the service
	 *                  during initialization
	 * @param onReady   callback to invoke when the service is fully initialized
	 */
	void initialize(Consumer<ServiceFailure> onFailure, Runnable onReady);

	/**
	 * Invoked by the hub when all of the services have been initialized. There is
	 * no guarantee that services will be started in any particular order (it is
	 * assumed that any initialization should be taken care of already). Services
	 * should be ready to send and receive messages.
	 * 
	 * @param hub
	 */
	void start(Hub hub);

}
