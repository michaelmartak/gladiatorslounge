/**
 * 
 */
package org.oaktownrpg.jgladiator.app;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

/**
 * Local database storage
 * 
 * @author michaelmartak
 *
 */
class AppLocalDatabase {

    private static final String CCG = "CCG";

    private ExecutorService databaseExecutor;
    private Connection connection;

    /**
     * 
     */
    AppLocalDatabase() {
    }

    /**
     * Start the database and connection.
     */
    public void initialize(AppExecutors executors) {
        // Invoke it on the correct thread
        databaseExecutor = executors.databaseExecutor();
        databaseExecutor.execute(() -> initializeConnection());
        databaseExecutor.execute(() -> ensureSchema());
    }

    /**
     * Initialize the connection
     */
    void initializeConnection() {
        try {
            // Documentation for Apache Derby recommends to launch indirectly, using
            // reflection.
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException | ClassNotFoundException e) {
            Logger.getLogger(getClass().getName()).severe("Cannot load Apache Derby embedded driver " + e.getMessage());
            return;
        }
        Properties dbProperties = new Properties();
        try {
            connection = DriverManager.getConnection("jdbc:derby:derbyDB;create=true", dbProperties);
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).severe("Cannot start database connection " + e.getMessage());
            return;
        }
        Logger.getLogger(getClass().getName()).info("Database connection successful.");
    }

    /**
     * Check the status of the DB schema and create tables, if necessary
     */
    void ensureSchema() {
        try {
            if (tableExists(CCG)) {
                // Table already exists
                return;
            }
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).severe("Could not check existence of table " + e.getMessage());
        }
        try {
            createSchema();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).severe("Could not create schema " + e.getMessage());
            return;
        }
        try {
            insertDefaultData();
        } catch (SQLException e) {
            Logger.getLogger(getClass().getName()).severe("Could not create schema " + e.getMessage());
            return;
        }
    }

    private void insertDefaultData() throws SQLException {
        final Statement statement = connection.createStatement();
        statement.executeUpdate("INSERT INTO " + CCG + " VALUES ('MTG')");
        Logger.getLogger(getClass().getName()).info("Inserted default DB info");
    }

    private void createSchema() throws SQLException {
        final Statement statement = connection.createStatement();
        statement
                .executeUpdate("CREATE TABLE " + CCG + "(" + "id VARCHAR(3) NOT NULL, " + " PRIMARY KEY ( id ) " + ")");
        Logger.getLogger(getClass().getName()).info("Created DB schema");
    }

    boolean tableExists(final String tableName) throws SQLException {
        final DatabaseMetaData metadata = connection.getMetaData();
        final ResultSet resultSet = metadata.getTables(null, null, null, new String[] { "TABLE" });
        while (resultSet.next()) {
            if (tableName.equals(resultSet.getString("TABLE_NAME"))) {
                Logger.getLogger(getClass().getName()).info("Schema already exists. Nothing to add.");
                return true;
            }
        }
        return false;
    }

}
