/**
 * 
 */
package org.oaktownrpg.jgladiator.edhrec;

import java.util.function.Consumer;

import org.oaktownrpg.jgladiator.framework.ServiceFailure;
import org.oaktownrpg.jgladiator.framework.helper.AbstractIntelligenceService;

/**
 * @author michaelmartak
 *
 */
class EdhrecIntelligenceService extends AbstractIntelligenceService<EdhrecServiceProvider> {

    EdhrecIntelligenceService(EdhrecServiceProvider provider) {
        super(provider, "edhrec.intelligence");
    }

    @Override
    public void start(Consumer<ServiceFailure> onFailure, Runnable onReady) {
        // TODO Auto-generated method stub

    }

}
