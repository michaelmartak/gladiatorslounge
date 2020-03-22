package org.oaktownrpg.jgladiator.app.db.ccg;

import org.oaktownrpg.jgladiator.app.db.annotation.DatabaseSchema;
import org.oaktownrpg.jgladiator.app.db.annotation.DatabaseTable;

/**
 * Tables in the CCG schema.
 * <p/>
 * Tables should be in the order in which they can be successfully created by
 * the schema builder.
 */
@DatabaseSchema
enum CcgSchema {

    @DatabaseTable(columns = CcgTable.class)
    CCG,

    @DatabaseTable(columns = LocaleTable.class)
    LOCALE,

    @DatabaseTable(columns = CcgFormatTable.class)
    CCG_FORMAT,

    @DatabaseTable(columns = CardSetTable.class)
    CARD_SET,

    @DatabaseTable(columns = CardIdentityTable.class)
    CARD_IDENTITY,

    @DatabaseTable(columns = CardPrintTable.class)
    CARD_PRINT,

    @DatabaseTable(columns = CardSetLocaleTable.class)
    CARD_SET_LOCALE,

    @DatabaseTable(columns = CardLegalityTable.class)
    CARD_LEGALITY,

    @DatabaseTable(columns = InventoryTable.class)
    INVENTORY

}
