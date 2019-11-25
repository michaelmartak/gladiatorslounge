/**
 * 
 */
package org.oaktownrpg.jgladiator.framework.helper;

import org.oaktownrpg.jgladiator.framework.GladiatorServiceProvider;
import org.oaktownrpg.jgladiator.framework.LookupService;
import org.oaktownrpg.jgladiator.framework.ServiceType;
import org.oaktownrpg.jgladiator.framework.ServiceTypeEnum;

/**
 * @author michaelmartak
 *
 */
public abstract class AbstractLookupService<P extends GladiatorServiceProvider> extends AbstractService<P>
        implements LookupService {

    protected AbstractLookupService(P provider, String identifier) {
        super(provider, identifier);
    }

    @Override
    public final ServiceTypeEnum getType() {
        return ServiceTypeEnum.LOOKUP;
    }

    @Override
    public <T extends ServiceType> T asType(Class<T> serviceType) {
        if (LookupService.class.equals(serviceType)) {
            return serviceType.cast(this);
        }
        throw new ClassCastException("Service is not of type " + serviceType);
    }

}
