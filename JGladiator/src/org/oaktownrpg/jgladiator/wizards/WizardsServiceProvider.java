/**
 * 
 */
package org.oaktownrpg.jgladiator.wizards;

import java.util.Set;

import org.oaktownrpg.jgladiator.framework.GladiatorService;
import org.oaktownrpg.jgladiator.framework.GladiatorServiceProvider;
import org.oaktownrpg.jgladiator.framework.Hub;

/**
 * @author michaelmartak
 *
 */
public class WizardsServiceProvider implements GladiatorServiceProvider {

    private final WizardsLookupService lookup = new WizardsLookupService(this);
    private Hub hub;

    /**
     * 
     */
    public WizardsServiceProvider() {
    }

    @Override
    public String getIdentifier() {
        return "wizards.com";
    }

    @Override
    public void initialize(Hub hub) {
        this.hub = hub;
    }

    @Override
    public String getLocalizedName() {
        return hub.localization().string("wizards");
    }

    @Override
    public Set<GladiatorService> getServices() {
        return Set.of(lookup);
    }

}
