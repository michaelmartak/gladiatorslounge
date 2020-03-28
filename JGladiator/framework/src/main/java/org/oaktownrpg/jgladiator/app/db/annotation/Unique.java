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
 * Used for a uniqueness constraint
 * 
 * See derby manual: <a href=
 * "http://db.apache.org/derby/docs/10.12/ref/rrefsqlj13590.html#rrefsqlj13590">Derby
 * Constraints</a>
 * 
 * @author michaelmartak
 *
 */
public @interface Unique {

    /**
     * The other fields that make up the constraint. The field that is being
     * annotated is assumed to be part of the constraint and should not be
     * specified.
     * 
     * @return array of field IDs
     */
    String[] fields() default {};

}
