/**
 * 
 */
package org.oaktownrpg.jgladiator.app.blob;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

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
        initialize(appDirectory, executors.blobStorageExecutor());
    }

    void initialize(File appDirectory, ExecutorService executor) {
        this.executor = executor;
        storeDirectory = new File(appDirectory.getAbsolutePath() + File.separator + "blobs");
        if (!storeDirectory.exists()) {
            try {
                storeDirectory.mkdir();
            } catch (SecurityException e) {
                Logger.getLogger(getClass().getName()).severe("Cannot create blob storage directory " + e.getMessage());
                return;
            }
        }
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
        try {
            writeMetadata(blob.getMetadata());
            writeBytes(blob.getId(), blob.getBytes());
        } catch (JAXBException | IOException e) {
            throw new RuntimeException(e);
        }
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
        try {
            BlobMetadata metadata = readMetadata(id);
            byte[] bytes = readBytes(id);
            return new AppBlob(metadata, bytes);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeMetadata(BlobMetadata metadata) throws FileNotFoundException, JAXBException {
        UUID id = metadata.getId();
        File metadataFile = metadataFile(id);
        JAXBContext context = JAXBContext.newInstance(BlobMetadata.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(metadata, new FileOutputStream(metadataFile));
    }

    private File metadataFile(UUID id) {
        return new File(metadataFileName(id));
    }

    private File byteFile(UUID id) {
        return new File(byteFileName(id));
    }

    private String metadataFileName(UUID id) {
        return storeDirectory + File.separator + id + ".md";
    }

    private String byteFileName(UUID id) {
        return storeDirectory + File.separator + id + ".dat";
    }

    private BlobMetadata readMetadata(UUID id) throws JAXBException {
        File metadataFile = metadataFile(id);
        JAXBContext context = JAXBContext.newInstance(BlobMetadata.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        return (BlobMetadata) unmarshaller.unmarshal(metadataFile);
    }

    private void writeBytes(UUID id, byte[] bytes) throws IOException {
        File byteFile = byteFile(id);
        Files.write(byteFile.toPath(), bytes);
    }

    private byte[] readBytes(UUID id) {
        // TODO Auto-generated method stub
        return null;
    }

}
