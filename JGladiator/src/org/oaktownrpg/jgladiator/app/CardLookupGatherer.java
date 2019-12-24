/**
 * 
 */
package org.oaktownrpg.jgladiator.app;

import java.util.List;

import org.oaktownrpg.jgladiator.framework.ccg.CardIdentity;
import org.oaktownrpg.jgladiator.framework.ccg.CardSet;
import org.oaktownrpg.jgladiator.framework.ccg.Gatherer;

/**
 * @author michaelmartak
 *
 */
class CardLookupGatherer implements Gatherer {

    private final JGladiator hub;

    /**
     * @param jGladiator
     * 
     */
    CardLookupGatherer(JGladiator hub) {
        this.hub = hub;
    }

    @Override
    public void gatherCardSet(CardSet cardSet) {
        hub.storage().storeCardSet(cardSet);
    }

    @Override
    public void gatherCardIdentity(List<CardIdentity> identity) {
        hub.storage().storeCardIdentity(identity);
    }

}
