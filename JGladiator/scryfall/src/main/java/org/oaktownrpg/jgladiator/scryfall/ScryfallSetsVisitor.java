/**
 * 
 */
package org.oaktownrpg.jgladiator.scryfall;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.logging.Logger;

import org.oaktownrpg.jgladiator.framework.Http;
import org.oaktownrpg.jgladiator.framework.Hub;
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
    private static final String SCRYFALL_URI = "scryfall_uri";
    private static final String SET_URI = "uri";
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
            processPageNode(node);
        } while (hasMore);
    }

    private void processPageNode(JsonNode node) {
        validatePageObjectTypeIsList(node);
        checkMorePages(node);
        processPageData(node.get(DATA));
    }

    private void processPageData(JsonNode data) {
        if (data.isArray()) {
            for (JsonNode child : data) {
                processPageData(child);
            }
        }
        processSetNode(data);
    }

    private void processSetNode(JsonNode set) {
        final JsonNode objectTypeNode = set.get(OBJECT);
        if (objectTypeNode == null || !OBJECT_TYPE_SET.equals(objectTypeNode.asText())) {
            logger.warning("Node was not a [card] set : " + objectTypeNode);
            return;
        }
        
        System.out.println();
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
