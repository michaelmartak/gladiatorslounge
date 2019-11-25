/**
 * 
 */
package org.oaktownrpg.jgladiator.framework.ccg;

/**
 * Card identification information.
 * 
 * @author michaelmartak
 *
 */
public interface CardIdentity {

    /**
     * Identifying string for the card. Names uniquely identify the card within the
     * game. Names are in the language the card game originated from (e.g., MtG =
     * English)
     * 
     * @return a card name, never null
     */
    String name();

}
