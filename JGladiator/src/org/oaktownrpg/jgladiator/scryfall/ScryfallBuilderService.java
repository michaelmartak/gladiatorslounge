/**
 * 
 */
package org.oaktownrpg.jgladiator.scryfall;

import java.util.function.Consumer;

import org.oaktownrpg.jgladiator.framework.ServiceFailure;
import org.oaktownrpg.jgladiator.framework.helper.AbstractBuilderService;

/**
 * @author michaelmartak
 *
 */
class ScryfallBuilderService extends AbstractBuilderService<ScryfallServiceProvider> {

    /**
     * 
     */
    ScryfallBuilderService(ScryfallServiceProvider provider) {
        super(provider, "scryfall.builder");
    }

    @Override
    public void start(Consumer<ServiceFailure> onFailure, Runnable onReady) {
        // TODO Auto-generated method stub

    }

}
