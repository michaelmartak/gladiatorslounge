/**
 * 
 */
package org.oaktownrpg.jgladiator.framework;

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
	 * @return localization service
	 */
	Localization localization();

	/**
	 * Returns the available service providers
	 * 
	 * @return an iterable, never null
	 */
	Iterable<GladiatorServiceProvider> getServiceProviders();

}
