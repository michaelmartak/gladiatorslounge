/**
 * 
 */
package org.oaktownrpg.jgladiator.framework.helper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.oaktownrpg.jgladiator.framework.GladiatorService;
import org.oaktownrpg.jgladiator.framework.GladiatorServiceProvider;
import org.oaktownrpg.jgladiator.framework.Hub;

/**
 * @author michaelmartak
 *
 */
public abstract class AbstractServiceProvider implements GladiatorServiceProvider {

    private final String id;
    private Hub hub;
    private Collection<GladiatorService> services = Collections.emptySet();

    /**
     * 
     */
    protected AbstractServiceProvider(String id) {
        assert id != null;
        this.id = id;
    }

    @Override
    public final String getIdentifier() {
        return id;
    }

    @Override
    public final void initialize(Hub hub) {
        this.hub = hub;
        initializeAfterHub(hub);
    }

    protected final void provideServices(GladiatorService... services) {
        List<GladiatorService> list = new ArrayList<>(services.length);
        for (GladiatorService s : services) {
            list.add(s);
        }
        this.services = Collections.unmodifiableList(list);
    }

    /**
     * Can be overridden to perform extra initialization afater the hub has been
     * initialized.
     * 
     * @param hub the hub, never null
     */
    protected void initializeAfterHub(Hub hub) {
        // No-op
    }

    /**
     * Returns the hub
     * 
     * @return the hub object, or null if not initialized
     */
    protected final Hub hub() {
        return hub;
    }

    @Override
    public final String getLocalizedName() {
        return hub.localization().string(getIdentifier());
    }

    @Override
    public final Collection<GladiatorService> getServices() {
        return services;
    }

}
