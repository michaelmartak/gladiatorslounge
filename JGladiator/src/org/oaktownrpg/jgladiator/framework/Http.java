/**
 * 
 */
package org.oaktownrpg.jgladiator.framework;

import java.net.http.HttpClient;

/**
 * Central service for managing HTTP.
 * 
 * @author michaelmartak
 *
 */
public interface Http {

    /**
     * Creates a new HTTP client
     * 
     * @return an http client object, never null
     */
    HttpClient newHttpClient();

}
