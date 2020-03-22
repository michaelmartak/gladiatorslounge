/**
 * 
 */
package org.oaktownrpg.jgladiator.app.mtg;

/**
 * Legality of a card in a given format.
 * 
 * @author michaelmartak
 *
 */
public enum MtgFormatLegality {

    /**
     * Card is legal for play in this format.
     */
    LEGAL,

    /**
     * Card is not legal for play in this format.
     * <p/>
     * In contrast to being banned, some cards are either not intended for legal
     * play, or are legal as of a specific date or set.
     */
    NOT_LEGAL,

    /**
     * Card is specifically banned for play in this format.
     */
    BANNED,

    /**
     * Card is restricted for play in this format.
     */
    RESTRICTED

}
