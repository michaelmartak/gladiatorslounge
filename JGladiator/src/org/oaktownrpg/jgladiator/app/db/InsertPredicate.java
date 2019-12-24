/**
 * 
 */
package org.oaktownrpg.jgladiator.app.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.oaktownrpg.jgladiator.util.BuilderException;

/**
 * Predicate for inserting values into the given table.
 * 
 * @author michaelmartak
 *
 */
public final class InsertPredicate {

    private final String tableName;
    private final List<String> columns = new ArrayList<>();
    private final List<Object> values = new ArrayList<>();

    /**
     * Create a new predicate for INSERT
     * 
     * @param table the table constant
     * @throws BuilderException
     * 
     */
    InsertPredicate(Enum<?> table) throws BuilderException {
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
    public InsertPredicate value(Enum<?> column, Object value) throws BuilderException {
        value = TableOperations.predicateValue(value);
        columns.add(SchemaBuilder.sqlColumnName(column));
        values.add(value);
        return this;
    }

    /**
     * Execute the INSERT statement
     * 
     * @param connection
     * @return 
     * @throws SQLException
     */
    public boolean execute(Connection connection) throws SQLException {
        final StringBuilder sql = new StringBuilder();
        sql.append("INSERT INTO ");
        sql.append(tableName);
        sql.append(" ( ");
        for (int i = 0; i < columns.size(); i++) {
            if (i > 0) {
                sql.append(", ");
            }
            sql.append(columns.get(i));
        }
        sql.append(" ) VALUES (");
        sql.append("?, ".repeat(values.size() - 1));
        sql.append("? )");
        PreparedStatement stmt = connection.prepareStatement(sql.toString());
        for (int i = 0; i < values.size(); i++) {
            stmt.setObject(i + 1, values.get(i));
        }
        return stmt.execute();
    }

}
