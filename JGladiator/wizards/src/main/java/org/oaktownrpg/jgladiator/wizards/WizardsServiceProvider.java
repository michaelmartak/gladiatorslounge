/**
 * 
 */
package org.oaktownrpg.jgladiator.wizards;

import org.oaktownrpg.jgladiator.framework.helper.AbstractServiceProvider;

/**
 * @author michaelmartak
 *
 */
public class WizardsServiceProvider extends AbstractServiceProvider {

    private final WizardsLookupService lookup = new WizardsLookupService(this);

    /**
     * 
     */
    public WizardsServiceProvider() {
        super("wizards.com");
        provide(lookup);
    }

}
