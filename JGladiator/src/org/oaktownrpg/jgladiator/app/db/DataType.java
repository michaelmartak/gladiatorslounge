/**
 * 
 */
package org.oaktownrpg.jgladiator.app.db;

/**
 * Database value types.
 * 
 * @author michaelmartak
 *
 */
public enum DataType {

    DATE, VARCHAR, INTEGER, BOOLEAN;

    public static boolean isMax(DataType dataType) {
        switch (dataType) {
        case VARCHAR:
            return true;
        default:
            return false;
        }
    }

}
