package org.oaktownrpg.jgladiator.app;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import org.oaktownrpg.jgladiator.framework.Localization;

/**
 * App localization service
 * 
 * @author michaelmartak
 *
 */
final class AppLocalization implements Localization {

	private final ResourceBundle resources;

	AppLocalization() {
		resources = ResourceBundle.getBundle(getClass().getName());
	}

	@Override
	public String string(String key) {
		try {
			return resources.getString(key);
		} catch (MissingResourceException ex) {
			Logger.getLogger(getClass().getName()).warning(ex.getMessage());
			return key + " - NOT FOUND";
		}
	}

}
