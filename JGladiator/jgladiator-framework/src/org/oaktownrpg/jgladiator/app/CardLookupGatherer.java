/**
 * 
 */
package org.oaktownrpg.jgladiator.app;

import java.util.ArrayList;
import java.util.List;

import org.oaktownrpg.jgladiator.framework.ccg.CardIdentity;
import org.oaktownrpg.jgladiator.framework.ccg.CardIdentityCatalog;
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
    public void gatherCardIdentity(CardSet cardSet, List<CardIdentity> identity) {
        final CardIdentityCatalog catalog = hub.cardIdentityCatalog(cardSet.getCcg());
        final ArrayList<CardIdentity> toUpdate = new ArrayList<>(identity.size());
        for (CardIdentity card : identity) {
            // Only update cards that are not yet in the catalog
            if (!catalog.contains(card)) {
                toUpdate.add(card);
                catalog.update(card);
            }
        }
        if (!toUpdate.isEmpty()) {
            hub.storage().storeCardIdentity(toUpdate);
        }
    }

}
