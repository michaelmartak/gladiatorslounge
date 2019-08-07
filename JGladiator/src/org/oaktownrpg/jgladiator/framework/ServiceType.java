/**
 * 
 */
package org.oaktownrpg.jgladiator.framework;

/**
 * Defined service types
 * 
 * @author mmartak
 *
 */
public enum ServiceType {

	/**
	 * A service providing sales, purchases, or trades. Generally requires
	 * authentication when performing transactions.
	 */
	MARKETPLACE,
	/**
	 * A service that provides the user with an inventory. Usually requires
	 * authentication, but may not always (e.g., a local database would not require
	 * any authentication).
	 */
	INVENTORY,
	/**
	 * A service that provides a stream of news articles, videos, etc. Can be an RSS
	 * stream. Authentication is typically not required, unless you are the New York
	 * Times.
	 */
	NEWS,
	/**
	 * A service that provides lookup-based information (e.g., search, or
	 * suggestions). Authentication is forbidden, since performance is critical.
	 * Caching and updating is an important aspect of the service.
	 */
	LOOKUP

}
