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
enum CardPrintTableColumn {

    @DatabaseColumn(type = DataType.VARCHAR, max = 3)
    @NotNull
    @PrimaryKey
    @ForeignKey(table = "CCG", fields = "CCG_ID")
    CCG_ID,

    @DatabaseColumn(type = DataType.VARCHAR, max = 255)
    @NotNull
    @PrimaryKey
    @ForeignKey(table = "CARD_SET", fields = { "CCG_ID", "CARD_SET_ID" })
    CARD_SET_ID,

    @DatabaseColumn(type = DataType.VARCHAR, max = 6)
    @NotNull
    @PrimaryKey
    @ForeignKey(table = "LOCALE", fields = "LOCALE_CODE")
    LOCALE,

    @DatabaseColumn(type = DataType.VARCHAR, max = 255)
    @NotNull
    @PrimaryKey
    @ForeignKey(table = "CARD_IDENTITY", fields = { "CCG_ID", "CARD_ID" })
    CARD_ID,

    @DatabaseColumn(type = DataType.VARCHAR, max = 10)
    @PrimaryKey
    PRINT_VARIANT,

    @DatabaseColumn(type = DataType.INTEGER)
    SET_NUMBER,

    @DatabaseColumn(type = DataType.BOOLEAN)
    FOIL,

    @DatabaseColumn(type = DataType.VARCHAR, max = 10)
    RARITY,

    @DatabaseColumn(type = DataType.VARCHAR, max = 16)
    FRONT_ART_REF,

    @DatabaseColumn(type = DataType.VARCHAR, max = 16)
    BACK_ART_REF,

    @DatabaseColumn(type = DataType.VARCHAR, max = 100)
    FRONT_ARTIST,

    @DatabaseColumn(type = DataType.VARCHAR, max = 100)
    BACK_ARTIST,

}
