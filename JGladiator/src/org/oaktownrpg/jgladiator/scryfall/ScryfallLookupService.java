/**
 * 
 */
package org.oaktownrpg.jgladiator.scryfall;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.function.Consumer;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.oaktownrpg.jgladiator.framework.Http;
import org.oaktownrpg.jgladiator.framework.ServiceFailure;
import org.oaktownrpg.jgladiator.framework.ccg.Gatherer;
import org.oaktownrpg.jgladiator.framework.helper.AbstractLookupService;

/**
 * @author michaelmartak
 *
 */
class ScryfallLookupService extends AbstractLookupService<ScryfallServiceProvider> {

    private static final URI SCRYFALL_URI = URI.create("https://scryfall.com/sets");

    /**
     * 
     */
    ScryfallLookupService(ScryfallServiceProvider provider) {
        super(provider, "scryfall.lookup");
    }

    @Override
    public void start(Consumer<ServiceFailure> onFailure, Runnable onReady) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean canGather() {
        return true;
    }

    @Override
    public void gather(Consumer<ServiceFailure> onFailure, final Gatherer gatherer) {
        try {
            gatherSets(gatherer);
        } catch (IOException | InterruptedException e) {
            serviceFailure(onFailure, "failure.scryfall.gather", e);
        }
    }

    private void gatherSets(final Gatherer gatherer) throws IOException, InterruptedException {
        // Start from "sets"
        final Http http = hub().http();
        final HttpResponse<String> response = http.get(SCRYFALL_URI);
        final int statusCode = response.statusCode();
        if (statusCode != 200) {
            throw new IOException("Status " + statusCode);
        }
        final String body = response.body();
        final Document document = http.parse(body);
        // js-checklist is the table with the list of sets.
        Element checklist = document.getElementById("js-checklist");
        // Get the table body (tbody)
        Elements tbodys = checklist.getElementsByTag("tbody");
        if (tbodys.size() > 1) {
            // More than one table body found (error)
            throw new IOException("More than one tbody element");
        }
        Element tbody = tbodys.get(0);
        // Fetch the table rows
        Elements rows = tbody.getElementsByTag("tr");
        for (int i = 0; i < rows.size(); i++) {
            Element row = rows.get(i);
            visitSet(gatherer, row);
        }
    }

    private void visitSet(final Gatherer gatherer, Element row) {
        final Elements cells = row.getElementsByTag("td");
        for (int i = 0; i < cells.size(); i++) {

        }
    }

}
