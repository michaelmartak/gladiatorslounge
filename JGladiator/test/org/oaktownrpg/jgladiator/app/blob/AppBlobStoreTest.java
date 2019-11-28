/**
 * 
 */
package org.oaktownrpg.jgladiator.app.blob;

import java.io.File;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author michaelmartak
 *
 */
public class AppBlobStoreTest {

    /**
     * 
     */
    public AppBlobStoreTest() {

    }

    @Test
    public void simpleBlobWriteAndRead() throws Exception {
        AppBlobStore store = new AppBlobStore();
        File appDir = new File(System.getProperty("user.dir") + File.separator + ".jgladiator_test");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        store.initialize(appDir, Executors.newSingleThreadExecutor());

        byte[] bytes = new byte[0];
        BlobMetadata metadata = new BlobMetadata(BlobType.SVG, "test.svg", "Test File", bytes.length);
        AppBlob blob = new AppBlob(metadata, bytes);
        UUID id = blob.getId();

        // Write
        Future<Boolean> writeResult = store.writeBlob(blob);
        Assert.assertTrue("Write successful", writeResult.get());

        // Read
        Future<AppBlob> readResult = store.readBlob(id);
        AppBlob resultBlob = readResult.get();
        Assert.assertEquals("Blob read metadata is different from write metadata", metadata, resultBlob.getMetadata());
    }

}
