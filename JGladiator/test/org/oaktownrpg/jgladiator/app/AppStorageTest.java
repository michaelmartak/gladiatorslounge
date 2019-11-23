package org.oaktownrpg.jgladiator.app;

import java.util.function.Supplier;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test application storage
 * 
 * @author michaelmartak
 *
 */
public class AppStorageTest {

	/**
	 * Tests that the cryptography store can save and load
	 */
	@Test
	public void testLoadCryptographyStore() {
		final AppStorage storage = new AppStorage();
		final String key = "testCredentials";
		final String passphrase = "abcdefg";
		storage.storeCredentials(key, passphrase);
		final Supplier<String> value = storage.fetchCredentials(key);
		Assert.assertEquals(passphrase, value.get());
	}
}
