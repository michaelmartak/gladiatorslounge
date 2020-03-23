/**
 * 
 */
package org.oaktownrpg.jgladiator.tappedout;

import org.oaktownrpg.jgladiator.framework.helper.AbstractServiceProvider;

/**
 * Service provider for www.tappedout.net
 * 
 * @author michaelmartak
 *
 */
public final class TappedOutServiceProvider extends AbstractServiceProvider {

    private final TappedOutBuilderService buildService = new TappedOutBuilderService(this);

    public TappedOutServiceProvider() {
        super("tappedout.net");
        provide(buildService);
    }

}
