/**
 * 
 */
package org.oaktownrpg.jgladiator.app.blob;

import java.util.UUID;

/**
 * Blob for storage in the BLOB store
 * @author michaelmartak
 *
 */
public class AppBlob {

    private final BlobMetadata metadata;
    private final byte[] bytes;

    /**
     * 
     */
    public AppBlob(BlobMetadata metadata, byte[] bytes) {
        this.metadata = metadata;
        this.bytes = bytes;
    }

    public UUID getId() {
        return metadata.getId();
    }

    public BlobMetadata getMetadata() {
        return metadata;
    }
    
    public byte[] getBytes() {
        return bytes;
    }

}
