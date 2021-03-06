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
 * Denotes a field that is part of the primary key.
 * 
 * @author michaelmartak
 *
 */
public @interface PrimaryKey {

}
