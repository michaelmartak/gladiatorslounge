/**
 * 
 */
package org.oaktownrpg.jgladiator.app;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.function.Supplier;
import java.util.logging.Logger;

import org.oaktownrpg.jgladiator.app.blob.AppBlobStore;
import org.oaktownrpg.jgladiator.app.db.AppLocalDatabase;
import org.oaktownrpg.jgladiator.framework.Storage;

/**
 * Main application storage.
 * 
 * @author michaelmartak
 *
 */
class AppStorage implements Storage {

    private final File homeDirectory = new File(System.getProperty("user.dir"));
    private final File appDirectory = new File(homeDirectory.getAbsolutePath() + File.separator + ".jgladiator");
    private final File credentialStore = new File(appDirectory.getAbsolutePath() + File.separator + "credentials.enc");

    private final AppCryptography crypto = new AppCryptography();
    private final AppBlobStore blobStore;
    private final AppLocalDatabase localDatabase;

    /**
     * 
     */
    AppStorage() {
        localDatabase = new AppLocalDatabase();
        blobStore = new AppBlobStore();
    }

    public void initialize(AppExecutors executors) {
        validateDirectories();
        localDatabase.initialize(executors);
        blobStore.initialize(appDirectory, executors);
    }

    private void validateDirectories() {
        if (!homeDirectory.exists()) {
            Logger.getLogger(getClass().getName()).severe("User's home directory does not exist: " + homeDirectory);
            return;
        }
        if (!homeDirectory.canWrite()) {
            Logger.getLogger(getClass().getName()).severe("User's home directory is not writable: " + homeDirectory);
            return;
        }
        if (!appDirectory.exists()) {
            try {
                appDirectory.mkdir();
            } catch (SecurityException e) {
                Logger.getLogger(getClass().getName()).severe("Cannot create application directory " + e.getMessage());
                return;
            }
        }
    }

    @Override
    public void storeCredentials(String key, String passphrase) {
        final Properties properties = loadCredentialStore();
        if (properties == null) {
            // Could not load
            return;
        }
        try {
            final String encrypted = crypto.encrypt(passphrase);
            properties.put(key, encrypted);
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).severe("Cannot encrypt passphrase " + e.getMessage());
            return;
        }
        saveCredentialStore(properties);
    }

    private void saveCredentialStore(final Properties properties) {
        FileWriter writer;
        try {
            writer = new FileWriter(credentialStore);
            try {
                properties.store(writer, "");
            } finally {
                writer.close();
            }
        } catch (IOException e) {
            Logger.getLogger(getClass().getName()).severe("Cannot save credential store " + e.getMessage());
        }
    }

    private Properties loadCredentialStore() {
        if (!credentialStore.exists()) {
            try {
                credentialStore.createNewFile();
            } catch (IOException e) {
                Logger.getLogger(getClass().getName()).severe("Cannot create credential store " + e.getMessage());
                return null;
            }
        }
        final Properties properties = new Properties();
        try {
            final FileReader reader = new FileReader(credentialStore);
            try {
                properties.load(reader);
            } finally {
                reader.close();
            }
        } catch (IOException e) {
            Logger.getLogger(getClass().getName())
                    .severe("Cannot load properties from credential store " + e.getMessage());
            return null;
        }
        return properties;
    }

    @Override
    public Supplier<String> fetchCredentials(String key) {
        Properties properties = loadCredentialStore();
        if (properties == null) {
            return null;
        }
        Object property = properties.get(key);
        if (property == null) {
            return null;
        }
        try {
            final String passphrase = crypto.decrypt(property.toString());
            return () -> passphrase;
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).severe("Cannot decrypt passphrase " + e.getMessage());
            return null;
        }
    }

}
