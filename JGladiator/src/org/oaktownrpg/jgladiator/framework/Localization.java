/**
 * 
 */
package org.oaktownrpg.jgladiator.framework;

/**
 * Handles localization of strings from the hub
 * 
 * @author michaelmartak
 *
 */
public interface Localization {

	/**
	 * Returns a localized string from the given key
	 * 
	 * @param key
	 * @return a localized string, or an empty string if not found
	 */
	String string(String key);

}
