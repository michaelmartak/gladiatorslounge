/**
 * 
 */
package org.oaktownrpg.jgladiator.app.blob;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import org.oaktownrpg.jgladiator.app.AppExecutors;

/**
 * Storage for binary large objects (BLOBs).
 * <p/>
 * Typical use case is image files, but should be able to handle docs of all
 * sizes.
 * 
 * @author michaelmartak
 *
 */
public class AppBlobStore {

    private File storeDirectory;
    private ExecutorService executor;

    /**
     * 
     */
    public AppBlobStore() {

    }

    public void initialize(File appDirectory, AppExecutors executors) {
        storeDirectory = new File(appDirectory.getAbsolutePath() + File.separator + "blobs");
        if (!storeDirectory.exists()) {
            try {
                storeDirectory.mkdir();
            } catch (SecurityException e) {
                Logger.getLogger(getClass().getName()).severe("Cannot create blob storage directory " + e.getMessage());
                return;
            }
        }
        executor = executors.blobStorageExecutor();
    }

    /**
     * Asynchronously write the blob to the store
     * 
     * @param blob the blob to write, never null
     * @return a Future that will return true when the write has succeeded
     */
    public Future<Boolean> writeBlob(final AppBlob blob) {
        assert blob != null;
        return executor.submit(() -> doWrite(blob), true);
    }

    void doWrite(final AppBlob blob) {
        writeMetadata(blob.getMetadata());
        writeBytes(blob.getId(), blob.getBytes());
    }

    /**
     * Asynchronously read the blob from the store
     * 
     * @param id the ID of the blob to read
     * @return a Future that will return the AppBlob when the read has succeeded
     */
    public Future<AppBlob> readBlob(UUID id) {
        assert id != null;
        return executor.submit(() -> doRead(id));
    }

    AppBlob doRead(UUID id) {
        BlobMetadata metadata = readMetadata(id);
        byte[] bytes = readBytes(id);
        return new AppBlob(metadata, bytes);
    }

    private void writeMetadata(BlobMetadata metadata) {
        // TODO Auto-generated method stub

    }

    private BlobMetadata readMetadata(UUID id) {
        // TODO Auto-generated method stub
        return null;
    }

    private void writeBytes(UUID id, byte[] bytes) {
        // TODO Auto-generated method stub

    }

    private byte[] readBytes(UUID id) {
        // TODO Auto-generated method stub
        return null;
    }

}
