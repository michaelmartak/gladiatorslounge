/**
 * 
 */
package org.oaktownrpg.jgladiator.app.blob;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.oaktownrpg.jgladiator.app.mtg.MtgGameSymbol;

/**
 * @author michaelmartak
 *
 */
public class AppBlobStoreTest {

    private static File appDir;

    /**
     * 
     */
    public AppBlobStoreTest() {

    }

    @Test
    public void simpleBlobWriteAndRead() throws Exception {
        AppBlobStore store = new AppBlobStore();
        store.initialize(appDir, Executors.newSingleThreadExecutor());

        byte[] bytes = MtgGameSymbol.BLACK.getSvg().getBytes();
        BlobMetadata metadata = new BlobMetadata(BlobType.SVG, "black_mana.svg", "Test File", bytes.length);
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

    @BeforeClass
    public static void createDirectory() throws Exception {
        appDir = new File(System.getProperty("user.dir") + File.separator + ".jgladiator_test");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
    }

    @AfterClass
    public static void cleanDirectory() throws Exception {
        Path directory = appDir.toPath();
        Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

}
