package org.oaktownrpg.jgladiator.app;

import javax.crypto.spec.SecretKeySpec;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for application cryptography
 * 
 * @author michaelmartak
 *
 */
public class AppCryptographyTest {

    private static final String TOKEN = "H1CvLE1E3R5FInNS";

    /**
     * Generates a default static key from the provided TOKEN bytes
     * 
     * @return a static key spec
     */
    private SecretKeySpec defaultKey() {
        return new SecretKeySpec(TOKEN.getBytes(), AppCryptography.ALGORITHM);
    }

    /**
     * Tests that encryption works using the MAC address-based key spec
     * 
     * @throws Exception
     */
    @Test
    public void testMacKey() throws Exception {
        final String string = "hello, World!99";
        final AppCryptography crypto = new AppCryptography();
        final String encrypted = crypto.encrypt(string);
        Assert.assertEquals(string, crypto.decrypt(encrypted));
    }

    /**
     * Tests that encryption works using a static key spec
     * 
     * @throws Exception
     */
    @Test
    public void testEncrypt() throws Exception {
        String foo = new AppCryptography().encrypt(defaultKey(), "abcdefg");
        Assert.assertEquals("adAgZMHG1HQt2s5P7Iliug==", foo);
    }

    /**
     * Tests that decryption works using a static key spec
     * 
     * @throws Exception
     */
    @Test
    public void testDecrypt() throws Exception {
        String foo = new AppCryptography().decrypt(defaultKey(), "adAgZMHG1HQt2s5P7Iliug==");
        Assert.assertEquals("abcdefg", foo);
    }

    /**
     * Tests encryption of a long-ish string using a static key spec
     * 
     * @throws Exception
     */
    @Test
    public void testLongString() throws Exception {
        String longString = "Insidious came into English in the early 16th century, initially with the meaning of “awaiting a chance to entrap, treacherous.” The word may be traced to the Latin insidiae, meaning “ambush.” Additional meanings of insidious include “having a gradual and cumulative effect” and “developing so gradually as to be well established before becoming apparent” (said of a disease).\n"
                + "\n";
        AppCryptography crypto = new AppCryptography();
        SecretKeySpec key = defaultKey();
        Assert.assertEquals(longString, crypto.decrypt(key, crypto.encrypt(key, longString)));
    }

}
