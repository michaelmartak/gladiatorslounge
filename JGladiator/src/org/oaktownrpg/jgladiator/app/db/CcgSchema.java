/**
 * 
 */
package org.oaktownrpg.jgladiator.app.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.logging.Logger;

import org.oaktownrpg.jgladiator.framework.mtg.MtgFormat;

/**
 * Schema for CCG games
 * 
 * @author michaelmartak
 *
 */
class CcgSchema {

    private final Logger logger = Logger.getLogger(getClass().getName());

    // Tables
    static enum CcgTable {
        CCG, LOCALE, CARD_SET, CARD_IDENTITY, CARD_PRINT, CCG_FORMAT, CARD_SET_LOCALE, CARD_LEGALITY
    }

    interface ColumnName<E extends Enum<E>> {

        String name();

        String columnDefinition();

    }

    @FunctionalInterface
    static interface TokenIterator<T> extends Function<Integer, T> {

        default int length() {
            return 0;
        }

        default void forEach(BiConsumer<Integer, ? super T> action) {
            final int length = length();
            for (int i = 0; i < length; i++) {
                T value = apply(i);
                action.accept(i, value);
            }
        }

    }

    static final class ArrayIterator<T> implements TokenIterator<T> {
        private final T[] array;

        public ArrayIterator(T[] array) {
            this.array = array;
        }

        @Override
        public int length() {
            if (array == null) {
                return 0;
            }
            return array.length;
        }

        @Override
        public T apply(Integer t) {
            return array[t];
        }

    }

    static final class ColumnNameIterator implements TokenIterator<String> {
        private final ColumnName<?>[] array;

        public ColumnNameIterator(ColumnName<?>[] array) {
            this.array = array;
        }
        @Override
        public int length() {
            if (array == null) {
                return 0;
            }
            return array.length;
        }

        @Override
        public String apply(Integer t) {
            return array[t].name();
        }

    }

    static final class ColumnDefinitionIterator implements TokenIterator<String> {
        private final ColumnName<?>[] array;

        public ColumnDefinitionIterator(ColumnName<?>[] array) {
            this.array = array;
        }
        
        @Override
        public int length() {
            if (array == null) {
                return 0;
            }
            return array.length;
        }

        @Override
        public String apply(Integer t) {
            return array[t].columnDefinition();
        }

    }

    class TableBuilder {

        private final CcgTable table;
        private Class<? extends ColumnName<?>> columns;
        private ColumnName<?>[] primaryKey;

        public TableBuilder(CcgTable table) {
            assert table != null;

            this.table = table;
        }

        public TableBuilder columns(Class<? extends ColumnName<?>> columns) {
            this.columns = columns;
            return this;
        }

        public TableBuilder primaryKey(ColumnName<?>... primaryKey) {
            this.primaryKey = primaryKey;
            return this;
        }

        public void build(Connection connection) throws SQLException {
            StringBuilder sb = new StringBuilder();
            sb.append("CREATE TABLE ");
            sb.append(table.name());
            sb.append(" ( ");
            if (columns != null) {
                ColumnName<?>[] columnEnums = columns.getEnumConstants();
                if (columnEnums != null && columnEnums.length > 0) {
                    concat(sb, new ColumnDefinitionIterator(columnEnums), ", ", "", "");
                }
            }
            if (primaryKey != null) {
                sb.append(", PRIMARY KEY ( ");
                concat(sb, new ColumnNameIterator(primaryKey), ", ", "", "");
                sb.append(" )");
            }
            sb.append(" )");
            final String sql = sb.toString();
            executeUpdate(connection, () -> {
                return sql;
            });
        }
    }

    static class DbType {

        private final String value;

        private DbType(String value) {
            this.value = value;
        }

        public static DbType varchar(int size, boolean isNull) {
            return new DbType("VARCHAR(" + size + ")" + (isNull ? "" : " NOT NULL"));
        }

        @Override
        public String toString() {
            return value;
        }

    }

    static enum CcgTableColumn implements ColumnName<CcgTableColumn> {
        CCG_ID(DbType.varchar(3, false));

        private final DbType type;

        private CcgTableColumn(DbType type) {
            this.type = type;
        }

        @Override
        public String columnDefinition() {
            return name() + " " + type;
        }
    }

    // Columns
    private static final String CCG_ID = "ccg_id";
    private static final String CCG_ID_COLUMN_DEF = CCG_ID + " VARCHAR(3) NOT NULL";
    private static final String LOCALE_CODE = "locale_code";
    private static final String LOCALE_CODE_COLUMN_DEF = LOCALE_CODE + " VARCHAR(6) NOT NULL";
    private static final String CCG_FORMAT_ID = "format_id";
    private static final String CCG_FORMAT_ID_COLUMN_DEF = CCG_FORMAT_ID + " VARCHAR(24) NOT NULL";
    private static final String CARD_SET_ID = "card_set_id";
    private static final String CARD_SET_ID_COLUMN_DEF = CARD_SET_ID + " VARCHAR(255) NOT NULL";
    private static final String CARD_SET_RELEASE_DATE = "release_date";
    private static final String CARD_SET_RELEASE_DATE_COLUMN_DEF = CARD_SET_RELEASE_DATE + " DATE NOT NULL";
    private static final String CARD_ID = "card_id";
    private static final String CARD_ID_COLUMN_DEF = CARD_ID + " VARCHAR(255) NOT NULL";
    private static final String ORACLE_TEXT = "oracle_text";
    private static final String ORACLE_TEXT_COLUMN_DEF = ORACLE_TEXT + " VARCHAR(1024)";
    private static final String LEGALITY = "legality";
    private static final String LEGALITY_COLUMN_DEF = LEGALITY + " INTEGER";
    private static final String FRONT_ART_REF = "front_art_ref";
    private static final String FRONT_ART_REF_COLUMN_DEF = FRONT_ART_REF + " VARCHAR(16)";
    private static final String BACK_ART_REF = "back_art_ref";
    private static final String BACK_ART_REF_COLUMN_DEF = BACK_ART_REF + " VARCHAR(16)";

    private static final String foreignKey(CcgTable foreignTable, String... column) {
        final String columnStr = concat(column, ", ");
        return "FOREIGN KEY ( " + columnStr + " ) REFERENCES " + foreignTable.name() + "( " + columnStr + " )";
    }

    private static final String primaryKey(String... column) {
        final String columnStr = concat(column, ", ");
        return "PRIMARY KEY ( " + columnStr + " )";
    }

    private static String concat(String[] array, String delimiter) {
        return concat(array, delimiter, "", "");
    }

    private static String concat(String[] array, String delimiter, String before, String after) {
        return concat(new ArrayIterator<String>(array), delimiter, before, after);
    }

    private static String concat(TokenIterator<String> iterator, String delimiter, String before, String after) {
        final StringBuilder sb = new StringBuilder();
        concat(sb, iterator, delimiter, before, after);
        return sb.toString();
    }

    private static void concat(StringBuilder sb, TokenIterator<String> iterator, String delimiter, String before,
            String after) {
        iterator.forEach((Integer i, String value) -> {
            sb.append(before);
            sb.append(value);
            if (i < iterator.length() - 1) {
                sb.append(delimiter);
            }
            sb.append(after);
        });
    }

    /**
     * 
     */
    public CcgSchema() {
    }

    /**
     * Check the status of the DB schema and create tables, if necessary
     */
    void ensureSchema(Connection connection) {
        try {
            if (tableExists(connection, CcgTable.CCG)) {
                // FIXME should just return. DROPPING tables now
                dropTableIfExists(connection, CcgTable.CARD_PRINT);
                dropTableIfExists(connection, CcgTable.CARD_LEGALITY);
                dropTableIfExists(connection, CcgTable.CARD_IDENTITY);
                dropTableIfExists(connection, CcgTable.CARD_SET_LOCALE);
                dropTableIfExists(connection, CcgTable.CARD_SET);
                dropTableIfExists(connection, CcgTable.CCG_FORMAT);
                dropTableIfExists(connection, CcgTable.LOCALE);
                dropTableIfExists(connection, CcgTable.CCG);
                // Table already exists
                // return;
            }
        } catch (SQLException e) {
            logger.severe("Could not check existence of table " + e.getMessage());
        }
        try {
            createSchema(connection);
        } catch (SQLException e) {
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

    private void dropTableIfExists(Connection connection, CcgTable table) throws SQLException {
        if (tableExists(connection, table)) {
            dropTable(connection, table);
        }
    }

    private void insertDefaultData(Connection connection) throws SQLException {
        // CCG
        insert(connection, CcgTable.CCG, "MTG");
        // Languages
        insert(connection, CcgTable.LOCALE, "en");
        insert(connection, CcgTable.LOCALE, "es");
        insert(connection, CcgTable.LOCALE, "fr");
        insert(connection, CcgTable.LOCALE, "de");
        insert(connection, CcgTable.LOCALE, "it");
        insert(connection, CcgTable.LOCALE, "pt");
        insert(connection, CcgTable.LOCALE, "ja");
        insert(connection, CcgTable.LOCALE, "ko");
        insert(connection, CcgTable.LOCALE, "ru");
        insert(connection, CcgTable.LOCALE, "zhCN"); // zhs, or Simplified Chinese
        insert(connection, CcgTable.LOCALE, "zhTW"); // zht, or Traditional Chinese
        // CCG Formats
        for (MtgFormat format : MtgFormat.values()) {
            insert(connection, CcgTable.CCG_FORMAT, "MTG", format.name());
        }
        logger.info("Inserted default DB info");
    }

    private void createSchema(Connection connection) throws SQLException {
        new TableBuilder(CcgTable.CCG).columns(CcgTableColumn.class).primaryKey(CcgTableColumn.CCG_ID).build(connection);

        createTable(connection, CcgTable.LOCALE, LOCALE_CODE_COLUMN_DEF, primaryKey(LOCALE_CODE));

        createTable(connection, CcgTable.CCG_FORMAT, CCG_ID_COLUMN_DEF, CCG_FORMAT_ID_COLUMN_DEF,
                primaryKey(CCG_ID, CCG_FORMAT_ID), foreignKey(CcgTable.CCG, CCG_ID));

        createTable(connection, CcgTable.CARD_SET, CCG_ID_COLUMN_DEF, CARD_SET_ID_COLUMN_DEF,
                CARD_SET_RELEASE_DATE_COLUMN_DEF, primaryKey(CCG_ID, CARD_SET_ID), foreignKey(CcgTable.CCG, CCG_ID));

        createTable(connection, CcgTable.CARD_SET_LOCALE, CCG_ID_COLUMN_DEF, CARD_SET_ID_COLUMN_DEF,
                LOCALE_CODE_COLUMN_DEF, primaryKey(CCG_ID, CARD_SET_ID, LOCALE_CODE),
                foreignKey(CcgTable.CARD_SET, CCG_ID, CARD_SET_ID), foreignKey(CcgTable.LOCALE, LOCALE_CODE),
                foreignKey(CcgTable.CCG, CCG_ID));

        createTable(connection, CcgTable.CARD_IDENTITY, CCG_ID_COLUMN_DEF, CARD_ID_COLUMN_DEF, ORACLE_TEXT_COLUMN_DEF,
                primaryKey(CCG_ID, CARD_ID), foreignKey(CcgTable.CCG, CCG_ID));

        createTable(connection, CcgTable.CARD_LEGALITY, CCG_ID_COLUMN_DEF, CARD_ID_COLUMN_DEF, CCG_FORMAT_ID_COLUMN_DEF,
                LEGALITY_COLUMN_DEF, primaryKey(CCG_ID, CARD_ID, CCG_FORMAT_ID, LEGALITY),
                foreignKey(CcgTable.CARD_IDENTITY, CCG_ID, CARD_ID), foreignKey(CcgTable.CCG_FORMAT, CCG_ID, CCG_FORMAT_ID),
                foreignKey(CcgTable.CCG, CCG_ID));

        createTable(connection, CcgTable.CARD_PRINT, CCG_ID_COLUMN_DEF, CARD_SET_ID_COLUMN_DEF, CARD_ID_COLUMN_DEF,
                LOCALE_CODE_COLUMN_DEF, FRONT_ART_REF_COLUMN_DEF, BACK_ART_REF_COLUMN_DEF,
                primaryKey(CCG_ID, CARD_SET_ID, CARD_ID, LOCALE_CODE), foreignKey(CcgTable.CARD_SET, CCG_ID, CARD_SET_ID),
                foreignKey(CcgTable.CARD_IDENTITY, CCG_ID, CARD_ID), foreignKey(CcgTable.LOCALE, LOCALE_CODE),
                foreignKey(CcgTable.CCG, CCG_ID));

        logger.info("Created DB schema");
    }

    private void executeUpdate(Connection connection, Supplier<String> supplier) throws SQLException {
        final Statement statement = connection.createStatement();
        try {
            final String sql = supplier.get();
            statement.executeUpdate(sql);
            logger.info("executeUpdate: " + sql);
        } finally {
            statement.close();
        }
    }

    private void dropTable(Connection connection, CcgTable table) throws SQLException {
        executeUpdate(connection, () -> {
            return "DROP TABLE " + table.name();
        });
    }

    private void createTable(Connection connection, CcgTable table, String... clause) throws SQLException {
        executeUpdate(connection, () -> {
            final String clauseStr = concat(clause, ", ");
            return "CREATE TABLE " + table.name() + " ( " + clauseStr + " )";
        });
    }

    private void insert(final Connection connection, final CcgTable table, final String... values) throws SQLException {
        executeUpdate(connection, () -> {
            final String valueStr = concat(values, ", ", "'", "'");
            return "INSERT INTO " + table.name() + " VALUES ( " + valueStr + " )";
        });
    }

    boolean tableExists(Connection connection, final CcgTable table) throws SQLException {
        final DatabaseMetaData metadata = connection.getMetaData();
        final ResultSet resultSet = metadata.getTables(null, null, null, new String[] { "TABLE" });
        while (resultSet.next()) {
            if (table.name().equals(resultSet.getString("TABLE_NAME"))) {
                logger.info("Schema already exists. Nothing to add.");
                return true;
            }
        }
        return false;
    }

}
