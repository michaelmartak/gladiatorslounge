/**
 * 
 */
package org.oaktownrpg.jgladiator.framework.ccg;

import java.util.Date;

import org.oaktownrpg.jgladiator.framework.BlobType;

/**
 * Builds a card set
 * 
 * @author michaelmartak
 *
 */
public class CardSetBuilder {

    private CardSet cardSet = new CardSet();

    /**
     * 
     */
    public CardSetBuilder() {
    }

    public CardSetBuilder ccg(Ccg ccg) {
        cardSet.setCcg(ccg);
        return this;
    }

    public CardSetBuilder symbolType(BlobType type) {
        cardSet.setCardSetSymbolType(type);
        return this;
    }

    public CardSetBuilder id(String id) {
        cardSet.setId(id);
        return this;
    }

    public CardSetBuilder symbolBytes(byte[] bytes) {
        cardSet.setCardSetSymbolBytes(bytes);
        return this;
    }

    public CardSetBuilder releaseDate(Date date) {
        cardSet.setReleaseDate(date);
        return this;
    }

    public CardSetBuilder symbolName(String name) {
        cardSet.setCardSetSymbolName(name);
        return this;
    }

    public CardSet build() {
        return cardSet;
    }

}
