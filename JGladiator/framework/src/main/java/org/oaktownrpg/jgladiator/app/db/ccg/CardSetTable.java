/**
 * 
 */
package org.oaktownrpg.jgladiator.app.db.ccg;

import org.oaktownrpg.jgladiator.app.db.annotation.DataType;
import org.oaktownrpg.jgladiator.app.db.annotation.DatabaseColumn;
import org.oaktownrpg.jgladiator.app.db.annotation.DatabaseTableDefinition;
import org.oaktownrpg.jgladiator.app.db.annotation.ForeignKey;
import org.oaktownrpg.jgladiator.app.db.annotation.NotNull;
import org.oaktownrpg.jgladiator.app.db.annotation.PrimaryKey;
import org.oaktownrpg.jgladiator.app.db.annotation.Unique;

/**
 * A fixed, defined collection of cards within a Ccg.
 * 
 * @author michaelmartak
 *
 */
@DatabaseTableDefinition
enum CardSetTable {

    @DatabaseColumn(type = DataType.VARCHAR, max = 3)
    @NotNull
    @PrimaryKey
    @ForeignKey(table = "CCG", fields = "CCG_ID")
    CCG_ID,

    /**
     * Uniquely identifies the card set.
     * <p/>
     * Using Scryfall's UUIDs as a source of truth for card set identity.
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 40)
    @NotNull
    @PrimaryKey
    CARD_SET_ID,

    /**
     * Expansion code
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 8)
    @Unique(fields = { "CCG_ID" })
    CODE,

    /**
     * MTGO code
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 8)
    MTGO_CODE,

    /**
     * Arena code
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 8)
    ARENA_CODE,

    /**
     * TCGPlayer ID (groupId)
     */
    @DatabaseColumn(type = DataType.INTEGER)
    TCGPLAYER_ID,

    /**
     * The English name of the set
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 255)
    NAME,

    /**
     * The type / classification of set
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 25)
    SET_TYPE,

    /**
     * Date the card set was released, if applicable.
     */
    @DatabaseColumn(type = DataType.DATE)
    RELEASE_DATE,

    /**
     * Block code
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 8)
    BLOCK_CODE,

    /**
     * Block
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 127)
    BLOCK,

    /**
     * If this is a subset of a larger set, the short code of the parent set.
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 8)
    @ForeignKey(table = "CARD_SET", fields = { "CCG_ID", "CODE" })
    PARENT_SET_CODE,

    /**
     * UUID of the card set symbol, if present and applicable.
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 40)
    SYMBOL_REF,

    /**
     * Basic set information. Up to the Ccg to define how to store and display.
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 1024)
    INFO,

}
