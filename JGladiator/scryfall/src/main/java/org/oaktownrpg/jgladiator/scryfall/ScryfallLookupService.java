/**
 * 
 */
package org.oaktownrpg.jgladiator.scryfall;

import java.io.IOException;
import java.util.function.Consumer;

import org.oaktownrpg.jgladiator.framework.ServiceFailure;
import org.oaktownrpg.jgladiator.framework.ccg.GatherScope;
import org.oaktownrpg.jgladiator.framework.ccg.Gatherer;
import org.oaktownrpg.jgladiator.framework.helper.AbstractLookupService;

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
        if (scope == null) {
            return;
        }
        try {
            switch (scope) {
            case CARDS_IN_SET: {
                gatherCards(gatherer);
                break;
            }
            case CARD_IMAGES: {
                gatherImages(gatherer);
                break;
            }
            case CARD_PRINTS: {
                gatherPrints(gatherer);
                break;
            }
            case CARD_SETS: {
                gatherSets(gatherer);
                break;
            }
            default:
                return;
            }
        } catch (IOException | InterruptedException e) {
            serviceFailure(onFailure, "failure.scryfall.gather", e);
        }
    }

    private void gatherPrints(Gatherer gatherer) {
        // TODO Auto-generated method stub
        
    }

    private void gatherImages(Gatherer gatherer) {
        // TODO Auto-generated method stub
        
    }

    private void gatherCards(Gatherer gatherer) {
        // TODO Auto-generated method stub
        
    }

    private void gatherSets(final Gatherer gatherer) throws IOException, InterruptedException {
        ScryfallSetsVisitor sets = new ScryfallSetsVisitor(hub());
        sets.visit(gatherer);
    }

}
