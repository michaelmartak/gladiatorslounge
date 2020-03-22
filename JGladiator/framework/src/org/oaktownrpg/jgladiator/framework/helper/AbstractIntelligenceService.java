/**
 * 
 */
package org.oaktownrpg.jgladiator.framework.helper;

import org.oaktownrpg.jgladiator.framework.GladiatorServiceProvider;
import org.oaktownrpg.jgladiator.framework.IntelligenceService;
import org.oaktownrpg.jgladiator.framework.ServiceType;
import org.oaktownrpg.jgladiator.framework.ServiceTypeEnum;

/**
 * @author michaelmartak
 *
 */
public abstract class AbstractIntelligenceService<P extends GladiatorServiceProvider> extends AbstractService<P>
        implements IntelligenceService {

    protected AbstractIntelligenceService(P provider, String identifier) {
        super(provider, identifier);
    }

    @Override
    public final ServiceTypeEnum getType() {
        return ServiceTypeEnum.INTELLIGENCE;
    }

    @Override
    public <T extends ServiceType> T asType(Class<T> serviceType) {
        if (IntelligenceService.class.equals(serviceType)) {
            return serviceType.cast(this);
        }
        throw new ClassCastException("Service is not of type " + serviceType);
    }

}
