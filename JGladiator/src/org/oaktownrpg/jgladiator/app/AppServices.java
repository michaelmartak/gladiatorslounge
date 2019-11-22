/**
 * 
 */
package org.oaktownrpg.jgladiator.app;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.Callable;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.oaktownrpg.jgladiator.framework.GladiatorService;
import org.oaktownrpg.jgladiator.framework.GladiatorServiceProvider;
import org.oaktownrpg.jgladiator.framework.Services;

/**
 * Discovered services in the application.
 * 
 * @author michaelmartak
 *
 */
class AppServices implements Services {

	private final JGladiator hub;
	private Iterable<GladiatorServiceProvider> serviceProviders;

	/**
	 * 
	 */
	AppServices(JGladiator hub) {
		this.hub = hub;
	}

	@Override
	public void visitServiceProviders(Consumer<GladiatorServiceProvider> consumer) {
		serviceProviders.forEach(consumer::accept);
	}

	@Override
	public void visitServices(BiConsumer<GladiatorServiceProvider, GladiatorService> consumer) {
		for (final GladiatorServiceProvider sp : serviceProviders) {
			for (final GladiatorService service : sp.getServices()) {
				consumer.accept(sp, service);
			}
		}
	}

	/**
	 * Discovers all available services
	 */
	void discoverServices() {
		// Load the services
		final ServiceLoader<GladiatorServiceProvider> sl = ServiceLoader.load(GladiatorServiceProvider.class);
		// Create a list and add the elements
		final LinkedList<GladiatorServiceProvider> ll = new LinkedList<>();
		sl.forEach(ll::add);
		// Set the local copy to be immutable
		serviceProviders = Collections.unmodifiableList(ll);

		// Initialize service providers
		visitServiceProviders((sp) -> sp.initialize(hub));

		// Initialize services
		visitServices((sp, service) -> {
			service.initialize(hub);
		});
	}

	/**
	 * Collects the registered services and turns them into Callable task objects to
	 * be run from the executor service
	 * 
	 * @return a collection of tasks to initialize the services
	 */
	Collection<Callable<?>> tasksFromServices() {
		final List<Callable<?>> tasks = new LinkedList<>();
		visitServices((GladiatorServiceProvider sp, GladiatorService service) -> {
			tasks.add(() -> {
				service.start(/* onFailed */ (failure) -> hub.serviceFailed(failure, sp, service),
						/* onReady */ () -> hub.serviceReady(sp, service));
				return null;
			});
		});
		return tasks;
	}

}
