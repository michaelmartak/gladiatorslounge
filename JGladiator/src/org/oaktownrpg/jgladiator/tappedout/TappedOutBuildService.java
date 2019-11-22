/**
 * 
 */
package org.oaktownrpg.jgladiator.tappedout;

import java.util.function.Consumer;

import org.oaktownrpg.jgladiator.framework.GladiatorService;
import org.oaktownrpg.jgladiator.framework.GladiatorServiceProvider;
import org.oaktownrpg.jgladiator.framework.Hub;
import org.oaktownrpg.jgladiator.framework.ServiceFailure;
import org.oaktownrpg.jgladiator.framework.ServiceType;

/**
 * Main build service for TappedOut.net
 * 
 * @author michaelmartak
 *
 */
final class TappedOutBuildService implements GladiatorService {
	
	private final TappedOutServiceProvider serviceProvider;
	
	TappedOutBuildService(TappedOutServiceProvider serviceProvider) {
		assert serviceProvider != null;
		this.serviceProvider = serviceProvider;
	}

	@Override
	public GladiatorServiceProvider getProvider() {
		return serviceProvider;
	}

	@Override
	public String getIdentifier() {
		return "TappedOut Deck Builder";
	}

	@Override
	public String getLocalizedName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServiceType getType() {
		return ServiceType.BUILD;
	}

	@Override
	public void start(Hub hub, Consumer<ServiceFailure> onFailure, Runnable onReady) {
		onReady.run();
	}

}
