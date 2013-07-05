package com.mx.client.webtools;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.apache.commons.codec.binary.Base64;

public class RSAEncryptor {
	private RSAEncryptor() {
	}

	/**
	 * SingletonHolder is loaded on the first execution of
	 * Singleton.getInstance() or the first access to SingletonHolder.INSTANCE,
	 * not before.
	 */
	private static class SingletonHolder {
		public static final RSAEncryptor INSTANCE = new RSAEncryptor();
	}

	public static RSAEncryptor getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private static final String ALGORITHM = "RSA/ECB/NoPadding";

	// private static final String ALGORITHM = "RSA/ECB/PKCS1Padding";

	public static int getBlockSize(int defaultLength, PublicKey key) {
		if (defaultLength > 0) {
			return defaultLength;
		}
		int length = key.getEncoded().length;
		if (length < 200) {
			return 128;
		} else if (length < 300) {
			return 256;
		} else {
			return 512;
		}
	}

	private Cipher getEncryptCipher(String keynick) throws CryptorException {

		PublicKey key = KeyManager.getInstance().getEncryptKey(keynick);
		System.out.println(key.toString());

		Cipher cipher;
		System.out.println("RSA:" + key.getFormat());
		System.out.println("RSA:" + key.getEncoded().length);

		try {
			cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key);

			System.out.println("RSA:size:" + getBlockSize(cipher.getBlockSize(), key));
			System.out.println("RSA:algorithm:" + cipher.getAlgorithm());
			System.out.println("RSA:algorithm:" + cipher.getBlockSize());
			System.out.println("RSA good");
			return cipher;
		} catch (NoSuchAlgorithmException e) {
			System.out.println("RSA NoSuchAlgorithmException");
			throw new CryptorException(e);
		} catch (NoSuchPaddingException e) {
			System.out.println("RSA NoSuchPaddingException");
			throw new CryptorException(e);
		} catch (InvalidKeyException e) {
			System.out.println("RSA InvalidKeyException");
			throw new CryptorException(e);
		}
	}

	private Cipher getDecryptCipher(String keynick) throws CryptorException {

		PrivateKey key = KeyManager.getInstance().getDecryptKey(keynick);
		PublicKey key1 = KeyManager.getInstance().getEncryptKey(keynick);
		System.out.println("private key length:" + key.getEncoded().length);
		System.out.println("private:" + Base64.encodeBase64String(key.getEncoded()));
		System.out.println("public:" + Base64.encodeBase64String(key1.getEncoded()));
		Cipher cipher;
		try {
			cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.DECRYPT_MODE, key);
		} catch (NoSuchAlgorithmException e) {
			throw new CryptorException(e);
		} catch (NoSuchPaddingException e) {
			throw new CryptorException(e);
		} catch (InvalidKeyException e) {
			throw new CryptorException(e);
		}

		return cipher;
	}

	/**
	 * 根据密钥名找到解密密钥后，对BASE64编码格式的数据进行解密
	 * 
	 * @param dataBase64
	 * @param keynick
	 * @return
	 * @throws Exception
	 */
	public byte[] decryptBase64String(String dataBase64, String keynick) throws CryptorException {
		boolean isBase64 = false;
		try {
			// isBase64 = Base64.isArrayByteBase64(dataBase64.getBytes());
			isBase64 = Base64.isBase64(dataBase64.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (isBase64) {
			return decrypt(Base64.decodeBase64(dataBase64.getBytes()), keynick);
		} else {
			return dataBase64.getBytes();
		}
	}

	public byte[] myDecryptBase64String(String dataBase64) throws CryptorException, InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException {
		boolean isBase64 = false;
		try {
			// isBase64 = Base64.isArrayByteBase64(dataBase64.getBytes());
			isBase64 = Base64.isBase64(dataBase64.getBytes());
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (isBase64) {
			System.out.println("确实是base64编码，开始解密");
			return myDecrypt(Base64.decodeBase64(dataBase64.getBytes()));
		} else {
			return dataBase64.getBytes();
		}
	}

	/**
	 * 根据密钥名找到解密密钥后，对数据进行解密
	 * 
	 * @param data
	 * @param keynick
	 * @return
	 * @throws Exception
	 */
	public synchronized byte[] decrypt(byte[] data, String keynick) throws CryptorException {
		// 对数据解密
		Cipher cipher = getDecryptCipher(keynick);
		PublicKey key = KeyManager.getInstance().getEncryptKey(keynick);
		int blockSize = getBlockSize(cipher.getBlockSize(), key);
<<<<<<< HEAD
=======
		System.out.println("decrypt blockSize:" + blockSize);
		System.out.println("keynick:" + keynick);
>>>>>>> 5875410dce950b2e346c84bf736d7bb55f308f3b
		byte[] bBuffer = new byte[blockSize];
		int nLoop = data.length / blockSize;

		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			for (int i = 0; i < nLoop; i++) {
				System.arraycopy(data, i * blockSize, bBuffer, 0, blockSize);

				bos.write(cipher.doFinal(bBuffer));

			}
			return bos.toByteArray();
		} catch (IllegalBlockSizeException e) {
			throw new CryptorException(e);
		} catch (BadPaddingException e) {
			throw new CryptorException(e);
		} catch (IOException e) {
			throw new CryptorException(e);
		}

	}

	/**
	 * 根据密钥名找到加密密钥后，对数据进行加密， 并且将加密结果用BASE64进行编码，返回编码后的字符串
	 * 
	 * @param data
	 * @param keynick
	 * @return
	 * @throws Exception
	 */
	public String encryptBase64Encode(byte[] data, String keynick) throws CryptorException {
		return new String(Base64.encodeBase64(encrypt(data, keynick)));
	}

	public String myEncryptBase64Encode(byte[] data) throws CryptorException, InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException {
		return new String(Base64.encodeBase64(myEncrypt(data)));
	}

	public synchronized byte[] myDecrypt(byte[] data) throws CryptorException, InvalidKeyException,
			NoSuchAlgorithmException, NoSuchPaddingException {
		// 对数据解密
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		PrivateKey privateKey = SConfig.getInstance().profile.getKeyPair().getPrivate();
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		PublicKey pubKey = SConfig.getInstance().profile.getKeyPair().getPublic();
		int blockSize = getBlockSize(cipher.getBlockSize(), pubKey);
		byte[] bBuffer = new byte[blockSize];
		int nLoop = data.length / blockSize;

		try {
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			for (int i = 0; i < nLoop; i++) {
				System.arraycopy(data, i * blockSize, bBuffer, 0, blockSize);

				bos.write(cipher.doFinal(bBuffer));

			}
			System.out.println("debug1");
			return bos.toByteArray();
		} catch (IllegalBlockSizeException e) {
			System.out.println("debug2");
			throw new CryptorException(e);
		} catch (BadPaddingException e) {
			System.out.println("debug3");
			throw new CryptorException(e);
		} catch (IOException e) {
			System.out.println("debug4");
			throw new CryptorException(e);
		}

	}

	public byte[] myEncrypt(byte[] data) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
			CryptorException {
		PublicKey pubkey = SConfig.getInstance().profile.getKeyPair().getPublic();
		Cipher cipher = Cipher.getInstance(ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, pubkey);
		int blockSize = getBlockSize(cipher.getBlockSize(), pubkey);
		byte[] bBuffer = new byte[blockSize];
		int nLoop = data.length / blockSize;
		int nRemaining = data.length - nLoop * blockSize;

		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {
			for (int i = 0; i < nLoop; i++) {
				System.arraycopy(data, i * blockSize, bBuffer, 0, blockSize);

				bos.write(cipher.doFinal(bBuffer));
			}

			if (nRemaining > 0) {
				System.arraycopy(data, nLoop * blockSize, bBuffer, 0, nRemaining);
				bos.write(cipher.doFinal(bBuffer, 0, nRemaining));
			}
		} catch (IllegalBlockSizeException e) {
			throw new CryptorException(e);
		} catch (BadPaddingException e) {
			throw new CryptorException(e);
		} catch (IOException e) {
			throw new CryptorException(e);
		}
		return bos.toByteArray();
	}

	/**
	 * 根据密钥名找到加密密钥后，对数据进行加密
	 * 
	 * @param data
	 * @param keynick
	 * @return
	 * @throws Exception
	 */
	public synchronized byte[] encrypt(byte[] data, String keynick) throws CryptorException {
		// 对数据加密
		System.out.println("keynick---" + keynick);
		PublicKey key = KeyManager.getInstance().getEncryptKey(keynick);
		Cipher cipher = getEncryptCipher(keynick);
		int blockSize = getBlockSize(cipher.getBlockSize(), key);
		byte[] bBuffer = new byte[blockSize];
		int nLoop = data.length / blockSize;
		int nRemaining = data.length - nLoop * blockSize;

		ByteArrayOutputStream bos = new ByteArrayOutputStream();

		try {
			for (int i = 0; i < nLoop; i++) {
				System.arraycopy(data, i * blockSize, bBuffer, 0, blockSize);

				bos.write(cipher.doFinal(bBuffer));
			}

			if (nRemaining > 0) {
				System.arraycopy(data, nLoop * blockSize, bBuffer, 0, nRemaining);
				bos.write(cipher.doFinal(bBuffer, 0, nRemaining));
			}
		} catch (IllegalBlockSizeException e) {
			throw new CryptorException(e);
		} catch (BadPaddingException e) {
			throw new CryptorException(e);
		} catch (IOException e) {
			throw new CryptorException(e);
		}
		return bos.toByteArray();
	}
}
