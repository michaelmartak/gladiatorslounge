/**
 * 
 */
package org.oaktownrpg.jgladiator.app.blob;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
 * <p/>
 * Here's the basic design. We're storing a bunch of BLOBs that are being pulled
 * from a bunch of places. We want to store them in a generic, reusable way. At
 * the same time, we want to make decent use of our disk space.
 * <p/>
 * Storing the actual BLOB contents is easy: we use a UUID to uniquely identify
 * the file (XXX.dat). We also store metadata about the BLOB in a metadata file
 * (XXX.md).
 * <p/>
 * We use UUIDs in order to reference the BLOB storage information using a
 * unique related key in the local database (text file names, while easy to
 * read, don't make for very good DB keys).
 * <p/>
 * Where this gets tricky is that we don't want to store the same BLOBs over and
 * over again. We need two things: first, a unique way to identify the blob
 * using the metadata. See {@link #compareMetadata} for how that unique key is
 * defined. Second, we want lookups to compare the contents of BLOBs to operate
 * quickly. For that, we use a hash. Hash works as a reverse lookup from the
 * hashed contents back to the UUID of the blob. For example, if blob XXX hashes
 * to '12345', then the file <code>12345.hash</code> has in its contents the
 * UUID value XXX. Since hashes are not guaranteed unique, that list can be
 * arbitrarily long, but is rarely going to collide. When a collision occurs, we
 * read our metadata files to determine the right one.
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
     * Asynchronously write the blob to the store. Note that this method will simply
     * write the blob given, without any care whether anything resembling it already
     * exists in the store.
     * <p/>
     * It is better to use {@link #writeIdempotent(AppBlob)} to not waste storage.
     * 
     * @param blob the blob to write, never null
     * @return a Future that will return true when the write has succeeded
     */
    public Future<Boolean> writeBlob(final AppBlob blob) {
        assert blob != null;
        return executor.submit(() -> doWrite(blob), true);
    }

    /**
     * Asynchronously write the blob to the store, using the Metadata's compareTo()
     * function and the hash to determine whether the blob has already been stored
     * here.
     * 
     * @param blob
     * @return
     */
    public Future<UUID> writeIdempotent(AppBlob blob) {
        assert blob != null;
        return executor.submit(() -> doWriteIdempotent(blob));
    }

    void doWrite(final AppBlob blob) {
        try {
            writeMetadata(blob.getMetadata());
            writeBytes(blob.getId(), blob.getBytes());
            writeHash(blob.getHash(), blob.getId());
        } catch (JAXBException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    UUID doWriteIdempotent(final AppBlob blob) throws IOException, JAXBException {
        // First check if we have a non-null UUID. If we do, this means we are writing a
        // unique file.
        final UUID initialId = blob.getId();
        if (initialId != null) {
            doWrite(blob);
            return initialId;
        }
        final BlobMetadata metadata = blob.getMetadata();
        final int hash = metadata.getHash();
        final File hashFile = hashFile(hash);
        // Check if hash file already exists
        if (!hashFile.exists()) {
            return writeNewBlob(blob);
        }
        // Hash already exists. Check metadata.
        List<String> lines = Files.readAllLines(hashFile.toPath());
        for (String idStr : lines) {
            UUID id = UUID.fromString(idStr);
            BlobMetadata md = readMetadata(id);
            if (compareMetadata(md, metadata) == 0) {
                // We found our file. No need to save the same thing again.
                metadata.setId(id);
                return id;
            }
        }
        // BLOB hashes to an existing value, but is new.
        return writeNewBlob(blob);
    }

    /**
     * Compares the metadata of two blobs to see if they represent the same thing
     * 
     * @param md1
     * @param md2
     * @return
     */
    private int compareMetadata(BlobMetadata md1, BlobMetadata md2) {
        int value;
        // Compare names
        value = Objects.compare(md1.getFileName(), md2.getFileName(), String::compareTo);
        if (value != 0) {
            return value;
        }
        // Compare sources
        value = Objects.compare(md1.getSource(), md2.getSource(), String::compareTo);
        if (value != 0) {
            return value;
        }
        // Compare types
        value = Objects.compare(md1.getBlobType(), md2.getBlobType(), Enum::compareTo);
        if (value != 0) {
            return value;
        }
        return 0;
    }

    private UUID writeNewBlob(AppBlob blob) {
        // Hash file does not yet exist. Simply generate a new UUID
        final UUID newId = BlobMetadata.generateUUID();
        blob.getMetadata().setId(newId);
        doWrite(blob);
        return newId;
    }

    private void writeHash(int hash, UUID id) throws IOException {
        final String idStr = id.toString();

        final File hashFile = hashFile(hash);
        final Path path = hashFile.toPath();

        final List<String> lines = new ArrayList<>();
        boolean found = false;
        if (hashFile.exists()) {
            List<String> existingLines = Files.readAllLines(path);
            for (String line : existingLines) {
                if (idStr.equals(line)) {
                    found = true;
                }
                lines.add(line);
            }
        }
        if (!found) {
            lines.add(idStr);
        }
        Files.write(path, lines, StandardOpenOption.WRITE, StandardOpenOption.CREATE);
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
        } catch (JAXBException | IOException e) {
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

    private File hashFile(int hash) {
        return new File(hashFileName(hash));
    }

    private String metadataFileName(UUID id) {
        return storeDirectory + File.separator + id + ".md";
    }

    private String byteFileName(UUID id) {
        return storeDirectory + File.separator + id + ".dat";
    }

    private String hashFileName(int hash) {
        return storeDirectory + File.separator + Integer.toString(hash) + ".hash";
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

    private byte[] readBytes(UUID id) throws IOException {
        File byteFile = byteFile(id);
        return Files.readAllBytes(byteFile.toPath());
    }

}
