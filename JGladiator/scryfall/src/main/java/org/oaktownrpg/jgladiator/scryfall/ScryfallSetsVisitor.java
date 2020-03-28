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
import java.util.logging.Logger;

import org.oaktownrpg.jgladiator.framework.Http;
import org.oaktownrpg.jgladiator.framework.Hub;
import org.oaktownrpg.jgladiator.framework.ccg.CardSet;
import org.oaktownrpg.jgladiator.framework.ccg.CardSetType;
import org.oaktownrpg.jgladiator.framework.ccg.Gatherer;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Visits "query all sets" from scryfall.com
 * 
 * @author michaelmartak
 *
 */
class ScryfallSetsVisitor {

    private static final URI BASE_URI = URI.create("https://api.scryfall.com/sets");

    private static final String OBJECT = "object";
    private static final String HAS_MORE = "has_more";
    private static final String NEXT_PAGE = "next_page";
    private static final String DATA = "data";
    private static final String OBJECT_TYPE_LIST = "list";
    private static final String OBJECT_TYPE_SET = "set";
    // Sets fields. See https://scryfall.com/docs/api/sets
    private static final String SET_ID = "id";
    private static final String SET_CODE = "code";
    private static final String MTGO_CODE = "mtgo_code";
    private static final String ARENA_CODE = "arena_code";
    private static final String TCGPLAYER_ID = "tcgplayer_id";
    private static final String SET_NAME = "name";
    private static final String SET_TYPE = "set_type";
    private static final String RELEASED_AT = "released_at";
    private static final String BLOCK_CODE = "block_code";
    private static final String BLOCK = "block";
    private static final String PARENT_SET_CODE = "parent_set_code";
    private static final String CARD_COUNT = "card_count";
    private static final String DIGITAL = "digital";
    private static final String FOIL_ONLY = "foil_only";
    // private static final String SCRYFALL_URI = "scryfall_uri";
    // private static final String SET_URI = "uri";
    private static final String ICON_SVG_URI = "icon_svg_uri";
    private static final String SEARCH_URI = "search_uri";

    private final Http http;
    private final Logger logger = Logger.getLogger(getClass().getName());

    // Current state. Whether we have more pages to parse.
    private boolean hasMore;
    // Current state. URI of the current page.
    private URI uri;

    ScryfallSetsVisitor(Hub hub) {
        http = hub.http();
    }

    public void visit(final Gatherer gatherer) throws IOException, InterruptedException {
        uri = BASE_URI;
        processPage(gatherer);
    }

    void processPage(final Gatherer gatherer) throws IOException, InterruptedException {
        do {
            final HttpResponse<String> response = http.get(uri);
            final int statusCode = response.statusCode();
            if (statusCode != 200) {
                throw new IOException("Status " + statusCode);
            }
            final String body = response.body();

            final ObjectMapper mapper = new ObjectMapper();
            final JsonNode node = mapper.readTree(body);
            processPageNode(gatherer, node);
        } while (hasMore);
    }

    private void processPageNode(final Gatherer gatherer, final JsonNode node) {
        validatePageObjectTypeIsList(node);
        checkMorePages(node);
        processPageData(gatherer, node.get(DATA));
    }

    private void processPageData(final Gatherer gatherer, final JsonNode data) {
        if (data.isArray()) {
            for (JsonNode child : data) {
                processPageData(gatherer, child);
            }
        }
        processSetNode(gatherer, data);
    }

    private void processSetNode(final Gatherer gatherer, final JsonNode setNode) {
        final JsonNode objectTypeNode = setNode.get(OBJECT);
        if (objectTypeNode == null || !OBJECT_TYPE_SET.equals(objectTypeNode.asText())) {
            logger.warning("Node was not a [card] set : " + objectTypeNode);
            return;
        }
        final CardSet cardSet = new CardSet();
        updateCardSet(setNode, SET_ID, (n) -> cardSet.setId(n.asText()));
        updateCardSet(setNode, SET_CODE, (n) -> cardSet.setCode(n.asText()));
        updateCardSet(setNode, MTGO_CODE, (n) -> cardSet.setMtgoCode(n.asText()));
        updateCardSet(setNode, ARENA_CODE, (n) -> cardSet.setArenaCode(n.asText()));
        updateCardSet(setNode, TCGPLAYER_ID, (n) -> cardSet.setTcgPlayerId(n.asInt()));
        updateCardSet(setNode, SET_NAME, (n) -> cardSet.setName(n.asText()));
        updateCardSet(setNode, SET_TYPE, (n) -> cardSet.setType(CardSetType.find(n.asText())));
        updateCardSet(setNode, RELEASED_AT, (n) -> cardSet.setReleaseDate(parseDate(n.asText())));
        updateCardSet(setNode, BLOCK_CODE, (n) -> cardSet.setBlockCode(n.asText()));
        updateCardSet(setNode, BLOCK, (n) -> cardSet.setBlock(n.asText()));
        updateCardSet(setNode, PARENT_SET_CODE, (n) -> cardSet.setParentSetCode(n.asText()));
        updateCardSet(setNode, CARD_COUNT, (n) -> cardSet.setCardCount(n.asInt()));
        updateCardSet(setNode, DIGITAL, (n) -> cardSet.setDigital(n.asBoolean()));
        updateCardSet(setNode, FOIL_ONLY, (n) -> cardSet.setFoilOnly(n.asBoolean()));
        gatherer.gatherCardSet(cardSet);
    }

    private Date parseDate(String text) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return format.parse(text);
        } catch (ParseException ex) {
            logger.warning("Could not parse date : '" + text + "'");
            return null;
        }
    }

    private void updateCardSet(JsonNode setNode, String key, Consumer<JsonNode> update) {
        final JsonNode node = setNode.get(key);
        if (node == null) {
            return;
        }
        update.accept(node);
    }

    private void checkMorePages(JsonNode node) {
        final JsonNode hasMoreNode = node.get(HAS_MORE);
        if (hasMoreNode == null) {
            // Expected "has_more", but if not present, assume none
            return;
        }
        hasMore = hasMoreNode.asBoolean();
        if (hasMore) {
            final JsonNode nextPageNode = node.get(NEXT_PAGE);
            if (nextPageNode == null) {
                logger.warning("Expected a next page, but none found.");
                return;
            }
            uri = URI.create(nextPageNode.asText());
        }
    }

    private void validatePageObjectTypeIsList(JsonNode node) {
        final JsonNode objectTypeNode = node.get(OBJECT);
        if (objectTypeNode == null) {
            logger.warning("No object type found");
            return;
        }
        final String objectType = objectTypeNode.asText();
        if (!OBJECT_TYPE_LIST.equals(objectType)) {
            logger.warning("Expected list, got type : '" + objectType + "'");
        }
    }

}
