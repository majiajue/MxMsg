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

	private static final String ALGORITHM = "RSA";



	private Cipher getEncryptCipher(String keynick) throws CryptorException {

		PublicKey key = KeyManager.getInstance().getEncryptKey(keynick);
		System.out.println(key.toString());

		Cipher cipher;
		try {
			cipher = Cipher.getInstance(ALGORITHM);
			cipher.init(Cipher.ENCRYPT_MODE, key);
			System.out.println("size==="+cipher.getBlockSize());
		} catch (NoSuchAlgorithmException e) {
			throw new CryptorException(e);
		} catch (NoSuchPaddingException e) {
			throw new CryptorException(e);
		} catch (InvalidKeyException e) {
			throw new CryptorException(e);
		}

		return cipher;
	}

	private Cipher getDecryptCipher(String keynick) throws CryptorException {

		PrivateKey key = KeyManager.getInstance().getDecryptKey(keynick);


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
	 * ������Կ���ҵ�������Կ�󣬶�BASE64�����ʽ�����ݽ��н���
	 * 
	 * @param dataBase64
	 * @param keynick
	 * @return
	 * @throws Exception
	 */
	public byte[] decryptBase64String(String dataBase64, String keynick) throws CryptorException {
		boolean isBase64 = false;
		try {
			isBase64 = Base64.isArrayByteBase64(dataBase64.getBytes());
		} catch (Exception e) {

		}
		if (isBase64) {
			return decrypt(Base64.decodeBase64(dataBase64.getBytes()), keynick);
		} else
			return dataBase64.getBytes();
	}

	/**
	 * ������Կ���ҵ�������Կ�󣬶����ݽ��н���
	 * 
	 * @param data
	 * @param keynick
	 * @return
	 * @throws Exception
	 */
	public synchronized byte[] decrypt(byte[] data, String keynick) throws CryptorException {
		// �����ݽ���
		Cipher cipher = getDecryptCipher(keynick);
		int blockSize = cipher.getBlockSize();
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
	 * ������Կ���ҵ�������Կ�󣬶����ݽ��м��ܣ� ���ҽ����ܽ����BASE64���б��룬���ر������ַ���
	 * 
	 * @param data
	 * @param keynick
	 * @return
	 * @throws Exception
	 */
	public String encryptBase64Encode(byte[] data, String keynick) throws CryptorException {
		return new String(Base64.encodeBase64(encrypt(data, keynick)));
	}

	/**
	 * ������Կ���ҵ�������Կ�󣬶����ݽ��м���
	 * 
	 * @param data
	 * @param keynick
	 * @return
	 * @throws Exception
	 */
	public synchronized byte[] encrypt(byte[] data, String keynick) throws CryptorException {
		// �����ݼ���
		System.out.println("keynick---"+keynick);
		Cipher cipher = getEncryptCipher(keynick);
		int blockSize = cipher.getBlockSize();
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
