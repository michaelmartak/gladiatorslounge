/**
 * 
 */
package org.oaktownrpg.jgladiator.app.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.oaktownrpg.jgladiator.app.db.annotation.PrimaryKey;
import org.oaktownrpg.jgladiator.util.BuilderException;

/**
 * Predicate for MERGE (UPSERT) values into the given table.
 * 
 * @author michaelmartak
 *
 */
public class UpsertPredicate {

    private final String tableName;
    private final List<String> columns = new ArrayList<>();
    private final List<Object> values = new ArrayList<>();
    private final List<String> pkColumns = new ArrayList<>();
    private final List<String> nonPkColumns = new ArrayList<>();
    private final List<Object> pkValues = new ArrayList<>();
    private final List<Object> nonPkValues = new ArrayList<>();

    /**
     * @throws BuilderException
     * 
     */
    UpsertPredicate(Enum<?> table) throws BuilderException {
        assert table != null;
        tableName = SchemaBuilder.sqlTableName(table);
    }

    /**
     * Add a value for this row.
     * 
     * @param column
     * @param value
     * @return
     * @throws BuilderException
     */
    public UpsertPredicate value(Enum<?> column, Object value) throws BuilderException {
        value = TableOperations.predicateValue(value);
        final String columnName = SchemaBuilder.sqlColumnName(column);
        PrimaryKey pk = SchemaBuilder.annotation(PrimaryKey.class, column);
        if (pk != null) {
            pkColumns.add(columnName);
            pkValues.add(value);
        } else {
            nonPkColumns.add(columnName);
            nonPkValues.add(value);
        }
        columns.add(columnName);
        values.add(value);
        return this;
    }

    public boolean execute(Connection connection) throws SQLException {
        final StringBuilder sql = new StringBuilder();
        sql.append("MERGE INTO ");
        sql.append(tableName);
        sql.append(" USING SYSIBM.SYSDUMMY1");
        sql.append(" ON ");
        final List<Object> stmtParams = new ArrayList<>(columns.size() * 3);
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
            // No-op
        } else {
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
        for (int i = 0; i < stmtParams.size(); i++) {
            stmt.setObject(i + 1, stmtParams.get(i));
        }
        return stmt.execute();
    }

}
