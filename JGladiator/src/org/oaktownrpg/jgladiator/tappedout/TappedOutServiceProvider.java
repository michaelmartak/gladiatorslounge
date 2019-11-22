/**
 * 
 */
package org.oaktownrpg.jgladiator.tappedout;

import java.util.Set;

import org.oaktownrpg.jgladiator.framework.GladiatorService;
import org.oaktownrpg.jgladiator.framework.GladiatorServiceProvider;
import org.oaktownrpg.jgladiator.framework.Hub;

/**
 * Service provider for www.tappedout.net
 * 
 * @author michaelmartak
 *
 */
public final class TappedOutServiceProvider implements GladiatorServiceProvider {

	private final TappedOutBuildService buildService;
	private Hub hub;

	public TappedOutServiceProvider() {
		buildService = new TappedOutBuildService(this);
	}

	@Override
	public String getIdentifier() {
		return "TappedOut.net";
	}

	@Override
	public String getLocalizedName() {
		return hub.localization().string("tappedOut");
	}

	@Override
	public Set<GladiatorService> getServices() {
		return Set.of(buildService);
	}

	@Override
	public void initialize(Hub hub) {
		this.hub = hub;
	}

}
