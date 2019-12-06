/**
 * 
 */
package org.oaktownrpg.jgladiator.app.db;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
/**
 * Denotes a database column.
 * <p/>
 * Must be an enum constant.
 * 
 * @author michaelmartak
 *
 */
public @interface DatabaseColumn {
    
    String name() default "";

    DataType type();
    
    int max() default -1;
    
}
