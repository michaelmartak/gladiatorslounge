package org.oaktownrpg.jgladiator.app.db.ccg;

import org.oaktownrpg.jgladiator.app.db.DataType;
import org.oaktownrpg.jgladiator.app.db.DatabaseColumn;
import org.oaktownrpg.jgladiator.app.db.NotNull;
import org.oaktownrpg.jgladiator.app.db.PrimaryKey;

/**
 * Table columns in the CCG table.
 * 
 * @author michaelmartak
 *
 */
enum CcgTableColumn {

    @DatabaseColumn(type = DataType.VARCHAR, max = 3)
    @NotNull
    @PrimaryKey
    CCG_ID

}
