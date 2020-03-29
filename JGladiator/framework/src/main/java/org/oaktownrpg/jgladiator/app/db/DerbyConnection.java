package org.oaktownrpg.jgladiator.app.db;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.logging.Logger;

import org.oaktownrpg.jgladiator.app.AppExecutors;

/**
 * A connection to a local Apache Derby DB.
 * 
 * @author michaelmartak
 *
 */
public class DerbyConnection {

    private static final String EMBEDDED_DRIVER_CLASS = "org.apache.derby.jdbc.EmbeddedDriver";
    private static final String CONNECTION_PREFIX = "jdbc:derby:";
    private static final String DB_DIRECTORY = ".jgladiator/derbyDB";
    private static final String CONNECTION_SUFFIX = ";create=true";

    private final Logger logger = Logger.getLogger(getClass().getName());

    private final String name;
    private final String connectionString;
    private ExecutorService executor;
    private Connection connection;

    DerbyConnection(String name) {
        this.name = name;
        connectionString = connectionString(name);
    }

    public void initialize(AppExecutors executors, Runnable onSuccess) {
        // Invoke it on the correct thread
        executor = executors.databaseExecutor("JGladiator-DB-" + name);
        executor.execute(() -> initializeConnection(onSuccess));
    }

    public Connection connection() {
        return connection;
    }

    public ExecutorService executor() {
        return executor;
    }

    /**
     * Initialize the connection
     */
    void initializeConnection(Runnable onSuccess) {
        try {
            connection = newConnection(connectionString);
        } catch (Exception e) {
            logger.severe("Cannot start database connection " + e.getMessage());
            return;
        }
        logger.info("Database connection successful.");
    }

    static String connectionString(String name) {
        return CONNECTION_PREFIX + DB_DIRECTORY + "/" + name + CONNECTION_SUFFIX;
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
    public static Connection newConnection(final String url)
            throws SQLException, InstantiationException, IllegalAccessException, IllegalArgumentException,
            InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException {
        // Documentation for Apache Derby recommends to launch indirectly, using
        // reflection.
        Class.forName(EMBEDDED_DRIVER_CLASS).getDeclaredConstructor().newInstance();
        Properties dbProperties = new Properties();
        Connection connection = DriverManager.getConnection(url, dbProperties);
        return connection;
    }

}