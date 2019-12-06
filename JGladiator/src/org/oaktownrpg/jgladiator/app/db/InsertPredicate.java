/**
 * 
 */
package org.oaktownrpg.jgladiator.app.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.oaktownrpg.jgladiator.util.BuilderException;

/**
 * Predicate for inserting values into the given table.
 * 
 * @author michaelmartak
 *
 */
public class InsertPredicate {

    private final StringBuilder sql = new StringBuilder();
    private final StringBuilder columns = new StringBuilder();
    private final StringBuilder values = new StringBuilder();

    /**
     * Create a new predicate for INSERT
     * 
     * @param table the table constant
     * @throws BuilderException
     * 
     */
    InsertPredicate(Enum<?> table) throws BuilderException {
        assert table != null;

        sql.append("INSERT INTO ");
        sql.append(SchemaBuilder.sqlTableName(table));
        sql.append(" ");
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
        if (columns.length() != 0) {
            columns.append(", ");
        }
        columns.append(SchemaBuilder.sqlColumnName(column));
        if (values.length() != 0) {
            values.append(",");
        }
        values.append(" '");
        values.append(value);
        values.append("' ");
        return this;
    }

    /**
     * Execute the INSERT statement
     * 
     * @param connection
     * @throws SQLException
     */
    public void execute(Connection connection) throws SQLException {
        sql.append("( ");
        sql.append(columns);
        sql.append(" ) VALUES (");
        sql.append(values);
        sql.append(")");
        Sql.executeUpdate(connection, sql.toString());
    }

}
