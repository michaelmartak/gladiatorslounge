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
public class AppExecutors implements HubExecutors {

    private static final int MAIN_THREAD_POOL_SIZE = 11;

    private final ExecutorService mainThreadPool = threadPool("Gladiator-Exec-", MAIN_THREAD_POOL_SIZE);
    private final ExecutorService databaseExecutor = singleThread("Gladiator-Database");
    private final ExecutorService blobStorageExecutor = singleThread("Gladiator-Blob-Storage");

    /**
     * 
     */
    AppExecutors() {
    }

    /**
     * Creates a thread pool executor
     */
    private static ExecutorService threadPool(final String prefix, final int size) {
        final AtomicInteger increment = new AtomicInteger(0);
        return Executors.newFixedThreadPool(size, (Runnable r) -> new Thread(r, prefix + increment.getAndIncrement()));
    }

    /**
     * Creates a single thread executor
     * 
     * @param name the thread name
     * @return a new executor
     */
    private static ExecutorService singleThread(final String name) {
        return Executors.newSingleThreadExecutor((Runnable r) -> new Thread(r, name));
    }

    @Override
    public void invokeAll(Collection<Callable<?>> tasks) throws InterruptedException {
        mainThreadPool.invokeAll(tasks);
    }

    /**
     * Returns the executor to use for the DB connection
     * 
     * @return an executor service, never null
     */
    public ExecutorService databaseExecutor() {
        return databaseExecutor;
    }

    /**
     * Returns the executor to use for Blob storage
     * 
     * @return an executor service, never null
     */
    public ExecutorService blobStorageExecutor() {
        return blobStorageExecutor;
    }

    @Override
    public void execute(Runnable task) {
        mainThreadPool.execute(task);
    }

}
