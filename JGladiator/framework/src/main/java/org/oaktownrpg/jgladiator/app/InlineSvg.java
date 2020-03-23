/**
 * 
 */
package org.oaktownrpg.jgladiator.app;

import java.util.Base64;

/**
 * Object that contains inline SVG.
 * 
 * @author michaelmartak
 *
 */
public interface InlineSvg {

    /**
     * Convenience method to decode a Base64 encoded SVG
     * 
     * @param encoded the encoded SVG
     * @return a decoded SVG content string
     */
    public static String decode(String encoded) {
        return new String(Base64.getDecoder().decode(encoded.getBytes()));
    }

    /**
     * Returns a Base64 encoded SVG
     * 
     * @return an SVG string
     */
    String getEncodedSvg();

    /**
     * Returns the representation of this object as an SVG.
     * 
     * @return an SVG string
     */
    default String getSvg() {
        return decode(getEncodedSvg());
    }

}
