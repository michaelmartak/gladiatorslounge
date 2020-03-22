/**
 * 
 */
package org.oaktownrpg.jgladiator.framework.ccg;

/**
 * @author michaelmartak
 *
 */
public final class CardIdentityBuilder {

    private final CardIdentity identity = new CardIdentity();

    /**
     * 
     */
    public CardIdentityBuilder() {
    }

    public CardIdentityBuilder ccg(Ccg ccg) {
        identity.setCcg(ccg);
        return this;
    }

    public CardIdentityBuilder cardId(String cardId) {
        identity.setCardId(cardId);
        return this;
    }

    public CardIdentityBuilder type(String type) {
        identity.setType(type);
        return this;
    }

    public CardIdentityBuilder altType(String altType) {
        identity.setAltType(altType);
        return this;
    }

    public CardIdentityBuilder manaCost(String manaCost) {
        identity.setManaCost(manaCost);
        return this;
    }

    public CardIdentityBuilder altManaCost(String altManaCost) {
        identity.setAltManaCost(altManaCost);
        return this;
    }

    public CardIdentityBuilder cmc(String cmc) {
        identity.setCmc(cmc);
        return this;
    }

    public CardIdentityBuilder altCmc(String altCmc) {
        identity.setAltCmc(altCmc);
        return this;
    }

    public CardIdentityBuilder color(String color) {
        identity.setColor(color);
        return this;
    }

    public CardIdentityBuilder altColor(String altColor) {
        identity.setAltColor(altColor);
        return this;
    }

    public CardIdentityBuilder colorIdentity(String colorIdentity) {
        identity.setColorIdentity(colorIdentity);
        return this;
    }

    public CardIdentityBuilder power(String power) {
        identity.setPower(power);
        return this;
    }

    public CardIdentityBuilder toughness(String toughness) {
        identity.setToughness(toughness);
        return this;
    }

    public CardIdentityBuilder oracleText(String oracleText) {
        identity.setOracleText(oracleText);
        return this;
    }

    public CardIdentityBuilder altOracleText(String altOracleText) {
        identity.setAltOracleText(altOracleText);
        return this;
    }

    public CardIdentityBuilder rulings(String rulings) {
        identity.setRulings(rulings);
        return this;
    }

    public CardIdentity build() {
        return identity;
    }

}
