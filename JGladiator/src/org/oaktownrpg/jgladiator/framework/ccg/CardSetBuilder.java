/**
 * 
 */
package org.oaktownrpg.jgladiator.framework.ccg;

import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

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

    void setSymbol(CardSetSymbol symbol) {
        cardSet.setSymbol(symbol);
    }

    public CardSetBuilder ccg(Ccg ccg) {
        cardSet.setCcg(ccg);
        return this;
    }

    public CardSetBuilder id(String id) {
        cardSet.setId(id);
        return this;
    }

    public CardSetBuilder releaseDate(Date date) {
        cardSet.setReleaseDate(date);
        return this;
    }

    public CardSetBuilder expansionCode(String expansionCode) {
        cardSet.setExpansionCode(expansionCode);
        return this;
    }

    public CardSetBuilder parentCardSet(String id) {
        cardSet.setParentCardSet(id);
        return this;
    }

    public CardSetBuilder languages(Set<String> languages) {
        cardSet.setLanguages(Collections.unmodifiableSet(new LinkedHashSet<>(languages)));
        return this;
    }

    public CardSetSymbolBuilder symbol() {
        return new CardSetSymbolBuilder(this);
    }
    
    public CardSet build() {
        return cardSet;
    }

}
