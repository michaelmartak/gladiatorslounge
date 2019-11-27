/**
 * 
 */
package org.oaktownrpg.jgladiator.app.blob;

import java.util.UUID;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.uuid.Generators;

/**
 * Metadata for BLOBs here in the blob storage.
 * <p/>
 * This metadata could be stored in the local DB, but it "belongs" with the Blob
 * itself, since the storage for those BLOBs is separate and distinct.
 * 
 * @author michaelmartak
 *
 */
@XmlRootElement
public class BlobMetadata {

    private UUID id;
    private BlobType blobType;
    private String fileName;
    private String altText;
    private long size;

    /**
     * Generates a UUID that is guaranteed to be unique for this machine.
     * 
     * @return a new UUID.
     */
    public static UUID generateUUID() {
        return Generators.timeBasedGenerator().generate();
    }

    /**
     * Public no-arg constructor for marshalling / unmarshalling
     */
    public BlobMetadata() {
    }

    /**
     * 
     */
    public BlobMetadata(BlobType blobType, String fileName, String altText, long size) {
        this(generateUUID(), blobType, fileName, altText, size);
    }

    /**
     * 
     */
    public BlobMetadata(UUID id, BlobType blobType, String fileName, String altText, long size) {
        this.id = id;
        this.blobType = blobType;
        this.fileName = fileName;
        this.altText = altText;
        this.size = size;
    }

    @XmlAttribute
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @XmlElement
    public BlobType getBlobType() {
        return blobType;
    }

    public void setBlobType(BlobType blobType) {
        this.blobType = blobType;
    }

    @XmlElement
    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    @XmlElement
    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    /**
     * Size in bytes
     * 
     * @return
     */
    @XmlElement
    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

}
