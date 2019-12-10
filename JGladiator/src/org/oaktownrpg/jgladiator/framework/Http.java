/**
 * 
 */
package org.oaktownrpg.jgladiator.framework;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import org.jsoup.Connection;
import org.jsoup.nodes.Document;

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

    /**
     * Performs a get request
     * 
     * @param uri the URI to send the request
     * @return an HTTP response
     */
    HttpResponse<String> get(URI uri) throws IOException, InterruptedException;

    /**
     * Creates a new HTML parser connection
     * 
     * @param url the URL, never null
     * @return a connection object
     */
    Connection parserConnection(String url);

    /**
     * Parse the given HTML body
     * 
     * @param html an HTML string
     * @return a parsed HTML document
     */
    Document parse(String html);

}
