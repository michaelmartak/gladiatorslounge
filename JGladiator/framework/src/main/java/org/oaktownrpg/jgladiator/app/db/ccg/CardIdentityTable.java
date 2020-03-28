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
     * <p/>
     * Using Scryfall's UUIDs as a source of truth for card set identity.
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 40)
    @NotNull
    @PrimaryKey
    CARD_ID,

    /**
     * Card types and/or subtypes
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 255)
    TYPE,

    /**
     * Alternate types and/or subtypes
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 255)
    ALT_TYPE,

    /**
     * Primary cost (for example, MtG = mana cost)
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 48)
    MANA_COST,

    /**
     * Secondary cost (for example, MtG = CMC)
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 48)
    CMC,

    /**
     * Alternate cost (for example, MtG = Adventure cost)
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 48)
    ALT_MANA_COST,

    /**
     * Secondary cost (for example, MtG = Adventure CMC)
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 48)
    ALT_CMC,

    /**
     * Searchable attribute (MtG = Color)
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 32)
    COLOR,

    /**
     * Alternate color
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 32)
    ALT_COLOR,

    /**
     * Searchable attribute (MtG = Color identity (e.g., Commander))
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 32)
    COLOR_IDENTITY,

    /**
     * Second searchable attribute (MtG = Power)
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 8)
    POWER,

    /**
     * Third searchable attribute (MtG = Toughness)
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 8)
    TOUGHNESS,

    /**
     * The oracle text for a card. Does not include rules clarifications
     * or interpretations. English only.
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 1024)
    ORACLE_TEXT,

    /**
     * The alternate oracle text for a card.
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 1024)
    ALT_ORACLE_TEXT,

    /**
     * Rules clarifications and interpretations.
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 5280)
    RULINGS,

}
