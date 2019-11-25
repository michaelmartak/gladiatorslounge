/**
 * 
 */
package org.oaktownrpg.jgladiator.framework.ccg;

/**
 * Identifying set of cards
 * 
 * @author michaelmartak
 *
 */
public interface CardSet {

    /**
     * Identifying string for the set. Names uniquely identify the set within the
     * game. Names are in the language the card game originated from (e.g., MtG =
     * English)
     * 
     * @return a set name, never null
     */
    String name();

}
