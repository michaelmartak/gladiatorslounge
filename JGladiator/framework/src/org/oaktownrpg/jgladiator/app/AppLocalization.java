package org.oaktownrpg.jgladiator.app;

import java.util.HashMap;
import java.util.Map;
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

    private final Map<Class<?>, ResourceBundle> resources = new HashMap<>();

    AppLocalization() {
    }

    @Override
    public String string(Class<?> type, String key) {
        if (type == null) {
            type = getClass();
        }
        ResourceBundle bundle = resources.get(type);
        if (bundle == null) {
            bundle = ResourceBundle.getBundle(type.getName());
            resources.put(type,  bundle);
        }
        try {
            return bundle.getString(key);
        } catch (MissingResourceException ex) {
            Logger.getLogger(getClass().getName()).warning(ex.getMessage());
            return key + " - NOT FOUND";
        }
    }

}
