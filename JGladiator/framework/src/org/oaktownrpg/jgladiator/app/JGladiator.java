/**
 * 
 */
package org.oaktownrpg.jgladiator.app;

import java.lang.reflect.InvocationTargetException;
import java.util.EnumMap;
import java.util.Map;
import java.util.logging.Logger;

import org.oaktownrpg.jgladiator.app.mtg.MtgCardIdentityCatalog;
import org.oaktownrpg.jgladiator.framework.GladiatorService;
import org.oaktownrpg.jgladiator.framework.GladiatorServiceProvider;
import org.oaktownrpg.jgladiator.framework.Http;
import org.oaktownrpg.jgladiator.framework.Hub;
import org.oaktownrpg.jgladiator.framework.HubExecutors;
import org.oaktownrpg.jgladiator.framework.Localization;
import org.oaktownrpg.jgladiator.framework.ServiceFailure;
import org.oaktownrpg.jgladiator.framework.Services;
import org.oaktownrpg.jgladiator.framework.Storage;
import org.oaktownrpg.jgladiator.framework.ccg.CardIdentityCatalog;
import org.oaktownrpg.jgladiator.framework.ccg.Ccg;
import org.oaktownrpg.jgladiator.framework.ccg.Gatherer;

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
    private final AppHttp http = new AppHttp();
    private final Map<Ccg, CardIdentityCatalog> cardIdentityCatalogs = new EnumMap<>(Ccg.class);

    /**
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        new JGladiator().start();
    }

    JGladiator() {
        cardIdentityCatalogs.put(Ccg.MTG, new MtgCardIdentityCatalog());
    }

    /**
     * Starts the application
     * 
     * @throws InterruptedException
     * @throws InvocationTargetException
     */
    private void start() throws InterruptedException, InvocationTargetException {
        // Initialize storage
        storage.initialize(executors);
        // Discovery : get all the services. Services must be discovered before the UI
        // starts.
        services.discoverServices();
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

    @Override
    public Http http() {
        return http;
    }

    @Override
    public Gatherer cardLookup() {
        return new CardLookupGatherer(this);
    }

    @Override
    public CardIdentityCatalog cardIdentityCatalog(Ccg ccg) {
        return cardIdentityCatalogs.get(ccg);
    }

}
