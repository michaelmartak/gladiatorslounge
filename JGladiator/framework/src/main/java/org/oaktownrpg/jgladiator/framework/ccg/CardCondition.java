/**
 * 
 */
package org.oaktownrpg.jgladiator.framework.ccg;

import java.util.Locale;

/**
 * Array of card conditions.
 * <p/>
 * Examples of card condition descriptions can be found at TCGPlayer, among
 * other places: <a href=
 * "https://help.tcgplayer.com/hc/en-us/articles/221430307-How-can-I-tell-what-condition-a-card-is-in-">link</a>
 * 
 * @author michaelmartak
 *
 */
public enum CardCondition {

    /**
     * Card condition is unknown. Generally the default. If a condition is
     * <code>null</code>, it is also considered UNKNOWN.
     */
    UNKNOWN,
    /**
     * Near mint, also NM
     */
    NEAR_MINT,
    /**
     * Lightly played, also LP
     */
    LIGHTLY_PLAYED,
    /**
     * Moderately played, also MP
     */
    MODERATELY_PLAYED,
    /**
     * Heavily played, also HP
     */
    HEAVILY_PLAYED,
    /**
     * Damaged, or DM
     */
    DAMAGED;

    /**
     * Lenient, fault-tolerant version of {@link #valueOf(String)}. Processes only
     * US English.
     * <p/>
     * Note that condition descriptions or categories might vary from site to site.
     * 
     * @param str the string value
     * @return a condition, never null
     */
    public static CardCondition value(String str) {
        if (str == null) {
            return UNKNOWN;
        }
        str = str.trim().toUpperCase(Locale.US);
        if (str.startsWith("N")) {
            return NEAR_MINT;
        }
        if (str.startsWith("L")) {
            return LIGHTLY_PLAYED;
        }
        if (str.startsWith("M")) {
            return MODERATELY_PLAYED;
        }
        if (str.startsWith("H")) {
            return HEAVILY_PLAYED;
        }
        if (str.startsWith("D")) {
            return DAMAGED;
        }
        return UNKNOWN;
    }

}
