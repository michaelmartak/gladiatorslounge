package org.oaktownrpg.jgladiator.app.db.ccg;

import org.oaktownrpg.jgladiator.app.db.annotation.DataType;
import org.oaktownrpg.jgladiator.app.db.annotation.DatabaseColumn;
import org.oaktownrpg.jgladiator.app.db.annotation.DatabaseTableDefinition;
import org.oaktownrpg.jgladiator.app.db.annotation.NotNull;
import org.oaktownrpg.jgladiator.app.db.annotation.PrimaryKey;

/**
 * Table definition for the CCG table.
 * <p/>
 * A collectible card game, virtual or otherwise.
 * 
 * @author michaelmartak
 *
 */
@DatabaseTableDefinition
enum CcgTable {

    /**
     * Identifier for the card game (e.g., "MTG" for Magic: the Gathering).
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 3)
    @NotNull
    @PrimaryKey
    CCG_ID

}
