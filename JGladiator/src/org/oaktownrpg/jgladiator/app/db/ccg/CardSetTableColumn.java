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
enum CardSetTableColumn {

    @DatabaseColumn(type = DataType.VARCHAR, max = 3)
    @NotNull
    @PrimaryKey
    @ForeignKey(table = "CCG", fields = "CCG_ID")
    CCG_ID,

    @DatabaseColumn(type = DataType.VARCHAR, max = 255)
    @NotNull
    @PrimaryKey
    CARD_SET_ID,

    @DatabaseColumn(type = DataType.DATE)
    @NotNull
    RELEASE_DATE

}
