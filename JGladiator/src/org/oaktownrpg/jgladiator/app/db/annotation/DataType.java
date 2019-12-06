/**
 * 
 */
package org.oaktownrpg.jgladiator.app.db.annotation;

/**
 * Supported database value types.
 * 
 * @author michaelmartak
 *
 */
public enum DataType {

    BOOLEAN, DATE, INTEGER, TIMESTAMP, VARCHAR;

    /**
     * Whether the given datatype supports "max" specification.
     * 
     * @param dataType
     * @return
     */
    public static boolean isMax(DataType dataType) {
        switch (dataType) {
        case VARCHAR:
            return true;
        default:
            return false;
        }
    }

}
