/**
 * 
 */
package org.oaktownrpg.jgladiator.app.db.ccg;

import org.oaktownrpg.jgladiator.app.db.DataType;
import org.oaktownrpg.jgladiator.app.db.DatabaseColumn;
import org.oaktownrpg.jgladiator.app.db.NotNull;
import org.oaktownrpg.jgladiator.app.db.PrimaryKey;

/**
 * Columns in the locale table.
 * 
 * @author michaelmartak
 *
 */
enum LocaleTableColumn {

    @DatabaseColumn(type = DataType.VARCHAR, max = 6)
    @NotNull
    @PrimaryKey
    LOCALE_CODE

}
