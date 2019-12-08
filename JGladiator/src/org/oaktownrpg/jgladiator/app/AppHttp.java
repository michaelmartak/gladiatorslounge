/**
 * 
 */
package org.oaktownrpg.jgladiator.app;

import java.net.http.HttpClient;

import org.oaktownrpg.jgladiator.framework.Http;

/**
 * HTTP services
 * @author michaelmartak
 *
 */
class AppHttp implements Http {

    /**
     * 
     */
    AppHttp() {
    }

    @Override
    public HttpClient newHttpClient() {
        return HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
    }

}
