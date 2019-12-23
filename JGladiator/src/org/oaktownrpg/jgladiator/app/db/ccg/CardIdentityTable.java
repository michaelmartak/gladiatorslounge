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
     * Card types and/or subtypes
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 255)
    TYPE,

    /**
     * Primary cost (for example, MtG = mana cost, including color)
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 32)
    MANA_COST,

    /**
     * Secondary cost (for example, MtG = CMC)
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 32)
    CMC,

    /**
     * First searchable attribute (MtG = Color)
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 32)
    COLOR,

    /**
     * Second searchable attribute (MtG = Power)
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 32)
    POWER,

    /**
     * Third searchable attribute (MtG = Toughness)
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 32)
    TOUGHNESS,

    @DatabaseColumn(type = DataType.VARCHAR, max = 32)
    ATTRIBUTE_D,

    @DatabaseColumn(type = DataType.VARCHAR, max = 32)
    ATTRIBUTE_E,
    
    @DatabaseColumn(type = DataType.VARCHAR, max = 32)
    ATTRIBUTE_F,
    
    /**
     * The (up to date) rules text for a card. Does not include rules clarifications
     * or interpretations. English only.
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 1024)
    ORACLE_TEXT,

    /**
     * The flavor text for a card.
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 1024)
    FLAVOR_TEXT,

    /**
     * Rules clarifications and interpretations.
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 5280)
    RULINGS,

}
