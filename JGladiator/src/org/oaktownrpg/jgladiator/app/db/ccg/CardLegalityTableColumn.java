/**
 * 
 */
package org.oaktownrpg.jgladiator.app.db.ccg;

import org.oaktownrpg.jgladiator.app.db.DataType;
import org.oaktownrpg.jgladiator.app.db.DatabaseColumn;
import org.oaktownrpg.jgladiator.app.db.ForeignKey;
import org.oaktownrpg.jgladiator.app.db.NotNull;
import org.oaktownrpg.jgladiator.app.db.PrimaryKey;

/**
 * @author michaelmartak
 *
 */
enum CardLegalityTableColumn {

    @DatabaseColumn(type = DataType.VARCHAR, max = 3)
    @NotNull
    @PrimaryKey
    @ForeignKey(table = "CCG", fields = "CCG_ID")
    CCG_ID,

    @DatabaseColumn(type = DataType.VARCHAR, max = 255)
    @NotNull
    @PrimaryKey
    @ForeignKey(table = "CARD_IDENTITY", fields = { "CCG_ID", "CARD_ID" })
    CARD_ID,

    @DatabaseColumn(type = DataType.VARCHAR, max = 24)
    @NotNull
    @PrimaryKey
    @ForeignKey(table = "CCG_FORMAT", fields = { "CCG_ID", "CCG_FORMAT_ID" })
    CCG_FORMAT_ID,

    @DatabaseColumn(type = DataType.INTEGER)
    LEGALITY

}
