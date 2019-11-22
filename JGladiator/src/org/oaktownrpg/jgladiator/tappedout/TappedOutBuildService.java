/**
 * 
 */
package org.oaktownrpg.jgladiator.tappedout;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
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

	private static final URI TAPPED_OUT_URL = URI.create("https://tappedout.net");
	private final TappedOutServiceProvider serviceProvider;
	private HttpClient httpClient;
	private Hub hub;

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
		return hub.localization().string("tappedOut.service");
	}

	@Override
	public ServiceType getType() {
		return ServiceType.BUILD;
	}

	@Override
	public void initialize(Hub hub) {
		this.hub = hub;
	}

	@Override
	public void start(Consumer<ServiceFailure> onFailure, Runnable onReady) {
		httpClient = newHttpClient();
		final HttpRequest request = newHttpRequestGET();
		try {
			HttpResponse<String> response = httpClient.send(request, BodyHandlers.ofString());
			final int statusCode = response.statusCode();
			if (statusCode != 200) {
				throw new InterruptedException("Status " + statusCode);
			}
			String body = response.body();
		} catch (IOException | InterruptedException e) {
			onFailure.accept(new ServiceFailure(localize("initialHttpRequest"), e));
			return;
		}
		onReady.run();
	}

	private String localize(String key) {
		// TODO Auto-generated method stub
		return key;
	}

	private HttpRequest newHttpRequestGET() {
		return HttpRequest.newBuilder(TAPPED_OUT_URL).setHeader("User-Agent", "JGladiator").GET().build();
	}

	private HttpClient newHttpClient() {
		return HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
	}

}
