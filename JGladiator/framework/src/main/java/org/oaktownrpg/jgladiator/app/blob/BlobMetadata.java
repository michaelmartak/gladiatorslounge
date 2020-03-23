/**
 * 
 */
package org.oaktownrpg.jgladiator.app.blob;

import java.util.Objects;
import java.util.UUID;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import org.oaktownrpg.jgladiator.framework.BlobType;

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
public final class BlobMetadata {

    private UUID id;
    private BlobType blobType;
    private String fileName;
    private String altText;
    private long size;
    private int hash;
    private String source;

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
    BlobMetadata(BlobType blobType, String fileName, String altText, long size, int hash, String source) {
        this(generateUUID(), blobType, fileName, altText, size, hash, source);
    }

    /**
     * 
     */
    public BlobMetadata(UUID id, BlobType blobType, String fileName, String altText, long size, int hash,
            String source) {
        this.id = id;
        this.blobType = blobType;
        this.fileName = fileName;
        this.altText = altText;
        this.size = size;
        this.hash = hash;
        this.source = source;
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

    /**
     * Hash value
     * 
     * @return
     */
    @XmlElement
    public int getHash() {
        return hash;
    }

    public void setHash(int hash) {
        this.hash = hash;
    }

    /**
     * Origin source of the BLOB
     * 
     * @return
     */
    @XmlElement
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public int hashCode() {
        return Objects.hash(altText, blobType, fileName, hash, id, size, source);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BlobMetadata other = (BlobMetadata) obj;
        return Objects.equals(altText, other.altText) && blobType == other.blobType
                && Objects.equals(fileName, other.fileName) && hash == other.hash && Objects.equals(id, other.id)
                && size == other.size && Objects.equals(source, other.source);
    }

}
