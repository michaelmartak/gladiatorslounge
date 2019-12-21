/**
 * 
 */
package org.oaktownrpg.jgladiator.scryfall;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Consumer;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.oaktownrpg.jgladiator.framework.BlobType;
import org.oaktownrpg.jgladiator.framework.Http;
import org.oaktownrpg.jgladiator.framework.ServiceFailure;
import org.oaktownrpg.jgladiator.framework.ccg.CardSetBuilder;
import org.oaktownrpg.jgladiator.framework.ccg.Ccg;
import org.oaktownrpg.jgladiator.framework.ccg.Gatherer;
import org.oaktownrpg.jgladiator.framework.helper.AbstractLookupService;

import com.sun.istack.logging.Logger;

/**
 * @author michaelmartak
 *
 */
class ScryfallLookupService extends AbstractLookupService<ScryfallServiceProvider> {

    static class ScryfallSetData {

    }

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
            visitSet(gatherer, document, row);
        }
    }

    private void visitSet(final Gatherer gatherer, final Document document, final Element row) {
        final Elements cells = row.getElementsByTag("td");
        final Element name = cells.get(0);
        final Element cardCount = cells.get(1);
        final Element releaseDate = cells.get(2);
        final Element languages = cells.get(3);
        CardSetBuilder builder = new CardSetBuilder().ccg(Ccg.MTG).symbolType(BlobType.SVG).id(extractId(name))
                .symbolBytes(extractCardSymbol(document, name)).symbolName(extractCardSymbolName(document, name))
                .releaseDate(extractReleaseDate(releaseDate));
        gatherer.gatherCardSet(builder.build());
    }

    private String extractCardSymbolName(Document document, Element name) {
        final Element use = name.getElementsByTag("use").get(0);
        final String xlink = use.attr("xlink:href");
        return xlink.substring(1);
    }

    private Date extractReleaseDate(Element releaseDate) {
        final Element anchor = releaseDate.getElementsByTag("a").get(0);
        final String dateString = anchor.ownText();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = format.parse(dateString);
            return date;
        } catch (ParseException e) {
            Logger.getLogger(getClass()).warning(e.getMessage());
            return null;
        }
    }

    private byte[] extractCardSymbol(Document document, Element name) {
        final Element use = name.getElementsByTag("use").get(0);
        final String xlink = use.attr("xlink:href");
        final String svg = document.getElementById(xlink.substring(1)).outerHtml();
        return svg.getBytes();
    }

    private String extractId(Element name) {
        final Element anchor = name.getElementsByTag("a").get(0);
        return anchor.ownText();
    }

}
