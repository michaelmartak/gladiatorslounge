/**
 * 
 */
package org.oaktownrpg.jgladiator.app;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ServiceLoader;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Logger;

import org.oaktownrpg.jgladiator.framework.GladiatorService;
import org.oaktownrpg.jgladiator.framework.GladiatorServiceProvider;
import org.oaktownrpg.jgladiator.framework.Hub;
import org.oaktownrpg.jgladiator.framework.Localization;
import org.oaktownrpg.jgladiator.framework.ServiceFailure;
import org.oaktownrpg.jgladiator.framework.ServiceVisitor;
import org.oaktownrpg.jgladiator.ui.JGladiatorUI;

/**
 * 
 * JGladiator : Gladiators Lounge Java application
 * 
 * @author mmartak
 *
 */
public final class JGladiator implements Hub {

	private Iterable<GladiatorServiceProvider> serviceProviders;
	private ExecutorService executors;

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		new JGladiator().start();
	}

	JGladiator() {
	}

	/**
	 * Starts the application
	 * 
	 * @throws InterruptedException
	 * @throws InvocationTargetException
	 */
	private void start() throws InterruptedException, InvocationTargetException {
		// Discovery : get all the services
		discoverServices();
		// Start the UI
		new JGladiatorUI(this).start();
		// Executors : set up thread pool
		initExecutors();
		// Initialize services as tasks on the executor
		executors.invokeAll(tasksFromServices());
	}

	/**
	 * Called when a service fails to start
	 * 
	 * @param failure
	 * @param sp
	 * @param service
	 */
	void serviceFailed(final ServiceFailure failure, GladiatorServiceProvider sp, GladiatorService service) {
		Logger.getLogger(getClass().getName())
				.severe(sp.getIdentifier() + " : " + service.getIdentifier() + " : " + "Service Failed : " + failure);
	}

	/**
	 * Called when a service starts
	 * 
	 * @param serviceProviderName
	 * @param serviceName
	 */
	void serviceReady(GladiatorServiceProvider sp, GladiatorService service) {
		Logger.getLogger(getClass().getName())
				.info(sp.getIdentifier() + " : " + service.getIdentifier() + " : " + "Service Ready");
		// TODO
	}

	/**
	 * Collects the registered services and turns them into Callable task objects to
	 * be run from the executor service
	 * 
	 * @return a collection of tasks to initialize the services
	 */
	private Collection<Callable<?>> tasksFromServices() {
		final List<Callable<?>> tasks = new LinkedList<>();
		ServiceVisitor.visit(serviceProviders, (GladiatorServiceProvider sp, GladiatorService service) -> {
			tasks.add(() -> {
				service.start(/* onFailed */ (ServiceFailure failure) -> serviceFailed(failure, sp, service),
						/* onReady */ () -> serviceReady(sp, service));
				return null;
			});
		});
		return tasks;
	}

	/**
	 * Starts the executor service
	 */
	private void initExecutors() {
		final AtomicInteger increment = new AtomicInteger(0);
		executors = Executors.newFixedThreadPool(11,
				(Runnable r) -> new Thread(r, "Gladiator-Exec-" + increment.getAndIncrement()));
	}

	/**
	 * Discovers all available services
	 */
	private void discoverServices() {
		// Load the services
		final ServiceLoader<GladiatorServiceProvider> sl = ServiceLoader.load(GladiatorServiceProvider.class);
		// Create a list and add the elements
		final LinkedList<GladiatorServiceProvider> ll = new LinkedList<>();
		sl.forEach(ll::add);
		// Set the local copy to be immutable
		serviceProviders = Collections.unmodifiableList(ll);

		// Initialize them with the hub
		ServiceVisitor.visit(serviceProviders, (GladiatorServiceProvider sp, GladiatorService service) -> {
			sp.initialize(this);
			service.initialize(this);
		});
	}

	@Override
	public Localization localization() {
		return new AppLocalization();
	}

	@Override
	public Iterable<GladiatorServiceProvider> getServiceProviders() {
		return serviceProviders;
	}

}
