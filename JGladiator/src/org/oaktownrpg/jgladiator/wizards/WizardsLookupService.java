/**
 * 
 */
package org.oaktownrpg.jgladiator.wizards;

import java.util.function.Consumer;

import org.oaktownrpg.jgladiator.framework.GladiatorService;
import org.oaktownrpg.jgladiator.framework.GladiatorServiceProvider;
import org.oaktownrpg.jgladiator.framework.Hub;
import org.oaktownrpg.jgladiator.framework.LookupService;
import org.oaktownrpg.jgladiator.framework.ServiceFailure;
import org.oaktownrpg.jgladiator.framework.ServiceType;
import org.oaktownrpg.jgladiator.framework.ServiceTypeEnum;

/**
 * Wizards of the Coast lookup service.
 * 
 * @author michaelmartak
 *
 */
class WizardsLookupService implements GladiatorService, LookupService {

    private final WizardsServiceProvider sp;
    private Hub hub;

    /**
     * 
     */
    WizardsLookupService(WizardsServiceProvider sp) {
        this.sp = sp;
    }

    @Override
    public GladiatorServiceProvider getProvider() {
        return sp;
    }

    @Override
    public String getIdentifier() {
        return "gatherer.wizards.com";
    }

    @Override
    public String getLocalizedName() {
        return hub.localization().string("gatherer.wizards.com");
    }

    @Override
    public ServiceTypeEnum getType() {
        return ServiceTypeEnum.LOOKUP;
    }

    @Override
    public <T extends ServiceType> T asType(Class<T> serviceType) {
        if (LookupService.class.equals(serviceType)) {
            return serviceType.cast(this);
        }
        throw new ClassCastException("Service is not of type " + serviceType);
    }

    @Override
    public void initialize(Hub hub) {
        this.hub = hub;
    }

    @Override
    public void start(Consumer<ServiceFailure> onFailure, Runnable onReady) {
        // TODO Auto-generated method stub

    }

}
