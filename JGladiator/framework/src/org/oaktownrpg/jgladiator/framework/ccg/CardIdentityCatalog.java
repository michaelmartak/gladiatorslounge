/**
 * 
 */
package org.oaktownrpg.jgladiator.framework.ccg;

/**
 * Catalog of known cards.
 * 
 * @author michaelmartak
 *
 */
public interface CardIdentityCatalog {
    
    Ccg ccg();

    boolean contains(CardIdentity cardIdentity);

    void update(CardIdentity cardIdentity);

}
