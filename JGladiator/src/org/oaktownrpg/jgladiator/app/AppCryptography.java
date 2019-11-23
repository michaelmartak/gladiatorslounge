/**
 * 
 */
package org.oaktownrpg.jgladiator.app;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Cryptography provided for the application.
 * 
 * @author michaelmartak
 *
 */
class AppCryptography {

	/**
	 * Encryption algorithm
	 */
	static final String ALGORITHM = "AES";
	/**
	 * String encoding
	 */
	private static final String ENCODING = "UTF8";

	/**
	 * 
	 */
	AppCryptography() {
	}

	/**
	 * Returns a secret keyspec using the local MAC hardware address.
	 * <p/>
	 * Ensures that the key used locally is unique to the machine on which it is
	 * encrypted.
	 * 
	 * @return a keyspec, never null
	 * @throws IOException
	 */
	SecretKeySpec macKey() throws IOException {
		final InetAddress ip = InetAddress.getLocalHost();
		final NetworkInterface network = NetworkInterface.getByInetAddress(ip);
		byte[] macAddress = network.getHardwareAddress();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < macAddress.length; i++) {
			sb.append(String.format("%02X", macAddress[i]));
		}
		while (sb.length() < 16) {
			sb.append("0");
		}
		return new SecretKeySpec(sb.toString().getBytes(), ALGORITHM);
	}

	/**
	 * Encrypts the given passphrase
	 * 
	 * @param passphrase the passphrase to encrypt
	 * @return an encrypted, encoded string
	 * @throws InvalidKeyException
	 * @throws BadPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws IllegalBlockSizeException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public String encrypt(final String passphrase)
			throws InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException,
			IllegalBlockSizeException, UnsupportedEncodingException, IOException {
		return encrypt(macKey(), passphrase);
	}

	/**
	 * Encrypts the given passphrase using the given key specification.
	 * <p/>
	 * Do not call this method unless you are providing a key spec! Key specs should
	 * be either unique or hard to falsify.
	 * 
	 * @param key        the key specification, never null
	 * @param passphrase
	 * @return
	 * @throws BadPaddingException
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws UnsupportedEncodingException
	 */
	String encrypt(final SecretKeySpec key, final String passphrase)
			throws BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			IllegalBlockSizeException, UnsupportedEncodingException {
		final Cipher c = Cipher.getInstance(ALGORITHM);
		c.init(Cipher.ENCRYPT_MODE, key);
		final byte[] encrypted = c.doFinal(passphrase.getBytes());
		final byte[] encoded = Base64.getEncoder().encode(encrypted);
		return new String(encoded, ENCODING);
	}

	/**
	 * Decrypts the given encoded message
	 * 
	 * @param encoded the encoded, encrypted string
	 * @return a decrypted message, in raw form
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws IOException
	 */
	public String decrypt(final String encoded) throws NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidKeyException, IllegalBlockSizeException, BadPaddingException, IOException {
		return decrypt(macKey(), encoded);
	}

	/**
	 * Decrypts the given encoded message using the provided key spec.
	 * 
	 * @param key     the key specification, never null
	 * @param encoded the encoded, encrypted message
	 * @return a decrypted passphrase
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws UnsupportedEncodingException
	 */
	String decrypt(final SecretKeySpec key, final String encoded)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, UnsupportedEncodingException {
		byte[] decoded = Base64.getDecoder().decode(encoded.getBytes());
		Cipher c = Cipher.getInstance(ALGORITHM);
		c.init(Cipher.DECRYPT_MODE, key);
		byte[] decrypted = c.doFinal(decoded);
		return new String(decrypted, ENCODING);
	}

}
