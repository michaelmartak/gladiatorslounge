/**
 * 
 */
package org.oaktownrpg.jgladiator.framework.ccg;

import org.oaktownrpg.jgladiator.framework.BlobType;

/**
 * @author michaelmartak
 *
 */
public class CardSetSymbolBuilder {

    private final CardSetBuilder setBuilder;
    private final CardSetSymbol symbol = new CardSetSymbol();

    /**
     * 
     */
    CardSetSymbolBuilder(CardSetBuilder setBuilder) {
        this.setBuilder = setBuilder;
        setBuilder.setSymbol(symbol);
    }

    public CardSetBuilder cardSet() {
        return setBuilder;
    }

    public CardSetSymbolBuilder type(BlobType type) {
        symbol.setType(type);
        return this;
    }

    public CardSetSymbolBuilder bytes(byte[] bytes) {
        symbol.setBytes(bytes);
        return this;
    }

    public CardSetSymbolBuilder name(String name) {
        symbol.setName(name);
        return this;
    }

    public CardSetSymbolBuilder altText(String altText) {
        symbol.setAltText(altText);
        return this;
    }

    public CardSetSymbolBuilder source(String source) {
        symbol.setSource(source);
        return this;
    }

}
