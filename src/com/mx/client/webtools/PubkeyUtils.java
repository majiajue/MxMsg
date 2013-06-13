package com.mx.client.webtools;

import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.RSAPrivateCrtKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

public class PubkeyUtils {
	public static final String PKCS8_START = "-----BEGIN PRIVATE KEY-----";
	public static final String PKCS8_END = "-----END PRIVATE KEY-----";
	public static final String KEY_TYPE_RSA = "RSA";
	// Size in bytes of salt to use.
	private static final int SALT_SIZE = 8;

	// Number of iterations for password hashing. PKCS#5 recommends 1000
	private static final int ITERATIONS = 1000;

	public static String formatKey(Key key) {
		String algo = key.getAlgorithm();
		String fmt = key.getFormat();
		byte[] encoded = key.getEncoded();
		return "Key[algorithm=" + algo + ", format=" + fmt + ", bytes=" + encoded.length + "]";
	}

	public static String describeKey(Key key, boolean encrypted) {
		String desc = null;
		if (key instanceof RSAPublicKey) {
			int bits = ((RSAPublicKey) key).getModulus().bitLength();
			desc = "RSA " + String.valueOf(bits) + "-bit";
		} else if (key instanceof DSAPublicKey) {
			desc = "DSA 1024-bit";
		} else {
			desc = "Unknown Key Type";
		}

		if (encrypted)
			desc += " (encrypted)";

		return desc;
	}

	public static byte[] sha256(byte[] data) throws NoSuchAlgorithmException {
		return MessageDigest.getInstance("SHA-256").digest(data);
	}

	public static byte[] cipher(int mode, byte[] data, byte[] secret) throws NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		SecretKeySpec secretKeySpec = new SecretKeySpec(sha256(secret), "AES");
		Cipher c = Cipher.getInstance("AES");
		c.init(mode, secretKeySpec);
		return c.doFinal(data);
	}

	public static byte[] encrypt(byte[] cleartext, String secret) throws CryptorException {
		byte[] salt = new byte[SALT_SIZE];

		byte[] ciphertext = AESEncryptor.encrypt(salt, ITERATIONS, secret, cleartext);

		byte[] complete = new byte[salt.length + ciphertext.length];

		System.arraycopy(salt, 0, complete, 0, salt.length);
		System.arraycopy(ciphertext, 0, complete, salt.length, ciphertext.length);

		Arrays.fill(salt, (byte) 0x00);
		Arrays.fill(ciphertext, (byte) 0x00);

		return complete;
	}

	public static byte[] decrypt(byte[] complete, String secret) throws CryptorException {
		try {
			byte[] salt = new byte[SALT_SIZE];
			byte[] ciphertext = new byte[complete.length - salt.length];

			System.arraycopy(complete, 0, salt, 0, salt.length);
			System.arraycopy(complete, salt.length, ciphertext, 0, ciphertext.length);

			return AESEncryptor.decrypt(salt, ITERATIONS, secret, ciphertext);
		} catch (CryptorException e) {
			// LOG.d("decrypt", "Could not decrypt with new method", e);
			// We might be using the old encryption method.
			try {
				return cipher(Cipher.DECRYPT_MODE, complete, secret.getBytes());
			} catch (InvalidKeyException ex) {
				throw new CryptorException(ex);
			} catch (NoSuchAlgorithmException ex) {
				throw new CryptorException(ex);
			} catch (NoSuchPaddingException ex) {
				throw new CryptorException(ex);
			} catch (IllegalBlockSizeException ex) {
				throw new CryptorException(ex);
			} catch (BadPaddingException ex) {
				throw new CryptorException(ex);
			}
		}
	}

	public static byte[] getEncodedPublic(PublicKey pk) {
		return new X509EncodedKeySpec(pk.getEncoded()).getEncoded();
	}

	public static byte[] getEncodedPrivate(PrivateKey pk) {
		return new PKCS8EncodedKeySpec(pk.getEncoded()).getEncoded();
	}

	public static byte[] getEncodedPrivate(PrivateKey pk, String secret) throws CryptorException {
		if (secret.length() > 0)
			return encrypt(getEncodedPrivate(pk), secret);
		else
			return getEncodedPrivate(pk);
	}

	public static PrivateKey decodePrivate(byte[] encoded, String keyType) throws NoSuchAlgorithmException,
			InvalidKeySpecException {
		PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(encoded);
		KeyFactory kf = KeyFactory.getInstance(keyType);
		return kf.generatePrivate(privKeySpec);
	}

	public static PrivateKey decodePrivate(byte[] encoded, String keyType, String secret) throws CryptorException {
		try {
			if (secret != null && secret.length() > 0)
				return decodePrivate(decrypt(encoded, secret), keyType);
			else
				return decodePrivate(encoded, keyType);
		} catch (NoSuchAlgorithmException e) {
			throw new CryptorException("NoSuchAlgorithmException");
		} catch (InvalidKeySpecException e) {
			throw new CryptorException("InvalidKeySpecException");
		}
	}

	public static PublicKey decodePublic(byte[] encoded, String keyType) throws NoSuchAlgorithmException,
			InvalidKeySpecException {
		X509EncodedKeySpec pubKeySpec = new X509EncodedKeySpec(encoded);
		KeyFactory kf = KeyFactory.getInstance(keyType);
		return kf.generatePublic(pubKeySpec);
	}

	public static PublicKey decodePublicKey(KeySpec spec, String KeyType) throws NoSuchAlgorithmException,
			InvalidKeySpecException {
		RSAPublicKeySpec pubkeyspec = (RSAPublicKeySpec) spec;
		KeyFactory kf = KeyFactory.getInstance(KeyType);
		return kf.generatePublic(pubkeyspec);
	}

	public static KeyPair recoverKeyPair(byte[] encoded) throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeySpec privKeySpec = new PKCS8EncodedKeySpec(encoded);
		KeySpec pubKeySpec;

		PrivateKey priv;
		PublicKey pub;
		KeyPair pair;
		KeyFactory kf;
		try {
			kf = KeyFactory.getInstance(KEY_TYPE_RSA);
			priv = kf.generatePrivate(privKeySpec);

			pubKeySpec = new RSAPublicKeySpec(((RSAPrivateCrtKey) priv).getModulus(),
					((RSAPrivateCrtKey) priv).getPublicExponent());

			pub = kf.generatePublic(pubKeySpec);
			pair = new KeyPair(pub, priv);
		} catch (ClassCastException e) {
			pair = null;
		}

		return pair;
	}

}
