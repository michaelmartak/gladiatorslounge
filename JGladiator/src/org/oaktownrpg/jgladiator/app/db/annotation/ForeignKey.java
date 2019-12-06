/**
 * 
 */
package org.oaktownrpg.jgladiator.app.db.annotation;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
/**
 * Denotes a field that is a foreign key to a field in another table.
 * 
 * @author michaelmartak
 *
 */
public @interface ForeignKey {

    /**
     * SQL table name for the foreign table
     * 
     * @return the foreign table name, in SQL
     */
    String table();

    /**
     * The name of the field in the foreign table, in SQL.
     * 
     * @return the foreign column name, in SQL
     */
    String[] fields();

}
