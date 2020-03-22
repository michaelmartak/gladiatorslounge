/**
 * 
 */
package org.oaktownrpg.jgladiator.app.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

/**
 * Methods and utilities for constructing and executing SQL strings.
 * 
 * @author michaelmartak
 *
 */
public final class Sql {

    private static final Logger LOGGER = Logger.getLogger(Sql.class.getName());

    private Sql() {
    }
    
    public static void executeUpdate(Connection connection, final String sql) throws SQLException {
        final Statement statement = connection.createStatement();
        try {
            statement.executeUpdate(sql);
            LOGGER.info("executeUpdate: " + sql);
        } finally {
            statement.close();
        }
    }

    public static void dropTable(Connection connection, String tableName) throws SQLException {
        executeUpdate(connection, "DROP TABLE " + tableName);
    }

    // FIXME
//    private void insert(final Connection connection, final CcgTable table, final String... values) throws SQLException {
//        executeUpdate(connection, () -> {
//            final String valueStr = concat(values, ", ", "'", "'");
//            return "INSERT INTO " + table.name() + " VALUES ( " + valueStr + " )";
//        });
//    }

    static boolean tableExists(Connection connection, final String tableName) throws SQLException {
        final DatabaseMetaData metadata = connection.getMetaData();
        final ResultSet resultSet = metadata.getTables(null, null, null, new String[] { "TABLE" });
        while (resultSet.next()) {
            if (tableName.equals(resultSet.getString("TABLE_NAME"))) {
                LOGGER.info("Schema already exists. Nothing to add.");
                return true;
            }
        }
        return false;
    }

    static void dropTableIfExists(Connection connection, String tableName) throws SQLException {
        if (tableExists(connection, tableName)) {
            dropTable(connection, tableName);
        }
    }

}
