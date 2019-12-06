/**
 * 
 */
package org.oaktownrpg.jgladiator.app.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.oaktownrpg.jgladiator.util.BuilderException;

/**
 * Database table operations. Handles interaction between our table definitions
 * and SQL.
 * 
 * @author michaelmartak
 *
 */
public final class TableOperations {

    /**
     * 
     */
    private TableOperations() {
    }

    public static boolean tableExists(Connection connection, Enum<?> table) throws SQLException, BuilderException {
        return Sql.tableExists(connection, SchemaBuilder.sqlTableName(table));
    }

    public static void dropTableIfExists(Connection connection, Enum<?> table) throws SQLException, BuilderException {
        Sql.dropTableIfExists(connection, SchemaBuilder.sqlTableName(table));
    }

}
