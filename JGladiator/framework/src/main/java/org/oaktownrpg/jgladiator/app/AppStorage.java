/**
 * 
 */
package org.oaktownrpg.jgladiator.app;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.Future;
import java.util.function.Supplier;
import java.util.logging.Logger;

import org.oaktownrpg.jgladiator.app.blob.AppBlob;
import org.oaktownrpg.jgladiator.app.blob.AppBlobStore;
import org.oaktownrpg.jgladiator.app.blob.BlobMetadata;
import org.oaktownrpg.jgladiator.app.db.AppLocalDatabase;
import org.oaktownrpg.jgladiator.framework.Storage;
import org.oaktownrpg.jgladiator.framework.ccg.CardIdentity;
import org.oaktownrpg.jgladiator.framework.ccg.CardSet;
import org.oaktownrpg.jgladiator.framework.ccg.CardSetSymbol;

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

    @Override
    public void storeCardSet(final CardSet cardSet) {
        final CardSetSymbol symbol = cardSet.getSymbol();
        try {
            final Future<UUID> blobId = blobStore.writeIdempotent(blob(symbol));
            final Future<Boolean> dbResult = localDatabase.upsertCardSet(cardSet, blobId.get());
            if (!dbResult.get()) {
                Logger.getLogger(getClass().getName()).severe("Card set result store FALSE");
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).severe("Cannot store card set " + e.getMessage());
        }
    }

    private AppBlob blob(CardSetSymbol symbol) {
        final byte[] bytes = symbol.getBytes();
        BlobMetadata metadata = new BlobMetadata();
        metadata.setAltText(symbol.getAltText());
        metadata.setBlobType(symbol.getType());
        metadata.setFileName(symbol.getName());
        metadata.setHash(symbol.getName().hashCode());
        metadata.setSize(bytes.length);
        metadata.setSource(symbol.getSource());
        AppBlob blob = new AppBlob(metadata, bytes);
        return blob;
    }

    @Override
    public void storeCardIdentity(List<CardIdentity> identity) {
        try {
            final Future<Boolean> dbResult = localDatabase.upsertCardIdentity(identity);
            if (!dbResult.get()) {
                Logger.getLogger(getClass().getName()).severe("Card identity result store FALSE");
            }
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).severe("Cannot store card identity " + e.getMessage());
        }
    }

}
