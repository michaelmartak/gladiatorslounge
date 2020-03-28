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
    private String code;
    private String information;
    private String parentSetCode;
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

    public String getParentSetCode() {
        return parentSetCode;
    }

    void setParentSetCode(String parentSetCode) {
        this.parentSetCode = parentSetCode;
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

    public String getCode() {
        return code;
    }

    void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "CardSet [ccg=" + ccg + ", id=" + id + ", code=" + code + ", releaseDate="
                + releaseDate + ", symbol=" + symbol + "]";
    }

}
