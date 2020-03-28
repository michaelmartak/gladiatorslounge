/**
 * 
 */
package org.oaktownrpg.jgladiator.framework.ccg;

/**
 * Type of card set, as defined in
 * <a href="https://scryfall.com/docs/api/sets">Scryfall</a>
 * 
 * @author michaelmartak
 *
 */
public enum CardSetType {
    CORE, EXPANSION, MASTERS, MASTERPIECE, FROM_THE_VAULT, SPELLBOOK, PREMIUM_DECK, DUEL_DECK, DRAFT_INNOVATION,
    TREASURE_CHEST, COMMANDER, PLANECHASE, ARCHENEMY, VANGUARD, FUNNY, STARTER, BOX, PROMO, TOKEN, MEMORABILIA, UNKNOWN;

    /**
     * Safe lookup. Returns {@link #UNKNOWN} if not found.
     * 
     * @param text
     * @return
     */
    public static CardSetType find(String text) {
        try {
            return valueOf(text);
        } catch (Exception ex) {
            return UNKNOWN;
        }
    }
}
