/**
 * 
 */
package org.oaktownrpg.jgladiator.framework.ccg;

import org.oaktownrpg.jgladiator.framework.BlobType;

/**
 * Symbol for a {@link CardSet}
 * 
 * @author michaelmartak
 *
 */
public final class CardSetSymbol {

    private byte[] bytes;
    private BlobType type;
    private String name;
    private String altText;
    private String source;

    /**
     * 
     */
    public CardSetSymbol() {
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public BlobType getType() {
        return type;
    }

    public void setType(BlobType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAltText() {
        return altText;
    }

    public void setAltText(String altText) {
        this.altText = altText;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Override
    public String toString() {
        return "CardSetSymbol [type=" + type + ", name=" + name + ", altText=" + altText + ", source=" + source + "]";
    }

}
