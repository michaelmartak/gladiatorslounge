/**
 * 
 */
package org.oaktownrpg.jgladiator.scryfall;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.oaktownrpg.jgladiator.framework.BlobType;
import org.oaktownrpg.jgladiator.framework.Http;
import org.oaktownrpg.jgladiator.framework.ServiceFailure;
import org.oaktownrpg.jgladiator.framework.ccg.CardIdentity;
import org.oaktownrpg.jgladiator.framework.ccg.CardIdentityBuilder;
import org.oaktownrpg.jgladiator.framework.ccg.CardSet;
import org.oaktownrpg.jgladiator.framework.ccg.CardSetBuilder;
import org.oaktownrpg.jgladiator.framework.ccg.Ccg;
import org.oaktownrpg.jgladiator.framework.ccg.Gatherer;
import org.oaktownrpg.jgladiator.framework.helper.AbstractLookupService;

import com.sun.istack.logging.Logger;

/**
 * FIXME use API: https://scryfall.com/docs/api
 * 
 * @author michaelmartak
 *
 */
class ScryfallLookupService extends AbstractLookupService<ScryfallServiceProvider> {

    static class ScryfallSetData {

    }

    private static final URI SCRYFALL_SETS_URI = URI.create("https://scryfall.com/sets");

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
    public void gather(Consumer<ServiceFailure> onFailure, final Gatherer gatherer) {
        try {
            gatherSets(gatherer);
        } catch (IOException | InterruptedException e) {
            serviceFailure(onFailure, "failure.scryfall.gather", e);
        }
    }

    private void gatherSets(final Gatherer gatherer) throws IOException, InterruptedException {
        // Start from "sets"
        final Document document = visit(SCRYFALL_SETS_URI);
        final Element main = document.getElementById("main");
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
        String parentId = null;
        for (int i = 0; i < rows.size(); i++) {
            Element row = rows.get(i);
            parentId = visitSet(gatherer, main, row, parentId);
        }
    }

    private Document visit(URI uri) throws IOException, InterruptedException {
        final Http http = hub().http();
        final HttpResponse<String> response = http.get(uri);
        final int statusCode = response.statusCode();
        if (statusCode != 200) {
            throw new IOException("Status " + statusCode);
        }
        final String body = response.body();
        return http.parse(body);
    }

    private String visitSet(final Gatherer gatherer, final Element main, final Element row, final String parentId)
            throws IOException, InterruptedException {
        final Elements cells = row.getElementsByTag("td");
        final Element name = cells.get(0);
        // final Element cardCount = cells.get(1);
        final Element releaseDate = cells.get(2);
        final Element languages = cells.get(3);
        final String id = extractId(name);
        final boolean isIndent = isIndent(name);
        final String parentCardSet = isIndent ? parentId : null;

        final CardSetBuilder builder = new CardSetBuilder().ccg(Ccg.MTG).id(id).symbol().type(BlobType.SVG)
                .bytes(extractCardSymbol(main, name)).name(extractCardSymbolName(name)).source("scryfall").cardSet()
                .releaseDate(extractReleaseDate(releaseDate)).parentCardSet(parentCardSet)
                .languages(extractLanguages(languages)).expansionCode(extractExpansionCode(name));
        final CardSet cardSet = builder.build();
        gatherer.gatherCardSet(cardSet);

        visitSetCards(gatherer, cardSet, extractCardListUri(name));

        if (isIndent) {
            return parentId;
        }
        return id;
    }

    private URI extractCardListUri(Element name) {
        final Elements anchors = name.getElementsByTag("a");
        if (anchors.isEmpty()) {
            return null;
        }
        final Element anchor = anchors.get(0);
        String href = anchor.attr("href");
        return URI.create(href);
    }

    private void visitSetCards(Gatherer gatherer, CardSet cardSet, URI uri) throws IOException, InterruptedException {
        if (uri == null) {
            return;
        }
        final Document document = visit(uri);
        final Element main = document.getElementById("main");
        final Elements cardGridItemCards = main.getElementsByClass("card-grid-item-card");
        final List<CardIdentity> cardIdentities = new ArrayList<>();
        for (Element cardGridItemCard : cardGridItemCards) {
            String href = cardGridItemCard.attr("href");
            CardIdentity identity = visitSetCard(gatherer, cardSet.getId(), URI.create(href));
            if (identity != null) {
                cardIdentities.add(identity);
            }
        }
        // Gather all card identities for this set
        gatherer.gatherCardIdentity(cardSet, cardIdentities);
    }

    private CardIdentity visitSetCard(Gatherer gatherer, String setId, URI uri)
            throws IOException, InterruptedException {
        if (uri == null) {
            return null;
        }
        final Document document = visit(uri);
        final Element main = document.getElementById("main");
        final Elements cardTexts = main.getElementsByClass("card-text");
        if (cardTexts.isEmpty()) {
            logger.severe("Card Text area not found");
        }
        final Element cardText = cardTexts.get(0);
        final String cardId = extractCardId(cardText);
        if (cardId == null) {
            logger.severe("Card ID not found");
            return null;
        }

        final Elements types = cardText.getElementsByClass("card-text-type-line");
        final Elements manaCosts = cardText.getElementsByClass("card-text-mana-cost");

        // Visit the CardIdentity
        final String manaCost = extractManaCost(manaCosts);
        final String altManaCost = extractAltManaCost(manaCosts);

        final CardIdentityBuilder identityBuilder = new CardIdentityBuilder().ccg(Ccg.MTG).cardId(cardId)
                .type(extractType(types)).altType(extractAltType(types)).manaCost(manaCost).altManaCost(altManaCost)
                .cmc(toCmc(manaCost)).altCmc(toCmc(altManaCost));
        return identityBuilder.build();
    }

    private String toCmc(String manaCost) {
        if (manaCost == null) {
            return null;
        }
        if (manaCost.length() < 2) {
            return manaCost;
        }
        manaCost = manaCost.substring(1, manaCost.length() - 1);
        int value = 0;
        String[] tokens = manaCost.split("\\}\\{");
        for (String token : tokens) {
            try {
                int tokenVal = Integer.parseInt(token);
                value += tokenVal;
            } catch (NumberFormatException ex) {
                if ("X".equals(token)) {
                    // Ignored
                } else {
                    value++;
                }
            }
        }
        return Integer.toString(value);
    }

    private String extractAltManaCost(Elements manaCosts) {
        if (manaCosts.size() < 2) {
            return null;
        }
        return extractManaSymbols(manaCosts.get(1));
    }

    private String extractManaCost(Elements manaCosts) {
        if (manaCosts.isEmpty()) {
            return null;
        }
        return extractManaSymbols(manaCosts.get(0));
    }

    private String extractManaSymbols(Element element) {
        final Elements abbr = element.getElementsByTag("abbr");
        StringBuilder sb = new StringBuilder();
        for (Element symbol : abbr) {
            sb.append(symbol.ownText());
        }
        return sb.toString();
    }

    private String extractAltType(Elements types) {
        if (types.size() < 2) {
            return null;
        }
        return types.get(1).ownText();
    }

    private String extractType(Elements types) {
        if (types.isEmpty()) {
            return null;
        }
        return types.get(0).ownText();
    }

    private String extractCardId(Element cardText) {
        final Elements titleElements = cardText.getElementsByClass("card-text-title");
        if (titleElements.isEmpty()) {
            return null;
        }
        return titleElements.get(0).ownText();
    }

    private String extractExpansionCode(Element name) {
        final Elements smalls = name.getElementsByTag("small");
        if (smalls.isEmpty()) {
            return null;
        }
        final Element small = smalls.get(0);
        return small.ownText();
    }

    private Set<String> extractLanguages(Element languages) {
        Set<String> set = new LinkedHashSet<>();
        Elements boxes = languages.getElementsByClass("pillbox-item");
        for (Element box : boxes) {
            Set<String> classNames = box.classNames();
            if (!classNames.contains("disabled")) {
                String text = box.ownText();
                // Chinese locale is expressed / displayed in actual Chinese characters
                if ("汉语".equals(text)) {
                    text = "zhCN";
                } else if ("漢語".equals(text)) {
                    text = "zhTW";
                }
                set.add(text);
            }
        }
        return set;
    }

    private boolean isIndent(Element name) {
        String tdClass = name.attr("class");
        return tdClass.contains("indent");
    }

    private String extractCardSymbolName(Element name) {
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
            logger.warning(e.getMessage());
            return null;
        }
    }

    private byte[] extractCardSymbol(Element main, Element name) {
        final Element use = name.getElementsByTag("use").get(0);
        final String xlink = use.attr("xlink:href");
        final Element svgElement = main.getElementById(xlink.substring(1));
        String svg = svgElement.outerHtml();
        // Scryfall uses one big inline SVG with a bunch of <symbol> elements in it.
        // We save them out as individual svg files.
        if (svg.startsWith("<symbol") && svg.endsWith("symbol>")) {
            svg = "<svg" + svg.substring(7, svg.length() - 7) + "svg>";
        }
        return svg.getBytes();
    }

    private String extractId(Element name) {
        final Element anchor = name.getElementsByTag("a").get(0);
        return anchor.ownText();
    }

}
