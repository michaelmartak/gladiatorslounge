/**
 * 
 */
package org.oaktownrpg.jgladiator.app.db.ccg;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import org.oaktownrpg.jgladiator.app.db.SchemaBuilder;
import org.oaktownrpg.jgladiator.app.db.TableOperations;
import org.oaktownrpg.jgladiator.util.BuilderException;

/**
 * Schema processor for CCG games
 * 
 * @author michaelmartak
 *
 */
public class CcgSchemaProcessor {

    private final Logger logger = Logger.getLogger(getClass().getName());

    /**
     * 
     */
    public CcgSchemaProcessor() {
    }

    /**
     * Check the status of the DB schema and create tables, if necessary
     */
    public void ensureSchema(Connection connection) {
        try {
            if (TableOperations.tableExists(connection, CcgSchema.CCG)) {
                // FIXME should just return. DROPPING tables now
                TableOperations.dropTableIfExists(connection, CcgSchema.CARD_PRINT);
                TableOperations.dropTableIfExists(connection, CcgSchema.CARD_LEGALITY);
                TableOperations.dropTableIfExists(connection, CcgSchema.CARD_IDENTITY);
                TableOperations.dropTableIfExists(connection, CcgSchema.CARD_SET_LOCALE);
                TableOperations.dropTableIfExists(connection, CcgSchema.CARD_SET);
                TableOperations.dropTableIfExists(connection, CcgSchema.CCG_FORMAT);
                TableOperations.dropTableIfExists(connection, CcgSchema.LOCALE);
                TableOperations.dropTableIfExists(connection, CcgSchema.CCG);
                // Table already exists
                // return;
            }
        } catch (SQLException | BuilderException e) {
            logger.severe("Could not check existence of table " + e.getMessage());
        }
        try {
            createSchema(connection);
        } catch (SQLException | BuilderException e) {
            logger.severe("Could not create schema " + e.getMessage());
            return;
        }
        try {
            insertDefaultData(connection);
        } catch (SQLException e) {
            logger.severe("Could not insert default data " + e.getMessage());
            return;
        }
    }

    private void insertDefaultData(Connection connection) throws SQLException {
        // CCG
//        insert(connection, CcgTable.CCG, "MTG");
//        // Languages
//        insert(connection, CcgTable.LOCALE, "en");
//        insert(connection, CcgTable.LOCALE, "es");
//        insert(connection, CcgTable.LOCALE, "fr");
//        insert(connection, CcgTable.LOCALE, "de");
//        insert(connection, CcgTable.LOCALE, "it");
//        insert(connection, CcgTable.LOCALE, "pt");
//        insert(connection, CcgTable.LOCALE, "ja");
//        insert(connection, CcgTable.LOCALE, "ko");
//        insert(connection, CcgTable.LOCALE, "ru");
//        insert(connection, CcgTable.LOCALE, "zhCN"); // zhs, or Simplified Chinese
//        insert(connection, CcgTable.LOCALE, "zhTW"); // zht, or Traditional Chinese
//        // CCG Formats
//        for (MtgFormat format : MtgFormat.values()) {
//            insert(connection, CcgTable.CCG_FORMAT, "MTG", format.name());
//        }
        logger.info("Inserted default DB info");
    }

    private void createSchema(Connection connection) throws SQLException, BuilderException {
        new SchemaBuilder(CcgSchema.class).build(connection);
        logger.info("Created DB schema");
    }

}
