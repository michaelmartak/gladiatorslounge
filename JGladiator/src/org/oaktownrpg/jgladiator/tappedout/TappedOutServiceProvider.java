/**
 * 
 */
package org.oaktownrpg.jgladiator.tappedout;

import java.util.Set;

import org.oaktownrpg.jgladiator.framework.GladiatorService;
import org.oaktownrpg.jgladiator.framework.GladiatorServiceProvider;

/**
 * Service provider for www.tappedout.net
 * 
 * @author michaelmartak
 *
 */
public final class TappedOutServiceProvider implements GladiatorServiceProvider {

	@Override
	public String getIdentifier() {
		return "TappedOut.net";
	}

	@Override
	public String getLocalizedName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Set<GladiatorService> getServices() {
		return Set.of(new TappedOutBuildService(this));
	}

}
