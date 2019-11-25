/**
 * 
 */
package org.oaktownrpg.jgladiator.app;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.oaktownrpg.jgladiator.framework.HubExecutors;

/**
 * Handles all concurrency and threading for the application
 * 
 * @author michaelmartak
 *
 */
class AppExecutors implements HubExecutors {

    private static final int MAIN_THREAD_POOL_SIZE = 11;

    private final ExecutorService executors = initExecutors();

    /**
     * 
     */
    AppExecutors() {
    }

    /**
     * Starts the executor service
     */
    private ExecutorService initExecutors() {
        final AtomicInteger increment = new AtomicInteger(0);
        return Executors.newFixedThreadPool(MAIN_THREAD_POOL_SIZE,
                (Runnable r) -> new Thread(r, "Gladiator-Exec-" + increment.getAndIncrement()));
    }

    @Override
    public void invokeAll(Collection<Callable<?>> tasks) throws InterruptedException {
        executors.invokeAll(tasks);
    }

}
