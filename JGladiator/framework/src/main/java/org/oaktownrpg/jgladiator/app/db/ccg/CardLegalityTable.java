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
 * Rules that govern which format(s) a card can be legally played in. Includes
 * concepts such as "Restricted" or "Banned", generally relevant to the Ccg.
 * 
 * @author michaelmartak
 *
 */
@DatabaseTableDefinition
enum CardLegalityTable {

    @DatabaseColumn(type = DataType.VARCHAR, max = 3)
    @NotNull
    @PrimaryKey
    @ForeignKey(table = "CCG", fields = "CCG_ID")
    CCG_ID,

    @DatabaseColumn(type = DataType.VARCHAR, max = 40)
    @NotNull
    @PrimaryKey
    @ForeignKey(table = "CARD_IDENTITY", fields = { "CCG_ID", "CARD_ID" })
    CARD_ID,

    @DatabaseColumn(type = DataType.VARCHAR, max = 24)
    @NotNull
    @PrimaryKey
    @ForeignKey(table = "CCG_FORMAT", fields = { "CCG_ID", "CCG_FORMAT_ID" })
    CCG_FORMAT_ID,

    /**
     * @see org.oaktownrpg.jgladiator.app.mtg.MtgFormatLegality
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 10)
    LEGALITY

}
