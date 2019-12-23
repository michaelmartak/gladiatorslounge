/**
 * 
 */
package org.oaktownrpg.jgladiator.app.db.ccg;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;
import java.util.logging.Logger;

import org.oaktownrpg.jgladiator.app.db.SchemaBuilder;
import org.oaktownrpg.jgladiator.app.db.TableOperations;
import org.oaktownrpg.jgladiator.framework.ccg.CardSet;
import org.oaktownrpg.jgladiator.framework.ccg.Ccg;
import org.oaktownrpg.jgladiator.framework.mtg.MtgFormat;
import org.oaktownrpg.jgladiator.util.BuilderException;

/**
 * Schema processor for CCG database objects.
 * <p/>
 * Handles all DB storage of CCG items.
 * 
 * @author michaelmartak
 *
 */
public class CcgSchemaProcessor {

    private final Logger logger = Logger.getLogger(getClass().getName());
    private Connection connection;

    /**
     * 
     */
    public CcgSchemaProcessor() {
    }

    public void initializeConnection(Connection connection) {
        this.connection = connection;
    }

    /**
     * Check the status of the DB schema and create tables, if necessary
     */
    public void ensureSchema() {
        String fault = "Could not check existence of table ";
        try {
            if (TableOperations.tableExists(connection, CcgSchema.CCG)) {
                // Table already exists
                return;
            }
            fault = "Could not create schema ";
            createSchema();
            fault = "Could not insert default data ";
            insertDefaultData();
        } catch (SQLException | BuilderException e) {
            logger.severe(fault + e.getMessage());
        }
    }

    private void insertDefaultData() throws SQLException, BuilderException {
        // CCG
        for (Ccg ccg : Ccg.values()) {
            TableOperations.insert(CcgSchema.CCG).value(CcgTable.CCG_ID, ccg).execute(connection);
        }
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

    private void createSchema() throws SQLException, BuilderException {
        new SchemaBuilder(CcgSchema.class).build(connection);
        logger.info("Created DB schema");
    }

    public boolean upsertCardSet(CardSet cardSet, UUID symbolId) {
        try {
            TableOperations.upsert(CcgSchema.CARD_SET).value(CardSetTable.CARD_SET_ID, cardSet.getId())
                    .value(CardSetTable.CCG_ID, cardSet.getCcg())
                    .value(CardSetTable.RELEASE_DATE, cardSet.getReleaseDate())
                    .value(CardSetTable.INFO, cardSet.getInformation()).value(CardSetTable.SYMBOL_REF, symbolId)
                    .value(CardSetTable.PARENT_SET_ID, cardSet.getParentCardSet())
                    .execute(connection);
            logger.info("UPSERTED Card Set '" + cardSet.getId() + "'");
        } catch (BuilderException | SQLException e) {
            logger.severe(e.getMessage());
            return false;
        }
        return true;
    }

}
