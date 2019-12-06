/**
 * 
 */
package org.oaktownrpg.jgladiator.app.db.annotation;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(TYPE)
/**
 * @author michaelmartak
 *
 */
public @interface DatabaseTableDefinition {

}
