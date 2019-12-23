/**
 * 
 */
package org.oaktownrpg.jgladiator.framework.ccg;

import java.util.Date;
import java.util.Set;

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
    private String information;
    private String parentCardSet;
    private CardSetSymbol symbol;
    private Set<String> languages;

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

    public String getParentCardSet() {
        return parentCardSet;
    }

    void setParentCardSet(String parentCardSet) {
        this.parentCardSet = parentCardSet;
    }

    public String getInformation() {
        return information;
    }

    void setInformation(String information) {
        this.information = information;
    }

    public CardSetSymbol getSymbol() {
        return symbol;
    }

    void setSymbol(CardSetSymbol symbol) {
        this.symbol = symbol;
    }

    public Set<String> getLanguages() {
        return languages;
    }

    void setLanguages(Set<String> languages) {
        this.languages = languages;
    }

    @Override
    public String toString() {
        return "CardSet [ccg=" + ccg + ", id=" + id + ", releaseDate=" + releaseDate + ", symbol=" + symbol + "]";
    }

}
