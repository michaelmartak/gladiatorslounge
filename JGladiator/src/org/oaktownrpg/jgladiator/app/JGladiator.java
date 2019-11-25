/**
 * 
 */
package org.oaktownrpg.jgladiator.app;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Logger;

import org.oaktownrpg.jgladiator.framework.GladiatorService;
import org.oaktownrpg.jgladiator.framework.GladiatorServiceProvider;
import org.oaktownrpg.jgladiator.framework.Hub;
import org.oaktownrpg.jgladiator.framework.HubExecutors;
import org.oaktownrpg.jgladiator.framework.Localization;
import org.oaktownrpg.jgladiator.framework.ServiceFailure;
import org.oaktownrpg.jgladiator.framework.Services;
import org.oaktownrpg.jgladiator.framework.Storage;
import org.oaktownrpg.jgladiator.ui.JGladiatorUI;

/**
 * 
 * JGladiator : Gladiators Lounge Java application
 * 
 * @author mmartak
 *
 */
public final class JGladiator implements Hub {

    private final AppExecutors executors = new AppExecutors();
    private final AppLocalization localization = new AppLocalization();
    private final AppServices services = new AppServices(this);
    private final AppStorage storage = new AppStorage();

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
        // Discovery : get all the services. Services must be discovered before the UI
        // starts.
        services.discoverServices();
        // Start the UI
        new JGladiatorUI(this).start();
        // Initialize services as tasks on the executor
        executors.invokeAll(services.tasksFromServices());
    }

    /**
     * Called when a service fails to start
     * 
     * @param failure
     * @param sp
     * @param service
     */
    void serviceFailed(final ServiceFailure failure, final GladiatorServiceProvider sp,
            final GladiatorService service) {
        Logger.getLogger(getClass().getName())
                .severe(sp.getIdentifier() + " : " + service.getIdentifier() + " : " + "Service Failed : " + failure);
    }

    /**
     * Called when a service starts
     * 
     * @param serviceProviderName
     * @param serviceName
     */
    void serviceReady(final GladiatorServiceProvider sp, final GladiatorService service) {
        Logger.getLogger(getClass().getName())
                .info(sp.getIdentifier() + " : " + service.getIdentifier() + " : " + "Service Ready");
        // TODO
    }

    @Override
    public Localization localization() {
        return localization;
    }

    @Override
    public Services services() {
        return services;
    }

    @Override
    public HubExecutors executors() {
        return executors;
    }

    @Override
    public Storage storage() {
        return storage;
    }

}
