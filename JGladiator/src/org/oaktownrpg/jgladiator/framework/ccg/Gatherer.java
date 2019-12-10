/**
 * 
 */
package org.oaktownrpg.jgladiator.framework.ccg;

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

}
