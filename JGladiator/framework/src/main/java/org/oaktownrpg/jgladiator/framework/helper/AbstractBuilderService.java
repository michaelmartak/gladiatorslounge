/**
 * 
 */
package org.oaktownrpg.jgladiator.framework.helper;

import org.oaktownrpg.jgladiator.framework.BuilderService;
import org.oaktownrpg.jgladiator.framework.ServiceType;
import org.oaktownrpg.jgladiator.framework.ServiceTypeEnum;

/**
 * @author michaelmartak
 *
 */
public abstract class AbstractBuilderService<P extends AbstractServiceProvider> extends AbstractService<P>
        implements BuilderService {

    protected AbstractBuilderService(P provider, String identifier) {
        super(provider, identifier);
    }

    @Override
    public final ServiceTypeEnum getType() {
        return ServiceTypeEnum.BUILDER;
    }

    @Override
    public <T extends ServiceType> T asType(Class<T> serviceType) {
        if (BuilderService.class.equals(serviceType)) {
            return serviceType.cast(this);
        }
        throw new ClassCastException("Service is not of type " + serviceType);
    }

}
