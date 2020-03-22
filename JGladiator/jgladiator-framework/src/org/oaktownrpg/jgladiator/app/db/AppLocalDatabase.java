/**
 * 
 */
package org.oaktownrpg.jgladiator.app.db;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.logging.Logger;

import org.oaktownrpg.jgladiator.app.AppExecutors;
import org.oaktownrpg.jgladiator.app.db.ccg.CcgSchemaProcessor;
import org.oaktownrpg.jgladiator.framework.ccg.CardIdentity;
import org.oaktownrpg.jgladiator.framework.ccg.CardSet;

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
    private final CcgSchemaProcessor ccgSchema = new CcgSchemaProcessor();

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
            connection = newConnection();
        } catch (Exception e) {
            logger.severe("Cannot start database connection " + e.getMessage());
            return;
        }
        logger.info("Database connection successful.");
        ccgSchema.initializeConnection(connection);
        ccgSchema.ensureSchema();
    }

    /**
     * Create a new connection
     * 
     * @return
     * @throws SQLException
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws SecurityException
     * @throws ClassNotFoundException
     */
    public static Connection newConnection()
            throws SQLException, InstantiationException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
        // Documentation for Apache Derby recommends to launch indirectly, using
        // reflection.
        Class.forName(EMBEDDED_DRIVER_CLASS).getDeclaredConstructor().newInstance();
        Properties dbProperties = new Properties();
        Connection connection = DriverManager.getConnection(CONNECTION_URL, dbProperties);
        return connection;
    }

    public Future<Boolean> upsertCardSet(CardSet cardSet, UUID symbolId) {
        return databaseExecutor.submit(() -> ccgSchema.upsertCardSet(cardSet, symbolId));
    }

    public Future<Boolean> upsertCardIdentity(List<CardIdentity> identity) {
        return databaseExecutor.submit(() -> ccgSchema.upsertCardIdentity(identity));
    }

}
