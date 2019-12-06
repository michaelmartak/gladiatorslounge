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
 * Identity of a unique card within a Ccg. Things such as "rules" printed in a
 * card are generally associated with its identity, not a specific printing of a
 * card.
 * 
 * @author michaelmartak
 *
 */
@DatabaseTableDefinition
enum CardIdentityTable {

    @DatabaseColumn(type = DataType.VARCHAR, max = 3)
    @NotNull
    @PrimaryKey
    @ForeignKey(table = "CCG", fields = "CCG_ID")
    CCG_ID,

    /**
     * Unique identifier for a card within a Ccg.
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 255)
    @NotNull
    @PrimaryKey
    CARD_ID,

    /**
     * The (up to date) rules text for a card. Does not include rules clarifications
     * or interpretations.
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 1024)
    ORACLE_TEXT,

    /**
     * Last update of this card's identity in the database
     */
    @DatabaseColumn(type = DataType.TIMESTAMP)
    LAST_UPDATE

}
