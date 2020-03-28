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
 * The locales that are available for a specific card set.
 * 
 * @author michaelmartak
 *
 */
@DatabaseTableDefinition
enum CardSetLocaleTable {

    @DatabaseColumn(type = DataType.VARCHAR, max = 3)
    @NotNull
    @PrimaryKey
    @ForeignKey(table = "CCG", fields = "CCG_ID")
    CCG_ID,

    @DatabaseColumn(type = DataType.VARCHAR, max = 40)
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
