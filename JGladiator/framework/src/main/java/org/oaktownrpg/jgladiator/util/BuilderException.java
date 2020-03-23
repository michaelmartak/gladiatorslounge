/**
 * 
 */
package org.oaktownrpg.jgladiator.util;

/**
 * Checked exception while using a builder pattern.
 * 
 * @author michaelmartak
 *
 */
public class BuilderException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 8889779668459449333L;

    /**
     * @param message
     */
    public BuilderException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public BuilderException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public BuilderException(String message, Throwable cause) {
        super(message, cause);
    }

}
