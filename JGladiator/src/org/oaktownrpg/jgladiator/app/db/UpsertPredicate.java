/**
 * 
 */
package org.oaktownrpg.jgladiator.app.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import org.oaktownrpg.jgladiator.app.db.annotation.PrimaryKey;
import org.oaktownrpg.jgladiator.util.BuilderException;

/**
 * Predicate for MERGE (UPSERT) values into the given table.
 * 
 * @author michaelmartak
 *
 */
public final class UpsertPredicate<T> {

    private final String tableName;
    private final List<? extends T> items;

    private final List<String> columns = new ArrayList<>();
    private final List<String> pkColumns = new ArrayList<>();
    private final List<String> nonPkColumns = new ArrayList<>();

    private final List<Function<T, Object>> values = new ArrayList<>();
    private final List<Function<T, Object>> pkValues = new ArrayList<>();
    private final List<Function<T, Object>> nonPkValues = new ArrayList<>();

    /**
     * @throws BuilderException
     * 
     */
    UpsertPredicate(Enum<?> table, List<? extends T> items) throws BuilderException {
        assert table != null;
        assert items != null;
        tableName = SchemaBuilder.sqlTableName(table);
        this.items = items;
    }

    /**
     * Add a value for this row.
     * 
     * @param column
     * @param value
     * @return
     * @throws BuilderException
     */
    public UpsertPredicate<T> value(Enum<?> column, Function<T, Object> fetchValue) throws BuilderException {
        final String columnName = SchemaBuilder.sqlColumnName(column);
        PrimaryKey pk = SchemaBuilder.annotation(PrimaryKey.class, column);
        if (pk != null) {
            pkColumns.add(columnName);
            pkValues.add(fetchValue);
        } else {
            nonPkColumns.add(columnName);
            nonPkValues.add(fetchValue);
        }
        columns.add(columnName);
        values.add(fetchValue);
        return this;
    }

    public int[] execute(Connection connection) throws SQLException {
        final StringBuilder sql = new StringBuilder();
        sql.append("MERGE INTO ");
        sql.append(tableName);
        sql.append(" USING SYSIBM.SYSDUMMY1");
        sql.append(" ON ");
        final List<Function<T, Object>> stmtParams = new ArrayList<>(columns.size() * 3);
        for (int i = 0; i < pkColumns.size(); i++) {
            if (i > 0) {
                sql.append(" AND ");
            }
            sql.append(tableName);
            sql.append(".");
            sql.append(pkColumns.get(i));
            sql.append(" = ");
            sql.append("?");
            stmtParams.add(pkValues.get(i));
        }
        if (pkColumns.size() == columns.size() && nonPkColumns.size() == 0) {
            // No-op. If all columns are the primary key, then we are only inserting new
            // items.
            // Existing matches are already in the database.
        } else {
            // Primary key matches, do UPDATE
            sql.append(" WHEN MATCHED THEN");
            sql.append(" UPDATE SET ");
            for (int i = 0; i < nonPkColumns.size(); i++) {
                if (i > 0) {
                    sql.append(", ");
                }
                sql.append(nonPkColumns.get(i));
                sql.append(" = ");
                sql.append("?");
                stmtParams.add(nonPkValues.get(i));
            }
        }
        // Primary key does not match, do INSERT
        sql.append(" WHEN NOT MATCHED THEN");
        sql.append(" INSERT (");
        for (int i = 0; i < columns.size(); i++) {
            if (i > 0) {
                sql.append(", ");
            }
            sql.append(columns.get(i));
            stmtParams.add(values.get(i));
        }
        sql.append(") VALUES (");
        sql.append("?, ".repeat(values.size() - 1));
        sql.append("?");
        sql.append(")");
        final PreparedStatement stmt = connection.prepareStatement(sql.toString());
        for (T item : items) {
            for (int i = 0; i < stmtParams.size(); i++) {
                Function<T, Object> fetch = stmtParams.get(i);
                Object value = TableOperations.predicateValue(fetch.apply(item));
                stmt.setObject(i + 1, value);
            }
            stmt.addBatch();
        }
        return stmt.executeBatch();
    }

}
