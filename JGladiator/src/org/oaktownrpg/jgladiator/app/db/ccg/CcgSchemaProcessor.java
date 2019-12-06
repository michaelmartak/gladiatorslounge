/**
 * 
 */
package org.oaktownrpg.jgladiator.app.db.ccg;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Logger;

import org.oaktownrpg.jgladiator.app.db.SchemaBuilder;
import org.oaktownrpg.jgladiator.app.db.TableOperations;
import org.oaktownrpg.jgladiator.framework.mtg.MtgFormat;
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
        String fault = "Could not check existence of table ";
        try {
            if (TableOperations.tableExists(connection, CcgSchema.CCG)) {
                // FIXME should just return. DROPPING tables now
                final CcgSchema[] tables = CcgSchema.values();
                for (int i = tables.length - 1; i >= 0; i--) {
                    TableOperations.dropTableIfExists(connection, tables[i]);
                }
                // Table already exists
                // return;
            }
            fault = "Could not create schema ";
            createSchema(connection);
            fault = "Could not insert default data ";
            insertDefaultData(connection);
        } catch (SQLException | BuilderException e) {
            logger.severe(fault + e.getMessage());
        }
    }

    private void insertDefaultData(Connection connection) throws SQLException, BuilderException {
        // CCG
        TableOperations.insert(CcgSchema.CCG).value(CcgTable.CCG_ID, "MTG").execute(connection);
        // Languages
        TableOperations.insert(CcgSchema.LOCALE).value(LocaleTable.LOCALE_CODE, "en").execute(connection);
        TableOperations.insert(CcgSchema.LOCALE).value(LocaleTable.LOCALE_CODE, "es").execute(connection);
        TableOperations.insert(CcgSchema.LOCALE).value(LocaleTable.LOCALE_CODE, "fr").execute(connection);
        TableOperations.insert(CcgSchema.LOCALE).value(LocaleTable.LOCALE_CODE, "de").execute(connection);
        TableOperations.insert(CcgSchema.LOCALE).value(LocaleTable.LOCALE_CODE, "it").execute(connection);
        TableOperations.insert(CcgSchema.LOCALE).value(LocaleTable.LOCALE_CODE, "pt").execute(connection);
        TableOperations.insert(CcgSchema.LOCALE).value(LocaleTable.LOCALE_CODE, "ja").execute(connection);
        TableOperations.insert(CcgSchema.LOCALE).value(LocaleTable.LOCALE_CODE, "ko").execute(connection);
        TableOperations.insert(CcgSchema.LOCALE).value(LocaleTable.LOCALE_CODE, "ru").execute(connection);
        // zhs, or Simplified Chinese
        TableOperations.insert(CcgSchema.LOCALE).value(LocaleTable.LOCALE_CODE, "zhCN").execute(connection);
        // zht, or Traditional Chinese
        TableOperations.insert(CcgSchema.LOCALE).value(LocaleTable.LOCALE_CODE, "zhTW").execute(connection);
        // CCG Formats
        for (MtgFormat format : MtgFormat.values()) {
            TableOperations.insert(CcgSchema.CCG_FORMAT).value(CcgFormatTable.CCG_ID, "MTG")
                    .value(CcgFormatTable.CCG_FORMAT_ID, format.name()).execute(connection);
        }
        logger.info("Inserted default DB info");
    }

    private void createSchema(Connection connection) throws SQLException, BuilderException {
        new SchemaBuilder(CcgSchema.class).build(connection);
        logger.info("Created DB schema");
    }

}
