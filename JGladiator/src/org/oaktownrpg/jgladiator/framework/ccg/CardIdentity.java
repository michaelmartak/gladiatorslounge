/**
 * 
 */
package org.oaktownrpg.jgladiator.framework.ccg;

/**
 * Card identification information.
 * 
 * @author michaelmartak
 *
 */
public final class CardIdentity {

    private Ccg ccg;
    /**
     * Identifying string for the card. Names uniquely identify the card within the
     * game. Names are in the language the card game originated from (e.g., MtG =
     * English)
     */
    private String cardId;
    private String type;
    private String altType;
    private String manaCost;
    private String altManaCost;
    private String cmc;
    private String altCmc;
    private String color;
    private String altColor;
    private String colorIdentity;
    private String power;
    private String toughness;
    private String oracleText;
    private String altOracleText;
    private String rulings;

    CardIdentity() {
    }

    public Ccg getCcg() {
        return ccg;
    }

    void setCcg(Ccg ccg) {
        this.ccg = ccg;
    }

    public String getCardId() {
        return cardId;
    }

    void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getType() {
        return type;
    }

    void setType(String type) {
        this.type = type;
    }

    public String getAltType() {
        return altType;
    }

    void setAltType(String altType) {
        this.altType = altType;
    }

    public String getManaCost() {
        return manaCost;
    }

    void setManaCost(String manaCost) {
        this.manaCost = manaCost;
    }

    public String getAltManaCost() {
        return altManaCost;
    }

    void setAltManaCost(String altManaCost) {
        this.altManaCost = altManaCost;
    }

    public String getCmc() {
        return cmc;
    }

    void setCmc(String cmc) {
        this.cmc = cmc;
    }

    public String getAltCmc() {
        return altCmc;
    }

    void setAltCmc(String altCmc) {
        this.altCmc = altCmc;
    }

    public String getColor() {
        return color;
    }

    void setColor(String color) {
        this.color = color;
    }

    public String getAltColor() {
        return altColor;
    }

    void setAltColor(String altColor) {
        this.altColor = altColor;
    }

    public String getColorIdentity() {
        return colorIdentity;
    }

    void setColorIdentity(String colorIdentity) {
        this.colorIdentity = colorIdentity;
    }

    public String getPower() {
        return power;
    }

    void setPower(String power) {
        this.power = power;
    }

    public String getToughness() {
        return toughness;
    }

    void setToughness(String toughness) {
        this.toughness = toughness;
    }

    public String getOracleText() {
        return oracleText;
    }

    void setOracleText(String oracleText) {
        this.oracleText = oracleText;
    }

    public String getAltOracleText() {
        return altOracleText;
    }

    void setAltOracleText(String altOracleText) {
        this.altOracleText = altOracleText;
    }

    public String getRulings() {
        return rulings;
    }

    void setRulings(String rulings) {
        this.rulings = rulings;
    }

}
