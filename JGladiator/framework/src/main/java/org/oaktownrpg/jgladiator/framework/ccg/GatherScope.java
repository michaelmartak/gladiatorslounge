/**
 * 
 */
package org.oaktownrpg.jgladiator.framework.ccg;

/**
 * Predicate used by {@link Gatherer} to describe what kind of information to
 * retrieve.
 * 
 * @author michaelmartak
 *
 */
public enum GatherScope {

    /**
     * Gather available card set metadata (names, etc.) but not individual cards
     * within that set.
     */
    CARD_SETS,

    /**
     * Gather card details within given set(s). Does not include images.
     * <p/>
     * Includes both card identity information (which is the same for the card
     * regardless of what set it's in) as well as details about how the card print
     * is available in the set (e.g., foil only, or varying art within the set).
     */
    CARDS_IN_SET,

    /**
     * Gather card images for specific card(s).
     */
    CARD_IMAGES,

    /**
     * Gather all existing print variants for given card(s), across all sets.
     */
    CARD_PRINTS

}
