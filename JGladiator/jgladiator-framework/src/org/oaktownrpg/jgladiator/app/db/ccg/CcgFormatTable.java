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
 * A format for games of a specific CCG to be played in.
 * 
 * @author michaelmartak
 *
 */
@DatabaseTableDefinition
enum CcgFormatTable {

    @DatabaseColumn(type = DataType.VARCHAR, max = 3)
    @NotNull
    @PrimaryKey
    @ForeignKey(table = "CCG", fields = "CCG_ID")
    CCG_ID,

    /**
     * Format, usually stored as an easy-to-lookup Enum value.
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 24)
    @NotNull
    @PrimaryKey
    CCG_FORMAT_ID

}
