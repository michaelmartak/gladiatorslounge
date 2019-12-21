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
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 255)
    @NotNull
    @PrimaryKey
    CARD_SET_ID,

    /**
     * Date the card set was released, if applicable.
     */
    @DatabaseColumn(type = DataType.DATE)
    RELEASE_DATE,

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

    /**
     * Last update of this card set information in the database
     */
    @DatabaseColumn(type = DataType.TIMESTAMP)
    LAST_UPDATE

}
