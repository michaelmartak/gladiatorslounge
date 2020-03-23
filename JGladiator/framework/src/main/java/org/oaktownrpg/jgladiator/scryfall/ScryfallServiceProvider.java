/**
 * 
 */
package org.oaktownrpg.jgladiator.scryfall;

import org.oaktownrpg.jgladiator.framework.helper.AbstractServiceProvider;

/**
 * @author michaelmartak
 *
 */
public class ScryfallServiceProvider extends AbstractServiceProvider {

    private final ScryfallBuilderService builder = new ScryfallBuilderService(this);
    private final ScryfallLookupService lookup = new ScryfallLookupService(this);

    /**
     * 
     */
    public ScryfallServiceProvider() {
        super("scryfall.com");
        provide(builder, lookup);
    }

}
