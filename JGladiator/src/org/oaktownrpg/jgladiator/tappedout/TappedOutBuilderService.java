/**
 * 
 */
package org.oaktownrpg.jgladiator.tappedout;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.function.Consumer;

import org.oaktownrpg.jgladiator.framework.ServiceFailure;
import org.oaktownrpg.jgladiator.framework.helper.AbstractBuilderService;

/**
 * Main builder service for TappedOut.net
 * 
 * @author michaelmartak
 *
 */
final class TappedOutBuilderService extends AbstractBuilderService<TappedOutServiceProvider> {

    private static final URI TAPPED_OUT_URL = URI.create("https://tappedout.net");
    private HttpClient httpClient;

    TappedOutBuilderService(TappedOutServiceProvider provider) {
        super(provider, "tappedout.builder");
    }

    @Override
    public void start(Consumer<ServiceFailure> onFailure, Runnable onReady) {
        httpClient = newHttpClient();
        final HttpRequest request = newHttpRequestGET();
        try {
            HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
            final int statusCode = response.statusCode();
            if (statusCode != 200) {
                throw new InterruptedException("Status " + statusCode);
            }
            String body = response.body();
        } catch (IOException | InterruptedException e) {
            onFailure.accept(new ServiceFailure(hub().localization().string("failure.http"), e)); // FIXME
            return;
        }
        onReady.run();
    }

    private HttpRequest newHttpRequestGET() {
        return HttpRequest.newBuilder(TAPPED_OUT_URL).setHeader("User-Agent", "JGladiator").GET().build();
    }

    private HttpClient newHttpClient() {
        return HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
    }

}
