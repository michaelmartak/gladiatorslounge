/**
 * 
 */
package org.oaktownrpg.jgladiator.app.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

import org.oaktownrpg.jgladiator.app.AppExecutors;

/**
 * Local database storage
 * 
 * @author michaelmartak
 *
 */
public class AppLocalDatabase {

    private static final String EMBEDDED_DRIVER_CLASS = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String CONNECTION_URL = "jdbc:derby:derbyDB;create=true";

    private final Logger logger = Logger.getLogger(getClass().getName());
    private ExecutorService databaseExecutor;
    private Connection connection;
    private final CcgSchema ccgSchema = new CcgSchema();

    /**
     * 
     */
    public AppLocalDatabase() {
    }

    /**
     * Start the database and connection.
     */
    public void initialize(AppExecutors executors) {
        // Invoke it on the correct thread
        databaseExecutor = executors.databaseExecutor();
        databaseExecutor.execute(() -> initializeConnection());
    }

    /**
     * Initialize the connection
     */
    void initializeConnection() {
        try {
            // Documentation for Apache Derby recommends to launch indirectly, using
            // reflection.
            Class.forName(EMBEDDED_DRIVER_CLASS).getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            logger.severe("Cannot load Apache Derby embedded driver " + e.getMessage());
            return;
        }
        Properties dbProperties = new Properties();
        try {
            connection = DriverManager.getConnection(CONNECTION_URL, dbProperties);
        } catch (SQLException e) {
            logger.severe("Cannot start database connection " + e.getMessage());
            return;
        }
        logger.info("Database connection successful.");
        ccgSchema.ensureSchema(connection);
    }

}
