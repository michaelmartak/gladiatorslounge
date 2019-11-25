package org.oaktownrpg.jgladiator.framework.ccg;

import java.util.Locale;

/**
 * Represents a single instance of a Card. Is a stand-in for an actual (or
 * virtual) card.
 * 
 * @author michaelmartak
 *
 */
public interface CardSingle {

    /**
     * Card's condition
     * 
     * @return a card's condition. If null, assumed to be UNKNOWN.
     */
    CardCondition condition();

    /**
     * Whether the card is a foil
     * 
     * @return true if a foil
     */
    boolean foil();

    /**
     * Locale for the language of the card
     * 
     * @return the locale, never null.
     */
    Locale language();

    /**
     * The uniquely identifying card name. Should be used to match a card with the
     * {@link CardIdentity}
     * 
     * @return a string, never null
     */
    String name();

}
