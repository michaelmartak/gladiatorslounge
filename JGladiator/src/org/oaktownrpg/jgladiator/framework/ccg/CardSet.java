/**
 * 
 */
package org.oaktownrpg.jgladiator.framework.ccg;

import java.util.Date;

import org.oaktownrpg.jgladiator.framework.BlobType;

/**
 * Identifying set of cards
 * 
 * @author michaelmartak
 *
 */
public final class CardSet {

    private Ccg ccg;
    private String id;
    private Date releaseDate;
    private byte[] cardSetSymbolBytes;
    private BlobType cardSetSymbolType;
    private String cardSetSymbolName;
    private String cardSetInformation;

    CardSet() {
    }
    
    public Ccg getCcg() {
        return ccg;
    }

    void setCcg(Ccg ccg) {
        this.ccg = ccg;
    }

    public String getId() {
        return id;
    }

    void setId(String id) {
        this.id = id;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public byte[] getCardSetSymbolBytes() {
        return cardSetSymbolBytes;
    }

    void setCardSetSymbolBytes(byte[] cardSetSymbolBytes) {
        this.cardSetSymbolBytes = cardSetSymbolBytes;
    }

    public BlobType getCardSetSymbolType() {
        return cardSetSymbolType;
    }

    void setCardSetSymbolType(BlobType cardSetSymbolType) {
        this.cardSetSymbolType = cardSetSymbolType;
    }
    
    public String getCardSetSymbolName() {
        return cardSetSymbolName;
    }

    void setCardSetSymbolName(String cardSetSymbolName) {
        this.cardSetSymbolName = cardSetSymbolName;
    }

    public String getCardSetInformation() {
        return cardSetInformation;
    }

    void setSetInformation(String cardSetInformation) {
        this.cardSetInformation = cardSetInformation;
    }

    @Override
    public String toString() {
        return "CardSet [ccg=" + ccg + ", id=" + id + ", releaseDate=" + releaseDate + ", cardSetSymbolType="
                + cardSetSymbolType + "]";
    }

}
