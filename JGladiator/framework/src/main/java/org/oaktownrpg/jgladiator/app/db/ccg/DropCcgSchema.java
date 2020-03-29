/**
 * 
 */
package org.oaktownrpg.jgladiator.app.db.ccg;

import java.sql.Connection;

import org.oaktownrpg.jgladiator.app.db.AppLocalDatabase;
import org.oaktownrpg.jgladiator.app.db.DerbyConnection;
import org.oaktownrpg.jgladiator.app.db.TableOperations;

/**
 * Convenience app main for dropping all tables from the CCG schema.
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
        final Connection connection = DerbyConnection.newConnection(AppLocalDatabase.CCG_DB);
        if (TableOperations.tableExists(connection, CcgSchema.CCG)) {
            final CcgSchema[] tables = CcgSchema.values();
            for (int i = tables.length - 1; i >= 0; i--) {
                TableOperations.dropTableIfExists(connection, tables[i]);
            }
        }
    }

}
