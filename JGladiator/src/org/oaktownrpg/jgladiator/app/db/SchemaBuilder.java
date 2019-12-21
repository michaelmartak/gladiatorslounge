/**
 * 
 */
package org.oaktownrpg.jgladiator.app.db;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.oaktownrpg.jgladiator.app.db.annotation.DataType;
import org.oaktownrpg.jgladiator.app.db.annotation.DatabaseColumn;
import org.oaktownrpg.jgladiator.app.db.annotation.DatabaseSchema;
import org.oaktownrpg.jgladiator.app.db.annotation.DatabaseTable;
import org.oaktownrpg.jgladiator.app.db.annotation.ForeignKey;
import org.oaktownrpg.jgladiator.app.db.annotation.NotNull;
import org.oaktownrpg.jgladiator.app.db.annotation.PrimaryKey;
import org.oaktownrpg.jgladiator.util.BuilderException;

import com.sun.istack.logging.Logger;

/**
 * Annotation-based builder of a DB schema.
 * 
 * @author michaelmartak
 *
 */
public class SchemaBuilder {

    private final Class<? extends Enum<?>> schemaClass;

    /**
     * @param schemaClass
     * 
     */
    public SchemaBuilder(Class<? extends Enum<?>> schemaClass) {
        assert schemaClass != null;

        this.schemaClass = schemaClass;
    }

    /**
     * Builds the schema from the schema class
     * 
     * @param connection the connection, never null
     * @throws SQLException     if an error occurred during SQL processing
     * @throws BuilderException if an error occurred during reflection or annotation
     *                          processing
     */
    public void build(final Connection connection) throws SQLException, BuilderException {
        assert connection != null;

        DatabaseSchema schemaDef = schemaClass.getAnnotation(DatabaseSchema.class);
        if (schemaDef == null) {
            throw new BuilderException("Schema annotation is not present on " + schemaClass.getName());
        }

        processSchema(connection, schemaDef);
    }

    /**
     * Returns the table name in SQL from the annotations in the table definition
     * constant
     * 
     * @param constant the constant
     * @return a table name in valid SQL
     * @throws BuilderException
     */
    static String sqlTableName(Enum<?> constant) throws BuilderException {
        DatabaseTable table = annotation(DatabaseTable.class, constant);
        if (table == null) {
            throw new BuilderException("DatabaseTable annotation missing on " + constant);
        }
        String nameOverride = table.name();
        if (nameOverride == null || nameOverride.isEmpty()) {
            return constant.name();
        }
        return nameOverride;
    }

    /**
     * Returns the column name in SQL from the annotations in the column definition
     * constant
     * 
     * @param constant the constant
     * @return a column name in valid SQL
     * @throws BuilderException
     */
    static String sqlColumnName(Enum<?> constant) throws BuilderException {
        DatabaseColumn column = annotation(DatabaseColumn.class, constant);
        String nameOverride = column.name();
        if (nameOverride == null || nameOverride.isEmpty()) {
            return constant.name();
        }
        return nameOverride;
    }

    private void processSchema(Connection connection, DatabaseSchema schemaDef) throws BuilderException, SQLException {
        // Process the DB Table enum constants in the schema
        Enum<?>[] constants = schemaClass.getEnumConstants();
        for (Enum<?> constant : constants) {
            // Get the table annotation
            processTable(connection, constant);
        }
    }

    private void processTable(Connection connection, Enum<?> constant) throws BuilderException, SQLException {
        DatabaseTable table = annotation(DatabaseTable.class, constant);
        if (table == null) {
            Logger.getLogger(getClass()).warning("No table annotation found for " + constant);
            return;
        }
        final StringBuilder createSql = new StringBuilder();

        final String tableName = sqlTableName(constant);

        createSql.append("CREATE TABLE ");
        createSql.append(tableName);
        createSql.append(" ( ");

        // Process columns
        Class<? extends Enum<?>> columnClass = table.columns();
        if (columnClass == null) {
            throw new BuilderException("No column class defined for " + tableName);
        }
        processColumnClass(createSql, columnClass);

        createSql.append(" )");

        // Execute the Create SQL
        Sql.executeUpdate(connection, createSql.toString());
    }

    private void processColumnClass(StringBuilder createSql, Class<? extends Enum<?>> columnClass)
            throws BuilderException {
        Enum<?>[] enumConstants = columnClass.getEnumConstants();
        final List<Enum<?>> primaryKey = new ArrayList<>();
        final List<Enum<?>> foreignKey = new ArrayList<>();
        for (Enum<?> column : enumConstants) {
            processColumn(createSql, primaryKey, foreignKey, column);
        }
        processPrimaryKey(createSql, primaryKey);
        processForeignKey(createSql, foreignKey);
    }

    private void processForeignKey(StringBuilder createSql, List<Enum<?>> foreignKey) throws BuilderException {
        if (foreignKey.isEmpty()) {
            return;
        }
        // Note : a foreign key might involve multiple columns:
        // (A1, A2) REFERENCES FOO (B1, B2)
        for (Enum<?> column : foreignKey) {
            createSql.append(", ");
            createSql.append("FOREIGN KEY ( ");

            final ForeignKey fk = annotation(ForeignKey.class, column);
            final String table = fk.table();

            final StringBuilder columnSql = new StringBuilder();
            final StringBuilder refSql = new StringBuilder();
            String[] fields = fk.fields();
            if (fields.length == 1) {
                // For the typical case, one field
                columnSql.append(sqlColumnName(column));
                refSql.append(fields[0]);
            } else {
                // Multiple columns
                for (int i = 0; i < fields.length; i++) {
                    String field = fields[i];
                    if (i > 0) {
                        columnSql.append(", ");
                        refSql.append(", ");
                    }
                    columnSql.append(field);
                    refSql.append(field);
                }
            }
            createSql.append(columnSql);
            createSql.append(" ) ");
            createSql.append("REFERENCES ");
            createSql.append(table);
            createSql.append("( ");
            createSql.append(refSql);
            createSql.append(" )");
        }
    }

    private void processPrimaryKey(StringBuilder createSql, List<Enum<?>> primaryKey) throws BuilderException {
        if (primaryKey.isEmpty()) {
            return;
        }
        createSql.append(", ");
        createSql.append("PRIMARY KEY ( ");
        boolean first = true;
        for (Enum<?> column : primaryKey) {
            if (first) {
                first = false;
            } else {
                createSql.append(", ");
            }
            createSql.append(sqlColumnName(column));
        }
        createSql.append(" ) ");
    }

    private void processColumn(StringBuilder createSql, List<Enum<?>> primaryKey, List<Enum<?>> foreignKey,
            Enum<?> column) throws BuilderException {

        NotNull notNull = annotation(NotNull.class, column);
        DatabaseColumn columnDef = annotation(DatabaseColumn.class, column);
        PrimaryKey pk = annotation(PrimaryKey.class, column);
        ForeignKey fk = annotation(ForeignKey.class, column);

        final String columnName = sqlColumnName(column);
        // If the create SQL ends with a space, we are at the beginning of the column
        // definitions.
        if (!createSql.toString().endsWith(" ")) {
            createSql.append(", ");
        }
        createSql.append(columnName);
        createSql.append(" ");
        processColumnType(createSql, columnDef);
        if (notNull != null) {
            createSql.append(" NOT NULL");
        }

        if (pk != null) {
            primaryKey.add(column);
        }
        if (fk != null) {
            foreignKey.add(column);
        }
    }

    private void processColumnType(StringBuilder createSql, DatabaseColumn columnDef) {
        final DataType dataType = columnDef.type();
        final int max = columnDef.max();
        createSql.append(dataType.toString());
        if (DataType.isMax(dataType) && max >= 0) {
            createSql.append("(");
            createSql.append(max);
            createSql.append(")");
        }
    }

    /**
     * Pulls an annotation from an enum constant, from the class where it is
     * defined.
     * 
     * @param constantName
     * @return
     * @throws BuilderException
     */
    static <A extends Annotation> A annotation(Class<? extends A> annotationClass, Enum<?> constant)
            throws BuilderException {
        try {
            Field field = constant.getClass().getField(constant.name());
            return field.getAnnotation(annotationClass);
        } catch (IllegalArgumentException | SecurityException | NoSuchFieldException e) {
            throw new BuilderException(e);
        }
    }
}
