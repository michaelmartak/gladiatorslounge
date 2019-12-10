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
    private String cardSetInformation;

    public Ccg getCcg() {
        return ccg;
    }

    public void setCcg(Ccg ccg) {
        this.ccg = ccg;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public byte[] getCardSetSymbolBytes() {
        return cardSetSymbolBytes;
    }

    public void setCardSetSymbolBytes(byte[] cardSetSymbolBytes) {
        this.cardSetSymbolBytes = cardSetSymbolBytes;
    }

    public BlobType getCardSetSymbolType() {
        return cardSetSymbolType;
    }

    public void setCardSetSymbolType(BlobType cardSetSymbolType) {
        this.cardSetSymbolType = cardSetSymbolType;
    }

    public String getCardSetInformation() {
        return cardSetInformation;
    }

    public void setSetInformation(String cardSetInformation) {
        this.cardSetInformation = cardSetInformation;
    }

}
