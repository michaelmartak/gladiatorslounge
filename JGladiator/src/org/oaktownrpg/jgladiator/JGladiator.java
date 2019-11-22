/**
 * 
 */
package org.oaktownrpg.jgladiator;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.logging.Logger;

import org.oaktownrpg.jgladiator.framework.GladiatorService;
import org.oaktownrpg.jgladiator.framework.GladiatorServiceProvider;
import org.oaktownrpg.jgladiator.framework.Hub;
import org.oaktownrpg.jgladiator.framework.ServiceFailure;

/**
 * 
 * JGladiator : Gladiators Lounge Java application
 * 
 * @author mmartak
 *
 */
public final class JGladiator implements Hub {

	private final Map<String, GladiatorServiceProvider> serviceProvidersById = new LinkedHashMap<>();
	private final Map<String, Map<String, GladiatorService>> servicesByProviderId = new LinkedHashMap<>();
	private final Set<String> readyServices = new HashSet<>();
	private ExecutorService executors;

	/**
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		new JGladiator().start();
	}

	/**
	 * Starts the application
	 * 
	 * @throws InterruptedException
	 */
	private void start() throws InterruptedException {
		// Discovery : get all the services
		discoverServices();
		// Executors : set up thread pool
		initExecutors();
		// Initialize services as tasks on the executor
		executors.invokeAll(tasksFromServices());
	}

	/**
	 * Called when a service fails to start
	 * 
	 * @param failure
	 * @param serviceProviderName
	 * @param serviceName
	 */
	void serviceFailed(final ServiceFailure failure, final String serviceProviderName, final String serviceName) {
		Logger.getLogger(getClass().getName())
				.severe(serviceProviderName + " : " + serviceName + " : " + "Service Failed : " + failure);
	}

	/**
	 * Called when a service starts
	 * 
	 * @param serviceProviderName
	 * @param serviceName
	 */
	void serviceReady(final String serviceProviderName, final String serviceName) {
		Logger.getLogger(getClass().getName())
				.info(serviceProviderName + " : " + serviceName + " : " + "Service Ready");
		readyServices.add(serviceName);
	}

	/**
	 * Collects the registered services and turns them into Callable task objects to
	 * be run from the executor service
	 * 
	 * @return a collection of tasks to initialize the services
	 */
	private Collection<Callable<?>> tasksFromServices() {
		final List<Callable<?>> tasks = new LinkedList<>();
		for (final Entry<String, Map<String, GladiatorService>> spEntry : servicesByProviderId.entrySet()) {
			final String serviceProviderName = spEntry.getKey();
			final Map<String, GladiatorService> services = spEntry.getValue();
			for (final Entry<String, GladiatorService> serviceEntry : services.entrySet()) {
				final String serviceName = serviceEntry.getKey();
				final GladiatorService service = serviceEntry.getValue();
				final Consumer<ServiceFailure> onFailure = (ServiceFailure failure) -> serviceFailed(failure,
						serviceProviderName, serviceName);
				final Runnable onReady = () -> serviceReady(serviceProviderName, serviceName);
				tasks.add(() -> {
					service.start(JGladiator.this, onFailure, onReady);
					return null;
				});
			}
		}
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
		final ServiceLoader<GladiatorServiceProvider> serviceProviders = ServiceLoader
				.load(GladiatorServiceProvider.class);
		for (final GladiatorServiceProvider s : serviceProviders) {
			final String id = s.getIdentifier();
			serviceProvidersById.put(id, s);
			final Map<String, GladiatorService> services = new LinkedHashMap<>();
			for (GladiatorService service : s.getServices()) {
				services.put(service.getIdentifier(), service);
			}
			servicesByProviderId.put(id, services);
		}
	}

}
