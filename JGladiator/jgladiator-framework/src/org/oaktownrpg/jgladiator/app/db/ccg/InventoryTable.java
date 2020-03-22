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

/**
 * Local card inventory. A collection of card singles.
 * 
 * @author michaelmartak
 *
 */
@DatabaseTableDefinition
enum InventoryTable {

    @DatabaseColumn(type = DataType.VARCHAR, max = 3)
    @NotNull
    @PrimaryKey
    @ForeignKey(table = "CCG", fields = "CCG_ID")
    CCG_ID,

    /**
     * The card set the printed card belongs to
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 255)
    @NotNull
    @PrimaryKey
    @ForeignKey(table = "CARD_SET", fields = { "CCG_ID", "CARD_SET_ID" })
    CARD_SET_ID,

    /**
     * The locale of the printed card
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 6)
    @NotNull
    @PrimaryKey
    @ForeignKey(table = "LOCALE", fields = "LOCALE_CODE")
    LOCALE,

    /**
     * The card's identity in the larger scope of the Ccg
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 255)
    @NotNull
    @PrimaryKey
    @ForeignKey(table = "CARD_IDENTITY", fields = { "CCG_ID", "CARD_ID" })
    CARD_ID,

    /**
     * Any variant in the card printing that makes it unique within a set (e.g.,
     * different art, etc.)
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 10)
    @PrimaryKey
    @ForeignKey(table = "CARD_PRINT", fields = { "CCG_ID", "CARD_SET_ID", "LOCALE", "CARD_ID", "PRINT_VARIANT" })
    PRINT_VARIANT,

    /**
     * Specific material variant for this card
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 10)
    @PrimaryKey
    MATERIAL_VARIANT,

    /**
     * Card condition
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 3)
    @PrimaryKey
    CONDITION,

    /**
     * Count of these cards
     */
    @DatabaseColumn(type = DataType.INTEGER)
    QUANTITY

}
