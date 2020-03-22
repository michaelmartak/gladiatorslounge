/**
 * 
 */
package org.oaktownrpg.jgladiator.wizards;

import java.util.function.Consumer;

import org.oaktownrpg.jgladiator.framework.ServiceFailure;
import org.oaktownrpg.jgladiator.framework.helper.AbstractLookupService;

/**
 * Wizards of the Coast lookup service.
 * 
 * @author michaelmartak
 *
 */
class WizardsLookupService extends AbstractLookupService<WizardsServiceProvider> {

    /**
     * 
     */
    WizardsLookupService(WizardsServiceProvider provider) {
        super(provider, "wizards.lookup");
    }

    @Override
    public void start(Consumer<ServiceFailure> onFailure, Runnable onReady) {
        // TODO Auto-generated method stub

    }

}
