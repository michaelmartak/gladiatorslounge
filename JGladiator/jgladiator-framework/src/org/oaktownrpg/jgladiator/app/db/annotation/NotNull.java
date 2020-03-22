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
 * Denotes a not-null column.
 * 
 * @author michaelmartak
 *
 */
public @interface NotNull {

}
