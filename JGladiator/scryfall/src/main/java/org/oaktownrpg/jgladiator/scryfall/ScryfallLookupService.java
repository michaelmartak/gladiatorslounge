/**
 * 
 */
package org.oaktownrpg.jgladiator.scryfall;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.function.Consumer;

import org.oaktownrpg.jgladiator.framework.Http;
import org.oaktownrpg.jgladiator.framework.ServiceFailure;
import org.oaktownrpg.jgladiator.framework.ccg.GatherScope;
import org.oaktownrpg.jgladiator.framework.ccg.Gatherer;
import org.oaktownrpg.jgladiator.framework.helper.AbstractLookupService;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.sun.istack.logging.Logger;

/**
 * FIXME use API: https://scryfall.com/docs/api
 * <p/>
 * Cookbook examples:
 * <ul>
 * <li>Sets : https://api.scryfall.com/sets</li>
 * <li>Single set info ("mb1") : https://api.scryfall.com/sets/mb1</li>
 * <li>Cards in set (search_uri) :
 * https://api.scryfall.com/cards/search?order=set&q=e%3Amb1&unique=prints</li>
 * </ul>
 * Use Jackson : http://tutorials.jenkov.com/java-json/jackson-jsonparser.html
 * 
 * @author michaelmartak
 *
 */
class ScryfallLookupService extends AbstractLookupService<ScryfallServiceProvider> {

    static class ScryfallSetData {

    }

    private static final URI SCRYFALL_SETS_URI = URI.create("https://api.scryfall.com/sets");

    private Logger logger = Logger.getLogger(getClass());

    /**
     * 
     */
    ScryfallLookupService(ScryfallServiceProvider provider) {
        super(provider, "scryfall.lookup");
    }

    @Override
    public void start(Consumer<ServiceFailure> onFailure, Runnable onReady) {
        onReady.run();
    }

    @Override
    public boolean canGather() {
        return true;
    }

    @Override
    public void gather(final Gatherer gatherer, final GatherScope scope, final String pattern,
            final Consumer<ServiceFailure> onFailure) {
        try {
            gatherSets(gatherer);
        } catch (IOException | InterruptedException e) {
            serviceFailure(onFailure, "failure.scryfall.gather", e);
        }
    }

    private void gatherSets(final Gatherer gatherer) throws IOException, InterruptedException {
        final Http http = hub().http();
        // Start from "sets"
        final HttpResponse<String> response = http.get(SCRYFALL_SETS_URI);
        final int statusCode = response.statusCode();
        if (statusCode != 200) {
            throw new IOException("Status " + statusCode);
        }
        final String body = response.body();
        JsonFactory factory = new JsonFactory();
        JsonParser parser = factory.createParser(body);
        while (!parser.isClosed()) {
            JsonToken jsonToken = parser.nextToken();
            System.out.println("jsonToken = " + jsonToken);
        }
    }

}
