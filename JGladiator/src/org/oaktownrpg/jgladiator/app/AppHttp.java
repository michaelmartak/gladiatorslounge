/**
 * 
 */
package org.oaktownrpg.jgladiator.app;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.oaktownrpg.jgladiator.framework.Http;

/**
 * HTTP services
 * 
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

    @Override
    public Connection parserConnection(String url) {
        return Jsoup.connect(url);
    }

    @Override
    public HttpResponse<String> get(URI uri) throws IOException, InterruptedException {
        HttpClient httpClient = newHttpClient();
        final HttpRequest request = HttpRequest.newBuilder(uri).setHeader("User-Agent", "JGladiator").GET().build();
        return httpClient.send(request, BodyHandlers.ofString());
    }

    @Override
    public Document parse(String html) {
        return Jsoup.parse(html);
    }

}
