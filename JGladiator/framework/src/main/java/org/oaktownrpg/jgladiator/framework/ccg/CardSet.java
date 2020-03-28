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
    private String code;
    private String mtgoCode;
    private String arenaCode;
    private Integer tcgPlayerId;
    private String name;
    private CardSetType type;
    private Date releaseDate;
    private String blockCode;
    private String block;
    private String parentSetCode;
    private int cardCount;
    private boolean digital;
    private boolean foilOnly;
    private String information;
    private CardSetSymbol symbol;
    private Set<String> languages;

    public CardSet() {
    }

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

    public String getParentSetCode() {
        return parentSetCode;
    }

    public void setParentSetCode(String parentSetCode) {
        this.parentSetCode = parentSetCode;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public CardSetSymbol getSymbol() {
        return symbol;
    }

    public void setSymbol(CardSetSymbol symbol) {
        this.symbol = symbol;
    }

    public Set<String> getLanguages() {
        return languages;
    }

    public void setLanguages(Set<String> languages) {
        this.languages = languages;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMtgoCode() {
        return mtgoCode;
    }

    public void setMtgoCode(String mtgoCode) {
        this.mtgoCode = mtgoCode;
    }

    public String getArenaCode() {
        return arenaCode;
    }

    public void setArenaCode(String arenaCode) {
        this.arenaCode = arenaCode;
    }

    public Integer getTcgPlayerId() {
        return tcgPlayerId;
    }

    public void setTcgPlayerId(Integer tcgPlayerId) {
        this.tcgPlayerId = tcgPlayerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CardSetType getType() {
        return type;
    }

    public void setType(CardSetType type) {
        this.type = type;
    }

    public String getBlockCode() {
        return blockCode;
    }

    public void setBlockCode(String blockCode) {
        this.blockCode = blockCode;
    }

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public int getCardCount() {
        return cardCount;
    }

    public void setCardCount(int cardCount) {
        this.cardCount = cardCount;
    }

    public boolean isDigital() {
        return digital;
    }

    public void setDigital(boolean digital) {
        this.digital = digital;
    }

    public boolean isFoilOnly() {
        return foilOnly;
    }

    public void setFoilOnly(boolean foilOnly) {
        this.foilOnly = foilOnly;
    }

    @Override
    public String toString() {
        return "CardSet [ccg=" + ccg + ", id=" + id + ", code=" + code + ", mtgoCode=" + mtgoCode + ", arenaCode="
                + arenaCode + ", tcgPlayerId=" + tcgPlayerId + ", name=" + name + ", type=" + type + ", releaseDate="
                + releaseDate + ", blockCode=" + blockCode + ", block=" + block + ", parentSetCode=" + parentSetCode
                + ", cardCount=" + cardCount + ", digital=" + digital + ", foilOnly=" + foilOnly + ", symbol=" + symbol
                + "]";
    }

}
