/**
 * 
 */
package org.oaktownrpg.jgladiator.app.db.ccg;

import java.sql.Connection;

import org.oaktownrpg.jgladiator.app.db.AppLocalDatabase;
import org.oaktownrpg.jgladiator.app.db.TableOperations;

/**
 * Convenience app main for dropping all tables.
 * 
 * @author michaelmartak
 *
 */
public final class DropCcgSchema {

    /**
     * 
     */
    private DropCcgSchema() {
    }

    /**
     * @param args
     */
    public static void main(String[] args) throws Exception {
        final Connection connection = AppLocalDatabase.newConnection();
        if (TableOperations.tableExists(connection, CcgSchema.CCG)) {
            final CcgSchema[] tables = CcgSchema.values();
            for (int i = tables.length - 1; i >= 0; i--) {
                TableOperations.dropTableIfExists(connection, tables[i]);
            }
        }
    }

}
