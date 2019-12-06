package org.oaktownrpg.jgladiator.app.db.ccg;

import org.oaktownrpg.jgladiator.app.db.DatabaseSchema;
import org.oaktownrpg.jgladiator.app.db.DatabaseTable;

/**
 * Tables in the CCG schema.
 */
@DatabaseSchema
enum CcgTable {
    /**
     * A collectible card game, virtual or otherwise.
     */
    @DatabaseTable(columns = CcgTableColumn.class)
    CCG,
    /**
     * Locale that a CCG can be successfully rendered in. For example, available
     * languages where cards of a specific CCG have been printed.
     */
    @DatabaseTable(columns = LocaleTableColumn.class)
    LOCALE,
    /**
     * A format for games of a specific CCG to be played in.
     */
    @DatabaseTable(columns = CcgFormatTableColumn.class)
    CCG_FORMAT,
    /**
     * A fixed, defined set of cards.
     */
    @DatabaseTable(columns = CardSetTableColumn.class)
    CARD_SET,
    /**
     * Identity of a unique card, its rules, etc.
     */
    @DatabaseTable(columns = CardIdentityTableColumn.class)
    CARD_IDENTITY,
    /**
     * A specific printing of a card.
     */
    @DatabaseTable(columns = CardPrintTableColumn.class)
    CARD_PRINT,
    /**
     * The locales available for a specific card set.
     */
    @DatabaseTable(columns = CardSetLocaleTableColumn.class)
    CARD_SET_LOCALE,
    /**
     * Rules that govern which format(s) a card can be legally played in. Includes
     * concepts such as "Restricted" or "Banned"
     */
    @DatabaseTable(columns = CardLegalityTableColumn.class)
    CARD_LEGALITY,

}
