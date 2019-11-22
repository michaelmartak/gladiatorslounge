/**
 * 
 */
package org.oaktownrpg.jgladiator.framework;

import java.util.function.BiConsumer;

/**
 * @author michaelmartak
 *
 */
public final class ServiceVisitor {

	private Iterable<GladiatorServiceProvider> serviceProviders;

	/**
	 * 
	 */
	public ServiceVisitor(Iterable<GladiatorServiceProvider> serviceProviders) {
		this.serviceProviders = serviceProviders;
	}

	public void visit(BiConsumer<GladiatorServiceProvider, GladiatorService> consumer) {
		for (final GladiatorServiceProvider sp : serviceProviders) {
			for (final GladiatorService service : sp.getServices()) {
				consumer.accept(sp, service);
			}
		}
	}

	/**
	 * Convenience method to iterate through all services by providers
	 * 
	 * @param serviceProviders
	 * @param consumer
	 */
	public static void visit(Iterable<GladiatorServiceProvider> serviceProviders,
			BiConsumer<GladiatorServiceProvider, GladiatorService> consumer) {
		new ServiceVisitor(serviceProviders).visit(consumer);
	}

}
