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

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((altText == null) ? 0 : altText.hashCode());
        result = prime * result + ((blobType == null) ? 0 : blobType.hashCode());
        result = prime * result + ((fileName == null) ? 0 : fileName.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + (int) (size ^ (size >>> 32));
        return result;
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
        if (altText == null) {
            if (other.altText != null)
                return false;
        } else if (!altText.equals(other.altText))
            return false;
        if (blobType != other.blobType)
            return false;
        if (fileName == null) {
            if (other.fileName != null)
                return false;
        } else if (!fileName.equals(other.fileName))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (size != other.size)
            return false;
        return true;
    }

}
