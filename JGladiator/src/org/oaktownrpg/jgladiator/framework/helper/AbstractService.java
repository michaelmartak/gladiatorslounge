/**
 * 
 */
package org.oaktownrpg.jgladiator.framework.helper;

import org.oaktownrpg.jgladiator.framework.GladiatorService;
import org.oaktownrpg.jgladiator.framework.GladiatorServiceProvider;
import org.oaktownrpg.jgladiator.framework.Hub;

/**
 * @author michaelmartak
 *
 */
public abstract class AbstractService<P extends GladiatorServiceProvider> implements GladiatorService {

    private final P provider;
    private final String identifier;
    private Hub hub;

    /**
     * 
     */
    AbstractService(P provider, String identifier) {
        assert provider != null;
        assert identifier != null;

        this.provider = provider;
        this.identifier = identifier;
    }

    @Override
    public final P getProvider() {
        return provider;
    }

    @Override
    public final String getIdentifier() {
        return identifier;
    }

    @Override
    public final String getLocalizedName() {
        return hub.localization().string(identifier);
    }

    @Override
    public final void initialize(Hub hub) {
        this.hub = hub;
    }

    protected final Hub hub() {
        return hub;
    }

}
