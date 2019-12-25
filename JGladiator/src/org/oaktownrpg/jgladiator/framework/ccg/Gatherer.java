/**
 * 
 */
package org.oaktownrpg.jgladiator.framework.ccg;

import java.util.List;

/**
 * Gathers metadata on cards for lookup purposes.
 * <p/>
 * Embodies the Visitor pattern to decouple the service that does the lookup
 * (the caller) from the service that receives and processes the information
 * (the gatherer)
 * 
 * @author michaelmartak
 *
 */
public interface Gatherer {

    /**
     * Called when a card set's information is gathered
     * 
     * @param cardSet
     */
    void gatherCardSet(CardSet cardSet);

    /**
     * Called when card identity information is gathered for a given card set
     * 
     * @param cardSet
     * @param identity a collection of card identity objects
     */
    void gatherCardIdentity(CardSet cardSet, List<CardIdentity> identity);

}
