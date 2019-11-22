/**
 * 
 */
package org.oaktownrpg.jgladiator.framework;

import java.util.Collection;
import java.util.concurrent.Callable;

/**
 * Handles all concurrency and threading for the application.
 * 
 * @author michaelmartak
 *
 */
public interface HubExecutors {

	/**
	 * Invokes all of the given tasks in the main thread pool.
	 * <p>
	 * To be called for most asynchronous tasks.
	 * 
	 * @param tasks tasks to invoke
	 * @throws InterruptedException
	 */
	void invokeAll(Collection<Callable<?>> tasks) throws InterruptedException;

}
