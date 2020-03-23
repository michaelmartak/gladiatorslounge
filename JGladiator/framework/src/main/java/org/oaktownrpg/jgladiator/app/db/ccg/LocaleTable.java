/**
 * 
 */
package org.oaktownrpg.jgladiator.app.db.ccg;

import org.oaktownrpg.jgladiator.app.db.annotation.DataType;
import org.oaktownrpg.jgladiator.app.db.annotation.DatabaseColumn;
import org.oaktownrpg.jgladiator.app.db.annotation.DatabaseTableDefinition;
import org.oaktownrpg.jgladiator.app.db.annotation.NotNull;
import org.oaktownrpg.jgladiator.app.db.annotation.PrimaryKey;

/**
 * Locale that a CCG can be successfully rendered in. For example, available
 * languages where cards of a specific CCG have been printed.
 * 
 * @author michaelmartak
 *
 */
@DatabaseTableDefinition
enum LocaleTable {

    /**
     * Locale code. For simplicity, stored in the format llRRvv, where l is
     * Language, R is Region, and V is variant.
     */
    @DatabaseColumn(type = DataType.VARCHAR, max = 6)
    @NotNull
    @PrimaryKey
    LOCALE_CODE

}
