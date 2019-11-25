/**
 * 
 */
package org.oaktownrpg.jgladiator.edhrec;

import org.oaktownrpg.jgladiator.framework.helper.AbstractServiceProvider;

/**
 * @author michaelmartak
 *
 */
public class EdhrecServiceProvider extends AbstractServiceProvider {
    
    private final EdhrecIntelligenceService intelligence = new EdhrecIntelligenceService(this);

    /**
     * 
     */
    public EdhrecServiceProvider() {
        super("edhrec.com");
        provideServices(intelligence);
    }

}
