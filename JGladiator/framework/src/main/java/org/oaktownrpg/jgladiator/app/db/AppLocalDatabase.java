/**
 * 
 */
package org.oaktownrpg.jgladiator.app.db;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Future;

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

    public static final String CCG_DB = "ccg";
    private final CcgSchemaProcessor ccgSchema = new CcgSchemaProcessor();

    private DerbyConnection ccgConnection = new DerbyConnection(CCG_DB);

    /**
     * 
     */
    public AppLocalDatabase() {
    }

    /**
     * Start the database and connection.
     */
    public void initialize(AppExecutors executors) {
        ccgConnection.initialize(executors, () -> onCcgConnected());
    }

    void onCcgConnected() {
        ccgSchema.initializeConnection(ccgConnection.connection());
        ccgSchema.ensureSchema();
    }

    public Future<Boolean> upsertCardSet(CardSet cardSet, UUID symbolId) {
        return ccgConnection.executor().submit(() -> ccgSchema.upsertCardSet(cardSet, symbolId));
    }

    public Future<Boolean> upsertCardIdentity(List<CardIdentity> identity) {
        return ccgConnection.executor().submit(() -> ccgSchema.upsertCardIdentity(identity));
    }

}
