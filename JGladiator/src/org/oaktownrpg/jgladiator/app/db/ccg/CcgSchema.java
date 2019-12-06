package org.oaktownrpg.jgladiator.app.db.ccg;

import org.oaktownrpg.jgladiator.app.db.annotation.DatabaseSchema;
import org.oaktownrpg.jgladiator.app.db.annotation.DatabaseTable;

/**
 * Tables in the CCG schema.
 */
@DatabaseSchema
enum CcgSchema {

    @DatabaseTable(columns = CcgTable.class)
    CCG,

    /**
     * Locale that a CCG can be successfully rendered in. For example, available
     * languages where cards of a specific CCG have been printed.
     */
    @DatabaseTable(columns = LocaleTable.class)
    LOCALE,
    /**
     * A format for games of a specific CCG to be played in.
     */
    @DatabaseTable(columns = CcgFormatTable.class)
    CCG_FORMAT,
    /**
     * A fixed, defined set of cards.
     */
    @DatabaseTable(columns = CardSetTable.class)
    CARD_SET,
    /**
     * Identity of a unique card, its rules, etc.
     */
    @DatabaseTable(columns = CardIdentityTable.class)
    CARD_IDENTITY,
    /**
     * A specific printing of a card.
     */
    @DatabaseTable(columns = CardPrintTable.class)
    CARD_PRINT,
    /**
     * The locales available for a specific card set.
     */
    @DatabaseTable(columns = CardSetLocaleTable.class)
    CARD_SET_LOCALE,
    /**
     * Rules that govern which format(s) a card can be legally played in. Includes
     * concepts such as "Restricted" or "Banned"
     */
    @DatabaseTable(columns = CardLegalityTable.class)
    CARD_LEGALITY,

}
