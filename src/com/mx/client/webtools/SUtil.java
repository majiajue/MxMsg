package com.mx.client.webtools;

import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;




public class SUtil {
	public static final String HMAC_SHA256 = "HmacSHA256";
	public static final String COMPLEMENT = "ghjklGHJKghjklGHJKLGYUJGYHGhbgyujhgyujHBGHHJVFGFTGyhgyujhbghyujhBGHUJHGYUHBGYHBVGHBVGYHbghyghygHBGYUJHGUJHgyuhgysdffdfdfdffdfefd";
	/**
	 * SingletonHolder is loaded on the first execution of
	 * Singleton.getInstance() or the first access to SingletonHolder.INSTANCE,
	 * not before.
	 */
	private static class SingletonHolder {
		public static final SUtil INSTANCE = new SUtil();
	}

	public static SUtil getInstance() {
		return SingletonHolder.INSTANCE;
	}
	
	public static String toSHAString64(String str, String encName) {
		if (str.length() < 64) {
			str = str + COMPLEMENT.substring(0, 64 - str.length());
			//LOG.v("pwd2", str);
		}
		return Encrypt(str, encName);
	}

	/**
	 * 对字符串加密,加密算法使用MD5,SHA-1,SHA-256,默认使用SHA-256
	 * 
	 * @param strSrc
	 *            要加密的字符串
	 * @param encName
	 *            加密类型
	 * @return
	 */
	public static String Encrypt(String strSrc, String encName) {
		MessageDigest md = null;
		String strDes = null;

		byte[] bt = strSrc.getBytes();
		try {
			if (encName==null) {
				encName = "SHA-256";
			}
			md = MessageDigest.getInstance(encName);
			md.update(bt);
			strDes = bytes2Hex(md.digest()); // to HexString
		} catch (NoSuchAlgorithmException e) {
			return null;
		}
		return strDes;
	}

	public static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}
	
	public static byte[] Hmac(String skey, String str) {
		SecretKey sk = new SecretKeySpec(skey.getBytes(), HMAC_SHA256);
		String temp = sk.getAlgorithm();
		Mac mac = null;
		try {
			mac = Mac.getInstance(sk.getAlgorithm());
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			mac.init(sk);
		} catch (InvalidKeyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		byte[] result = mac.doFinal(str.getBytes());
		return result;
	}
}
