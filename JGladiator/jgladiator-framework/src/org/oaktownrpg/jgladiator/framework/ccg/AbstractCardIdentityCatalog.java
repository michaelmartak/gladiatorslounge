/**
 * 
 */
package org.oaktownrpg.jgladiator.framework.ccg;

import java.util.HashMap;
import java.util.Map;

/**
 * @author michaelmartak
 *
 */
public abstract class AbstractCardIdentityCatalog implements CardIdentityCatalog {
    
    private final Ccg ccg;
    protected final Map<String, CardIdentity> byId = new HashMap<>();

    /**
     * 
     */
    protected AbstractCardIdentityCatalog(Ccg ccg) {
        this.ccg = ccg;
    }

    @Override
    public final Ccg ccg() {
        return ccg;
    }

    @Override
    public final boolean contains(CardIdentity cardIdentity) {
        return byId.containsKey(cardIdentity.getCardId());
    }

    @Override
    public void update(CardIdentity cardIdentity) {
        byId.put(cardIdentity.getCardId(), cardIdentity);
    }

}
