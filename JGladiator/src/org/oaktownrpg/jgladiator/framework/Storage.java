/**
 * 
 */
package org.oaktownrpg.jgladiator.framework;

import java.util.function.Supplier;

import org.oaktownrpg.jgladiator.framework.ccg.CardSet;

/**
 * Application local storage.
 * 
 * @author michaelmartak
 *
 */
public interface Storage {

    /**
     * Stores credentials in an encrypted local store.
     * <p/>
     * Note that the encrypted file is secure, but not invulnerable to hacking (no
     * differently than a web browser's saved credentials).
     * 
     * @param key        the key name for the encrypted item
     * @param passphrase the item to be encrypted
     */
    void storeCredentials(String key, String passphrase);

    /**
     * Retrieves credentials from the encrypted local store
     * 
     * @param key the key name for the encrypted item
     * @return the decoded item. Never should be used for logging or
     *         display--intended only for immediate use in authentication, then
     *         quickly disposed. Returns a supplier of a String rather than a String
     *         itself, for added reminder and caution.
     */
    Supplier<String> fetchCredentials(String key);

    /**
     * Insert or update the representation of the given CardSet
     * 
     * @param cardSet
     */
    void storeCardSet(CardSet cardSet);

}
