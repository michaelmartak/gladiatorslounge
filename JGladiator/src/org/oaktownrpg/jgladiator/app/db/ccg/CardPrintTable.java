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
 * A specific printing of a card.
 * 
 * @author michaelmartak
 *
 */
@DatabaseTableDefinition
enum CardPrintTable {

    @DatabaseColumn(type = DataType.VARCHAR, max = 3)
    @NotNull
    @PrimaryKey
    @ForeignKey(table = "CCG", fields = "CCG_ID")
    CCG_ID,

    /**
     * The card set the printed card belongs to
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 255)
    @NotNull
    @PrimaryKey
    @ForeignKey(table = "CARD_SET", fields = { "CCG_ID", "CARD_SET_ID" })
    CARD_SET_ID,

    /**
     * The locale of the printed card
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 6)
    @NotNull
    @PrimaryKey
    @ForeignKey(table = "LOCALE", fields = "LOCALE_CODE")
    LOCALE,

    /**
     * The card's identity in the larger scope of the Ccg
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 255)
    @NotNull
    @PrimaryKey
    @ForeignKey(table = "CARD_IDENTITY", fields = { "CCG_ID", "CARD_ID" })
    CARD_ID,

    /**
     * Any variant in the card printing that makes it unique within a set (e.g.,
     * different art, etc.)
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 10)
    @PrimaryKey
    PRINT_VARIANT,

    /**
     * The number of the card printing in the set, if the set has a numerical order.
     */
    @DatabaseColumn(type = DataType.INTEGER)
    SET_NUMBER,

    /**
     * Localized title of the card. In English, this is the same as the Card ID.
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 255)
    TITLE,

    /**
     * Localized card types and/or subtypes, if different from the Card Identity.
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 255)
    TYPE,

    /**
     * Localized Oracle Text, if different from the Card Identity.
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 1024)
    ORACLE_TEXT,

    /**
     * The localized flavor text for a card, if different from the Card Identity.
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 1024)
    FLAVOR_TEXT,

    /**
     * The rarity of the card within the set, if applicable.
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 10)
    RARITY,

    /**
     * UUID of the front art in the BLOB storage, if present and applicable.
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 40)
    FRONT_ART_REF,

    /**
     * UUID of the back art in the BLOB storage, if the card is two-sided and is
     * present and applicable.
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 40)
    BACK_ART_REF,

    /**
     * Name of the artist(s) on the front art
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 100)
    FRONT_ARTIST,

    /**
     * Name of the artist(s) on the back art
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 100)
    BACK_ARTIST,

    /**
     * The availability of material variants for this print. For example, foil
     * variants.
     * <p/>
     * It would be impractical to store each material variant possibility as
     * separate row in a database (e.g., the art is the same for foil and non-foil
     * variants, and the set number is the same, etc.). The availability of the
     * materials in the print is also heavily set-dependent.
     * <p/>
     * For a particular card printing in a set, we store which material variants are
     * available (e.g., Foil only, Nonfoil only, etc.).
     * 
     * @see org.oaktownrpg.jgladiator.framework.ccg.FoilAvailability
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 10)
    MATERIAL_VARIANTS,

}
