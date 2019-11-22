/**
 * 
 */
package org.oaktownrpg.jgladiator.framework;

import java.util.Set;

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
	 * Returns the localized name of the service provider (e.g., FOO.com)
	 * 
	 * @return a localized string, never null
	 */
	String getLocalizedName();

	/**
	 * Returns a set of services provided by this provider
	 * 
	 * @return a set of services, never null, presumed to be immutable
	 */
	Set<GladiatorService> getServices();

}
