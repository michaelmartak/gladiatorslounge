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
enum CardSetLocaleTableColumn {

    @DatabaseColumn(type = DataType.VARCHAR, max = 3)
    @NotNull
    @PrimaryKey
    @ForeignKey(table = "CCG", fields = "CCG_ID")
    CCG_ID,

    @DatabaseColumn(type = DataType.VARCHAR, max = 255)
    @NotNull
    @PrimaryKey
    @ForeignKey(table = "CARD_SET", fields = { "CCG_ID", "CARD_SET_ID" })
    CARD_SET_ID,

    @DatabaseColumn(type = DataType.VARCHAR, max = 6)
    @NotNull
    @PrimaryKey
    @ForeignKey(table = "LOCALE", fields = "LOCALE_CODE")
    LOCALE

}
